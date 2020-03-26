
package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemandCompleteModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private Boolean message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DemandCompleteModle() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public DemandCompleteModle(Boolean status, Boolean message, List<Datum> data) {
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
