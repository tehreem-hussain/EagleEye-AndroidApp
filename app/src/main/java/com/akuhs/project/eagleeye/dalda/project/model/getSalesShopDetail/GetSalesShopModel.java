
package com.akuhs.project.eagleeye.dalda.project.model.getSalesShopDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSalesShopModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<GetSalesShopResponse> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<GetSalesShopResponse> getResult() {
        return result;
    }

    public void setResult(List<GetSalesShopResponse> result) {
        this.result = result;
    }

}
