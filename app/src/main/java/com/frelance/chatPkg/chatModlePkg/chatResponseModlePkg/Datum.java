
package com.frelance.chatPkg.chatModlePkg.chatResponseModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image_name")
    @Expose
    private String imageName;
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
     * @param imageName
     * @param dateCreated
     * @param id
     */
    public Datum(String id, String imageName, String dateCreated) {
        super();
        this.id = id;
        this.imageName = imageName;
        this.dateCreated = dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

}
