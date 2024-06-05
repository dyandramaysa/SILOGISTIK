package apap.ti.silogistik2106632232.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateKaryawanRequestDTO {
    @NotNull(message = "Nama karyawan tidak boleh kosong")
    private String nama;

    @NotNull(message = "Jenis kelamin tidak boleh kosong")
    private Integer jenisKelamin;

    @NotNull(message = "Tanggal lahir tidak boleh kosong")
    private LocalDate tanggalLahir;

}
