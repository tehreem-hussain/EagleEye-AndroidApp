package com.akuhs.project.eagleeye.dalda.project.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import java.util.List;
import java.util.Map;

@Dao
public interface ShopBrandDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ShopBrandData> brandData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShopBrand(ShopBrandData brandData);

    @Query("SELECT  * FROM shopBrands where bit=0")
    LiveData<List<ShopBrandData>> getAllBrandData();

    @Query("DELETE FROM shopBrands")
    void deleteAll();

    @Query("UPDATE shopBrands SET bit=1 WHERE bit =0 ")
    void update();

//    @Insert
//    void insert(ProductPriceResponse note);
//    @Update
//    void update(ProductPriceResponse note);
//    @Delete
//    void delete(ProductPriceResponse note);
//
////    @Query("DELETE FROM note_table")
////    void deleteAllNotes();
//
//    @Query("SELECT * FROM post")
//    LiveData<List<ProductPriceResponse>> getAllProductPrice();


}

