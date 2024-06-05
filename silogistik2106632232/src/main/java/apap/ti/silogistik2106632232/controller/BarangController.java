package apap.ti.silogistik2106632232.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import apap.ti.silogistik2106632232.dto.BarangMapper;
import apap.ti.silogistik2106632232.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106632232.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.Gudang;
import apap.ti.silogistik2106632232.model.GudangBarang;
import apap.ti.silogistik2106632232.service.BarangService;
import apap.ti.silogistik2106632232.service.GudangBarangService;
import jakarta.validation.Valid;

@Controller
public class BarangController {
    @Autowired
    BarangService barangService;

    @Autowired
    BarangMapper barangMapper;

    @Autowired
    GudangBarangService gudangBarangService;

    @GetMapping("/barang")
    public String listBarangDataTables (Model model) {
        //Mendapatkan semua Barang
        List<GudangBarang> listGudangBarang = gudangBarangService.getAllGudangBarang();
        List<Barang> listBarang = barangService.getAllBarangNotInGudang();
        
        Map<Barang, Integer> barangStokMap = new HashMap<>();
        for (GudangBarang gudangBarang : listGudangBarang) {
            List<GudangBarang> listGudangBarang2 = gudangBarang.getBarang().getListGudangBarang();
            Integer totalStok = 0;
            for (GudangBarang gudangBarang2 : listGudangBarang2) {
                totalStok += gudangBarang2.getStok();
            }
            barangStokMap.put(gudangBarang.getBarang(), totalStok);
        }
        for (Barang barang : listBarang) {
            Integer totalStok = 0;
            barangStokMap.put(barang, totalStok);
        }
    
        model.addAttribute("isBarangActive", true);
        model.addAttribute("listBarang", barangStokMap);

        return "viewall-barang-datatables";
        
    }

    @GetMapping("/barang/tambah")
	public String formTambahBarang(Model model){
		//Membuat barangDTO baru untuk diisi di form
		var barangDTO = new CreateBarangRequestDTO();

		//Menambah penerbitDTO ke model thymeleaf
		model.addAttribute("barangDTO", barangDTO);
        model.addAttribute("isBarangActive", true);

		return "form-tambah-barang";
	}

    @PostMapping("/barang/tambah")
	public String addPenerbit(@Valid @ModelAttribute CreateBarangRequestDTO barangDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Maaf, barang tidak dapat ditambahkan karena kesalahan input berikut: <br>");
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); // Tambahkan pesan kesalahan dengan newline
            }
            model.addAttribute("errorMessage", errorMessage.toString());
        
            return "error-view";
        }

        //Melakukan validasi merk barang yang baru dibuat
        if (barangService.isMerkExist(barangDTO.getMerk())) {
            var errorMessage = "Maaf, merk barang sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

		//Membuat object barang dengan data yang berasal dari DTO
        var barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);

        var sku = barangService.generateSKU(barang.getTipeBarang());
        
        barang.setSku(sku);

		//Memanggil Service createPenerbit
        barangService.saveBarang(barang);      

		//Menambah penerbit ke model thymeleaf
		model.addAttribute("barang", barang);

		return "success-tambah-barang";
	}

    @GetMapping("/barang/{idBarang}")
    public String detailBarang(@PathVariable("idBarang") String sku, Model model) {
        //Mendapatkan barang melalui sku
        var barang = barangService.getBarangBySKU(sku);

        // Mengonversi barang menjadi ReadBarangResponseDTO
        var barangResponseDTO = barangMapper.barangToReadBarangResponseDTO(barang);

        //Mendapatkan daftar gudangbarang 
		List<GudangBarang> listGudangbarang = barangResponseDTO.getListGudangBarang();
		
        // Membuat Map untuk menyimpan objek Barang dan stoknya
        Map<Gudang, Integer> barangStokMap = new HashMap<>();
        // Membuat variabel totalStok untuk menyimpan totalStok dari seluruh gudang
        Integer totalStok = 0;
        for (GudangBarang gudangBarang : listGudangbarang) {
            barangStokMap.put(gudangBarang.getGudang(), gudangBarang.getStok());
            totalStok += gudangBarang.getStok();
        }

        model.addAttribute("isBarangActive", true);
        model.addAttribute("barang", barangResponseDTO);
        model.addAttribute("totalStok", totalStok);
        model.addAttribute("listGudang", barangStokMap);

        return "view-barang";
    }

    @GetMapping("/barang/{idBarang}/ubah")
	public String formUpdateBarang(@PathVariable("idBarang") String sku, Model model) {
        //Mendapatkan barang melalui idBarang
        var barang = barangService.getBarangBySKU(sku);

        if (barang != null) {
            
            //Memindahkan data barang ke DTO untuk selanjutnya diubah di form pengguna
            var barangDTO = barangMapper.barangToUpdateBarangRequestDTO(barang);
            model.addAttribute("barangDTO", barangDTO);
        }

        model.addAttribute("isBarangActive", true);

		return "form-update-barang";
	}

    @PostMapping("/barang/{idBarang}/ubah")
    public String ubahBarang(@Valid @ModelAttribute UpdateBarangRequestDTO barangDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Maaf, gudang tidak dapat di restock karena kesalahan input berikut: <br>");
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); // Tambahkan pesan kesalahan dengan newline
            }
            model.addAttribute("errorMessage", errorMessage.toString());
        
            return "error-view";
        }

        //Melakukan validasi merk barang yang baru dibuat
        if (barangService.isMerkExist(barangDTO.getMerk(), barangDTO.getSku())) {
            var errorMessage = "Maaf, merk barang sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

        var barangFromDTO = barangMapper.updateBarangRequestDTOToBarang(barangDTO);
        barangService.ubahBarang(barangFromDTO);
        
        model.addAttribute("sku", barangFromDTO.getSku());
        model.addAttribute("merk", barangFromDTO.getMerk());

        return "success-update-barang";
    }
}
