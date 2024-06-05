package apap.ti.silogistik2106632232.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import apap.ti.silogistik2106632232.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106632232.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.Karyawan;
import apap.ti.silogistik2106632232.model.PermintaanPengiriman;
import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106632232.repository.PermintaanPengirimanDb;
import apap.ti.silogistik2106632232.service.BarangService;
import apap.ti.silogistik2106632232.service.KaryawanService;
import apap.ti.silogistik2106632232.service.PermintaanPengirimanService;
import jakarta.validation.Valid;

@Controller
public class PermintaanPengirimanController {
    @Autowired
    PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    PermintaanPengirimanMapper permintaanPengirimanMapper;

    @Autowired
    BarangService barangService;

    @Autowired
    KaryawanService karyawanService;

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @GetMapping("/permintaan-pengiriman")
    public String listPermintaanPengirimanDataTables (Model model) {
        //Mendapatkan semua permintaan pengiriman
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanService.getAllPermintaanPengiriman();

        model.addAttribute("isPermintaanPengirimanActive", true);
        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);
        return "viewall-permintaan-pengiriman-datatables";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}")
    public String detailPermintaanPengiriman(@PathVariable("idPermintaanPengiriman") Long idPermintaanPengiriman, Model model ) {
        //Mendapatkan Permintaan Pengiriman berdasarkan ID
        var permintaanPengiriman = permintaanPengirimanService.getPermintaanPengirimanById(idPermintaanPengiriman);

        //Mengonversi Permintaan Pengiriman menjadi ReadPermintaanPengirimanResponseDTO
        var permintaanPengirimanDTO = permintaanPengirimanMapper.permintaanPengirimanToReadPermintaanPengirimanResponseDTO(permintaanPengiriman);

        //Mendapatkan daftar Permintaan Pengiriman Barang
        List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = permintaanPengirimanDTO.getListPermintaanPengirimanBarang();

        //Membuat Map untuk menyimpan objek Barang dan Stoknya
        Map<PermintaanPengirimanBarang, Long> barangPesanMap = new HashMap<>();
        for (PermintaanPengirimanBarang ppb : listPermintaanPengirimanBarang) {
            //kuantitas barang
            var kuantitas = ppb.getKuantitasPengiriman();
            //harga barang
            var hargaBarang = ppb.getBarang().getHargaBarang();
            //Total harga
            var totalHarga = kuantitas * hargaBarang;

            barangPesanMap.put(ppb, totalHarga);
        }
        model.addAttribute("isPermintaanPengirimanActive", true);
        model.addAttribute("pp", permintaanPengirimanDTO);
        model.addAttribute("barangPesanMap", barangPesanMap);

        return "view-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/tambah")
    public String formTambahPermintaanPengiriman(Model model) {
        //Membuat permintaanPengirimanDTO baru untuk diisi di form
        var permintaanPengirimanDTO = new CreatePermintaanPengirimanRequestDTO();

        List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        //Menambah permintaanPengirimanDTO ke model thymeleaf
        model.addAttribute("isPermintaanPengirimanActive", true);
        model.addAttribute("permintaanPengirimanDTO", permintaanPengirimanDTO);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listKaryawan", listKaryawan);

        // model.addAttribute("listPPB", listPPB);

        return "form-tambah-permintaan-pengiriman";
    }

    @PostMapping(value = "/permintaan-pengiriman/tambah", params = {"addRow"})
    public String addRowBuatPermintaanPengiriman(
        @ModelAttribute CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO, Model model
    ) {
        if (createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang() == null || 
            createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang().size() == 0) {
            createPermintaanPengirimanRequestDTO.setListPermintaanPengirimanBarang(new ArrayList<>());
        }

        // Memasukkan GudangBarang baru (kosong) ke list, untuk dirender sebagai row baru.
        createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang().add(new PermintaanPengirimanBarang());
        
        List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();
        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        // Kirim list gudangBarang untuk menjadi pilihan pada dropdown.
        model.addAttribute("isPermintaanPengirimanActive", true);
        model.addAttribute("permintaanPengirimanDTO", createPermintaanPengirimanRequestDTO);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listKaryawan", listKaryawan);
        return "form-tambah-permintaan-pengiriman";
    }

    @PostMapping("/permintaan-pengiriman/tambah")
    public String tambahPermintaanPengiriman(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Maaf, permintaan pengiriman tidak dapat dibuat karena kesalahan input berikut: <br>");
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); // Tambahkan pesan kesalahan dengan newline
            }
            model.addAttribute("errorMessage", errorMessage.toString());
        
            return "error-view";
        }

        try {
            var permintaanPengiriman = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(createPermintaanPengirimanRequestDTO);
            
            permintaanPengirimanService.buatPermintaanPengiriman(permintaanPengiriman);
    
            model.addAttribute("id", permintaanPengiriman.getId());
            model.addAttribute("nomorPengiriman", permintaanPengiriman.getNomorPengiriman());
            model.addAttribute("isPermintaanPengirimanActive", true);
    
            return "success-buat-permintaan-pengiriman";
        } catch (Exception e) {
            // Tangani exception
            model.addAttribute("errorMessage", "Jumlah kuantitas melebihi stok barang yang tersedia.");
            return "error-view";
        }
    }

    // Menambahkan fitur cancel permintaan berdasarkan id
    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}/cancel")  
    public String cancelPermintaan(@PathVariable("idPermintaanPengiriman") Long idPermintaanPengiriman, Model model) {
        var permintaanPengiriman = permintaanPengirimanService.getPermintaanPengirimanById(idPermintaanPengiriman);
        var waktuSekarang = LocalDateTime.now();
        var waktuPermintaan = permintaanPengiriman.getWaktuPermintaan();
        long selisihJam = ChronoUnit.HOURS.between(waktuPermintaan, waktuSekarang);

        // Batas waktu untuk pembatalan (24 jam)
        long batasWaktu = 24;

        if (selisihJam < batasWaktu) {
            permintaanPengirimanService.cancelPermintaan(permintaanPengiriman);

            model.addAttribute("nomorPengiriman", permintaanPengiriman.getNomorPengiriman());
            model.addAttribute("isPermintaanPengirimanActive", true);

            return "success-cancel-pengiriman";
        
        } else {
            model.addAttribute("errorMessage", "Maaf, permintaan pengiriman yang lebih dari 24 jam dibuat tidak bisa dibatalkan");
            model.addAttribute("isPermintaanPengirimanActive", true);

            return "error-view";
        }
    }

    // Menambahkan fitur filter permintaan pengiriman yang memuat barang tertentu
    @GetMapping("/filter-permintaan-pengiriman")
    public String cariPermintaanPengiriman(
        @RequestParam(name = "sku", required = false) String skuBarang,
        @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        Model model
    ) {
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (startDate != null) {
            startDateTime = startDate.atStartOfDay();
        }

        if (endDate != null) {
            endDateTime = endDate.atStartOfDay().plusDays(1); // Tambah 1 hari ke endDate
        }
        List<PermintaanPengiriman> listPermintaanPengiriman = new ArrayList<>();

        if (skuBarang != null && startDate != null && endDate != null) {
            // Dapatkan barang berdasarkan SKU
            Barang barang = barangService.getBarangBySKU(skuBarang);

            if (barang != null) {
                // Cari permintaan pengiriman yang memuat barang tertentu dalam rentang waktu
                listPermintaanPengiriman = permintaanPengirimanService.cariPermintaanPengirimanByBarangDanWaktu(barang, startDateTime, endDateTime);
            }
        }

        List<Barang> listBarang = barangService.getAllBarangSortedByMerk();

        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);
        model.addAttribute("skuBarang", skuBarang);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("isBonusActive", true);

        return "filter-permintaan-pengiriman";
    }

}
