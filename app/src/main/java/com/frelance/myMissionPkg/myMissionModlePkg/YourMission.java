
package com.frelance.myMissionPkg.myMissionModlePkg;

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
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("client_id")
    @Expose
    private String client_id;



    /**
     * No args constructor for use in serialization
     */
    public YourMission() {
    }

    /**
     * @param date
     * @param missionId
     * @param categoryImage
     * @param by
     * @param categoryTitle
     * @param missionStatus
     * @param missionDescription
     */
    public YourMission(String missionId, String missionDescription, String missionStatus, String date, String categoryImage, String categoryTitle, String by) {
        super();
        this.missionId = missionId;
        this.missionDescription = missionDescription;
        this.missionStatus = missionStatus;
        this.date = date;
        this.categoryImage = categoryImage;
        this.categoryTitle = categoryTitle;
        this.by = by;
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

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }



}
