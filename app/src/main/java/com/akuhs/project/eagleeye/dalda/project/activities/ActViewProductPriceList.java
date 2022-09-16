package com.akuhs.project.eagleeye.dalda.project.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.ProductPriceAdapter;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceModel;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.ProductPriceRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.ProductPriceViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActViewProductPriceList extends BaseActivity {

    private ProductPriceAdapter adapter;
    private List<ProductPriceResponse> list = new ArrayList<ProductPriceResponse>();
    private Context ctx = ActViewProductPriceList.this;
    private ApiInterface apiInterface;
    private String tag = "code";
    private ProductPriceViewModel productPriceViewModel;
    private ProductPriceRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eagleeye_activity_product_price_list);
        setupActionBar("Product Price List ", true);



//        setToolBar();
//        getBrandData();
//        repository = new Repository(getApplication());
//        getPostsList = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        ViewModelProviders.of(this)
//        productPriceViewModel = new ViewModelProvider(this).get(PostViewModel.class);
//        postAdapter = new PostAdapter(this, getPostsList);
//        makeRequest();
//        productPriceViewModel.getAllPosts().observe(this, new Observer<List<ProductPriceResponse>>() {
//            @Override
//            public void onChanged(List<ProductPriceResponse> posts) {
//                recyclerView.setAdapter(postAdapter);
//                postAdapter.getAllDatas(posts);
//
//                Log.d("main", "onChanged: " + posts);
//            }
//        });
        getproductPriceList();
        repository = new ProductPriceRepository(getApplication());
        productPriceViewModel = new ViewModelProvider(this).get(ProductPriceViewModel.class);
        RecyclerView rvList = findViewById(R.id.rvList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ProductPriceAdapter(list);
        productPriceViewModel.getAllProductPrice.observe(this, new Observer<List<ProductPriceResponse>>() {
            @Override
            public void onChanged(List<ProductPriceResponse> posts) {
                rvList.setAdapter(adapter);
                adapter.getAllDatas(posts);
                Log.d("main", "onChanged: " + posts);
            }
        });

    }

//    private void getBrandData() {
//        apiInterface = ClientApi.getClient().create(ApiInterface.class);
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
//                    List<ProductPriceResponse> data = response.body().getResult();
//                    repository.insert(data);
////                    List<ProductPriceResponse> data= response.body().getResult();
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


    public void load() {

        RecyclerView rvList = findViewById(R.id.rvList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ProductPriceAdapter(list);
        rvList.setAdapter(adapter);

    }

    private void setToolBar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Price List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void startAddShopActivity() {
        Intent intent = new Intent(ctx, ActAddShopData.class);
        startActivity(intent);
        finish();
    }


    private  void getproductPriceList() {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<ProductPriceModel> call = apiInterface.getproductPriceList();
        call.enqueue(new Callback<ProductPriceModel>() {
            @Override
            public void onResponse(Call<ProductPriceModel> call, Response<ProductPriceModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    List<ProductPriceResponse> data = response.body().getResult();
                    repository.insert(data);

                }
                else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<ProductPriceModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.price_search_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }

}