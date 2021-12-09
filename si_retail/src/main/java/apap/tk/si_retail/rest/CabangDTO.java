package apap.tk.si_retail.rest;
import apap.tk.si_retail.model.CabangModel;

public class CabangDTO {

    public String nama;
    public String alamat;
    public Integer ukuran;
    public String no_telp;

    public CabangModel convertToCabang(){
        CabangModel cabang = new CabangModel();
        cabang.setNama(nama);
        cabang.setAlamat(alamat);
        cabang.setUkuran(ukuran);
        cabang.setNo_telp(no_telp);
        cabang.setStatus(0);
        return cabang;
    }
}
