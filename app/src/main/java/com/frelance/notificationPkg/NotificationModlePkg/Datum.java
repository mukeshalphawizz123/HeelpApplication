
package com.frelance.notificationPkg.NotificationModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("demand_id")
    @Expose
    private String demandId;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("read_status")
    @Expose
    private String readStatus;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("type_id")
    @Expose
    private String typeId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param readStatus
     * @param notification
     * @param demandId
     * @param created
     * @param notificationId
     * @param typeId
     * @param userType
     * @param userId
     */
    public Datum(String notificationId, String userType, String userId, String demandId, String notification, String readStatus, String created, String typeId) {
        super();
        this.notificationId = notificationId;
        this.userType = userType;
        this.userId = userId;
        this.demandId = demandId;
        this.notification = notification;
        this.readStatus = readStatus;
        this.created = created;
        this.typeId = typeId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
