
package com.freelanceapp.notificationPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationCountResponseModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("count_payment")
    @Expose
    private Integer countPayment;
    @SerializedName("count_missionanddemands")
    @Expose
    private Integer countMissionanddemands;
    @SerializedName("count_Offers")
    @Expose
    private Integer countOffers;
    @SerializedName("count_messages")
    @Expose
    private Integer countMessages;
    @SerializedName("count_Reviews")
    @Expose
    private Integer countReviews;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NotificationCountResponseModle() {
    }

    /**
     * 
     * @param countMissionanddemands
     * @param countMessages
     * @param countPayment
     * @param countOffers
     * @param countReviews
     * @param message
     * @param status
     */
    public NotificationCountResponseModle(Boolean status, String message, Integer countPayment, Integer countMissionanddemands, Integer countOffers, Integer countMessages, Integer countReviews) {
        super();
        this.status = status;
        this.message = message;
        this.countPayment = countPayment;
        this.countMissionanddemands = countMissionanddemands;
        this.countOffers = countOffers;
        this.countMessages = countMessages;
        this.countReviews = countReviews;
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

    public Integer getCountPayment() {
        return countPayment;
    }

    public void setCountPayment(Integer countPayment) {
        this.countPayment = countPayment;
    }

    public Integer getCountMissionanddemands() {
        return countMissionanddemands;
    }

    public void setCountMissionanddemands(Integer countMissionanddemands) {
        this.countMissionanddemands = countMissionanddemands;
    }

    public Integer getCountOffers() {
        return countOffers;
    }

    public void setCountOffers(Integer countOffers) {
        this.countOffers = countOffers;
    }

    public Integer getCountMessages() {
        return countMessages;
    }

    public void setCountMessages(Integer countMessages) {
        this.countMessages = countMessages;
    }

    public Integer getCountReviews() {
        return countReviews;
    }

    public void setCountReviews(Integer countReviews) {
        this.countReviews = countReviews;
    }

}
