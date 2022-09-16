
package com.akuhs.project.eagleeye.dalda.project.model.productpricing;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "product_price")
public class ProductPriceResponse {

    @PrimaryKey
    private int id;

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

    public ProductPriceResponse() {
    }

    /**
     * 
     * @param tradePrice
     * @param retailPrice
     * @param productDescription
     * @param invoicePrice
     */
    public ProductPriceResponse(String productDescription, String tradePrice, String retailPrice, String invoicePrice) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
