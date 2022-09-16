
package com.akuhs.project.eagleeye.dalda.project.model.shop;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<ShopResponse> result = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ShopModel() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public ShopModel(String status, List<ShopResponse> result) {
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

    public List<ShopResponse> getResult() {
        return result;
    }

    public void setResult(List<ShopResponse> result) {
        this.result = result;
    }

}
