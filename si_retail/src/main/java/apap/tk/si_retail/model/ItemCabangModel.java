package apap.tk.si_retail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="item_cabang")
public class ItemCabangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid_item;

    @NotNull
    @Size(max=50)
    @Column(nullable = false)
    private String nama;

    @NotNull
    @Column(nullable = false)
    private Integer harga;

    @NotNull
    @Column(nullable = false)
    private Integer stok;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String kategori;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cabang_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CabangModel cabang;

    @NotNull
    @Column(nullable = false)
    private Integer id_promo;
}
