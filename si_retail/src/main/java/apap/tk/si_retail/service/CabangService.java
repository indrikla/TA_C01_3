package apap.tk.si_retail.service;
import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.model.UserModel;

import java.util.List;

public interface CabangService {
    void addCabang(CabangModel cabang);
    List<CabangModel> getListCabang();
    List<CabangModel> getListCabangManager(UserModel manager);
    List<CabangModel> getListCabangStatus(Integer status);
    CabangModel getCabangByIdCabang(Long idCabang);
    void updateCabang(CabangModel cabang);
    int deleteCabang(CabangModel cabang);
    ItemCabangModel getItemCabangInCabangByIdItemCabang(CabangModel cabang, String uuidItemCabang);
}
