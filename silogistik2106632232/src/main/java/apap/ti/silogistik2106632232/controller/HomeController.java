package apap.ti.silogistik2106632232.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import apap.ti.silogistik2106632232.service.BarangService;
import apap.ti.silogistik2106632232.service.GudangService;
import apap.ti.silogistik2106632232.service.KaryawanService;
import apap.ti.silogistik2106632232.service.PermintaanPengirimanService;

@Controller
public class HomeController {
    @Autowired
    BarangService barangService;

    @Autowired
    GudangService gudangService;

    @Autowired
    KaryawanService karyawanService;

    @Autowired
    PermintaanPengirimanService permintaanPengirimanService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("jumlahBarang", barangService.getJumlahBarang());
        model.addAttribute("jumlahGudang", gudangService.getJumlahGudang());
        model.addAttribute("jumlahKaryawan", karyawanService.getJumlahKaryawan());
        model.addAttribute("jumlahPermintaanPengiriman", permintaanPengirimanService.getJumlahPermintaanPengiriman());
        return "home.html";
    }
}
