package apap.tk.si_retail.repository;

import apap.tk.si_retail.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import apap.tk.si_retail.model.CabangModel;

import java.util.List;

public interface CabangDB extends JpaRepository<CabangModel, Integer> {
    List<CabangModel> findAllByPenanggungJawab(UserModel penanggungJawab);
}
