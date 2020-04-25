
package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleasePaymenModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Boolean data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReleasePaymenModel() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public ReleasePaymenModel(Boolean status, String message, Boolean data) {
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

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

}
