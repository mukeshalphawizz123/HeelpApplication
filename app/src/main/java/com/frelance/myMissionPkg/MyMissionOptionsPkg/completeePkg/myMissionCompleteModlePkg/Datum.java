
package com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.myMissionCompleteModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("your_comments")
    @Expose
    private String yourComments;
    @SerializedName("project_files")
    @Expose
    private String projectFiles;
    @SerializedName("project_image")
    @Expose
    private String project_image;

    @SerializedName("project_status")
    @Expose
    private String projectStatus;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;

    /**
     * No args constructor for use in serialization
     */
    public Datum() {
    }

    /**
     * @param projectStatus
     * @param firstName
     * @param dateCreated
     * @param projectFiles
     * @param pictureUrl
     * @param yourComments
     * @param id
     * @param projectId
     * @param userId
     */
    public Datum(String id, String projectId, String userId, String yourComments, String projectFiles, String projectStatus, String dateCreated, String firstName, String pictureUrl) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.yourComments = yourComments;
        this.projectFiles = projectFiles;
        this.projectStatus = projectStatus;
        this.dateCreated = dateCreated;
        this.firstName = firstName;
        this.pictureUrl = pictureUrl;
    }

    public String getProject_image() {
        return project_image;
    }

    public void setProject_image(String project_image) {
        this.project_image = project_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getYourComments() {
        return yourComments;
    }

    public void setYourComments(String yourComments) {
        this.yourComments = yourComments;
    }

    public String getProjectFiles() {
        return projectFiles;
    }

    public void setProjectFiles(String projectFiles) {
        this.projectFiles = projectFiles;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
