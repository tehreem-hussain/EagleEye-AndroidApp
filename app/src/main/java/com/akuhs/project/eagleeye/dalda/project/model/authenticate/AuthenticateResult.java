
package com.akuhs.project.eagleeye.dalda.project.model.authenticate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticateResult {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_emp_Id")
    @Expose
    private String userEmpId;
    @SerializedName("user_Id")
    @Expose
    private String userId;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_father_name")
    @Expose
    private Object userFatherName;
    @SerializedName("user_location")
    @Expose
    private Object userLocation;
    @SerializedName("user_created_date")
    @Expose
    private String userCreatedDate;
    @SerializedName("user_phone_number")
    @Expose
    private Object userPhoneNumber;
    @SerializedName("designation_name")
    @Expose
    private String designationName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("role_type")
    @Expose
    private String roleType;
    @SerializedName("user_password")
    @Expose
    private String userPassword;

    @SerializedName("user_location_region")
    @Expose
    private String user_location_region;



    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthenticateResult() {
    }

    /**
     * 
     * @param designationName
     * @param departmentName
     * @param userPassword
     * @param userEmpId
     * @param userPhoneNumber
     * @param userLocation
     * @param userEmail
     * @param userName
     * @param roleType
     * @param userId
     * @param userFatherName
     * @param userCreatedDate
     */
    public AuthenticateResult(String userName, String userEmpId, String userId, String userEmail, Object userFatherName, Object userLocation, String userCreatedDate, Object userPhoneNumber, String designationName, String departmentName, String roleType, String userPassword,String user_location_region) {
        super();
        this.userName = userName;
        this.userEmpId = userEmpId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userFatherName = userFatherName;
        this.userLocation = userLocation;
        this.userCreatedDate = userCreatedDate;
        this.userPhoneNumber = userPhoneNumber;
        this.designationName = designationName;
        this.departmentName = departmentName;
        this.roleType = roleType;
        this.userPassword = userPassword;
        this.user_location_region=user_location_region;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Object getUserFatherName() {
        return userFatherName;
    }

    public void setUserFatherName(Object userFatherName) {
        this.userFatherName = userFatherName;
    }

    public Object getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Object userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(String userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public Object getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(Object userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUser_location_region() {
        return user_location_region;
    }

    public void setUser_location_region(String user_location_region) {
        this.user_location_region = user_location_region;
    }
}
