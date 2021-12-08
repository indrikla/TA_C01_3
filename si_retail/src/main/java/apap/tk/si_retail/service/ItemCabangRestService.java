package apap.tk.si_retail.service;

import apap.tk.si_retail.rest.ItemDetailUpdate;
import apap.tk.si_retail.rest.ItemModel;

import java.util.List;

public interface ItemCabangRestService {
    List<ItemModel> retrieveListItemModel();
    ItemModel getItemCabangModelByUuid(String uuid);
    ItemDetailUpdate updateStokItem(String uuid, int stok);
}
