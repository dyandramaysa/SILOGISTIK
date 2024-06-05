package apap.ti.silogistik2106632232.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import apap.ti.silogistik2106632232.dto.GudangMapper;
import apap.ti.silogistik2106632232.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.Gudang;
import apap.ti.silogistik2106632232.model.GudangBarang;
import apap.ti.silogistik2106632232.service.BarangService;
import apap.ti.silogistik2106632232.service.GudangBarangService;
import apap.ti.silogistik2106632232.service.GudangService;
import jakarta.validation.Valid;

@Controller
public class GudangController {
    @Autowired
    GudangService gudangService;

    @Autowired
    GudangMapper gudangMapper;

    @Autowired
    GudangBarangService gudangBarangService;

    @Autowired
    BarangService barangService;

    @GetMapping("/gudang")
    public String listGudangDataTables (Model model) {
        //Mendapatkan semua gudang
        List<Gudang> listGudang = gudangService.getAllGudang();

        model.addAttribute("isGudangActive", true);
        model.addAttribute("listGudang", listGudang);
        return "viewall-gudang-datatables";
    }

    @GetMapping("/gudang/{id}")
    public String detailGudang(@PathVariable("id") Long id, Model model) {
        //Mendapatkan gudang melalui idGudang
        var gudang = gudangService.getGudangById(id);

        // Mengonversi gudang menjadi ReadGudangResponseDTO
        var gudangResponseDTO = gudangMapper.gudangToReadGudangResponseDTO(gudang);

        //Mendapatkan daftar gudangbarang 
		List<GudangBarang> listGudangbarang = gudangBarangService.getAllGudangBarang();
		
		// Filter gudangbarang berdasarkan gudang yang sama dengan gudang saat ini
		listGudangbarang = listGudangbarang.stream()
        .filter(barang -> barang.getGudang().getId().equals(id))
        .collect(Collectors.toList());

        // Membuat Map untuk menyimpan objek Barang dan stoknya
        Map<Barang, Integer> barangStokMap = new HashMap<>();
        for (GudangBarang gudangBarang : listGudangbarang) {
            barangStokMap.put(gudangBarang.getBarang(), gudangBarang.getStok());
        }

        model.addAttribute("isDaftarGudangActive", true);
        model.addAttribute("isGudangActive", true);
        model.addAttribute("gudang", gudangResponseDTO);
        model.addAttribute("listBarang", barangStokMap);

        return "view-gudang";
    }

    @GetMapping("/gudang/cari-barang")
    public String CariBarang(@RequestParam(name = "sku", required = false) String skuBarang, Model model) {
        var barang = barangService.getBarangBySKU(skuBarang);

        if (skuBarang != null && !skuBarang.isBlank()) {
            model.addAttribute("listGudangBarang", barang.getListGudangBarang());
        }

        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        model.addAttribute("listBarang", listBarang);
        model.addAttribute("skuBarang", skuBarang);
        model.addAttribute("isCariBarangActive", true);
        model.addAttribute("isGudangActive", true);


        return "cari-barang";
    }

    @GetMapping("/gudang/{id}/restock-barang")
    public String formRestockGudang(@PathVariable("id") Long id, Model model) {
        //Mendapatkan gudang melalui idGudang
        var gudang = gudangService.getGudangById(id);

        if (gudang != null) {
            //Memindahkan data gudang ke DTO untuk selanjutnya diubah di form pengguna
            var gudangDTO = gudangMapper.gudangToUpdateGudangRequestDTO(gudang);
            List<GudangBarang> listGudangBarang = gudangDTO.getListGudangBarang();
            model.addAttribute("gudangDTO", gudangDTO);
            model.addAttribute("listGudangBarang", listGudangBarang);
        }
        
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        model.addAttribute("listBarang", listBarang);
        model.addAttribute("isGudangActive", true);

        return "form-restock-barang";
 
    }

    @PostMapping(value = "/gudang/{id}/restock-barang", params = {"addRow"})
    public String addRowStockBarang(
        @ModelAttribute UpdateGudangRequestDTO updateGudangRequestDTO, Model model
    ) {
        if (updateGudangRequestDTO.getListGudangBarang() == null || updateGudangRequestDTO.getListGudangBarang().size() == 0) {
            updateGudangRequestDTO.setListGudangBarang(new ArrayList<>());
        }

        // Memasukkan GudangBarang baru (kosong) ke list, untuk dirender sebagai row baru.
        updateGudangRequestDTO.getListGudangBarang().add(new GudangBarang());
        
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();
        List<GudangBarang> listGudangBarang = updateGudangRequestDTO.getListGudangBarang();

        // Kirim list gudangBarang untuk menjadi pilihan pada dropdown.
        model.addAttribute("gudangDTO", updateGudangRequestDTO);
        model.addAttribute("listGudangBarang", listGudangBarang);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("isGudangActive", true);

        return "form-restock-barang";
    }

    @PostMapping("/gudang/{idGudang}/restock-barang")
    public String restockGudang(@Valid @ModelAttribute UpdateGudangRequestDTO gudangDTO, BindingResult bindingResult, Model model) {
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

        var gudangFromDTO = gudangMapper.updateGudangRequestDTOToGudang(gudangDTO);
        gudangService.restockGudang(gudangFromDTO);
        
        model.addAttribute("id", gudangFromDTO.getId());
        model.addAttribute("namaGudang", gudangFromDTO.getNama());

        return "success-update-gudang";
    }
}
