
package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.updateProfileModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllActivities {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("level_of_study")
    @Expose
    private String levelOfStudy;
    @SerializedName("school_address")
    @Expose
    private String schoolAddress;
    @SerializedName("skills")
    @Expose
    private String skills;
    @SerializedName("Field_of_study")
    @Expose
    private String fieldOfStudy;
    @SerializedName("intrested_category")
    @Expose
    private String intrestedCategory;
    @SerializedName("presentation")
    @Expose
    private String presentation;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AllActivities() {
    }

    /**
     * 
     * @param country
     * @param pictureUrl
     * @param levelOfStudy
     * @param schoolAddress
     * @param skills
     * @param presentation
     * @param password
     * @param dob
     * @param name
     * @param id
     * @param intrestedCategory
     * @param fieldOfStudy
     * @param email
     * @param username
     */
    public AllActivities(String id, String name, String email, String username, String password, String dob, String country, String levelOfStudy, String schoolAddress, String skills, String fieldOfStudy, String intrestedCategory, String presentation, String pictureUrl) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.country = country;
        this.levelOfStudy = levelOfStudy;
        this.schoolAddress = schoolAddress;
        this.skills = skills;
        this.fieldOfStudy = fieldOfStudy;
        this.intrestedCategory = intrestedCategory;
        this.presentation = presentation;
        this.pictureUrl = pictureUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public void setLevelOfStudy(String levelOfStudy) {
        this.levelOfStudy = levelOfStudy;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getIntrestedCategory() {
        return intrestedCategory;
    }

    public void setIntrestedCategory(String intrestedCategory) {
        this.intrestedCategory = intrestedCategory;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
