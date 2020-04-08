
package com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("attachment_name")
    @Expose
    private String attachmentName;
    @SerializedName("file_ext")
    @Expose
    private String fileExt;
    @SerializedName("mime_type")
    @Expose
    private String mimeType;
    @SerializedName("message_date_time")
    @Expose
    private String messageDateTime;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("message_type")
    @Expose
    private String messageType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param senderId
     * @param receiverId
     * @param messageType
     * @param ipAddress
     * @param attachmentName
     * @param id
     * @param mimeType
     * @param message
     * @param fileExt
     * @param messageDateTime
     */
    public Datum(String id, String senderId, String receiverId, String message, String attachmentName, String fileExt, String mimeType, String messageDateTime, String ipAddress, String messageType) {
        super();
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.attachmentName = attachmentName;
        this.fileExt = fileExt;
        this.mimeType = mimeType;
        this.messageDateTime = messageDateTime;
        this.ipAddress = ipAddress;
        this.messageType = messageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(String messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
