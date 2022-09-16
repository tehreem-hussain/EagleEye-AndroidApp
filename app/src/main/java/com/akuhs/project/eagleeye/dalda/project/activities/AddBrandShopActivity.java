package com.akuhs.project.eagleeye.dalda.project.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.BrandAdapter;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopBrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.BrandDataViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddBrandShopActivity extends AppCompatActivity {
    private List<BrandData> brandsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    Context ctx = AddBrandShopActivity.this;
    Button btnSave;
    BrandData brands;
    private Uri fileUri;
    private String shopName, shopDSR, shopAddress, shopAddressDetail, shopCategory, shoplocationTimeStamp, shopLat, shopLng;
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
    private List<BrandData> list = new ArrayList<BrandData>();
    private BrandDataViewModel brandDataViewModel;
    private BrandDataRepository repository;
    private String remarks;
    private ShopDataRepository shopDataRepository;
    private ShopBrandDataRepository shopBrandDataRepository;
    private String encodedImageShop;
    private Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brand);
        setToolBar();

        etRemarks = findViewById(R.id.etRemarks);
        btnSave = findViewById(R.id.btnSaveData);

        Bundle bundle = getIntent().getExtras();
        shopName = bundle.getString(Constant.SHOP_NAME);
        shopAddress = bundle.getString(Constant.SHOP_ADDRESS);
        shopDSR = bundle.getString(Constant.SHOP_DSR);
        shopAddressDetail = bundle.getString(Constant.SHOP_ADDRESS_DETAIL);
        shopCategory = bundle.getString(Constant.SHOP_CATEGORY);
        shopLat = bundle.getString(Constant.SHOP_LATITUDE);
        shopLng = bundle.getString(Constant.SHOP_LONGITUDE);
        shoplocationTimeStamp = bundle.getString(Constant.SHOP_LOCATION_TIMESTAMP);
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        shopUserID = sharedPreferences.getString(Constant.USER_ID, "");
        empID = sharedPreferences.getString(Constant.USER_EMPID, "");
        user_designation = sharedPreferences.getString(Constant.USER_DESIGNATION, "");
        imgByte = bundle.getByteArray(Constant.SHOP_IMAGE);
        Bitmap bitmap=bundle.getParcelable("bmp");
        shopLocationRegion = sharedPreferences.getString(Constant.USER_LOCATION_REGION, "");
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

        getBrandData();
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
//                    showProgressbar();
//                    addShopImage(imgByte);
                    addShopData("");
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
        ShopResponse shopResponse = new ShopResponse(shopKey, shopName, shopAddress, shopCategory, shopLat, shopLng,
                "", encodedImageShop, skuAvailable, skuorder, etRemarks.getText().toString(), "false", empID, shopDSR, date, time, shopLocationRegion,shopAddressDetail);
        shopDataRepository.insert(shopResponse);
        shopBrandDataRepository.insert(listShopBrand);
        startViewActivity();

    }

    private void showProgressbar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding Shop");
        progressDialog.show();
    }

    private void hideProgressbar() {
        progressDialog.dismiss();
    }

    private void setToolBar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void startViewActivity() {
        Intent intent = new Intent(ctx, ActViewShop.class);
        startActivity(intent);
        finish();
    }

    private void getBrandData() {
        brandDataViewModel = new ViewModelProvider(this).get(BrandDataViewModel.class);
        RecyclerView rvList = findViewById(R.id.rvBrandList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
//        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setHasFixedSize(true);

       brandAdapter = new BrandAdapter(list);

        brandDataViewModel.getAllBrandData().observe(this, new Observer<List<BrandData>>() {
            @Override
            public void onChanged(List<BrandData> brands) {
                rvList.setAdapter(brandAdapter);

                brandAdapter.getAllDatas(brands);

            }
        });
    }

//    private void getBrandDataList() {
//        apiInterface = ClientApi.getClient().create(ApiInterface.class);
//
//        Call<BrandModel> call = apiInterface.getBrandData();
//        call.enqueue(new Callback<BrandModel>() {
//            @Override
//            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {
//
//                if (response.isSuccessful()) {
//                    Gson gson = new Gson();
//                    String test = gson.toJson(response.body());
//
//                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
//
//                    List<BrandData> brandData = response.body().getResult();
//                    recyclerView = findViewById(R.id.rvBrandList);
//                    brandAdapter = new BrandAdapter(ctx, brandData);
//
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
//                    recyclerView.setAdapter(brandAdapter);
//
//                } else {
//                    Log.d(tag, String.valueOf(response.code()));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BrandModel> call, Throwable t) {
//                Log.d(tag, "Failure: " + t.getMessage());
//
//            }
//        });
//
//    }



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


}
