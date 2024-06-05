package apap.ti.silogistik2106632232.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="barang")
public class Barang {
    @Id
    @Column(name = "sku", nullable = false, unique = true)
    @Size(max = 7)
    private String sku;

    @Column(name = "tipe_barang", nullable = false)
    @NotNull()
    private Integer tipeBarang;

    @Column(name = "merk", nullable = false)
    @Size(max = 255)
    @NotNull()
    private String merk;

    @Column(name = "harga_barang", nullable = false)
    @Positive()
    @NotNull()
    private Long hargaBarang;

    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    private List<GudangBarang> listGudangBarang = new ArrayList<>();}
