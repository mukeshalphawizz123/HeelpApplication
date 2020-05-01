
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactionInModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sent_from")
    @Expose
    private String sentFrom;
    @SerializedName("sent_to")
    @Expose
    private String sentTo;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("tra_id")
    @Expose
    private String traId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("project_title")
    @Expose
    private String projectTitle;

    @SerializedName("sent_from_username")
    @Expose
    private String sent_from_username;

    @SerializedName("sent_to_username")
    @Expose
    private String sent_to_username;


    public String getSent_from_username() {
        return sent_from_username;
    }

    public void setSent_from_username(String sent_from_username) {
        this.sent_from_username = sent_from_username;
    }

    public String getSent_to_username() {
        return sent_to_username;
    }

    public void setSent_to_username(String sent_to_username) {
        this.sent_to_username = sent_to_username;
    }


    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param amount
     * @param sentTo
     * @param createdDate
     * @param sentFrom
     * @param traId
     * @param id
     * @param username
     * @param projectTitle
     */
    public Datum(String id, String sentFrom, String sentTo, String amount, String traId, String createdDate, String username, String projectTitle) {
        super();
        this.id = id;
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.amount = amount;
        this.traId = traId;
        this.createdDate = createdDate;
        this.username = username;
        this.projectTitle = projectTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTraId() {
        return traId;
    }

    public void setTraId(String traId) {
        this.traId = traId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

}
