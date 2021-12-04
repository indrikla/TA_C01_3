package apap.tk.si_retail.repository;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCabangDB extends JpaRepository<ItemCabangModel, Long> {
    List<ItemCabangModel> findItemCabangModelByCabang(CabangModel cabang);
}
