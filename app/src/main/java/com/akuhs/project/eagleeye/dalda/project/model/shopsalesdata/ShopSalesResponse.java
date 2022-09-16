
package com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "shop_sales")

public class ShopSalesResponse {

    @PrimaryKey
    @SerializedName("shop_data_id")
    @Expose
    private String shopDataId;
    @SerializedName("user_emp_Id")
    @Expose
    private String userEmpId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_type")
    @Expose
    private String shopType;
    @SerializedName("shop_category")
    @Expose
    private String shopCategory;
    @SerializedName("shop_region")
    @Expose
    private String shopRegion;
    @SerializedName("shop_area")
    @Expose
    private String shopArea;

    @SerializedName("shop_bit")
    @Expose
    private String bit;


    @SerializedName("shop_remarks")
    @Expose
    private String remarks;

    @SerializedName("shop_address")
    @Expose
    private String shopAddress;

    @SerializedName("shop_latitude")
    @Expose
    private String shopLatitude;

    @SerializedName("shop_longitude")
    @Expose
    private String shopLongitude;

    @SerializedName("shop_added_date")
    @Expose
    private String shopAddedDate;

    @SerializedName("shop_image_url")
    @Expose
    private String shopImageUrl;



    /**
     * No args constructor for use in serialization
     * 
     */
    public ShopSalesResponse() {
    }

    public ShopSalesResponse(String shopDataId, String bit, String remarks, String shopAddress, String shopLatitude, String shopLongitude, String shopImageUrl) {
        this.shopDataId = shopDataId;
        this.bit = bit;
        this.remarks = remarks;
        this.shopAddress = shopAddress;
        this.shopLatitude = shopLatitude;
        this.shopLongitude = shopLongitude;
        this.shopImageUrl= shopImageUrl;
    }

    /**
     * 
     * @param shopArea
     * @param shopDataId
     * @param shopName
     * @param shopType
     * @param shopRegion
     * @param shopCategory
     */



    public ShopSalesResponse(String shopDataId, String shopName, String shopType, String shopCategory, String shopRegion, String shopArea) {
        super();
        this.shopDataId = shopDataId;
        this.userEmpId = userEmpId;
        this.shopName = shopName;
        this.shopType = shopType;
        this.shopCategory = shopCategory;
        this.shopRegion = shopRegion;
        this.shopArea = shopArea;
    }


    public ShopSalesResponse(String shopDataId, String shopName) {
        this.shopDataId = shopDataId;
        this.shopName = shopName;
    }

    public String getShopDataId() {
        return shopDataId;
    }

    public void setShopDataId(String shopDataId) {
        this.shopDataId = shopDataId;
    }

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopRegion() {
        return shopRegion;
    }

    public void setShopRegion(String shopRegion) {
        this.shopRegion = shopRegion;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

}
