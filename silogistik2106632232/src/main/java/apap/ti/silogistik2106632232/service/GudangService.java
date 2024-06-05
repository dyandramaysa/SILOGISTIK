package apap.ti.silogistik2106632232.service;

import java.util.List;

import apap.ti.silogistik2106632232.model.Gudang;

public interface GudangService {
    //Method untuk menambahkan Gudang
    void saveGudang(Gudang gudang);

    //Method untuk mengetahui jumlah Gudang
    Long getJumlahGudang();

    //Method untuk mendapatkan semua Gudang
    List<Gudang> getAllGudang();

    //Method untuk mendapatkan Gudang berdasarkan id
    Gudang getGudangById(Long idGudang);

    //Method untuk restock barang
    void restockGudang(Gudang gudangFromDTO);
}
