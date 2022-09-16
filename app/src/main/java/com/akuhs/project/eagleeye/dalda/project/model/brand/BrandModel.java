
package com.akuhs.project.eagleeye.dalda.project.model.brand;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<BrandData> result = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BrandModel() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public BrandModel(String status, List<BrandData> result) {
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

    public List<BrandData> getResult() {
        return result;
    }

    public void setResult(List<BrandData> result) {
        this.result = result;
    }

}
