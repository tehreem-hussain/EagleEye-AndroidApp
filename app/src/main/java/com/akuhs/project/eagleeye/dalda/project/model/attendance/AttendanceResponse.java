
package com.akuhs.project.eagleeye.dalda.project.model.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceResponse {

    @SerializedName("attendance_id")
    @Expose
    private String attendanceId;
    @SerializedName("user_emp_Id")
    @Expose
    private String userEmpId;
    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;
    @SerializedName("check_in_location")
    @Expose
    private String checkInLocation;
    @SerializedName("check_out_location")
    @Expose
    private String checkOutLocation;
    @SerializedName("check_in_lat")
    @Expose
    private String checkInLat;
    @SerializedName("check_in_lng")
    @Expose
    private String checkInLng;
    @SerializedName("check_out_lat")
    @Expose
    private String checkOutLat;
    @SerializedName("check_out_lng")
    @Expose
    private String checkOutLng;
    @SerializedName("attendance_date")
    @Expose
    private String attendanceDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AttendanceResponse() {
    }

    /**
     * 
     * @param checkInLocation
     * @param checkOutLat
     * @param userEmpId
     * @param checkIn
     * @param checkOutLocation
     * @param checkInLat
     * @param checkOutLng
     * @param checkInLng
     * @param checkOut
     * @param attendanceDate
     * @param attendanceId
     */
    public AttendanceResponse(String attendanceId, String userEmpId, String checkIn, String checkOut, String checkInLocation, String checkOutLocation, String checkInLat, String checkInLng, String checkOutLat, String checkOutLng, String attendanceDate) {
        super();
        this.attendanceId = attendanceId;
        this.userEmpId = userEmpId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.checkInLocation = checkInLocation;
        this.checkOutLocation = checkOutLocation;
        this.checkInLat = checkInLat;
        this.checkInLng = checkInLng;
        this.checkOutLat = checkOutLat;
        this.checkOutLng = checkOutLng;
        this.attendanceDate = attendanceDate;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getCheckInLocation() {
        return checkInLocation;
    }

    public void setCheckInLocation(String checkInLocation) {
        this.checkInLocation = checkInLocation;
    }

    public String getCheckOutLocation() {
        return checkOutLocation;
    }

    public void setCheckOutLocation(String checkOutLocation) {
        this.checkOutLocation = checkOutLocation;
    }

    public String getCheckInLat() {
        return checkInLat;
    }

    public void setCheckInLat(String checkInLat) {
        this.checkInLat = checkInLat;
    }

    public String getCheckInLng() {
        return checkInLng;
    }

    public void setCheckInLng(String checkInLng) {
        this.checkInLng = checkInLng;
    }

    public String getCheckOutLat() {
        return checkOutLat;
    }

    public void setCheckOutLat(String checkOutLat) {
        this.checkOutLat = checkOutLat;
    }

    public String getCheckOutLng() {
        return checkOutLng;
    }

    public void setCheckOutLng(String checkOutLng) {
        this.checkOutLng = checkOutLng;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

}
