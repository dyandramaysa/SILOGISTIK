package apap.ti.silogistik2106632232.dto;

import org.mapstruct.Mapper;

import apap.ti.silogistik2106632232.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106632232.dto.response.ReadPermintaanPengirimanResponseDTO;
import apap.ti.silogistik2106632232.model.PermintaanPengiriman;

@Mapper(componentModel = "spring")
public interface PermintaanPengirimanMapper {
    PermintaanPengiriman createPermintaanPengirimanRequestDTOToPermintaanPengiriman (CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO);
    ReadPermintaanPengirimanResponseDTO permintaanPengirimanToReadPermintaanPengirimanResponseDTO(PermintaanPengiriman permintaanPengiriman);
}
