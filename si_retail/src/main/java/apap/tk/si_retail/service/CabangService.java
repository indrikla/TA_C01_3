package apap.tk.si_retail.service;
import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.UserModel;

import java.util.List;

public interface CabangService {
    void addCabang(CabangModel cabang);
    List<CabangModel> getListCabang();
    List<CabangModel> getListCabangManager(UserModel manager);
}
