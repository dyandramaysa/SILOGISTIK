package apap.ti.silogistik2106632232.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import apap.ti.silogistik2106632232.model.Karyawan;
import apap.ti.silogistik2106632232.model.PermintaanPengirimanBarang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadPermintaanPengirimanResponseDTO {
    private Long id;
    private String nomorPengiriman;
    private String namaPenerima;
    private String alamatPenerima;
    private LocalDate tanggalPengiriman;
    private LocalDateTime waktuPermintaan;
    private Integer biayaPengiriman;
    private Integer jenisLayanan;
    private Karyawan karyawan;
    private Boolean isCancelled;
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang;
}
