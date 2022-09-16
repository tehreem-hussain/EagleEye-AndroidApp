
package com.akuhs.project.eagleeye.dalda.project.model.authenticate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticateResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private AuthenticateResult result;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthenticateResponse() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public AuthenticateResponse(String status, AuthenticateResult result) {
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

    public AuthenticateResult getResult() {
        return result;
    }

    public void setResult(AuthenticateResult result) {
        this.result = result;
    }

}
