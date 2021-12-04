package apap.tk.si_retail.restcontroller;

import apap.tk.si_retail.repository.ItemCabangDB;
import apap.tk.si_retail.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/item")
public class ItemCabangRestController {

    @Autowired
    ItemCabangDB itemCabangDB;

    private final WebClient webClient;

    public ItemCabangRestController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.siItemUrl).build();
    }

//    @Autowired
//    PenjagaDB penjagaDB;
//
//    @Autowired
//    private PenjagaRestService penjagaRestService;
//
//    @PostMapping(value = "/penjaga")
//    private ResponseEntity createPenjaga(
//            @Valid @RequestBody PenjagaModel penjaga,
//            BindingResult bindingResult
//    ) {
//        if(bindingResult.hasFieldErrors()) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
//            );
//        } else {
//            penjagaRestService.createPenjaga(penjaga);
//            return ResponseEntity.ok("Create penjaga success!");
//        }
//    }
//
//    @GetMapping(value = "/penjaga/{noPenjaga}")
//    private PenjagaModel retrievePenjaga(@PathVariable("noPenjaga") Long noPenjaga) {
//        try {
//            return penjagaRestService.getPenjagaByNoPenjaga(noPenjaga);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "No Bioskop " + String.valueOf(noPenjaga) + " Not Found,"
//            );
//        }
//    }
//
//    @DeleteMapping(value = "/penjaga/{noPenjaga}")
//    private ResponseEntity deletePenjaga(@PathVariable("noPenjaga") Long noPenjaga) {
//        try {
//            penjagaRestService.deletePenjaga(noPenjaga);
//            return ResponseEntity.ok("Penjaga with No Penjaga " + String.valueOf(noPenjaga) + " Deleted!");
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Penjaga with No Penjaga " + String.valueOf(noPenjaga) + " Not Found."
//            );
//        } catch (UnsupportedOperationException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "Bioskop where Penjaga is working on is still open!"
//            );
//        }
//    }
//
//    @PutMapping(value = "/penjaga/{noPenjaga}")
//    private ResponseEntity updatePenjaga(@PathVariable("noPenjaga") Long noPenjaga, @RequestBody PenjagaModel penjaga) {
//        try {
//            penjagaRestService.updatePenjaga(noPenjaga, penjaga);
//            return ResponseEntity.ok("Update penjaga success!");
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Penjaga with No Penjaga " + String.valueOf(noPenjaga) + " Not Found."
//            );
//        }
//    }
//
//    @GetMapping(value = "/list-penjaga")
//    private List<PenjagaModel> retrieveListPenjaga(){
//        return penjagaRestService.retrieveListPenjaga();
//    }
//
//    @GetMapping(value = "/penjaga/umur/{noPenjaga}")
//    private PenjagaModel getUmur(@PathVariable("noPenjaga") Long noPenjaga) {
//        PenjagaModel penjaga = penjagaRestService.getPenjagaByNoPenjaga(noPenjaga);
//        penjaga.setUmur(penjagaRestService.getUmur(noPenjaga).getAge());
//        penjagaDB.save(penjaga);
//        return penjaga;
//    }
}


