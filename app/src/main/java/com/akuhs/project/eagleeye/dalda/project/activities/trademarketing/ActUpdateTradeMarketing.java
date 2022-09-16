package com.akuhs.project.eagleeye.dalda.project.activities.trademarketing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.BrandAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.StockPositionUpdateAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.TradeMarketingUpdateAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.store.ActViewStockShops;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionModel;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionResponse;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingModel;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.ProductPriceRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopBrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.BrandDataViewModel;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.ProductPriceViewModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActUpdateTradeMarketing extends BaseActivity {
    private List<BrandData> brandsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    Context ctx = ActUpdateTradeMarketing.this;
    Button btnSave;
    BrandData brands;
    private Uri fileUri;
    private String shopName,shopAddress, shopLat, shopLng;
    private String Id;
    private EditText etRemarks;
    private String shopUserID, shopAddedUserName;
    private String empID;
    ProgressDialog progressDialog;
    private byte[] imgByte;
    String TAG = "code";
    private String user_designation;
    private String tag = "code";
    private String shopKey;
    private String shopLocationRegion;
    private BrandDataViewModel brandDataViewModel;
    private String remarks;
    private ShopDataRepository shopDataRepository;
    private ShopBrandDataRepository shopBrandDataRepository;
    private String encodedImageShop;
    private Bitmap bmp;



    private TradeMarketingUpdateAdapter adapter;
    private List<ProductPriceResponse> list = new ArrayList<ProductPriceResponse>();
    private ApiInterface apiInterface;
    private ProductPriceViewModel productPriceViewModel;
    private ProductPriceRepository repository;
    private String shopId;
    private String shop_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_trade_marketing);
//        setToolBar();
        setupActionBar("View Trade Marketing", true);

        etRemarks = findViewById(R.id.etRemarks);
        btnSave = findViewById(R.id.btnSaveData);

        Bundle bundle = getIntent().getExtras();

        shop_key = bundle.getString("shop_key");
        Log.d(tag,""+shop_key);

        etRemarks.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);

        getStockPositionList();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    remarks = etRemarks.getText().toString();
                    showProgressbar();
                    addStockPosition(shop_key);

            }
        });

    }

    private void showProgressbar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating stock position");
        progressDialog.show();
    }

    private void hideProgressbar() {
        progressDialog.dismiss();
    }

    private void startViewActivity() {
        Intent intent = new Intent(ctx, ActViewTMShops.class);
        startActivity(intent);
        finish();
    }


    private void getStockPositionList() {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);


        Call<TradeMarketingModel> call = apiInterface.getTradeMarketing(shop_key);
        call.enqueue(new Callback<TradeMarketingModel>() {
            @Override
            public void onResponse(Call<TradeMarketingModel> call, Response<TradeMarketingModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));

                    List<TradeMarketingResponse> brandData = response.body().getResult();
                    recyclerView = findViewById(R.id.rvBrandList);
                    adapter = new TradeMarketingUpdateAdapter(ctx, brandData);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<TradeMarketingModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }

    private void addStockPosition(String shopKey)
    {
        List<TradeMarketingResponse> ArrayList = adapter.getArrayList();
        TradeMarketingResponse stockPositionResponse;
        List<TradeMarketingResponse> listStock = new ArrayList<>();

        for (int i = 0; i < ArrayList.size(); i++) {
            stockPositionResponse = new TradeMarketingResponse(
                            shopKey,
                            ArrayList.get(i).getSkuId(),
                            ArrayList.get(i).getquantity(),
                            ArrayList.get(i).getfacing(),
                            ArrayList.get(i).getprices()

            );
            listStock.add(stockPositionResponse);

        }
        postStockPosition(listStock);
        Gson gson = new Gson();
        String test = gson.toJson(listStock);
        Log.d("tehreem",test);
    }
    
    private void postStockPosition(List<TradeMarketingResponse> listData) {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<TradeMarketingModel> call2 = apiInterface.postUpdateTradeMarketing(listData);
        call2.enqueue(new Callback<TradeMarketingModel>() {
            @Override
            public void onResponse(Call<TradeMarketingModel> call, Response<TradeMarketingModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE Brand Shop Data: " + test + String.valueOf(response.code()));
//                    shopBrandDataRepository.update();
                    Toast.makeText(ctx, "Stock Position updated successfully", Toast.LENGTH_SHORT).show();
                    hideProgressbar();
                    startViewActivity();

                }
                else
                {
                    Log.d(tag+"infail Brand",response.message() +String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<TradeMarketingModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });
    }

}
