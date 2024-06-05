package apap.ti.silogistik2106632232.service;

import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;

@Service
public interface PermintaanPengirimanBarangService {
    //Method untuk menambahkan Permintaan Pengiriman
    void savePermintaanPengirimanBarang(PermintaanPengirimanBarang permintaanPengirimanBarang);
}
