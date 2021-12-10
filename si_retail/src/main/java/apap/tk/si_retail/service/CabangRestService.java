package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.rest.CabangDTO;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CabangRestService {
    CabangModel createCabang(CabangModel cabang);
    ArrayList<HashMap<String, String>> retrieveAlamatCabang();
    CabangModel createRequestCabang(CabangDTO cabangDTO);

}
