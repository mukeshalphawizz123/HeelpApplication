
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private Boolean message;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("your_missions")
    @Expose
    private List<YourMission> yourMissions = null;

    /**
     * No args constructor for use in serialization
     */
    public GetProfileModle() {
    }

    /**
     * @param message
     * @param yourMissions
     * @param status
     */
    public GetProfileModle(Boolean status, Boolean message, List<YourMission> yourMissions) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
