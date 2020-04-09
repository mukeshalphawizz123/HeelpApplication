
package com.frelance.chatPkg.chatModlePkg.voiceRecordingModle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("voicefile_name")
    @Expose
    private String voicefileName;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param dateCreated
     * @param id
     * @param voicefileName
     */
    public Datum(String id, String voicefileName, String dateCreated) {
        super();
        this.id = id;
        this.voicefileName = voicefileName;
        this.dateCreated = dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoicefileName() {
        return voicefileName;
    }

    public void setVoicefileName(String voicefileName) {
        this.voicefileName = voicefileName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

}
