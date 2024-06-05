package apap.ti.silogistik2106632232.service;

import java.util.List;

import apap.ti.silogistik2106632232.model.GudangBarang;

public interface GudangBarangService {
    //Method untuk menambahkan GudangBarang
    void saveGudangBarang(GudangBarang gudangBarang);

    //Method untuk mengambil semua GudangBarang
    List<GudangBarang> getAllGudangBarang();

    //Method untuk mendapatkan GudangBarang berdasarkan id
    GudangBarang getGudangBarangById(Long idGudangBarang);
}
