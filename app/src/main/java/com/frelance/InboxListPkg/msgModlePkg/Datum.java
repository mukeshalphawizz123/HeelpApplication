
package com.frelance.InboxListPkg.msgModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;
    @SerializedName("id")
    @Expose
    private String clientId;


/*    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    /**
     * No args constructor for use in serialization
     */
    public Datum() {
    }

    /**
     * @param firstName
     * @param lastName
     * @param clientId
     * @param pictureUrl
     */
    public Datum(String firstName, String lastName, String pictureUrl, String clientId) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureUrl = pictureUrl;
        this.clientId = clientId;
    }


}
