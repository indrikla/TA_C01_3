package apap.tk.si_retail.service;

import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.repository.ItemCabangDB;
import apap.tk.si_retail.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class ItemCabangRestServiceImpl implements ItemCabangRestService {

    @Autowired
    ItemCabangDB itemCabangDB;

    private final WebClient webClient;

    public ItemCabangRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.siItemUrl).build();
    }

    @Override
    public List<ItemModel> retrieveListItemModel() {
        try {
            BaseResponseListItemModel response = this.webClient.get()
                    .uri(Setting.siItemUrl + "api/item")
                    .retrieve()
                    .bodyToMono(BaseResponseListItemModel.class).block();
            return response.getItemCabangDetail();
        } catch(Exception exc)  {
            return null;
        }
    }

    @Override
    public ItemModel getItemCabangModelByUuid(String uuid) {
        try {
            BaseResponseItemModel response = this.webClient.get()
                    .uri(Setting.siItemUrl + "api/item/" + uuid)
                    .retrieve()
                    .bodyToMono(BaseResponseItemModel.class).block();
            return response.getItemCabangModel();
        } catch(Exception exc)  {
            return null;
        }
    }

    @Override
    public ItemDetailUpdate updateStokItem(String uuid, int stok) {
        ItemDetailUpdate data = new ItemDetailUpdate();
        data.setStok(stok);
        try {
            ItemDetailUpdate response = this.webClient.put()
                    .uri(Setting.siItemUrl + "api/item/" + uuid)
                    .syncBody(data)
                    .retrieve()
                    .bodyToMono(ItemDetailUpdate.class).block();
            return response;
        } catch(Exception exc)  {
            return null;
        }

    }
}
