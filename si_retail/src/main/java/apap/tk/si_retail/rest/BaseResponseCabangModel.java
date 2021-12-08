package apap.tk.si_retail.rest;

import apap.tk.si_retail.model.CabangModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseCabangModel {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private CabangModel cabangModel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CabangModel getCabangModel() {
        return cabangModel;
    }

    public void setCabangModel(CabangModel cabangModel) {
        this.cabangModel = cabangModel;
    }
}
