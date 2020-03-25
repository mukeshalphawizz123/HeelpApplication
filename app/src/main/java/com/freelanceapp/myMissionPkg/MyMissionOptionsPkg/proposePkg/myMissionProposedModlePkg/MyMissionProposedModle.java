
package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyMissionProposedModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private Boolean message;
    @SerializedName("your_missions")
    @Expose
    private List<YourMission> yourMissions = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MyMissionProposedModle() {
    }

    /**
     * 
     * @param message
     * @param yourMissions
     * @param status
     */
    public MyMissionProposedModle(Boolean status, Boolean message, List<YourMission> yourMissions) {
        super();
        this.status = status;
        this.message = message;
        this.yourMissions = yourMissions;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

    public List<YourMission> getYourMissions() {
        return yourMissions;
    }

    public void setYourMissions(List<YourMission> yourMissions) {
        this.yourMissions = yourMissions;
    }

}
