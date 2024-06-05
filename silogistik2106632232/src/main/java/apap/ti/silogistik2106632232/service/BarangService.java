package apap.ti.silogistik2106632232.service;

import java.util.List;

import apap.ti.silogistik2106632232.model.Barang;

public interface BarangService {
    //Method untuk menambahkan Barang
    void saveBarang(Barang barang);

    //Method untuk mendapatkan semua Barang
    List<Barang> getAllBarang();

    //Method untuk mendapatkan barang berdasarkan SKU
    Barang getBarangBySKU(String sku);

    //Method untuk menghasilkan SKU dengan auto-increment
    String generateSKU(Integer tipeBarang);

    //Method untuk mendapatkan tiga karakter angka auto-increment
    Integer getNextIncrement();

    //Method untuk menghasilkan awalan SKU berdasarkan tipe barang
    String getSKUPrefix(Integer tipeBarang);

    //Method untuk mengetahui jumlah Barang
    Long getJumlahBarang();

    //Method untuk mengambil semua barang yang diurutkan berdasarkan merk
    List<Barang> getAllBarangSortedByMerk();

    //Method untuk mengambil semua barang yang belum ada di gudang
    List<Barang> getAllBarangNotInGudang();

    //Method untuk update barang
    Barang ubahBarang(Barang barangFromDTO);

    //Method untuk memastikan merk barang tidak duplikat
    boolean isMerkExist(String merk);

    boolean isMerkExist(String merk, String sku);
}