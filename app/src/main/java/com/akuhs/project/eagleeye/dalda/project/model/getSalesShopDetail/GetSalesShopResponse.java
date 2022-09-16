
package com.akuhs.project.eagleeye.dalda.project.model.getSalesShopDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSalesShopResponse {

    @SerializedName("shop_data_id")
    @Expose
    private String shopDataId;
    @SerializedName("shop_key")
    @Expose
    private String shopKey;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_latitude")
    @Expose
    private String shopLatitude;
    @SerializedName("shop_longitude")
    @Expose
    private String shopLongitude;
    @SerializedName("shop_image_url")
    @Expose
    private String shopImageUrl;
    @SerializedName("shop_remarks")
    @Expose
    private String shopRemarks;
    @SerializedName("shop_bit")
    @Expose
    private String shopBit;
    @SerializedName("shop_date_added")
    @Expose
    private String shopDateAdded;
    @SerializedName("shop_added_time")
    @Expose
    private String shopAddedTime;
    @SerializedName("user_emp_Id")
    @Expose
    private String userEmpId;
    @SerializedName("user_name")
    @Expose
    private String userName;
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

    public String getShopDataId() {
        return shopDataId;
    }

    public void setShopDataId(String shopDataId) {
        this.shopDataId = shopDataId;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public String getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public String getShopRemarks() {
        return shopRemarks;
    }

    public void setShopRemarks(String shopRemarks) {
        this.shopRemarks = shopRemarks;
    }

    public String getShopBit() {
        return shopBit;
    }

    public void setShopBit(String shopBit) {
        this.shopBit = shopBit;
    }

    public String getShopDateAdded() {
        return shopDateAdded;
    }

    public void setShopDateAdded(String shopDateAdded) {
        this.shopDateAdded = shopDateAdded;
    }

    public String getShopAddedTime() {
        return shopAddedTime;
    }

    public void setShopAddedTime(String shopAddedTime) {
        this.shopAddedTime = shopAddedTime;
    }

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
