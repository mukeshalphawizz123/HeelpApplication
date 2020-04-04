
package com.frelance.homeTablayout.homeModel.RepondreUneDemandeModelPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YourMission {

    @SerializedName("mission_id")
    @Expose
    private String missionId;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    @SerializedName("client_id")
    @Expose
    private String client_id;

    @SerializedName("mission_title")
    @Expose
    private String missionTitle;
    @SerializedName("mission_description")
    @Expose
    private String missionDescription;
    @SerializedName("mission_image")
    @Expose
    private String missionImage;
    @SerializedName("mission_budget")
    @Expose
    private String missionBudget;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("User_image")
    @Expose
    private String userImage;

    @SerializedName("category_project_image")
    @Expose
    private String category_project_image;

    @SerializedName("mission_doc")
    @Expose
    private String mission_doc;


    public String getCategory_project_image() {
        return category_project_image;
    }

    public void setCategory_project_image(String category_project_image) {
        this.category_project_image = category_project_image;
    }


    public String getMission_doc() {
        return mission_doc;
    }

    public void setMission_doc(String mission_doc) {
        this.mission_doc = mission_doc;
    }


    /**
     * No args constructor for use in serialization
     */
    public YourMission() {
    }

    /**
     * @param missionBudget
     * @param duration
     * @param missionId
     * @param userImage
     * @param missionImage
     * @param missionDescription
     * @param missionTitle
     */
    public YourMission(String missionId, String missionTitle, String missionDescription, String missionImage, String missionBudget, String duration, String userImage) {
        super();
        this.missionId = missionId;
        this.missionTitle = missionTitle;
        this.missionDescription = missionDescription;
        this.missionImage = missionImage;
        this.missionBudget = missionBudget;
        this.duration = duration;
        this.userImage = userImage;
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

    public String getMissionDescription() {
        return missionDescription;
    }

    public void setMissionDescription(String missionDescription) {
        this.missionDescription = missionDescription;
    }

    public String getMissionImage() {
        return missionImage;
    }

    public void setMissionImage(String missionImage) {
        this.missionImage = missionImage;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

}
