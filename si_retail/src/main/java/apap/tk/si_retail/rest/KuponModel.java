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

    public Integer getId_kupon() {
        return id_kupon;
    }

    public void setId_kupon(Integer id_kupon) {
        this.id_kupon = id_kupon;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

}
