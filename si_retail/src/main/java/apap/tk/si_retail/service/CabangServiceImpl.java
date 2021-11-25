package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.repository.CabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CabangServiceImpl implements CabangService {
    @Autowired
    CabangDB cabangDB;

    @Override
    public void addCabang(CabangModel cabang) {
        cabangDB.save(cabang);
    }

    @Override
    public List<CabangModel> getListCabang() {
        return cabangDB.findAll();
    }
}
