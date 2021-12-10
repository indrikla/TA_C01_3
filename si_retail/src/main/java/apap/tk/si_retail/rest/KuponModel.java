package apap.tk.si_retail.rest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
public class KuponModel {

    @JsonProperty("ID Coupon")
    private Integer id_kupon;

    @JsonProperty("Coupon Code")
    private String coupon_code;

    @JsonProperty("Coupon Name")
    private String coupon_name;

    @JsonProperty("Discount Amount")
    private float discountAmount;

    @JsonProperty("Expiry Date")
    private LocalDate expiryDate;
}
