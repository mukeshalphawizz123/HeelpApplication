
package com.freelanceapp.loginInitial.LoginPkgModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loginmodel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("loggged_in_as")
    @Expose
    private String logggedInAs;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Loginmodel() {
    }

    /**
     * 
     * @param data
     * @param otp
     * @param message
     * @param logggedInAs
     * @param status
     */
    public Loginmodel(Boolean status, String message, String logggedInAs, Integer otp, Data data) {
        super();
        this.status = status;
        this.message = message;
        this.logggedInAs = logggedInAs;
        this.otp = otp;
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

    public String getLogggedInAs() {
        return logggedInAs;
    }

    public void setLogggedInAs(String logggedInAs) {
        this.logggedInAs = logggedInAs;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
