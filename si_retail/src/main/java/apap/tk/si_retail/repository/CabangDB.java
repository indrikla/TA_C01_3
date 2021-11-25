package apap.tk.si_retail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import apap.tk.si_retail.model.CabangModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabangDB extends JpaRepository<CabangModel, Long> {
//    Optional<CabangModel> findByIdCabang(Long idCabang);

}
