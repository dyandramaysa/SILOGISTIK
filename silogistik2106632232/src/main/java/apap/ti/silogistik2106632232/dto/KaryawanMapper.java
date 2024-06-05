package apap.ti.silogistik2106632232.dto;

import org.mapstruct.Mapper;

import apap.ti.silogistik2106632232.dto.request.CreateKaryawanRequestDTO;
import apap.ti.silogistik2106632232.model.Karyawan;

@Mapper(componentModel = "spring")
public interface KaryawanMapper {
    Karyawan createKaryawanRequestDTOToKaryawan(CreateKaryawanRequestDTO createKaryawanRequestDTO);
}
