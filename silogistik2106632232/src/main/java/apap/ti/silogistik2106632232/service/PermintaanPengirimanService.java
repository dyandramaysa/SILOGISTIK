package apap.ti.silogistik2106632232.service;

import java.time.LocalDateTime;
import java.util.List;

import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.PermintaanPengiriman;

public interface PermintaanPengirimanService {
    //Method untuk menambahkan Permintaan Pengiriman
    void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman);

    //Method untuk mengetahui jumlah Permintaan Pengiriman
    Long getJumlahPermintaanPengiriman();

    //Method untuk mengambil semua object Permintaan Pengiriman
    List<PermintaanPengiriman> getAllPermintaanPengiriman();

    //Method untuk mendapatkan Permintaan Pengiriman berdasarkan ID
    PermintaanPengiriman getPermintaanPengirimanById(Long id);

    //Method untuk membuat Permintaan Pengiriman baru
    PermintaanPengiriman buatPermintaanPengiriman(PermintaanPengiriman permintaanFromDTO);

    //Method unutk membuat Nomor Pengiriman
    String generateNomorPengiriman(PermintaanPengiriman permintaanPengiriman);

    //Method untuk melakukan cancel permintaan
    void cancelPermintaan(PermintaanPengiriman permintaanPengiriman);

    //Method untuk menampilkan permintaan pengiriman yang memuat barang tertentu pada kurun waktu tertentu
    List<PermintaanPengiriman> cariPermintaanPengirimanByBarangDanWaktu(Barang barang, LocalDateTime startDate, LocalDateTime endDate);
}
