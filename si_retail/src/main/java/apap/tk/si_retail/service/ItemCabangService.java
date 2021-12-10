package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;

import java.util.List;

public interface ItemCabangService {
    void addItemCabang(ItemCabangModel itemCabang);
    List<ItemCabangModel> getListItemCabang();
    List<ItemCabangModel> getListItemCabangByCabang(CabangModel cabang);
    ItemCabangModel getItemCabangByIdItemCabang(Long idItemCabang);
    void tambahanStok(String uuid, Integer stok, Long idCabang);
}
