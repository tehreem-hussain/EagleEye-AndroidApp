
package com.akuhs.project.eagleeye.dalda.project.model.brand;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "shopBrands")
public class ShopBrandData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean bit;

    @SerializedName("brand_id")
    @Expose
    private int brand_id;

    @SerializedName("shopKey")
    @Expose
    private String shopKey;

    @SerializedName("brand_abr")
    @Expose
    private String brandAbr;

    @SerializedName("brand_sku")
    @Expose
    private int brand_sku;

    @SerializedName("brand_order")
    @Expose
    private int brand_order;

    public ShopBrandData(boolean bit, int brand_id, String shopKey, String brandAbr, int brand_sku, int brand_order) {
        this.bit= bit;
        this.brand_id = brand_id;
        this.shopKey = shopKey;
        this.brandAbr = brandAbr;
        this.brand_sku = brand_sku;
        this.brand_order = brand_order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBit() {
        return bit;
    }

    public void setBit(boolean bit) {
        this.bit = bit;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public String getBrandAbr() {
        return brandAbr;
    }

    public void setBrandAbr(String brandAbr) {
        this.brandAbr = brandAbr;
    }

    public int getBrand_sku() {
        return brand_sku;
    }

    public void setBrand_sku(int brand_sku) {
        this.brand_sku = brand_sku;
    }

    public int getBrand_order() {
        return brand_order;
    }

    public void setBrand_order(int brand_order) {
        this.brand_order = brand_order;
    }
}
