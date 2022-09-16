package com.akuhs.project.eagleeye.dalda.project.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.akuhs.project.eagleeye.dalda.project.dao.BrandDataDao;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.test.EagleEyeDatabase;

import java.util.List;

public class BrandDataRepository {

    public BrandDataDao brandDataDao;
    public LiveData<List<BrandData>> getAllBrandData;
    private EagleEyeDatabase database;


    public BrandDataRepository(Application application) {
        database = EagleEyeDatabase.getInstance(application);
        brandDataDao = database.brandDataDao();
        getAllBrandData = brandDataDao.getAllBrandData();

    }

    public void insert(List<BrandData> posts) {

        new InsertAsyncTask(brandDataDao).execute(posts);
    }

    public LiveData<List<BrandData>> getAllBrandData() {
        return getAllBrandData;
    }


    private static class InsertAsyncTask extends AsyncTask<List<BrandData>, Void, Void> {
        private BrandDataDao brandDataDao;

        public InsertAsyncTask(BrandDataDao brandDataDao) {
            this.brandDataDao = brandDataDao;
        }

        @Override
        protected Void doInBackground(List<BrandData>... lists) {
            brandDataDao.insert(lists[0]);
            return null;
        }
    }


}
