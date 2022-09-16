package com.akuhs.project.eagleeye.dalda.project.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.akuhs.project.eagleeye.dalda.project.dao.BrandDataDao;
import com.akuhs.project.eagleeye.dalda.project.dao.ShopBrandDataDao;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.test.EagleEyeDatabase;

import java.util.List;

public class ShopBrandDataRepository {

    public ShopBrandDataDao brandDataDao;
    public LiveData<List<ShopBrandData>> getAllBrandData;
    private EagleEyeDatabase database;


    public ShopBrandDataRepository(Application application) {
        database = EagleEyeDatabase.getInstance(application);
        brandDataDao = database.shopBrandDataDao();
        getAllBrandData = brandDataDao.getAllBrandData();

    }

    public void insert(List<ShopBrandData> posts) {

        new InsertAsyncTask(brandDataDao).execute(posts);
    }

    public void update() {

        new UpdateAsyncTask(brandDataDao).execute();
    }

    public void insert(ShopBrandData posts) {

        new InsertShopAsyncTask(brandDataDao).execute(posts);
    }

    public void delete(List<ShopBrandData> posts) {

        new InsertAsyncTask(brandDataDao).execute(posts);
    }

    public LiveData<List<ShopBrandData>> getAllBrandData() {
        return getAllBrandData;
    }


    private static class InsertAsyncTask extends AsyncTask<List<ShopBrandData>, Void, Void> {
        private ShopBrandDataDao brandDataDao;

        public InsertAsyncTask(ShopBrandDataDao brandDataDao) {
            this.brandDataDao = brandDataDao;
        }

        @Override
        protected Void doInBackground(List<ShopBrandData>... lists) {
            brandDataDao.insert(lists[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<List<ShopBrandData>, Void, Void> {
        private ShopBrandDataDao brandDataDao;

        public UpdateAsyncTask(ShopBrandDataDao brandDataDao) {
            this.brandDataDao = brandDataDao;
        }

        @Override
        protected Void doInBackground(List<ShopBrandData>... lists) {
            brandDataDao.update();
            return null;
        }
    }
    private static class InsertShopAsyncTask extends AsyncTask<ShopBrandData, Void, Void> {
        private ShopBrandDataDao brandDataDao;

        public InsertShopAsyncTask(ShopBrandDataDao brandDataDao) {
            this.brandDataDao = brandDataDao;
        }

        @Override
        protected Void doInBackground(ShopBrandData... lists) {
            brandDataDao.insertShopBrand(lists[0]);
            return null;
        }
    }


}
