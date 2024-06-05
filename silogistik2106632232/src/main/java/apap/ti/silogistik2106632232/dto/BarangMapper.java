package apap.ti.silogistik2106632232.dto;

import apap.ti.silogistik2106632232.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106632232.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106632232.dto.response.ReadBarangResponseDTO;
import apap.ti.silogistik2106632232.model.Barang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarangMapper {
    Barang createBarangRequestDTOToBarang(CreateBarangRequestDTO createBarangRequestDTO);
    ReadBarangResponseDTO barangToReadBarangResponseDTO(Barang barang);
    Barang updateBarangRequestDTOToBarang(UpdateBarangRequestDTO updateBarangRequestDTO);
    UpdateBarangRequestDTO barangToUpdateBarangRequestDTO(Barang barang);
}
