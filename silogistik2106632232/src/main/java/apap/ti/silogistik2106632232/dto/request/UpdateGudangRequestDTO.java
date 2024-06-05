package apap.ti.silogistik2106632232.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import apap.ti.silogistik2106632232.model.GudangBarang;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateGudangRequestDTO extends CreateGudangRequestDTO {
    @NotNull
    private Long id;
    private List<GudangBarang> listGudangBarang;
}
