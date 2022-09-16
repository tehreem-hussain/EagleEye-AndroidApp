
package com.akuhs.project.eagleeye.dalda.project.model.stockposition;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "stock_position")
public class StockPositionResponse {

    private String shop_key;

    @SerializedName("sku_id")
    @Expose
    private String skuId;

    @SerializedName("sku")
    @Expose
    private String sku;

    private String opening="0";

    private String receiving="0";

    private String sales="0";

    private String closing="0";

    public StockPositionResponse(String shop_key, String skuId, String opening, String receiving, String sales, String closing) {
        this.shop_key = shop_key;
        this.skuId = skuId;
        this.opening = opening;
        this.receiving = receiving;
        this.sales = sales;
        this.closing = closing;
    }

    public String getshop_key() {
        return shop_key;
    }

    public void setshop_key(String shop_key) {
        this.shop_key = shop_key;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getReceiving() {
        return receiving;
    }

    public void setReceiving(String receiving) {
        this.receiving = receiving;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }
}
