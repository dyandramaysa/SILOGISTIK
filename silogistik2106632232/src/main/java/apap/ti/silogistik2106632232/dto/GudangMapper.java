package apap.ti.silogistik2106632232.dto;

import apap.ti.silogistik2106632232.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106632232.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106632232.dto.response.ReadGudangResponseDTO;
import apap.ti.silogistik2106632232.model.Gudang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GudangMapper {
    Gudang createGudangRequestDTOToGudang(CreateGudangRequestDTO createGudangRequestDTO);
    ReadGudangResponseDTO gudangToReadGudangResponseDTO(Gudang gudang);
    Gudang updateGudangRequestDTOToGudang(UpdateGudangRequestDTO updateGudangRequestDTO);
    UpdateGudangRequestDTO gudangToUpdateGudangRequestDTO(Gudang gudang);
}
