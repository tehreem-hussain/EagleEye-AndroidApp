
package com.akuhs.project.eagleeye.dalda.project.model.stockposition;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockPositionModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<StockPositionResponse> result = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StockPositionModel() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public StockPositionModel(Boolean status, List<StockPositionResponse> result) {
        super();
        this.status = status;
        this.result = result;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<StockPositionResponse> getResult() {
        return result;
    }

    public void setResult(List<StockPositionResponse> result) {
        this.result = result;
    }

}
