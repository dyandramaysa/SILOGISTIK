package apap.ti.silogistik2106632232.repository;

import apap.ti.silogistik2106632232.model.Gudang;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface GudangDb extends JpaRepository<Gudang, Long>{
    @Query("SELECT g FROM Gudang g ORDER BY LOWER(g.nama)")
    List<Gudang> sortGudangByNamaLower();
}
