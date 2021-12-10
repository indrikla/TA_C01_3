package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.repository.CabangDB;
import apap.tk.si_retail.repository.UserDB;
import apap.tk.si_retail.rest.CabangDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CabangRestServiceImpl implements CabangRestService{
    @Autowired
    private CabangDB cabangDB;

    @Override
    public CabangModel createCabang(CabangModel cabang){
        cabang.setStatus(2);
        return cabangDB.save(cabang);
    }

    @Override
    public CabangModel createRequestCabang(CabangDTO cabangDTO) {
        CabangModel newCabang = cabangDTO.convertToCabang();
        return cabangDB.save(newCabang);
    }

    @Override
    public ArrayList<HashMap<String, String>> retrieveAlamatCabang() {
        List<CabangModel> listCabang = cabangDB.findAll();
        ArrayList<HashMap<String, String>> res = new ArrayList<>();

        for (CabangModel cabang: listCabang) {
            HashMap<String, String> alamatCabang = new HashMap<>();
            Long id = cabang.getId();
            String idCabang = String.valueOf(id);
            String alamat = cabang.getAlamat();
            alamatCabang.put("id",idCabang);
            alamatCabang.put("alamat",alamat);
            res.add(alamatCabang);
        }
        return res;
    }
}
