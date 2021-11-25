package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.repository.CabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<CabangModel> getListCabangManager(UserModel manager) {
        return cabangDB.findAllByPenanggungJawab(manager);
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
        // Untuk constraint terkait dengan item dan status, belum bisa di handle pada progres 1.
        int status = cabang.getStatus();

        if (status == 0 || status == 1|| status == 2) {
            cabangDB.delete(cabang);
            return 1;
        }
        return 0;
    }
}
