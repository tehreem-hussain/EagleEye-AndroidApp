
package com.akuhs.project.eagleeye.dalda.project.model.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private AttendanceResponse result;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AttendanceModel() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public AttendanceModel(String status, AttendanceResponse result) {
        super();
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AttendanceResponse getResult() {
        return result;
    }

    public void setResult(AttendanceResponse result) {
        this.result = result;
    }

}
