
package com.freelanceapp.myDemandsPkg.myRequestModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("Fullname")
    @Expose
    private String fullname;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param date
     * @param categoryImage
     * @param categoryTitle
     * @param description
     * @param id
     * @param fullname
     * @param status
     */
    public Datum(String id, String description, String status, String date, String fullname, String categoryImage, String categoryTitle) {
        super();
        this.id = id;
        this.description = description;
        this.status = status;
        this.date = date;
        this.fullname = fullname;
        this.categoryImage = categoryImage;
        this.categoryTitle = categoryTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

}
