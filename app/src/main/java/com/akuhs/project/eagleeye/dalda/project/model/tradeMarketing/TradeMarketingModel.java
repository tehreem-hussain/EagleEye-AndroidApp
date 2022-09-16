
package com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TradeMarketingModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<TradeMarketingResponse> result = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TradeMarketingModel() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public TradeMarketingModel(Boolean status, List<TradeMarketingResponse> result) {
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

    public List<TradeMarketingResponse> getResult() {
        return result;
    }

    public void setResult(List<TradeMarketingResponse> result) {
        this.result = result;
    }

}
