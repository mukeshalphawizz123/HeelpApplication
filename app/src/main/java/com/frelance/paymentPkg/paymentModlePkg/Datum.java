
package com.frelance.paymentPkg.paymentModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("mission_id")
    @Expose
    private String missionId;
    @SerializedName("mission_budget")
    @Expose
    private String missionBudget;
    @SerializedName("bank_fee")
    @Expose
    private String bankFee;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param missionBudget
     * @param bankFee
     * @param missionId
     */
    public Datum(String missionId, String missionBudget, String bankFee) {
        super();
        this.missionId = missionId;
        this.missionBudget = missionBudget;
        this.bankFee = bankFee;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getMissionBudget() {
        return missionBudget;
    }

    public void setMissionBudget(String missionBudget) {
        this.missionBudget = missionBudget;
    }

    public String getBankFee() {
        return bankFee;
    }

    public void setBankFee(String bankFee) {
        this.bankFee = bankFee;
    }

}
