package com.akuhs.project.eagleeye.dalda.project.interfaces;

import com.akuhs.project.eagleeye.dalda.project.model.attendance.AttendanceModel;
import com.akuhs.project.eagleeye.dalda.project.model.authenticate.AuthenticateResponse;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandModel;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.getSalesShopDetail.GetSalesShopModel;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceModel;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopModel;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSales;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionModel;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionResponse;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingModel;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingResponse;
import com.akuhs.project.eagleeye.dalda.project.test.Posts;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    //******************************************* Authenticate ***************************************************//

    //old
    @POST("authenticate")
    Call<AuthenticateResponse> oldgetUserAuthenticate(
            @Query("user_emp_Id") String user_emp_Id,
            @Query("user_password") String user_password
    );


    @POST("user/authentication")
    Call<AuthenticateResponse> getUserAuthenticate(
            @Query("user_emp_Id") String user_emp_Id,
            @Query("user_password") String user_password,
            @Query("app_version") String app_version,
            @Query("android_app_version_name") String app_version_name,
            @Query("android_app_version_code") int app_version_code,
            @Query("user_device_id") String user_device_id
    );

    @GET("brand")
    Call<BrandModel> getBrandData(
    );

//    shop_name=test&shop_address=hh&category_name=Retail&
//    user_emp_Id=2894&shop_latitude=66654&shop_longitude=3443&shop_image_url=fff
//    &shop_total_sku_available=2&shop_total_sku_order=3&shop_remarks=fff&dsr=kkk

    @POST("shopOld")
    Call<BrandModel> oldPostShop(

            @Query("shop_name") String shop_name,
            @Query("shop_address") String shop_address,
            @Query("category_name") String category_name,
            @Query("user_emp_Id") String user_emp_Id,
            @Query("shop_latitude") double shop_latitude,
            @Query("shop_longitude") double shop_longitude,
            @Query("shop_image_url") String shop_image_url,
            @Query("shop_total_sku_available") int shop_total_sku_available,
            @Query("shop_total_sku_order") int shop_total_sku_order,
            @Query("dsr") String dsr,
            @Query("shop_date_added") String shop_date_added,
            @Query("shop_added_time") String shop_added_time,
            @Body List<Map<String, Object>> brandData
            );

    @POST("shop")
    @FormUrlEncoded
    Call<BrandModel> postShop(
            @Field("imgShop") String base64,
            @Query("shop_name") String shop_name,
            @Query("shop_address") String shop_address,
            @Query("category_name") String category_name,
            @Query("user_emp_Id") String user_emp_Id,
            @Query("shop_latitude") String shop_latitude,
            @Query("shop_longitude") String shop_longitude,
            @Query("shopKey") String shopKey,
            @Query("shop_total_sku_available") int shop_total_sku_available,
            @Query("shop_total_sku_order") int shop_total_sku_order,
            @Query("dsr") String dsr,
            @Query("shop_date_added") String shop_date_added,
            @Query("shop_added_time") String shop_added_time,
            @Query("shop_address_details") String shop_address_details,
            @Query("shop_location_region") String shopLocationRegion,
            @Query("shop_remarks") String remarks
    );



    @POST("shopOld")
    Call<BrandModel> postShopData(@Body List<ShopResponse> shop);


    @POST("shopBrand")
    Call<BrandModel> postShopBrand(
            @Body List<ShopBrandData> brandData
    );





   @POST("shopImg")
   @FormUrlEncoded
   Call<ResponseBody> shopImg(
           @Field("imgShop") String base64
            );



    @GET("shop/{empId}")
    Call<ShopModel> getUserShop(
            @Path("empId") String empId
    );


    @GET("productPriceList")
    Call<ProductPriceModel> getproductPriceList(
    );

    @GET("productPriceList")
    Call<Posts> getproductPriceListTest(
    );

    @POST("userLoginDetails")
    @FormUrlEncoded
    Call<AuthenticateResponse> postUserLoginDetails(
            @Field("user_location") String user_location_region,
            @Field("user_name") String userName,
            @Field("designation_name") String designationName,
            @Field("user_emp_id") String user_emp_id,
            @Field("user_last_seen") String user_last_seen,
            @Field("app_version") String app_version,
            @Field("android_app_version_name") String app_version_name,
            @Field("android_app_version_code") int app_version_code,
            @Field("user_device_id") String user_device_id
    );

    @POST("userCheckedIn")
    @FormUrlEncoded
    Call<AuthenticateResponse> postUserCheckedIn(
            @Field("user_emp_Id") String user_emp_id,
            @Field("check_in_lat") double check_in_lat,
            @Field("check_in_lng") String check_in_lng,
            @Field("check_in_location") String check_in_location

    );
    @POST("userCheckedOut")
    @FormUrlEncoded
    Call<AuthenticateResponse> postUserCheckedOut(
            @Field("user_emp_Id") String user_emp_id,
            @Field("check_out_lat") double check_out_lat,
            @Field("check_out_lng") String check_out_lng,
            @Field("check_out_location") String check_out_location

    );


    @GET("attendance/checked")
    Call<AttendanceModel> getUserAttendanceChecked(
            @Query("user_emp_Id") String user_emp_id

    );

    @GET("shop/sku")
    Call<StockPositionModel> getSKUList(
    );

    @GET("shop/sku")
    Call<TradeMarketingModel> getSKUListTM(
    );


    @GET("shop/list/details")
    Call<GetSalesShopModel> getSalesShopDetails(
            @Query("user_emp_Id") String user_emp_id

    );

    @GET("shop/stock-position/single")
    Call<StockPositionModel> getStockPositionSingle(
            @Query("shop_data_id") String shop_data_id

    );

    @GET("shop/trade-marketing/single")
    Call<TradeMarketingModel> getTradeMarketingingle(
            @Query("shop_data_id") String shop_data_id

    );


       @GET("shop/list")
    Call<ShopSales> getShopSales(
            @Query("user_emp_Id") String user_emp_Id

    );


    @POST("shop/data/stock-position")
    Call<StockPositionModel> postStockPosition(
            @Body List<StockPositionResponse> data
    );

    @POST("shop/data/trade-marketing")
    Call<StockPositionModel> postTradeMarketing(
            @Body List<TradeMarketingResponse> data
    );


    @POST("shop/data")
    @FormUrlEncoded
    Call<ShopSales> postShopSales(
            @Field("imgShop") String base64,
            @Query("shop_address") String shop_address,
            @Query("shop_latitude") String shop_latitude,
            @Query("shop_longitude") String shop_longitude,
            @Query("shop_remarks") String remarks,
            @Query("shopKey") String shopKey,
            @Query("shop_date_added") String shop_date_added,
            @Query("shop_added_time") String shop_added_time,
            @Query("shop_data_id") String shop_data_id

    );

    @GET("shop/data/stock-position/single")
    Call<StockPositionModel> getStockPosition(
            @Query("shop_key") String shop_key

    );

    @GET("shop/data/trade-marketing/single")
    Call<TradeMarketingModel> getTradeMarketing(
            @Query("shop_key") String shop_key

    );

    @PUT("shop/data/stock-position")
    Call<StockPositionModel> postUpdateStockPosition(
                    @Body List<StockPositionResponse> data
            );

   @PUT("shop/data/trade-marketing")
    Call<TradeMarketingModel> postUpdateTradeMarketing(
                    @Body List<TradeMarketingResponse> data
            );



}

