package apap.tk.si_retail.service;

import apap.tk.si_retail.model.CabangModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CabangRestService {
    CabangModel createCabang(CabangModel cabang);
    ArrayList<HashMap<String, String>> retrieveAlamatCabang();

}
