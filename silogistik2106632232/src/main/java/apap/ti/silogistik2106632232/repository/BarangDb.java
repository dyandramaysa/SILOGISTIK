package apap.ti.silogistik2106632232.repository;

import apap.ti.silogistik2106632232.model.Barang;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BarangDb extends JpaRepository<Barang, String>{
    @Query("SELECT COUNT(b) FROM Barang b")
    Integer findMaxIncrement();

    @Query("SELECT b FROM Barang b ORDER BY LOWER(b.merk)")
    List<Barang> sortBarangByMerkLower();

    @Query("SELECT b FROM Barang b WHERE b NOT IN (SELECT gb.barang FROM GudangBarang gb)")
    List<Barang> findAllBarangNotInGudang();

    @Query(value = "SELECT * FROM barang ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Barang> findRandomBarang(@Param("limit") int limit);
}
