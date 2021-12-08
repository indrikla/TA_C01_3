package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.repository.CabangDB;
import apap.tk.si_retail.repository.UserDB;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CabangRestServiceImpl implements CabangRestService{
    @Autowired
    private CabangDB cabangDB;
    @Autowired
    private UserDB userDB;

    @Override
    public CabangModel createCabang(CabangModel cabang){
        cabang.setStatus(0);
        return cabangDB.save(cabang);
    }
}
