package apap.ti.silogistik2106632232.service;

import java.util.List;

import apap.ti.silogistik2106632232.model.Karyawan;

public interface KaryawanService {
    //Method untuk menambahkan Karyawan
    void saveKaryawan(Karyawan karyawan);

    //Method untuk mendapatkan Karyawan yang telah tersimpan
	List<Karyawan> getAllKaryawan();

    //Method untuk mendapatkan Karyawan berdasarkan ID
    Karyawan getKaryawanById(Long id);

    //Method untuk mengetahui jumlah Karyawan
    Long getJumlahKaryawan();
}
