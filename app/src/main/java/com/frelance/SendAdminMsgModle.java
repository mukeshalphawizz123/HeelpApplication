
package com.frelance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendAdminMsgModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private Boolean message;
    @SerializedName("data")
    @Expose
    private Boolean data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SendAdminMsgModle() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public SendAdminMsgModle(Boolean status, Boolean message, Boolean data) {
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

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

}
