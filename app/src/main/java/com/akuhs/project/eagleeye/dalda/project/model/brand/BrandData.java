
package com.akuhs.project.eagleeye.dalda.project.model.brand;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "brands")
public class BrandData {

    @PrimaryKey
    @SerializedName("brand_id")
    @Expose
    private int brand_id;

    @SerializedName("brand_abr")
    @Expose
    private String brandAbr;

    @SerializedName("brand_sku")
    @Expose
    private int limit;

    private String brandSku="0";
    private String brandOrder="0";



    /**
     * No args constructor for use in serialization
     * 
     */
    public BrandData() {
    }

    /**
     * 
     * @param brandAbr
     * @param brandSku
     */
    public BrandData(String brandAbr, String brandSku) {
        super();
        this.brandAbr = brandAbr;
        this.brandSku = brandSku;
    }

    public String getBrandAbr() {
        return brandAbr;
    }

    public void setBrandAbr(String brandAbr) {
        this.brandAbr = brandAbr;
    }

    public String getBrandSku() {
        return brandSku;
    }

    public void setBrandSku(String brandSku) {
        this.brandSku = brandSku;
    }

    public String getBrandOrder() {
        return brandOrder;
    }

    public void setBrandOrder(String brandOrder) {
        this.brandOrder = brandOrder;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
