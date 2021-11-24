package apap.tk.si_retail.service;

import apap.tk.si_retail.model.RoleModel;
import apap.tk.si_retail.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDB roleDB;

    @Override
    public List<RoleModel> getListRole() {
        return roleDB.findAll();
    }
}
