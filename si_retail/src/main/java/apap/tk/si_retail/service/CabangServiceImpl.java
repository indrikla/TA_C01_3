package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.repository.CabangDB;
import apap.tk.si_retail.repository.ItemCabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CabangServiceImpl implements CabangService {
    @Autowired
    CabangDB cabangDB;

    @Autowired
    ItemCabangDB itemCabangDB;

    @Override
    public void addCabang(CabangModel cabang) { cabangDB.save(cabang); }

    @Override
    public List<CabangModel> getListCabang() {
        return cabangDB.findAll();
    }

    @Override
    public List<CabangModel> getListCabangStatus(Integer status){
        return cabangDB.findAllByStatus(status);
    }

    @Override
    public List<CabangModel> getListCabangManager(UserModel manager) {
        List<CabangModel> ListCabang = cabangDB.findAllByPenanggungJawab(manager);
        for (CabangModel cabang : ListCabang) {
            if(cabang.getStatus()==0){
                ListCabang.remove(cabang);
            }
        }
        return ListCabang;
    }

    @Override
    public CabangModel getCabangByIdCabang(Long idCabang) {
        Optional<CabangModel> cabang = cabangDB.findById(idCabang);
        if(cabang.isPresent()) {
            return cabang.get();
        }
        return null;
    }

    @Override
    public void updateCabang(CabangModel cabang) {
        cabangDB.save(cabang);
    }

    @Override
    public int deleteCabang(CabangModel cabang) {
        int status = cabang.getStatus();
        List<ItemCabangModel> listItem = cabang.getListItemCabang();
        Boolean checkItem = listItem.size() == 0;

        if (checkItem) {
            cabangDB.delete(cabang);
            return 1;
        }
        return 0;
    }

    @Override
    public ItemCabangModel getItemCabangInCabangByIdItemCabang(CabangModel cabang, String uuidItemCabang) {
        for (ItemCabangModel icm : cabang.getListItemCabang()) {
            if (icm.getUuid_item().equals(uuidItemCabang)) {
                return icm;
            }
        }
        return null;
    }
}
