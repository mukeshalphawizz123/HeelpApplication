
package com.frelance.myDemandsPkg.MyDemandsOptionsPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoDisputeModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GoDisputeModle() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public GoDisputeModle(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
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

}
