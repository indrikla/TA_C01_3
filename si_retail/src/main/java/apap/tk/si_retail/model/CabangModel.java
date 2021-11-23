package apap.tk.si_retail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name="cabang")
public class CabangModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=30)
    @Column(nullable = false)
    private String nama;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String alamat;

    @NotNull
    @Column(nullable = false)
    private Integer ukuran;

    @NotNull
    @Column(nullable = false)
    private Integer status=1;

    @NotNull
    @Size(max=20)
    @Column(nullable = false)
    private String no_telp;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "id_user", referencedColumnName = "idUser", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private UserModel user;
}
