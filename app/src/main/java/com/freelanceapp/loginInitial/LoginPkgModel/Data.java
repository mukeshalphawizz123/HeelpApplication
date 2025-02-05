
package com.freelanceapp.loginInitial.LoginPkgModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_email_verify")
    @Expose
    private String isEmailVerify;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alternateEmail")
    @Expose
    private String alternateEmail;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("vendor_file")
    @Expose
    private String vendorFile;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("lastlogged")
    @Expose
    private String lastlogged;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("facebook_status")
    @Expose
    private String facebookStatus;
    @SerializedName("google_status")
    @Expose
    private String googleStatus;
    @SerializedName("auth_token")
    @Expose
    private String authToken;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param profileUrl
     * @param lastName
     * @param country
     * @param googleStatus
     * @param role
     * @param gender
     * @param city
     * @param authToken
     * @param about
     * @param language
     * @param source
     * @param type
     * @param isEmailVerify
     * @param password
     * @param facebookStatus
     * @param modified
     * @param id
     * @param state
     * @param email
     * @param alternateEmail
     * @param pincode
     * @param website
     * @param address
     * @param lastlogged
     * @param address2
     * @param created
     * @param pictureUrl
     * @param ipAddress
     * @param mobileNo
     * @param firstName
     * @param dob
     * @param name
     * @param vendorFile
     * @param username
     * @param status
     */
    public Data(String id, String username, String password, String role, String source, String status, String isEmailVerify, String name, String firstName, String lastName, String email, String alternateEmail, String mobileNo, String website, String pictureUrl, String profileUrl, String vendorFile, String dob, String gender, String about, String type, String address, String address2, String country, String language, String state, String city, String pincode, String ipAddress, String created, String lastlogged, String modified, String facebookStatus, String googleStatus, String authToken) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.source = source;
        this.status = status;
        this.isEmailVerify = isEmailVerify;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.alternateEmail = alternateEmail;
        this.mobileNo = mobileNo;
        this.website = website;
        this.pictureUrl = pictureUrl;
        this.profileUrl = profileUrl;
        this.vendorFile = vendorFile;
        this.dob = dob;
        this.gender = gender;
        this.about = about;
        this.type = type;
        this.address = address;
        this.address2 = address2;
        this.country = country;
        this.language = language;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.ipAddress = ipAddress;
        this.created = created;
        this.lastlogged = lastlogged;
        this.modified = modified;
        this.facebookStatus = facebookStatus;
        this.googleStatus = googleStatus;
        this.authToken = authToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsEmailVerify() {
        return isEmailVerify;
    }

    public void setIsEmailVerify(String isEmailVerify) {
        this.isEmailVerify = isEmailVerify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getVendorFile() {
        return vendorFile;
    }

    public void setVendorFile(String vendorFile) {
        this.vendorFile = vendorFile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastlogged() {
        return lastlogged;
    }

    public void setLastlogged(String lastlogged) {
        this.lastlogged = lastlogged;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getFacebookStatus() {
        return facebookStatus;
    }

    public void setFacebookStatus(String facebookStatus) {
        this.facebookStatus = facebookStatus;
    }

    public String getGoogleStatus() {
        return googleStatus;
    }

    public void setGoogleStatus(String googleStatus) {
        this.googleStatus = googleStatus;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
