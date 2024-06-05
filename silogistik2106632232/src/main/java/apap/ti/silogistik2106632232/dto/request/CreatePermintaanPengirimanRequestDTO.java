package apap.ti.silogistik2106632232.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import apap.ti.silogistik2106632232.model.Karyawan;
import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePermintaanPengirimanRequestDTO {
    private String nomorPengiriman;

    @NotNull(message = "Nama penerima tidak boleh kosong")
    private String namaPenerima;

    @NotNull(message = "Alamat penerima tidak boleh kosong")
    private String alamatPenerima;

    @NotNull(message = "Tanggal pengiriman tidak boleh kosong")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tanggalPengiriman;

    @NotNull(message = "Biaya pengiriman tidak boleh kosong")
    private Integer biayaPengiriman;

    @NotNull(message = "Jenis layanan tidak boleh kosong")
    @Min(value = 1)
    @Max(value = 4)
    private Integer jenisLayanan;

    private LocalDateTime waktuPermintaan;

    @NotNull(message = "Karyawan tidak boleh kosong")
    private Karyawan karyawan;

    @NotNull(message = "Barang dan kuantitasnya tidak boleh kosong")
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang;
}