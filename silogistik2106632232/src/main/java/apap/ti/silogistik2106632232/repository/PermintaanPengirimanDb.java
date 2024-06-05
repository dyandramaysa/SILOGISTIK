package apap.ti.silogistik2106632232.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import apap.ti.silogistik2106632232.model.Barang;
import apap.ti.silogistik2106632232.model.PermintaanPengiriman;


@Repository
public interface PermintaanPengirimanDb extends JpaRepository<PermintaanPengiriman, Long>{
    @Query("SELECT p FROM PermintaanPengiriman p ORDER BY p.waktuPermintaan DESC")
    List<PermintaanPengiriman> getAllByOrderByWaktuPermintaanDesc();

    @Query("SELECT COUNT(pp) > 0 FROM PermintaanPengiriman pp WHERE pp.nomorPengiriman = :nomorPengiriman")
    boolean existsByNomorPengiriman(@Param("nomorPengiriman") String nomorPengiriman);

    @Query("SELECT pp FROM PermintaanPengiriman pp " +
           "JOIN pp.listPermintaanPengirimanBarang ppb " +
           "WHERE ppb.barang = :barang " +
           "AND pp.waktuPermintaan BETWEEN :startDate AND :endDate")
    List<PermintaanPengiriman> cariPermintaanPengirimanByBarangDanWaktu(
        @Param("barang") Barang barang,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}
