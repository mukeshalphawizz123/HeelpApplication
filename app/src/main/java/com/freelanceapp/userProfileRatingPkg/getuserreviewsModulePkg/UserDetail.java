
package com.freelanceapp.userProfileRatingPkg.getuserreviewsModulePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;
    @SerializedName("rating_avg")
    @Expose
    private Integer ratingAvg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserDetail() {
    }

    /**
     * 
     * @param pictureUrl
     * @param fullName
     * @param ratingAvg
     */
    public UserDetail(String fullName, String pictureUrl, Integer ratingAvg) {
        super();
        this.fullName = fullName;
        this.pictureUrl = pictureUrl;
        this.ratingAvg = ratingAvg;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(Integer ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

}
