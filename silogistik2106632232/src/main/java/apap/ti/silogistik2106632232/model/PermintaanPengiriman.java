package apap.ti.silogistik2106632232.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="permintaan_pengiriman")
public class PermintaanPengiriman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Nomor pengiriman tidak boleh kosong")
    @Size(max = 16)
    @Column(name = "nomor_pengiriman", nullable = false, unique = true)
    private String nomorPengiriman;

    @NotNull(message = "Status permintaan pengiriman tidak boleh kosong")
    @Column(name = "is_cancelled", nullable = false)
    private Boolean isCancelled = false;

    @NotNull(message = "Nama penerima tidak boleh kosong")
    @Column(name = "nama_penerima", nullable = false)
    private String namaPenerima;

    @NotNull(message = "Alamat penerima tidak boleh kosong")
    @Column(name = "alamat_penerima", nullable = false)
    private String alamatPenerima;

    @NotNull(message = "Tanggal pengiriman tidak boleh kosong")
    @Column(name = "tanggal_pengiriman", nullable = false)
    private LocalDate tanggalPengiriman;

    @NotNull(message = "Biaya pengiriman tidak boleh kosong")
    @Column(name = "biaya_pengiriman", nullable = false)
    private Integer biayaPengiriman;

    @NotNull(message = "Jenis layanan tidak boleh kosong")
    @Column(name = "jenis_layanan", nullable = false)
    private Integer jenisLayanan;

    @NotNull(message = "Waktu permintaan tidak boleh kosong")
    @Column(name = "waktu_permintaan", nullable = false)
    private LocalDateTime waktuPermintaan;

    @NotNull(message = "Karyawan tidak boleh kosong")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_karyawan", referencedColumnName = "id")
    private Karyawan karyawan;

    @OneToMany(mappedBy = "permintaanPengiriman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = new ArrayList<>();
}
