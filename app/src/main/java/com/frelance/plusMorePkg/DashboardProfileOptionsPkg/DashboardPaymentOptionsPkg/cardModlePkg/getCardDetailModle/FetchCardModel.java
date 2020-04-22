
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchCardModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("username")
    @Expose
    private List<Username> username = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FetchCardModel() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     * @param username
     */
    public FetchCardModel(Boolean status, String message, Data data, List<Username> username) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.username = username;
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

    public List<Username> getUsername() {
        return username;
    }

    public void setUsername(List<Username> username) {
        this.username = username;
    }

}
