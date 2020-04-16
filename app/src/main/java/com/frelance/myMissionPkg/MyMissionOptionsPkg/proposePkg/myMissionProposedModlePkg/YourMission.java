
package com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg;

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


    @SerializedName("category_title")
    @Expose
    private String category_title;

    @SerializedName("category_image")
    @Expose
    private String category_image;


    @SerializedName("category_description")
    @Expose
    private String category_description;

    @SerializedName("duration")
    @Expose
    private String duration;


    @SerializedName("mission_description")
    @Expose
    private String mission_description;


    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMission_description() {
        return mission_description;
    }

    public void setMission_description(String mission_description) {
        this.mission_description = mission_description;
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
