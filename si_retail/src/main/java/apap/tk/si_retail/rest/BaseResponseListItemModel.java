package apap.tk.si_retail.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseListItemModel {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private List<ItemModel> listItemCabangDetail;

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

    public List<ItemModel> getItemCabangDetail() {
        return listItemCabangDetail;
    }

    public void setItemCabangDetail(List<ItemModel> listItemCabangDetail) {
        this.listItemCabangDetail = listItemCabangDetail;
    }
}
