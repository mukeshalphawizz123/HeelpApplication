
package com.freelanceapp.detailsPkg.detailModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YourMission {

    @SerializedName("mission_id")
    @Expose
    private String missionId;
    @SerializedName("mission_description")
    @Expose
    private String missionDescription;
    @SerializedName("mission_status")
    @Expose
    private String missionStatus;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("mission_budget")
    @Expose
    private String missionBudget;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;

    /**
     * No args constructor for use in serialization
     * 
     */
    public YourMission() {
    }

    /**
     * 
     * @param date
     * @param missionBudget
     * @param duration
     * @param missionId
     * @param categoryImage
     * @param categoryTitle
     * @param missionStatus
     * @param missionDescription
     */
    public YourMission(String missionId, String missionDescription, String missionStatus, String date, String categoryImage, String missionBudget, String duration, String categoryTitle) {
        super();
        this.missionId = missionId;
        this.missionDescription = missionDescription;
        this.missionStatus = missionStatus;
        this.date = date;
        this.categoryImage = categoryImage;
        this.missionBudget = missionBudget;
        this.duration = duration;
        this.categoryTitle = categoryTitle;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getMissionBudget() {
        return missionBudget;
    }

    public void setMissionBudget(String missionBudget) {
        this.missionBudget = missionBudget;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

}
