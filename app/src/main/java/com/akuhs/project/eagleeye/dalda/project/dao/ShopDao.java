package com.akuhs.project.eagleeye.dalda.project.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;

import java.util.List;

@Dao
public interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShopResponse shop);
//    @Query("SELECT shopDateAdded,shop_location_region,shopAddedDate,shopAddedTime,shopKey,shopLatitude,shopLongitude,shopName,shopRemarks,shopTotalSkuAvailable,shopTotalSkuOrder,dsr,userEmpId,categoryName FROM shop where shopDateAdded == :date ")

//    @Query("SELECT * FROM shop where shopDateAdded == :date ORDER BY id DESC ")
    @Query("SELECT * FROM shop where shopDateAdded BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime') ORDER BY id DESC ")
    LiveData<List<ShopResponse>> getAllShopResponse();

    @Query("SELECT * FROM shop where shopBit =='false' ")
    LiveData<List<ShopResponse>> getAllShopResponseFalse();

    @Query("SELECT * FROM shop where shopBit =='False' " )
    List<ShopResponse> getShopData();

    @Query("DELETE FROM shop")
    void deleteAll();

    @Query("UPDATE shop SET shopBit='true' WHERE shopBit ='false' ")
    void update();

}