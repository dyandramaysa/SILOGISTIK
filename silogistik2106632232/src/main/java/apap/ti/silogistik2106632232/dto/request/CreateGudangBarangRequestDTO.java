package apap.ti.silogistik2106632232.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.Gudang;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateGudangBarangRequestDTO {
    @NotNull
    Gudang gudang;

    @NotNull
    Barang barang;

    @NotNull(message = "Stok barang tidak boleh kosong")
    private Integer stok;
}
