
package com.akuhs.project.eagleeye.dalda.project.model.shop;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "shop",indices = {@Index(value = {"shopKey"},
        unique = true)})
//@Entity(tableName = "shop")
public class ShopResponse {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @SerializedName("shop_key")
    private String shopKey;

    @SerializedName("shop_name")
    @Expose
    private String shopName;

    @SerializedName("shop_address")
    @Expose
    private String shopAddress;

    @SerializedName("category_name")
    @Expose
    private String categoryName;

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

    @SerializedName("shop_total_sku_available")
    @Expose
    private int shopTotalSkuAvailable;
    @SerializedName("shop_total_sku_order")
    @Expose
    private int shopTotalSkuOrder;
    @SerializedName("shop_remarks")
    @Expose
    private String shopRemarks;
    @SerializedName("shop_bit")
    @Expose
    private String shopBit;
    @SerializedName("user_emp_Id")
    @Expose
    private String userEmpId;
    @SerializedName("dsr")
    @Expose
    private String dsr;
    @SerializedName("shop_date_added")
    @Expose
    private String shopDateAdded;
    @SerializedName("shop_added_time")
    @Expose
    private String shopAddedTime;

    @SerializedName("shop_location_region")
    @Expose
    private String shop_location_region;

    @SerializedName("shop_address_details")
    @Expose
    private String shop_address_details;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ShopResponse() {
    }

    /**
     *
     * @param shopKey
     * @param shopName
     * @param shopAddress
     * @param categoryName
     * @param shopLatitude
     * @param shopLongitude
     * @param shopAddedDate
     * @param shopImageUrl
     * @param shopTotalSkuAvailable
     * @param shopTotalSkuOrder
     * @param shopRemarks
     * @param shopBit
     * @param userEmpId
     * @param dsr
     * @param shopDateAdded
     * @param shopAddedTime
     */
    public ShopResponse(String shopKey, String shopName, String shopAddress, String categoryName, String shopLatitude, String shopLongitude, String shopAddedDate, String shopImageUrl, int shopTotalSkuAvailable, int shopTotalSkuOrder, String shopRemarks, String shopBit, String userEmpId, String dsr, String shopDateAdded, String shopAddedTime,String shop_location_region,String shop_address_details) {
        super();
        this.shopKey = shopKey;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.categoryName = categoryName;
        this.shopLatitude = shopLatitude;
        this.shopLongitude = shopLongitude;
        this.shopAddedDate = shopAddedDate;
        this.shopImageUrl = shopImageUrl;
        this.shopTotalSkuAvailable = shopTotalSkuAvailable;
        this.shopTotalSkuOrder = shopTotalSkuOrder;
        this.shopRemarks = shopRemarks;
        this.shopBit = shopBit;
        this.userEmpId = userEmpId;
        this.dsr = dsr;
        this.shopDateAdded = shopDateAdded;
        this.shopAddedTime = shopAddedTime;
        this.shop_location_region=shop_location_region;
        this.shop_address_details=shop_address_details;
    }

    @Ignore
    public ShopResponse(String shopBit) {
        this.shopBit = shopBit;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getShopAddedDate() {
        return shopAddedDate;
    }

    public void setShopAddedDate(String shopAddedDate) {
        this.shopAddedDate = shopAddedDate;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public int getShopTotalSkuAvailable() {
        return shopTotalSkuAvailable;
    }

    public void setShopTotalSkuAvailable(int shopTotalSkuAvailable) {
        this.shopTotalSkuAvailable = shopTotalSkuAvailable;
    }

    public int getShopTotalSkuOrder() {
        return shopTotalSkuOrder;
    }

    public void setShopTotalSkuOrder(int shopTotalSkuOrder) {
        this.shopTotalSkuOrder = shopTotalSkuOrder;
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

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getDsr() {
        return dsr;
    }

    public void setDsr(String dsr) {
        this.dsr = dsr;
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

    public String getShop_location_region() {
        return shop_location_region;
    }

    public void setShop_location_region(String shop_location_region) {
        this.shop_location_region = shop_location_region;
    }

    public String getShop_address_details() {
        return shop_address_details;
    }

    public void setShop_address_details(String shop_address_details) {
        this.shop_address_details = shop_address_details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
