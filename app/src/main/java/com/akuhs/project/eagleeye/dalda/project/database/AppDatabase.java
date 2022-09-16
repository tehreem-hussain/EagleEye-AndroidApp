package com.akuhs.project.eagleeye.dalda.project.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.akuhs.project.eagleeye.dalda.project.dao.ProductPriceDao;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;

@Database(entities = {ProductPriceResponse.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductPriceDao taskDao();
}
