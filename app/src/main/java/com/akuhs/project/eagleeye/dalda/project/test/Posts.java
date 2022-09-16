package com.akuhs.project.eagleeye.dalda.project.test;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "post",indices = @Index(value = {"postId"},unique = true))
public class Posts {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "postId")
    private int postId;

    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("trade_price")
    @Expose
    private String tradePrice;
    @SerializedName("retail_price")
    @Expose
    private String retailPrice;
    @SerializedName("invoice_price")
    @Expose
    private String invoicePrice;

    public Posts() {
    }

    /**
     *
     * @param tradePrice
     * @param retailPrice
     * @param productDescription
     * @param invoicePrice
     */
    public Posts(String productDescription, String tradePrice, String retailPrice, String invoicePrice) {
        super();
        this.productDescription = productDescription;
        this.tradePrice = tradePrice;
        this.retailPrice = retailPrice;
        this.invoicePrice = invoicePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(String invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "postId=" + postId +
                ", productDescription='" + productDescription + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", retailPrice='" + retailPrice + '\'' +
                ", invoicePrice='" + invoicePrice + '\'' +
                '}';
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
