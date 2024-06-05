package apap.ti.silogistik2106632232.dto.request;

import apap.ti.silogistik2106632232.model.GudangBarang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateBarangRequestDTO {

    @NotNull(message = "Tipe barang tidak boleh kosong")
    private Integer tipeBarang;

    @Size(max = 255)
    @NotNull(message = "Merk barang tidak boleh kosong")
    private String merk;

    @Positive(message = "harga barang tidak boleh nol")
    @NotNull(message = "Harga barang tidak boleh kosong")
    private Long hargaBarang;

    private List<GudangBarang> listgudangbarang;
}
