//package com.akuhs.project.eagleeye.dalda.project.test;
//
//import android.app.Application;
//import android.os.AsyncTask;
//import androidx.lifecycle.LiveData;
//import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
//import java.util.List;
//
//public class Repository {
//
//    public PostDao postDao;
//    public LiveData<List<ProductPriceResponse>> getAllPosts;
//    private EagleEyeDatabase database;
//
//
//    public Repository(Application application) {
//        database = EagleEyeDatabase.getInstance(application);
//        postDao = database.postDao();
//        getAllPosts = postDao.getAllPosts();
//
//    }
//
//    public void insert(List<ProductPriceResponse> posts) {
//
//        new InsertAsyncTask(postDao).execute(posts);
//    }
//
//    public LiveData<List<ProductPriceResponse>> getAllPosts() {
//        return getAllPosts;
//    }
//
//
//    private static class InsertAsyncTask extends AsyncTask<List<ProductPriceResponse>, Void, Void> {
//        private PostDao postDao;
//
//        public InsertAsyncTask(PostDao postDao) {
//            this.postDao = postDao;
//        }
//
//        @Override
//        protected Void doInBackground(List<ProductPriceResponse>... lists) {
//            postDao.insert(lists[0]);
//            return null;
//        }
//    }
//
//
//}
