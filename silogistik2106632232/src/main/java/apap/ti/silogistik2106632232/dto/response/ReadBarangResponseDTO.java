package apap.ti.silogistik2106632232.dto.response;

import java.util.List;

import apap.ti.silogistik2106632232.model.GudangBarang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadBarangResponseDTO {
    private String sku;
    private Integer tipeBarang;
    private String merk;
    private Long hargaBarang;
    private List<GudangBarang> listGudangBarang;
}
