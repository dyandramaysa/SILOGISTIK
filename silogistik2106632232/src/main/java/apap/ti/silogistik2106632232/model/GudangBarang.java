package apap.ti.silogistik2106632232.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="gudang_barang")
public class GudangBarang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "idGudang tidak boleh kosong")
    @ManyToOne
    @JoinColumn(name = "id_gudang", referencedColumnName = "id")
    Gudang gudang;

    @NotNull(message = "idBarang tidak boleh kosong")
    @ManyToOne
    @JoinColumn(name = "sku_barang", referencedColumnName = "sku")
    Barang barang;

    @Column(name = "stok", nullable = false)
    @NotNull(message = "Stok barang tidak boleh kosong")
    private Integer stok = 0;
}
