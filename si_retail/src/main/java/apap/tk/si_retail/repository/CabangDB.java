package apap.tk.si_retail.repository;

import apap.tk.si_retail.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import apap.tk.si_retail.model.CabangModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CabangDB extends JpaRepository<CabangModel, Long> {
    //    Optional<CabangModel> findByIdCabang(Long idCabang);
    List<CabangModel> findAllByPenanggungJawab(UserModel penanggungJawab);
    List<CabangModel> findAllByStatus(Integer status);
    Optional<CabangModel> findByNama(String nama);
}