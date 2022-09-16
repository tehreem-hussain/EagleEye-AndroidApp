package com.akuhs.project.eagleeye.dalda.project.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.ShopAdapter;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandModel;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopModel;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopBrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.ShopBrandDataViewModel;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.ShopDataViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActViewShop extends AppCompatActivity {

    private ShopAdapter adapter;
    private List<ShopResponse> list = new ArrayList<>();
    private List<String> shopResponseList = new ArrayList<String>();
    private Context ctx = ActViewShop.this;
    private String empID;
    private ApiInterface apiInterface;
    private String tag = "code";
    private ShopDataViewModel shopDataViewModel;
    private ShopDataRepository repository;
    private ShopBrandDataRepository shopBrandDataRepository;
    private ShopBrandDataViewModel shopBrandDataViewModel;
    private ShopResponse shopResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eagleeye_act_shop);
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        empID = sharedPreferences.getString(Constant.USER_EMPID, "");
        setToolBar();
        getShopList();
        addShopListArray();
        getShopBrandList();
        findViewById(R.id.fabAddRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAddShopActivity();

            }
        });


    }

    private void getBrandData() {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<ShopModel> call = apiInterface.getUserShop(empID);
        call.enqueue(new Callback<ShopModel>() {
            @Override
            public void onResponse(Call<ShopModel> call, Response<ShopModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));

                    List<ShopResponse> data = response.body().getResult();
                    for (ShopResponse shopResponse : data) {
                        list.add(shopResponse);

                    }
                    load();

                } else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<ShopModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }
    public void load() {

        RecyclerView rvList = findViewById(R.id.rvShopList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShopAdapter(list);
        rvList.setAdapter(adapter);

    }
    public void getShopList() {

        repository = new ShopDataRepository(getApplication());
        shopDataViewModel = new ViewModelProvider(this).get(ShopDataViewModel.class);
        RecyclerView rvList = findViewById(R.id.rvShopList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShopAdapter(list);
        shopDataViewModel.getAllShopResponse().observe(this, new Observer<List<ShopResponse>>() {
            @Override
            public void onChanged(List<ShopResponse> shop) {
                viewJSON(shop);
//                Gson gson = new Gson();
//                String test = gson.toJson(shop);
//                Log.d(tag, "Get CODE SHOP DATA: " + test + String.valueOf(test));
//                shopResponseList.add(test);
//                Log.d(tag, "Get CODE: " + test + String.valueOf(test));
                rvList.setAdapter(adapter);
                adapter.getAllDatas(shop);

            }
        });

    }
    public void addShopListArray() {

        repository = new ShopDataRepository(getApplication());
        shopDataViewModel = new ViewModelProvider(this).get(ShopDataViewModel.class);
        shopDataViewModel.getAllShopResponseFalse.observe(this, new Observer<List<ShopResponse>>() {
            @Override
            public void onChanged(List<ShopResponse> shop) {
//                Gson gson = new Gson();
//                String test = gson.toJson(shop);
//                shopResponseList.add(test);
//                Log.d(tag, "Get CODE: " + test + String.valueOf(test));
//                rvList.setAdapter(adapter);
//                adapter.getAllDatas(shop);

                for(int i=0; i< shop.size(); i++) {
                    Log.d("main", "onChanged: Size" + shop.size()+""+i);
                    shop.get(i).setShopBit("true");

                    addShop(shop.get(i).getShopImageUrl(),
                            shop.get(i).getShopName(),
                            shop.get(i).getShopAddress(),
                            shop.get(i).getCategoryName(),
                            shop.get(i).getUserEmpId(),
                            shop.get(i).getShopLatitude(),
                            shop.get(i).getShopLongitude(),
                            shop.get(i).getShopKey(),
                            shop.get(i).getShopTotalSkuAvailable(),
                            shop.get(i).getShopTotalSkuOrder(),
                            shop.get(i).getDsr(),
                            shop.get(i).getShopRemarks(),
                            shop.get(i).getShopDateAdded(),
                            shop.get(i).getShopAddedTime(),
                            shop.get(i).getShop_location_region(),
                            shop.get(i).getShop_address_details()
                            );
                }
            }
        });
    }
    public void getShopBrandList() {
        shopBrandDataRepository = new ShopBrandDataRepository(getApplication());
        shopBrandDataViewModel = new ViewModelProvider(this).get(ShopBrandDataViewModel.class);
        shopBrandDataViewModel.getAllShopBrandData().observe(this, new Observer<List<ShopBrandData>>() {
            @Override
            public void onChanged(List<ShopBrandData> shop) {
                Gson gson = new Gson();
                String test = gson.toJson(shop);
//                Log.d(tag, "Get CODE SHOP DATA: " + test + String.valueOf(test));
                addShopBrandData(shop);
//
////                Log.d("main", "onChanged: Size raBnd" + shop.size());
//                for(int i=0; i< shop.size(); i++) {
//                    Log.d("main", "onChanged: Size raBnd" + shop.size()+""+i);
//                    Log.d("main", "onChanged: Size raBnd" + shop.get(i).getBrandAbr()+""+i);
//                }
            }
        });

    }

    private void  viewJSON(List<ShopResponse> shop)
    {
        Gson gson = new Gson();
        String test = gson.toJson(shop);
        Log.d(tag, "Get CODE SHOP DATA: " + test + String.valueOf(test));
    }

    private void addShop(String shopImageUrl,
                         String shopName,
                         String shopAddress,
                         String categoryName,
                         String userEmpId,
                         String shopLatitude,
                         String shopLongitude,
                         String shopKey,
                         int shopTotalSkuAvailable,
                         int shopTotalSkuOrder,
                         String dsr,
                         String remarks,
                         String shopDateAdded,
                         String shopAddedTime,
                         String lahore,
                         String address_detail
                         ){

        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<BrandModel> call = apiInterface.postShop(
                shopImageUrl,
                shopName,
                shopAddress,
                categoryName,
                userEmpId,
                shopLatitude,
                shopLongitude,
                shopKey,
                shopTotalSkuAvailable,
                shopTotalSkuOrder,
                dsr,
                shopDateAdded,
                shopAddedTime,
                address_detail,
                lahore,
                remarks
                );
        call.enqueue(new Callback<BrandModel>() {
            @Override
            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    getShopBrandList();
                    repository.update();
                    shopBrandDataRepository.update();


                }
                else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<BrandModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }

    private void addShopList(List<ShopResponse> shop)
    {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<BrandModel> call = apiInterface.postShopData(shop);
        call.enqueue(new Callback<BrandModel>() {
            @Override
            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    getShopBrandList();
                    repository.update();
                    shopBrandDataRepository.update();


                }
                else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<BrandModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }

    private void addShopBrandData(List<ShopBrandData> shop) {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<BrandModel> call2 = apiInterface.postShopBrand(shop);
        call2.enqueue(new Callback<BrandModel>() {
            @Override
            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE Brand Shop Data: " + test + String.valueOf(response.code()));
                    shopBrandDataRepository.update();
                }
                else
                {
                    Log.d(tag+"infail Brand", String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<BrandModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });
    }

    private void setToolBar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shop Added List");
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
        Intent intent = new Intent(ctx, ActCameraLocation.class);
        startActivity(intent);
        finish();
    }

}