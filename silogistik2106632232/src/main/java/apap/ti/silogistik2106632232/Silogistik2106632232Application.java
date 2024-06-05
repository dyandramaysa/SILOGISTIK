package apap.ti.silogistik2106632232;

import com.github.javafaker.Faker;

import apap.ti.silogistik2106632232.dto.BarangMapper;
import apap.ti.silogistik2106632232.dto.GudangBarangMapper;
import apap.ti.silogistik2106632232.dto.GudangMapper;
import apap.ti.silogistik2106632232.dto.KaryawanMapper;
import apap.ti.silogistik2106632232.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106632232.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106632232.dto.request.CreateGudangBarangRequestDTO;
import apap.ti.silogistik2106632232.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106632232.dto.request.CreateKaryawanRequestDTO;
import apap.ti.silogistik2106632232.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106632232.repository.BarangDb;
import apap.ti.silogistik2106632232.service.BarangService;
import apap.ti.silogistik2106632232.service.GudangBarangService;
import apap.ti.silogistik2106632232.service.GudangService;
import apap.ti.silogistik2106632232.service.KaryawanService;
import apap.ti.silogistik2106632232.service.PermintaanPengirimanBarangService;
import apap.ti.silogistik2106632232.service.PermintaanPengirimanService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Silogistik2106632232Application {

	public static void main(String[] args) {
		SpringApplication.run(Silogistik2106632232Application.class, args);
	}

	// CommandLineRunner digunakan untuk execute code saat spring pertama kali start up
	@Bean
	@Transactional
	CommandLineRunner run(
		GudangService gudangService, 
		BarangService barangService, 
		GudangBarangService gudangBarangService,
		KaryawanService karyawanService, 
		PermintaanPengirimanService permintaanPengirimanService,
		GudangMapper gudangMapper, 
		BarangMapper barangMapper, 
		GudangBarangMapper gudangBarangMapper,
		KaryawanMapper karyawanMapper, 
		PermintaanPengirimanMapper permintaanPengirimanMapper,
		BarangDb barangDb,
		PermintaanPengirimanBarangService permintaanPengirimanBarangService
	) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));
			// Membuat fake data untuk Karyawan
			for (int i = 0; i < 10; i++) {
				var karyawanDTO = new CreateKaryawanRequestDTO();
				
				karyawanDTO.setNama(faker.name().name());
				karyawanDTO.setJenisKelamin(faker.number().numberBetween(1, 3));
				karyawanDTO.setTanggalLahir(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

				var karyawan = karyawanMapper.createKaryawanRequestDTOToKaryawan(karyawanDTO);

				karyawanService.saveKaryawan(karyawan);
			}

			for (int i = 0; i < 10; i++) {
				// Membuat fake data untuk Gudang
				var gudangDTO = new CreateGudangRequestDTO();
				var fakeGudang = faker.address().city();
				var fakeAddress = faker.address().fullAddress();

				gudangDTO.setNama(fakeGudang);
				gudangDTO.setAlamatGudang(fakeAddress);

				// Mapping gudangDTO ke Gudang lalu save Gudang ke database
				var gudang = gudangMapper.createGudangRequestDTOToGudang(gudangDTO);
				gudangService.saveGudang(gudang);

				// Membuat fake data untuk Barang
				var barangDTO = new CreateBarangRequestDTO();
				var fakeType = faker.number().numberBetween(1, 6);
				var fakeBrand = faker.commerce().productName();
				var fakePrice = faker.number().numberBetween(1L, 1000000L);
				
				// Menggunakan BarangService untuk menghasilkan SKU sesuai dengan tipe barang
				var sku = barangService.generateSKU(fakeType);

				barangDTO.setTipeBarang(fakeType);
				barangDTO.setMerk(fakeBrand);
				barangDTO.setHargaBarang(fakePrice);
				
				// Mapping barangDTO ke Barang lalu save Barang ke database
				var barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);
				barang.setSku(sku);
				barangService.saveBarang(barang);

				// Membuat fake data untuk GudangBarang
				var gudangBarangDTO = new CreateGudangBarangRequestDTO();
				var fakeStock = faker.number().numberBetween(0, 1000);

				gudangBarangDTO.setStok(fakeStock);
				gudangBarangDTO.setGudang(gudang);
				gudangBarangDTO.setBarang(barang);
				// Mapping gudangBarangDTO ke GudangBarang lalu save GudangBarang ke database
				var gudangBarang = gudangBarangMapper.createGudangBarangRequestDTOTOGudangBarang(gudangBarangDTO);
				
				gudangBarangService.saveGudangBarang(gudangBarang);

				// Membuat fake data untuk Permintaan Pengiriman
				var permintaanPengirimanDTO = new CreatePermintaanPengirimanRequestDTO();
				var fakeJumlahBarang = (int) Math.floor(10 + Math.random() * 90);
				var fakeJenisLayanan = faker.number().numberBetween(1, 4);
				var jenisLayanan = switch (fakeJenisLayanan) {
					case 1 -> "SAM";
					case 2 -> "KIL";
					case 3 -> "REG";
					default -> "HEM";
				};
				var fakeWaktu = faker.date().past(30, 7,TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
				//SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
				var waktuFormatted = fakeWaktu.format(timeFormat);

				//permintaanPengirimanDTO.setNomorPengiriman("REQ" + fakeJumlahBarang + jenisLayanan + timeFormat.format(fakeWaktu));
				permintaanPengirimanDTO.setNomorPengiriman("REQ" + fakeJumlahBarang + jenisLayanan + waktuFormatted);
				//jenis layanan, waktu permintaan, id karyawan

				permintaanPengirimanDTO.setNamaPenerima(faker.name().firstName());
				permintaanPengirimanDTO.setAlamatPenerima(faker.address().fullAddress());
				permintaanPengirimanDTO.setTanggalPengiriman(faker.date().past(7, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
				permintaanPengirimanDTO.setBiayaPengiriman((int) Math.floor(Math.random() * 10000));
				permintaanPengirimanDTO.setJenisLayanan(fakeJenisLayanan);
				permintaanPengirimanDTO.setWaktuPermintaan(fakeWaktu);
				permintaanPengirimanDTO.setKaryawan(karyawanService.getKaryawanById(1 + (long) Math.floor(Math.random() * 10)));

				var permintaanPengiriman = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanPengirimanDTO);

				permintaanPengirimanService.savePermintaanPengiriman(permintaanPengiriman);

			}

			// Membuat objek Permintaan Pengiriman Barang
			// Mengambil 10 barang secara acak
			List<Barang> randomBarangList = barangDb.findRandomBarang(10);

			// Loop untuk membuat PermintaanPengirimanBarang
			for (Barang barang : randomBarangList) {
				var permintaanPengirimanBarang = new PermintaanPengirimanBarang();
				var fakeNum = (long) faker.number().numberBetween(1L, 11L);
				var permintaanPengiriman = permintaanPengirimanService.getPermintaanPengirimanById(fakeNum);

				// Mengatur objek PermintaanPengirimanBarang
				permintaanPengirimanBarang.setPermintaanPengiriman(permintaanPengiriman);
				permintaanPengirimanBarang.setBarang(barang);
				permintaanPengirimanBarang.setKuantitasPengiriman((int) Math.floor(1 + Math.random() * 9));

				// Menyimpan objek PermintaanPengirimanBarang ke database
				permintaanPengirimanBarangService.savePermintaanPengirimanBarang(permintaanPengirimanBarang);
			}

		};
	}

}