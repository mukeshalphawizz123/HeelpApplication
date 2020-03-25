
package com.freelanceapp.detailsPkg.detailModlePkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissionViewDetailModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("your_missions")
    @Expose
    private List<YourMission> yourMissions = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MissionViewDetailModle() {
    }

    /**
     * 
     * @param message
     * @param yourMissions
     * @param status
     */
    public MissionViewDetailModle(Boolean status, String message, List<YourMission> yourMissions) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<YourMission> getYourMissions() {
        return yourMissions;
    }

    public void setYourMissions(List<YourMission> yourMissions) {
        this.yourMissions = yourMissions;
    }

}
