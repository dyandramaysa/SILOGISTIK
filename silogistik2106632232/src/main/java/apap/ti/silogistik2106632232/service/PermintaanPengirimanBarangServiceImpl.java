package apap.ti.silogistik2106632232.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106632232.repository.PermintaanPengirimanBarangDb;

@Service
public class PermintaanPengirimanBarangServiceImpl implements PermintaanPengirimanBarangService {
    @Autowired
    PermintaanPengirimanBarangDb permintaanPengirimanBarangDb;

    @Override
	public void savePermintaanPengirimanBarang(PermintaanPengirimanBarang permintaanPengirimanBarang){
		permintaanPengirimanBarangDb.save(permintaanPengirimanBarang);
	}
}
