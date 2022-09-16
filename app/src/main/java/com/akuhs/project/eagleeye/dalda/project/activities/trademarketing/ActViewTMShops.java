package com.akuhs.project.eagleeye.dalda.project.activities.trademarketing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.SalesShopTMViewAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.SalesShopViewAdapter;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.getSalesShopDetail.GetSalesShopModel;
import com.akuhs.project.eagleeye.dalda.project.model.getSalesShopDetail.GetSalesShopResponse;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActViewTMShops extends BaseActivity {

    private String tag="code";
    private RecyclerView recyclerView;
    private String shopUserID;
    private Context ctx= ActViewTMShops.this;
    private SalesShopTMViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_stock_shops);
        setupActionBar("View Shops", true);

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        shopUserID = sharedPreferences.getString(Constant.USER_EMPID, "");
        getStockViewShopList();
        
    }

    private void getStockViewShopList() {
        
        ApiInterface apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<GetSalesShopModel> call = apiInterface.getSalesShopDetails(shopUserID);
        call.enqueue(new Callback<GetSalesShopModel>() {
            @Override
            public void onResponse(Call<GetSalesShopModel> call, Response<GetSalesShopModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));

                    List<GetSalesShopResponse> brandData = response.body().getResult();
                    recyclerView = findViewById(R.id.rvShopList);
                    adapter = new SalesShopTMViewAdapter(brandData);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<GetSalesShopModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }





}