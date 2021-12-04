package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.repository.ItemCabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemCabangServiceImpl implements ItemCabangService {

    @Autowired
    ItemCabangDB itemCabangDB;

    @Override
    public void addItemCabang(ItemCabangModel itemCabang) {

    }

    @Override
    public List<ItemCabangModel> getListItemCabang() {
        return itemCabangDB.findAll();
    }

    @Override
    public List<ItemCabangModel> getListItemCabangByCabang(CabangModel cabang) {
        return itemCabangDB.findItemCabangModelByCabang(cabang);
    }

    @Override
    public ItemCabangModel getItemCabangByIdItemCabang(Long idItemCabang) {
        Optional<ItemCabangModel> itemCabang = itemCabangDB.findById(idItemCabang);
        if(itemCabang.isPresent()) {
            return itemCabang.get();
        }
        return null;
    }
}
