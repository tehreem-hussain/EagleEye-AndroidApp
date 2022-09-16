package com.akuhs.project.eagleeye.dalda.project.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.akuhs.project.eagleeye.dalda.project.dao.ProductPriceDao;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.test.EagleEyeDatabase;

import java.util.List;

public class ProductPriceRepository {

    public ProductPriceDao productPriceDao;
    public LiveData<List<ProductPriceResponse>> getAllProductPrice;
    private EagleEyeDatabase database;


    public ProductPriceRepository(Application application) {
        database = EagleEyeDatabase.getInstance(application);
        productPriceDao = database.productPriceDao();
        getAllProductPrice = productPriceDao.getAllProductPrice();

    }

    public void insert(List<ProductPriceResponse> posts) {

        new InsertAsyncTask(productPriceDao).execute(posts);
    }

    public LiveData<List<ProductPriceResponse>> getAllProductPrice() {
        return getAllProductPrice;
    }


    private static class InsertAsyncTask extends AsyncTask<List<ProductPriceResponse>, Void, Void> {
        private ProductPriceDao productPriceDao;

        public InsertAsyncTask(ProductPriceDao productPriceDao) {
            this.productPriceDao = productPriceDao;
        }

        @Override
        protected Void doInBackground(List<ProductPriceResponse>... lists) {
            productPriceDao.insert(lists[0]);
            return null;
        }
    }


}
