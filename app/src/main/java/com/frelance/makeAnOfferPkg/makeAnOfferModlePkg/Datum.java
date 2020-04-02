
package com.frelance.makeAnOfferPkg.makeAnOfferModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("mission_id")
    @Expose
    private String missionId;
    @SerializedName("mission_title")
    @Expose
    private String missionTitle;
    @SerializedName("mission_budget")
    @Expose
    private String missionBudget;
    @SerializedName("mission_category")
    @Expose
    private String missionCategory;
    @SerializedName("mission_budget_currency")
    @Expose
    private String missionBudgetCurrency;
    @SerializedName("mission_image")
    @Expose
    private String missionImage;
    @SerializedName("mission_doc")
    @Expose
    private String missionDoc;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("mission_description")
    @Expose
    private String missionDescription;
    @SerializedName("mission_status")
    @Expose
    private String missionStatus;
    @SerializedName("created")
    @Expose
    private String created;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param missionBudget
     * @param missionId
     * @param missionBudgetCurrency
     * @param created
     * @param missionImage
     * @param missionDoc
     * @param missionStatus
     * @param missionDescription
     * @param missionTitle
     * @param missionCategory
     * @param userId
     */
    public Datum(String missionId, String missionTitle, String missionBudget, String missionCategory, String missionBudgetCurrency, String missionImage, String missionDoc, String userId, String missionDescription, String missionStatus, String created) {
        super();
        this.missionId = missionId;
        this.missionTitle = missionTitle;
        this.missionBudget = missionBudget;
        this.missionCategory = missionCategory;
        this.missionBudgetCurrency = missionBudgetCurrency;
        this.missionImage = missionImage;
        this.missionDoc = missionDoc;
        this.userId = userId;
        this.missionDescription = missionDescription;
        this.missionStatus = missionStatus;
        this.created = created;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getMissionTitle() {
        return missionTitle;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }

    public String getMissionBudget() {
        return missionBudget;
    }

    public void setMissionBudget(String missionBudget) {
        this.missionBudget = missionBudget;
    }

    public String getMissionCategory() {
        return missionCategory;
    }

    public void setMissionCategory(String missionCategory) {
        this.missionCategory = missionCategory;
    }

    public String getMissionBudgetCurrency() {
        return missionBudgetCurrency;
    }

    public void setMissionBudgetCurrency(String missionBudgetCurrency) {
        this.missionBudgetCurrency = missionBudgetCurrency;
    }

    public String getMissionImage() {
        return missionImage;
    }

    public void setMissionImage(String missionImage) {
        this.missionImage = missionImage;
    }

    public String getMissionDoc() {
        return missionDoc;
    }

    public void setMissionDoc(String missionDoc) {
        this.missionDoc = missionDoc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMissionDescription() {
        return missionDescription;
    }

    public void setMissionDescription(String missionDescription) {
        this.missionDescription = missionDescription;
    }

    public String getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(String missionStatus) {
        this.missionStatus = missionStatus;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
