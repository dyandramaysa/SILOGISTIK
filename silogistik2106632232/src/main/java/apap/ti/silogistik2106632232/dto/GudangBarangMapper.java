package apap.ti.silogistik2106632232.dto;

import apap.ti.silogistik2106632232.dto.request.CreateGudangBarangRequestDTO;
import apap.ti.silogistik2106632232.model.GudangBarang;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GudangBarangMapper {
    GudangBarang createGudangBarangRequestDTOTOGudangBarang(CreateGudangBarangRequestDTO createGudangBarangRequestDTO);
}
