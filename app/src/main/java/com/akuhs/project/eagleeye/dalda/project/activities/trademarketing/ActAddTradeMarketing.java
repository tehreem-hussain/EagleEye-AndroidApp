package com.akuhs.project.eagleeye.dalda.project.activities.trademarketing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.ActViewShop;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.BrandAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.TradeMarketingAddAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.store.ActViewStockShops;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSales;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSalesResponse;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionModel;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingModel;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.ProductPriceRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopBrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.BrandDataViewModel;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.ProductPriceViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActAddTradeMarketing extends BaseActivity {
    private List<BrandData> brandsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    Context ctx = ActAddTradeMarketing.this;
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



    private TradeMarketingAddAdapter adapter;
    private List<ProductPriceResponse> list = new ArrayList<ProductPriceResponse>();
    private ApiInterface apiInterface;
    private ProductPriceViewModel productPriceViewModel;
    private ProductPriceRepository repository;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_trade_marketing);
        setupActionBar("Add Trade Marketing ", true);

//        setToolBar();

        etRemarks = findViewById(R.id.etRemarks);
        btnSave = findViewById(R.id.btnSaveData);

        Bundle bundle = getIntent().getExtras();
        shopAddress = bundle.getString(Constant.SHOP_ADDRESS);
        shopLat = bundle.getString(Constant.SHOP_LATITUDE);
        shopLng = bundle.getString(Constant.SHOP_LONGITUDE);
        shopId = bundle.getString(Constant.SHOP_ID);
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        shopUserID = sharedPreferences.getString(Constant.USER_ID, "");
        imgByte = bundle.getByteArray(Constant.SHOP_IMAGE);
        Bitmap bitmap=bundle.getParcelable("bmp");
//        Log.d(tag,shopAddressDetail+"---"+ imgByte);
        getImg(bitmap);


        getTradeMarketingList();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRemarks.getText().toString().isEmpty()) {
                    remarks = etRemarks.getText().toString();

                    etRemarks.setError("Add Remarks");
                    etRemarks.requestFocus();
                    return;
                } else {
//                    btnSave.setClickable(false);
//                    btnSave.setEnabled(false);
                    showProgressbar();
//                    addShopImage(imgByte);
//                    addShopData("");
                    addShopDetail();
                }
            }
        });

    }


    private void showProgressbar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding trade marketing data");
        progressDialog.show();
    }

    private void hideProgressbar() {
        progressDialog.dismiss();
    }


    private void startViewActivity() {
        Intent intent = new Intent(ctx, ActViewShop.class);
        startActivity(intent);
        finish();
    }


    private void getTradeMarketingList() {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<TradeMarketingModel> call = apiInterface.getSKUListTM();
        call.enqueue(new Callback<TradeMarketingModel>() {
            @Override
            public void onResponse(Call<TradeMarketingModel> call, Response<TradeMarketingModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));

                    List<TradeMarketingResponse> data = response.body().getResult();
                    recyclerView = findViewById(R.id.rvBrandList);
                    adapter = new TradeMarketingAddAdapter(ctx, data);

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
            public void onFailure(Call<TradeMarketingModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }



    private void getImg(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] byteImg = stream.toByteArray();
        encodedImageShop = Base64.encodeToString(byteImg, Base64.DEFAULT);
        Log.d("encode",byteImg.toString());
        Log.d("encode",encodedImageShop);
    }




    private void addTradeMarketing(String shopKey)
    {
        List<TradeMarketingResponse> ArrayList = adapter.getArrayList();
        Log.d("brand", String.valueOf(ArrayList.size()));
        int skuAvailable = 0;
        int skuorder = 0;

        TradeMarketingResponse response;
        List<TradeMarketingResponse> listStock = new ArrayList<>();

        for (int i = 0; i < ArrayList.size(); i++) {
            response = new TradeMarketingResponse(
                            shopKey,
                            ArrayList.get(i).getSkuId(),
                            ArrayList.get(i).getquantity(),
                            ArrayList.get(i).getfacing(),
                            ArrayList.get(i).getprices()
            );
            listStock.add(response);

        }
        postTradeMarketing(listStock);
        Gson gson = new Gson();
        String test = gson.toJson(listStock);
        Log.d("tehreem",test);
    }
    
    private void postTradeMarketing(List<TradeMarketingResponse> listData) {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<StockPositionModel> call2 = apiInterface.postTradeMarketing(listData);
        call2.enqueue(new Callback<StockPositionModel>() {
            @Override
            public void onResponse(Call<StockPositionModel> call, Response<StockPositionModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE Brand Shop Data: " + test + String.valueOf(response.code()));
//                    shopBrandDataRepository.update();
                    hideProgressbar();
                    Intent intent= new Intent(ctx, ActViewTMShops.class);
                    startActivity(intent);


                }
                else
                {
                    Log.d(tag+"infail Brand", String.valueOf(response.code()));
                    hideProgressbar();
                    Toast.makeText(ctx,"Shop not added",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<StockPositionModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();
                Toast.makeText(ctx,"Shop not added",Toast.LENGTH_SHORT).show();



            }
        });
    }



    private void addShopDetail() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dtime = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
        Date c = Calendar.getInstance().getTime();
        String date = df.format(c);
        String time = dtime.format(c);
        System.out.println("date => " + date);
        System.out.println("time => " + time);



        shopKey = date+shopId;
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<ShopSales> call = apiInterface.postShopSales(encodedImageShop,shopAddress,shopLat,shopLng,remarks,shopKey,date,time,shopId);
        call.enqueue(new Callback<ShopSales>() {
            @Override
            public void onResponse(Call<ShopSales> call, Response<ShopSales> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d("tehreem", "Get CODE: " + test + String.valueOf(response.code()));

                    List<ShopSalesResponse> brandData = response.body().getResult();
                    addTradeMarketing(shopKey);




                } else {
                    Log.d(tag, String.valueOf(response.code()));
                    hideProgressbar();
                    Toast.makeText(ctx,"Shop not added",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ShopSales> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();
                Toast.makeText(ctx,"Shop not added",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
