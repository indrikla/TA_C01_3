package apap.tk.si_retail.restcontroller;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cabang")
public class CabangRestController {
    @Autowired
    CabangRestService cabangRestService;

    @PostMapping(value="/create")
    private CabangModel createCabang(@Valid @RequestBody CabangModel cabang, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        }else{
            return cabangRestService.createCabang(cabang);
        }
    }
}
