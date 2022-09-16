package com.akuhs.project.eagleeye.dalda.project.test;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProductPriceResponse> posts);

    @Query("SELECT DISTINCT * FROM post")
    LiveData<List<ProductPriceResponse>>  getAllPosts();

    @Query("DELETE FROM post")
    void deleteAll();
}
