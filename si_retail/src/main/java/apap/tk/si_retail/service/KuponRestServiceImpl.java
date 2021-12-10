package apap.tk.si_retail.service;
import apap.tk.si_retail.repository.ItemCabangDB;
import apap.tk.si_retail.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class KuponRestServiceImpl implements KuponRestService{
    @Autowired
    ItemCabangDB itemCabangDB;

    private final WebClient webClient;

    public KuponRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.siBusinessUrl).build();
    }


    @Override
    public List<KuponModel> retrieveListKuponModel() {
        try {
            BaseResponseListItemModel response = this.webClient.get()
                    .uri(Setting.siBusinessUrl)
                    .retrieve()
                    .bodyToMono(BaseResponseListItemModel.class).block();
            return null; //TODO: Return apa ya?
        } catch(Exception exc)  {
            return null;
        }
    }


}
