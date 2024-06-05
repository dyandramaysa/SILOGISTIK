package apap.ti.silogistik2106632232.dto.request;

import apap.ti.silogistik2106632232.model.GudangBarang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateGudangRequestDTO {
    @NotNull(message = "Nama gudang tidak boleh kosong")
    @Size(max = 255)
    private String nama;

    @NotNull(message = "Alamat gudang tidak boleh kosong")
    @Size(max = 255)
    private String alamatGudang;

    private List<GudangBarang> listgudangbarang;
}
