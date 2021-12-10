package apap.tk.si_retail.rest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//import java.time.Da


@JsonIgnoreProperties(ignoreUnknown = true)
public class KuponModel {

    private Integer id_kupon;

    private boolean status;

    private String coupon_code;

    private String coupon_name;

    private float discountAmount;

//    private date
}
