package apap.tk.si_retail.restcontroller;

import apap.tk.si_retail.rest.BaseResponseCabangModel;
import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/cabang")
public class CabangRestController {
    @Autowired
    CabangRestService cabangRestService;

    @PostMapping(value="/create")
    private BaseResponseCabangModel createCabang(@Valid @RequestBody CabangModel cabang, BindingResult bindingResult) {
        BaseResponseCabangModel response = new BaseResponseCabangModel();
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        }else{
            try {
                CabangModel newCabang = cabangRestService.createCabang(cabang);
                response.setStatus(201);
                response.setMessage("created");
                response.setCabangModel(newCabang);
            } catch (Exception e) {
                response.setStatus(400);
                response.setMessage(e.toString());
                response.setCabangModel(null);
            }
            return response;
//            return cabangRestService.createCabang(cabang);
        }
    }

    @GetMapping(value="/list-alamat")
    private ArrayList<HashMap<String, String>> retrieveAlamatCabang(){
        return cabangRestService.retrieveAlamatCabang();
    }


}
