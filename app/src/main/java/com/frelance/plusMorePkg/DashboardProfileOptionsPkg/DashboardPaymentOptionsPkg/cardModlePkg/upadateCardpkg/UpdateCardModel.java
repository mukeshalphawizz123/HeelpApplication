
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.upadateCardpkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCardModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateCardModel() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public UpdateCardModel(Boolean status, String message, Data data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
