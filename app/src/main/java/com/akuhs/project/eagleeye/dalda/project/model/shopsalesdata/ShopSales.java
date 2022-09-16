
package com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopSales {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("result")
    @Expose
    private List<ShopSalesResponse> result = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ShopSales() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public ShopSales(boolean status, List<ShopSalesResponse> result) {
        super();
        this.status = status;
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ShopSalesResponse> getResult() {
        return result;
    }

    public void setResult(List<ShopSalesResponse> result) {
        this.result = result;
    }

}
