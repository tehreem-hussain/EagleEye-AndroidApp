package com.akuhs.project.eagleeye.dalda.project.activities.store;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.ActViewProductPriceList;
import com.akuhs.project.eagleeye.dalda.project.activities.ActViewShop;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.BrandAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.ProductPriceAdapter;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.StockPositionAddAdapter;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandModel;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSales;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSalesResponse;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionModel;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;
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

public class ActAddStockPosition extends BaseActivity {
    private List<BrandData> brandsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    Context ctx = ActAddStockPosition.this;
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



    private StockPositionAddAdapter adapter;
    private List<ProductPriceResponse> list = new ArrayList<ProductPriceResponse>();
    private ApiInterface apiInterface;
    private ProductPriceViewModel productPriceViewModel;
    private ProductPriceRepository repository;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_stock);
//        setToolBar();
        setupActionBar("Add Stock Position ", true);

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


//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        Bitmap bmpa = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
//        imgtest.setImageBitmap(bmp);
////        bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
//        imgByte = stream.toByteArray();
//        encodedImageShop = Base64.encodeToString(imgByte, Base64.DEFAULT);
//        Log.d(tag,encodedImageShop);
//        getImg(imgByte);

//        getBrandData();

        getStockPositionList();
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

    private void addShopData(String imgUri) {

        Date currentTime = Calendar.getInstance().getTime();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        shopDataRepository = new ShopDataRepository(getApplication());
        shopBrandDataRepository = new ShopBrandDataRepository(getApplication());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dtime = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
        String date = df.format(c);
        String time = dtime.format(c);
        System.out.println("date => " + date);
        System.out.println("time => " + time);



        shopKey = shopLat + shopLng + empID + date + time;





        List<BrandData> brandsArrayList = brandAdapter.getArrayList();
        Log.d("brand", String.valueOf(brandsArrayList.size()));
        int skuAvailable = 0;
        int skuorder = 0;

        ShopBrandData shopBrandData;
        List<ShopBrandData> listShopBrand = new ArrayList<>();

        for (int i = 0; i < brandsArrayList.size(); i++) {
            shopBrandData = new ShopBrandData(false, brandsArrayList.get(i).getBrand_id(), shopKey, brandsArrayList.get(i).getBrandAbr(),
                    Integer.parseInt(brandsArrayList.get(i).getBrandSku()), Integer.parseInt(brandsArrayList.get(i).getBrandOrder())
            );
            listShopBrand.add(shopBrandData);
            skuAvailable += Integer.parseInt(brandsArrayList.get(i).getBrandSku());
            skuorder += Integer.parseInt(brandsArrayList.get(i).getBrandOrder());

        }
        Gson gson = new Gson();
        String test = gson.toJson(listShopBrand);
        Log.d("tag", "putTerminologyPharmacySearch CODE: " + test);
//        ShopResponse shopResponse = new ShopResponse(shopKey, shopName, shopAddress, shopLat, shopLng,
//                "", encodedImageShop, skuAvailable,
//                skuorder, etRemarks.getText().toString(), "false", empID, date, time,
//                shopLocationRegion);
//        shopDataRepository.insert(shopResponse);
        shopBrandDataRepository.insert(listShopBrand);
        startViewActivity();

    }

    private void showProgressbar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding Stock Position");
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

    private void getBrandData() {
        productPriceViewModel = new ViewModelProvider(this).get(ProductPriceViewModel.class);
        RecyclerView rvList = findViewById(R.id.rvBrandList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
//        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setHasFixedSize(true);

//       adapter = new StockPositionAddAdapter(list);

        productPriceViewModel.getAllProductPrice.observe(this, new Observer<List<ProductPriceResponse>>() {
            @Override
            public void onChanged(List<ProductPriceResponse> posts) {
                rvList.setAdapter(adapter);
//                adapter.getAllDatas(posts);
                Log.d("main", "onChanged: " + posts);
            }
        });
    }

    private void getStockPositionList() {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<StockPositionModel> call = apiInterface.getSKUList();
        call.enqueue(new Callback<StockPositionModel>() {
            @Override
            public void onResponse(Call<StockPositionModel> call, Response<StockPositionModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));

                    List<StockPositionResponse> brandData = response.body().getResult();
                    recyclerView = findViewById(R.id.rvBrandList);
                    adapter = new StockPositionAddAdapter(brandData);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));

//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
                    recyclerView.setLayoutManager(layoutManager);
//        rvList.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);


                } else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<StockPositionModel> call, Throwable t) {
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
////      Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
//        bytes = stream.toByteArray();
//        encodedImageShop = Base64.encodeToString(bytes, Base64.DEFAULT);
//        Log.d(tag,encodedImageShop);
    }




    private void addStockPosition(String shopKey)
    {
        List<StockPositionResponse> ArrayList = adapter.getArrayList();
        Log.d("brand", String.valueOf(ArrayList.size()));
        int skuAvailable = 0;
        int skuorder = 0;

        StockPositionResponse stockPositionResponse;
        List<StockPositionResponse> listStock = new ArrayList<>();

        for (int i = 0; i < ArrayList.size(); i++) {
            stockPositionResponse = new StockPositionResponse(
                            shopKey,
                            ArrayList.get(i).getSkuId(),
                            ArrayList.get(i).getOpening(),
                            ArrayList.get(i).getReceiving(),
                            ArrayList.get(i).getSales(),
""
//                            String.valueOf(Integer.parseInt(ArrayList.get(i).getOpening()))

            );
            listStock.add(stockPositionResponse);

        }
        postStockPosition(listStock);
        Gson gson = new Gson();
        String test = gson.toJson(listStock);
        Log.d("tehreem",test);
    }
    
    private void postStockPosition(List<StockPositionResponse> listData) {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<StockPositionModel> call2 = apiInterface.postStockPosition(listData);
        call2.enqueue(new Callback<StockPositionModel>() {
            @Override
            public void onResponse(Call<StockPositionModel> call, Response<StockPositionModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE Brand Shop Data: " + test + String.valueOf(response.code()));
                    Toast.makeText(ctx,"Stock Position Updated Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(ctx,ActViewStockShops.class);
                    startActivity(intent);
                    hideProgressbar();
//                    shopBrandDataRepository.update();
                }
                else
                {
                    Log.d(tag+"infail Brand",response.message() +String.valueOf(response.code()));
                    hideProgressbar();
                }
            }
            @Override
            public void onFailure(Call<StockPositionModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();

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
                    addStockPosition(shopKey);




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
