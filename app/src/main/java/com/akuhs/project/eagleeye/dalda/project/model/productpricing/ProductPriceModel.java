
package com.akuhs.project.eagleeye.dalda.project.model.productpricing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPriceModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<ProductPriceResponse> result = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductPriceModel() {
    }

    /**
     * 
     * @param result
     * @param status
     */
    public ProductPriceModel(String status, List<ProductPriceResponse> result) {
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

    public List<ProductPriceResponse> getResult() {
        return result;
    }

    public void setResult(List<ProductPriceResponse> result) {
        this.result = result;
    }

}
