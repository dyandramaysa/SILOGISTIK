package apap.ti.silogistik2106632232.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.GudangBarang;
import apap.ti.silogistik2106632232.model.PermintaanPengiriman;
import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106632232.repository.PermintaanPengirimanBarangDb;
import apap.ti.silogistik2106632232.repository.PermintaanPengirimanDb;
import jakarta.transaction.Transactional;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService{
    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;
    @Autowired
    PermintaanPengirimanMapper permintaanPengirimanMapper;
    @Autowired
    PermintaanPengirimanBarangDb permintaanPengirimanBarangDb;
    @Autowired
    BarangService barangService;

    @Override
	public void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman){
		permintaanPengirimanDb.save(permintaanPengiriman);
	}

    @Override
    public Long getJumlahPermintaanPengiriman() {
        return permintaanPengirimanDb.count();
    }

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengiriman() {
        return permintaanPengirimanDb.getAllByOrderByWaktuPermintaanDesc();
    }

    @Override
    public PermintaanPengiriman getPermintaanPengirimanById(Long id) {
        for (PermintaanPengiriman permintaanPengiriman : getAllPermintaanPengiriman()) {
			if (permintaanPengiriman.getId().equals(id)) {
				return permintaanPengiriman;
			}
		}	
		return null;
    }

    @Transactional
    @Override
    public PermintaanPengiriman buatPermintaanPengiriman(PermintaanPengiriman permintaanFromDTO) {
        permintaanFromDTO.setWaktuPermintaan(LocalDateTime.now());
        var newNoPengiriman = generateNomorPengiriman(permintaanFromDTO);
        permintaanFromDTO.setNomorPengiriman(newNoPengiriman);
        permintaanPengirimanDb.save(permintaanFromDTO);

        List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = permintaanFromDTO.getListPermintaanPengirimanBarang();
        for (PermintaanPengirimanBarang ppb : listPermintaanPengirimanBarang ) {
            ppb.setPermintaanPengiriman(permintaanFromDTO);
            permintaanPengirimanBarangDb.save(ppb);

            var getBarang = ppb.getBarang().getSku();
            Barang barang = barangService.getBarangBySKU(getBarang);
            int jumlahDiminta = ppb.getKuantitasPengiriman();
            int stokBarang = 0;
            List<GudangBarang> listGudangBarang = barang.getListGudangBarang();

            for (GudangBarang barangs : listGudangBarang) {
                stokBarang += barangs.getStok();
            }

            if (jumlahDiminta > stokBarang) {
               throw new IllegalArgumentException("Jumlah diminta melebihi stok barang yang tersedia.");
            }
        }

        permintaanPengirimanBarangDb.saveAll(permintaanFromDTO.getListPermintaanPengirimanBarang());

        return permintaanFromDTO;
    }

    //Metode untuk menghasilkan nomor pengiriman 
    @Override
    public String generateNomorPengiriman(PermintaanPengiriman permintaanPengiriman) {
        // Mendapatkan jumlah barang yang ingin dipesan (harus diambil 2 digit terakhir)
        List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = permintaanPengiriman.getListPermintaanPengirimanBarang();
        int jumlahBarang = 0;
        for (PermintaanPengirimanBarang ppb : listPermintaanPengirimanBarang) {
            jumlahBarang += ppb.getKuantitasPengiriman();
        }
        int kuantitas = jumlahBarang % 100;

        // Mendapatkan jenis layanan
        int jenisLayanan = permintaanPengiriman.getJenisLayanan();

        // Mendapatkan waktu pembuatan permintaan pengiriman dalam format "HH:mm:ss"
        String waktuPermintaan = permintaanPengiriman.getWaktuPermintaan().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Menggabungkan semua elemen untuk membuat nomor pengiriman
        String nomorPengiriman = String.format("REQ%02d%s%s", kuantitas, getJenisLayananCode(jenisLayanan), waktuPermintaan);

        // Pastikan nomor pengiriman unik
        nomorPengiriman = ensureUniqueNomorPengiriman(nomorPengiriman);

        return nomorPengiriman;
    }

    
    // Fungsi ini akan memeriksa apakah nomorPengiriman sudah digunakan, jika iya, maka akan menambahkan sufiks unik
    private String ensureUniqueNomorPengiriman(String nomorPengiriman) {
        String uniqueNomorPengiriman = nomorPengiriman;
        int counter = 1;
        while (permintaanPengirimanDb.existsByNomorPengiriman(uniqueNomorPengiriman)) {
            uniqueNomorPengiriman = nomorPengiriman + "-" + counter;
            counter++;
        }
        return uniqueNomorPengiriman;
    }

    
    // Fungsi ini mengembalikan kode jenis layanan sesuai dengan angka
    private String getJenisLayananCode(int jenisLayanan) {
        switch (jenisLayanan) {
            case 1:
                return "SAM";
            case 2:
                return "KIL";
            case 3:
                return "REG";
            case 4:
                return "HEM";
            default:
                throw new IllegalArgumentException("Jenis Layanan tidak valid");
        }
    }

    @Override
    public void cancelPermintaan(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengiriman.setIsCancelled(true);
        permintaanPengirimanDb.save(permintaanPengiriman);
    }

    @Override
    public List<PermintaanPengiriman> cariPermintaanPengirimanByBarangDanWaktu(Barang barang, LocalDateTime startDate, LocalDateTime endDate) {
        return permintaanPengirimanDb.cariPermintaanPengirimanByBarangDanWaktu(barang, startDate, endDate);
    }
    
}
