
package com.frelance.homeTablayout.homeModel.PublierUneDemandeModelPkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfProjectModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Projects")
    @Expose
    private List<Project> projects = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ListOfProjectModel() {
    }

    /**
     * 
     * @param projects
     * @param status
     */
    public ListOfProjectModel(Boolean status, List<Project> projects) {
        super();
        this.status = status;
        this.projects = projects;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
