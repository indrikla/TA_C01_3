package apap.tk.si_retail.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseItemModel {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private ItemModel itemCabangModel;

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

    public ItemModel getItemCabangModel() {
        return itemCabangModel;
    }

    public void setItemCabangModel(ItemModel itemCabangModel) {
        this.itemCabangModel = itemCabangModel;
    }
}
