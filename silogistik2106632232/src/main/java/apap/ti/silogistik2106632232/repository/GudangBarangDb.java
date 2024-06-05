package apap.ti.silogistik2106632232.repository;

import apap.ti.silogistik2106632232.model.GudangBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GudangBarangDb extends JpaRepository<GudangBarang, Long> {
    
}
