package com.akuhs.project.eagleeye.dalda.project.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.akuhs.project.eagleeye.dalda.project.dao.ShopDao;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.test.EagleEyeDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShopDataRepository {

    public ShopDao shopDataDao;
    public LiveData<List<ShopResponse>> getAllShopResponse;
    public LiveData<List<ShopResponse>> getAllShopResponseFalse;
    public List<ShopResponse> getShopData;
    private EagleEyeDatabase database;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat dtime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
    String date = df.format(c);

    public ShopDataRepository(Application application) {
        database = EagleEyeDatabase.getInstance(application);
        shopDataDao = database.shopDataDao();

        getAllShopResponse = shopDataDao.getAllShopResponse();
        getAllShopResponseFalse= shopDataDao.getAllShopResponseFalse();

//        getShopData = shopDataDao.getShopData();

    }

    public void insert(ShopResponse shop) {

        new InsertAsyncTask(shopDataDao).execute(shop);
    }

    public void update() {

        new UpdateAsyncTask(shopDataDao).execute();
    }

    public LiveData<List<ShopResponse>> getAllShopResponse() {
        return getAllShopResponse;
    }

    public LiveData<List<ShopResponse>> getAllShopResponseFalse() {
        return getAllShopResponseFalse;
    }

    public List<ShopResponse> getShopData() {
        return getShopData;
    }

    private static class InsertAsyncTask extends AsyncTask<ShopResponse, Void, Void> {
        private ShopDao shopDataDao;

        public InsertAsyncTask(ShopDao shopDataDao) {
            this.shopDataDao = shopDataDao;
        }

        @Override
        protected Void doInBackground(ShopResponse... lists) {
            shopDataDao.insert(lists[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<List<ShopResponse>, Void, Void> {
        private ShopDao shopDataDao;

        public UpdateAsyncTask(ShopDao shopDataDao) {
            this.shopDataDao = shopDataDao;
        }

        @Override
        protected Void doInBackground(List<ShopResponse>... lists) {
            shopDataDao.update();
            return null;
        }
    }
}
