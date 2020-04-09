package com.frelance.chatPkg.chatModlePkg;

public class UnReadMessageUserModle {
    private String userId;
    private String name;
    private String imgUrl;
    private String dateAndTime;
    private String senderId;


    public UnReadMessageUserModle(String userId, String name, String imgUrl, String dateAndTime, String senderId) {
        this.userId = userId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.dateAndTime = dateAndTime;
        this.senderId = senderId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


}
