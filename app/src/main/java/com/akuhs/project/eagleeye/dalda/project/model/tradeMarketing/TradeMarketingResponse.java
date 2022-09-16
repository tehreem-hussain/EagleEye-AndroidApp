
package com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trade_marketing")
public class TradeMarketingResponse {


    private String shop_key;
    @SerializedName("sku_id")
    @Expose
    private String skuId;
    @SerializedName("sku")
    @Expose
    private String sku;

    private String quantity="0";

    private String facing="0";

    private String prices;


    /**
     * No args constructor for use in serialization
     * 
     */
    public TradeMarketingResponse() {
    }

    /**
     * 
     * @param facing
     * @param shop_key
     * @param sku
     * @param quantity
     * @param skuId
     * @param prices
     */
    public TradeMarketingResponse(String shop_key, String skuId, String sku, String quantity, String facing, String prices) {
        super();
        this.shop_key = shop_key;
        this.skuId = skuId;
        this.sku = sku;
        this.quantity = quantity;
        this.facing = facing;
        this.prices = prices;
    }
    public TradeMarketingResponse(String shop_key, String skuId, String quantity, String facing, String prices) {
        super();
        this.shop_key = shop_key;
        this.skuId = skuId;
        this.quantity = quantity;
        this.facing = facing;
        this.prices = prices;
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

    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getfacing() {
        return facing;
    }

    public void setfacing(String facing) {
        this.facing = facing;
    }

    public String getprices() {
        return prices;
    }

    public void setprices(String prices) {
        this.prices = prices;
    }

}
