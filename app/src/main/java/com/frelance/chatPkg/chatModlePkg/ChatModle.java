package com.frelance.chatPkg.chatModlePkg;

public class ChatModle {
    private String userId;
    private String client;
    private String dateTime;
    private String message;
    private String userImgPath;
    private String userVoice;

    public ChatModle(String userId, String client, String dateTime, String message, String userImgPath
            , String userVoice) {
        this.userId = userId;
        this.client = client;
        this.dateTime = dateTime;
        this.message = message;
        this.userImgPath = userImgPath;
        this.userVoice = userVoice;
    }


    /*    public ChatModle(String userId, String client, String dateTime, String message) {
            this.userId = userId;
            this.client = client;
            this.dateTime = dateTime;
            this.message = message;

        }*/
    public String getUserVoice() {
        return userVoice;
    }

    public void setUserVoice(String userVoice) {
        this.userVoice = userVoice;
    }


    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
