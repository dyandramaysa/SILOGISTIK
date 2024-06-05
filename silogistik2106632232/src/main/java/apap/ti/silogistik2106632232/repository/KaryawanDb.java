package apap.ti.silogistik2106632232.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.silogistik2106632232.model.Karyawan;

@Repository
public interface KaryawanDb extends JpaRepository<Karyawan, Long>{
    
}
