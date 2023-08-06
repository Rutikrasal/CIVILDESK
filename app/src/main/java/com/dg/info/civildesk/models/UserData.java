package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pin_no")
    @Expose
    private String pinNo;
    @SerializedName("current_address")
    @Expose
    private String currentAddress;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("party")
    @Expose
    private String party;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("aadhar_no")
    @Expose
    private String aadharNo;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("account_holder")
    @Expose
    private String accountHolder;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("academic_level")
    @Expose
    private String academicLevel;
    @SerializedName("imei_no")
    @Expose
    private String imeiNo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("app_access")
    @Expose
    private String appAccess;
    @SerializedName("wordstart_date")
    @Expose
    private String wordstartDate;
    @SerializedName("image")
    @Expose
    private String image;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppAccess() {
        return appAccess;
    }

    public void setAppAccess(String appAccess) {
        this.appAccess = appAccess;
    }

    public String getWordstartDate() {
        return wordstartDate;
    }

    public void setWordstartDate(String wordstartDate) {
        this.wordstartDate = wordstartDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
