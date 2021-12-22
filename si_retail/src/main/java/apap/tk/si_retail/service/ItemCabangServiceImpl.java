package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.ItemCabangModel;
import apap.tk.si_retail.repository.ItemCabangDB;
import apap.tk.si_retail.rest.Setting;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ItemCabangServiceImpl implements ItemCabangService {
    
    @Autowired
    ItemCabangDB itemCabangDB;

    private final WebClient webClient;

    public ItemCabangServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.siFactoryUrl).build();
    }

    @Override
    public void addItemCabang(ItemCabangModel itemCabang) {
        itemCabangDB.save(itemCabang);
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

    @Override
    public void tambahanStok(String uuid, Integer stok, Long idCabang){
        Map<String,Object> data = new HashMap();
        data.put("idItem", uuid);
        data.put("tambahanStok", stok);
        data.put("idCabang", Long.toString(idCabang));

        Mono<String> result =  this.webClient.post()
                    .uri("api/v1/create-request-update-item")
                    .accept(MediaType.APPLICATION_JSON)
                    // .body(BodyInserters.fromValue(data))
                    .body(Mono.just(data), HashMap.class)
                    .retrieve()
                    .bodyToMono(String.class);
        
        System.out.println(Mono.just(data).block());
        System.out.println(result.block());
        // System.out.println(Mono.just(data),Map.class);
    }
}
