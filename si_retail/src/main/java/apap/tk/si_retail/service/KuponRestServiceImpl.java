package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.repository.ItemCabangDB;
import apap.tk.si_retail.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class KuponRestServiceImpl implements KuponRestService {
    @Autowired
    ItemCabangDB itemCabangDB;

    private final WebClient webClient;

    public KuponRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.siBusinessUrl).build();
    }


    @Override
    public List<KuponModel> getAllKupon() {
      //return itemCabangDB.findAll();

        try {
            BaseResponseListKuponModel response = this.webClient.get()
                    .uri(Setting.siBusinessUrl + "api/list-coupon/")
                    .retrieve()
                    .bodyToMono(BaseResponseListKuponModel.class).block();
            return response.getListKuponDetail();

        } catch(Exception exc)  {
            return null;
        }
    }

    @Override
    public ItemCabangModel applyKupon(Long idItem, int idKupon, float discountAmount) {
        ItemCabangModel itemCabang = itemCabangDB.getById(idItem);
        int diskon = Math.round((discountAmount / 100) * itemCabang.getHarga());
        int hargaItem = itemCabang.getHarga();
        itemCabang.setHarga(hargaItem - diskon);
        itemCabang.setId_promo(idKupon);
        itemCabangDB.save(itemCabang);
        return itemCabang;
    }
}
