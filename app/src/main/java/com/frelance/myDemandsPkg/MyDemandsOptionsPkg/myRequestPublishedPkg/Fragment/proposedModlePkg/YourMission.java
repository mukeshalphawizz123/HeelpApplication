
package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YourMission {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
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
    @SerializedName("mission_image")
    @Expose
    private String missionImage;

    @SerializedName("picture_url")
    @Expose
    private String picture_url;
    @SerializedName("Profile_Rate")
    @Expose
    private String Profile_Rate;

    @SerializedName("duration")
    @Expose
    private String duration;


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getProfile_Rate() {
        return Profile_Rate;
    }

    public void setProfile_Rate(String profile_Rate) {
        Profile_Rate = profile_Rate;
    }


    /**
     * No args constructor for use in serialization
     */
    public YourMission() {
    }

    /**
     * @param missionBudget
     * @param createdDate
     * @param missionId
     * @param missionImage
     * @param offerId
     * @param message
     * @param projectId
     * @param userId
     * @param missionTitle
     * @param missionCategory
     */
    public YourMission(String offerId, String message, String projectId, String userId, String createdDate, String missionId, String missionTitle, String missionBudget, String missionCategory, String missionImage) {
        super();
        this.offerId = offerId;
        this.message = message;
        this.projectId = projectId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.missionId = missionId;
        this.missionTitle = missionTitle;
        this.missionBudget = missionBudget;
        this.missionCategory = missionCategory;
        this.missionImage = missionImage;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getMissionImage() {
        return missionImage;
    }

    public void setMissionImage(String missionImage) {
        this.missionImage = missionImage;
    }

}
