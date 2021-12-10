package apap.tk.si_retail.service;
import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.rest.KuponModel;
import java.util.List;

public interface KuponRestService {
    List<KuponModel> getAllKupon();
    ItemCabangModel applyKupon(Long idItem, int idKupon, float discountAmount);
}
