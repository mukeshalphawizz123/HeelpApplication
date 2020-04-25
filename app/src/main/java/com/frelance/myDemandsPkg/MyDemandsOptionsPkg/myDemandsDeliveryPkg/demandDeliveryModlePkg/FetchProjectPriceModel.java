
package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchProjectPriceModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("amount")
    @Expose
    private String amount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FetchProjectPriceModel() {
    }

    /**
     * 
     * @param amount
     * @param message
     * @param status
     */
    public FetchProjectPriceModel(Boolean status, String message, String amount) {
        super();
        this.status = status;
        this.message = message;
        this.amount = amount;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
