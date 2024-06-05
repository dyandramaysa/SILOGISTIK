package apap.ti.silogistik2106632232.dto.request;


import java.util.List;

import apap.ti.silogistik2106632232.model.GudangBarang;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateBarangRequestDTO extends CreateBarangRequestDTO {
    @NotNull
    private String sku;
    private List<GudangBarang> listGudangBarang;
}
