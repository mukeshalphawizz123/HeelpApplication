
package com.frelance.clientProfilePkg.getuserreviewsModulePkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserReviewsModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;
    @SerializedName("Reviews")
    @Expose
    private List<Review> reviews = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetUserReviewsModel() {
    }

    /**
     * 
     * @param reviews
     * @param userDetail
     * @param message
     * @param status
     */
    public GetUserReviewsModel(Boolean status, String message, UserDetail userDetail, List<Review> reviews) {
        super();
        this.status = status;
        this.message = message;
        this.userDetail = userDetail;
        this.reviews = reviews;
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

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
