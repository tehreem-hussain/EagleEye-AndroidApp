package com.akuhs.project.eagleeye.dalda.project.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;

import java.util.List;

@Dao
public interface BrandDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<BrandData> brandData);

    @Query("SELECT  * FROM brands")
    LiveData<List<BrandData>> getAllBrandData();

    @Query("DELETE FROM brands")
    void deleteAll();

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

