//package com.akuhs.project.eagleeye.dalda.project.test;
//
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//
//import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
//
//import java.util.List;
//
//public class PostViewModel extends AndroidViewModel {
//    private Repository repository;
//    public LiveData<List<ProductPriceResponse>> getAllPosts;
//
//    public PostViewModel(@NonNull Application application) {
//        super(application);
//        repository=new Repository(application);
//        getAllPosts=repository.getAllPosts();
//    }
//
//    public void insert(List<ProductPriceResponse> posts){
//        repository.insert(posts);
//    }
//
//    public LiveData<List<ProductPriceResponse>> getAllPosts()
//    {
//        return getAllPosts;
//    }
//}
