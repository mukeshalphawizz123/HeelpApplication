
package com.freelanceapp.homeTablayout.homeModel.PublierUneDemandeModelPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Project() {
    }

    /**
     * 
     * @param pictureUrl
     * @param description
     * @param title
     * @param projectId
     * @param budget
     */
    public Project(String projectId, String title, String description, String budget, String pictureUrl) {
        super();
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.pictureUrl = pictureUrl;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
