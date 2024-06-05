package apap.ti.silogistik2106632232.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.dto.BarangMapper;
import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.repository.BarangDb;

@Service
public class BarangServiceImpl implements BarangService{
    @Autowired
    BarangDb barangDb;
    @Autowired
    BarangMapper barangMapper;

    @Override
	public void saveBarang(Barang barang){
		barangDb.save(barang);
	}

    @Override
    public List<Barang> getAllBarang() {
        return barangDb.findAll();
    }

    @Override
    public Barang getBarangBySKU(String sku) {
        for (Barang barang : getAllBarang()) {
			if (barang.getSku().equals(sku)) {
				return barang;
			}
		}	
		return null;
    }

    @Override
    public List<Barang> getAllBarangSortedByMerk() {
        return barangDb.sortBarangByMerkLower();
    }

    // Metode untuk menghasilkan SKU dengan auto-increment
    public String generateSKU(Integer tipeBarang) {
        String prefix = getSKUPrefix(tipeBarang);
        int nextIncrement = getNextIncrement();
        String sku = prefix + String.format("%03d", nextIncrement);
        return sku;
    }

    // Metode untuk mendapatkan tiga karakter angka auto-increment
    public Integer getNextIncrement() {
        Integer maxIncrement = barangDb.findMaxIncrement();
        return (maxIncrement != null) ? maxIncrement + 1 : 1;
    }

    // Metode untuk menghasilkan awalan SKU berdasarkan tipe barang
    public String getSKUPrefix(Integer tipeBarang) {
        switch (tipeBarang) {
            case 1:
                return "ELEC";
            case 2:
                return "CLOT";
            case 3:
                return "FOOD";
            case 4:
                return "COSM";
            case 5:
                return "TOOL";
            default:
                throw new IllegalArgumentException("Tipe barang tidak valid: " + tipeBarang);
        }
    }

    // Metode untuk mengambil jumlah Barang
    @Override
    public Long getJumlahBarang() {
        return barangDb.count();
    }

    // Metode untuk mengambil semua barang yang belum ada di semua gudang
    public List<Barang> getAllBarangNotInGudang() {
        return barangDb.findAllBarangNotInGudang();
    }

    // Metode untuk mengubah barang
    @Override
    public Barang ubahBarang(Barang barangFromDTO) {
        Barang barang = getBarangBySKU(barangFromDTO.getSku());

        if (barang != null) {
            barang.setTipeBarang(barangFromDTO.getTipeBarang());
            barang.setMerk(barangFromDTO.getMerk());
            barang.setHargaBarang(barangFromDTO.getHargaBarang());
            barang.setListGudangBarang(barangFromDTO.getListGudangBarang());
            barangDb.save(barang);
        }
        return barang;
    }

    // Metode untuk memastikan merk barang tidak duplikat
    @Override
    public boolean isMerkExist(String merk) {
        return getAllBarang().stream().anyMatch(b -> b.getMerk().equals(merk));
    }

    @Override
    public boolean isMerkExist(String merk, String sku) {
        return getAllBarang().stream().anyMatch(b -> b.getMerk().equals(merk) && !b.getSku().equals(sku));
    }
}

