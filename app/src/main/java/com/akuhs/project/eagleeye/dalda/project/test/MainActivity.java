//package com.akuhs.project.eagleeye.dalda.project.test;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import com.akuhs.project.eagleeye.dalda.R;
//import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
//import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceModel;
//import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
//import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopModel;
//import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
//import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class MainActivity extends AppCompatActivity {
//    private PostViewModel postViewModal;
//    private List<ProductPriceResponse> getPostsList;
//    private PostAdapter postAdapter;
//    private RecyclerView recyclerView;
//    private Repository repository;
//    private static final String DATA_URL = "http://www.codingwithjks.tech/data.php/";
//    private ApiInterface apiInterface;
//    private String tag = "code";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        repository = new Repository(getApplication());
//        getPostsList = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
////        ViewModelProviders.of(this)
//        postViewModal = new ViewModelProvider(this).get(PostViewModel.class);
//
//        postAdapter = new PostAdapter(this, getPostsList);
////        makeRequest();
//        getBrandData();
//        postViewModal.getAllPosts().observe(this, new Observer<List<ProductPriceResponse>>() {
//            @Override
//            public void onChanged(List<ProductPriceResponse> posts) {
//                recyclerView.setAdapter(postAdapter);
//                postAdapter.getAllDatas(posts);
//
//                Log.d("main", "onChanged: " + posts);
//            }
//        });
//
//
//    }
//
//
////    public void makeRequest() {
////        Retrofit retrofit=new Retrofit.Builder()
////                .baseUrl(DATA_URL)
////                .addConverterFactory(GsonConverterFactory.create())
////                .build();
////        ApiInterface api=retrofit.create(ApiInterface.class);
////        Call<List<Posts>> call=api.getPosts();
////        call.enqueue(new retrofit2.Callback<List<Posts>>() {
////            @Override
////            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
////                if(response.isSuccessful()) {
////                    repository.insert(response.body());
////                }
////            }
////
////            @Override
////            public void onFailure(Call<List<Posts>> call, Throwable t) {
////                Log.d("main", "onFailure: "+t.getMessage());
////            }
////        });
////
////    }
////
////    private  void getBrandData()
////    {
////        apiInterface = ClientApi.getClient().create(ApiInterface.class);
////
////        Call<Posts> call = apiInterface.getproductPriceListTest();
////        call.enqueue(new Callback<Posts>() {
////            @Override
////            public void onResponse(Call<Posts> call, Response<Posts> response) {
////
////                if (response.isSuccessful()) {
////                    Gson gson = new Gson();
////                    String test = gson.toJson(response.body());
////                    Log.d(tag, test);
////
////
//////                    List<ProductPriceResponse> data= response.body().getResult();
//////                    repository.insert(response.body());
//////
//////                    for (ProductPriceResponse price:data)
//////                    {
//////                        repository.insert(price);
//////
//////                    }
////
////                }
////                else {
////                    Log.d(tag, String.valueOf(response.code()));
////                }
////
////            }
////
////            @Override
////            public void onFailure(Call<Posts> call, Throwable t) {
////                Log.d(tag, "Failure: " + t.getMessage());
////
////            }
////        });
////
////    }
//
//
//    private void getBrandData() {
//        apiInterface = ClientApi.getClient().create(ApiInterface.class);
//
//        Call<ProductPriceModel> call = apiInterface.getproductPriceList();
//        call.enqueue(new Callback<ProductPriceModel>() {
//            @Override
//            public void onResponse(Call<ProductPriceModel> call, Response<ProductPriceModel> response) {
//
//                if (response.isSuccessful()) {
//                    Gson gson = new Gson();
//                    String test = gson.toJson(response.body());
//
//                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
//
//                    List<ProductPriceResponse> data = response.body().getResult();
//                    repository.insert(data);
////                    for (ProductPriceResponse price:data)
////                    {
////                        list.add(price);
////
////                    }
////                    load();
//
//                } else {
//                    Log.d(tag, String.valueOf(response.code()));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ProductPriceModel> call, Throwable t) {
//                Log.d(tag, "Failure: " + t.getMessage());
//
//            }
//        });
//
//    }
//
//}