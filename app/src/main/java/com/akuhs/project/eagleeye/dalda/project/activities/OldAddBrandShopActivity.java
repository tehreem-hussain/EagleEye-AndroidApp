package com.akuhs.project.eagleeye.dalda.project.activities;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.BrandAdapter;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandModel;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopBrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.CameraUtils;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.BrandDataViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.BITMAP_SAMPLE_SIZE;

public class OldAddBrandShopActivity extends AppCompatActivity {
    private List<BrandData> brandsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    Context ctx = OldAddBrandShopActivity.this;
    Button btnSave;
    BrandData brands;
    private Uri fileUri;
    private String shopName, shopDSR, shopAddress, shopAddressDetail, shopCategory, shoplocationTimeStamp, shopLat, shopLng;
    private String Id;
    private EditText etRemarks;
    private String shopUserID, shopAddedUserName;
    double lat, lng;
    private String empID;
    ProgressDialog progressDialog;
    private String imgByte;
    String TAG="code";
    String defaultImg="https://via.placeholder.com/150";
    private String user_designation;
    private ApiInterface apiInterface;
    private  String tag="code";
    private String shopKey;
    private String shopLocationRegion;

    private List<BrandData> list = new ArrayList<BrandData>();
    private BrandDataViewModel brandDataViewModel;
    private BrandDataRepository repository;
    private String remarks;
    private ShopDataRepository shopDataRepository;
    private ShopBrandDataRepository shopBrandDataRepository;
    private String encodedImageShop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brand);
        setToolBar();
        shopDataRepository = new ShopDataRepository(getApplication());
        shopBrandDataRepository = new ShopBrandDataRepository(getApplication());
        etRemarks = findViewById(R.id.etRemarks);
        btnSave = findViewById(R.id.btnSaveData);
        getBrandData();

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
        user_designation=sharedPreferences.getString(Constant.USER_DESIGNATION, "");
        imgByte = bundle.getString(Constant.SHOP_IMAGE);
        shopLocationRegion= sharedPreferences.getString(Constant.USER_LOCATION_REGION, "");
        getImg(imgByte);

Log.d(tag,shopName+shopAddress+shopAddressDetail+shopDSR+shopCategory+shopLat+shopLng+imgByte);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRemarks.getText().toString().isEmpty()) {
                    remarks=etRemarks.getText().toString();

                    etRemarks.setError("Add Remarks");
                    etRemarks.requestFocus();
                    return;
                }
                else {
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

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dtime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        String date = df.format(c);
        String time = dtime.format(c);
        System.out.println("date => " + date);
        System.out.println("time => " + time);
        shopKey= shopLat+shopLng+empID+date+time;


        List<BrandData> brandsArrayList = brandAdapter.getArrayList();
        Log.d("brand", String.valueOf(brandsArrayList.size()));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        int skuAvailable=0;
        int skuorder=0;

        ShopBrandData shopBrandData;
        List<ShopBrandData> listShopBrand=new ArrayList<>();

        for (int i = 0; i < brandsArrayList.size(); i++) {
            shopBrandData=new ShopBrandData(false,brandsArrayList.get(i).getBrand_id(),shopKey, brandsArrayList.get(i).getBrandAbr(),
                    Integer.parseInt(brandsArrayList.get(i).getBrandSku()),Integer.parseInt(brandsArrayList.get(i).getBrandOrder())
                    );
            map = new HashMap<String, Object>();
            map.put("shopKey",shopKey);
            map.put("brand_id", brandsArrayList.get(i).getBrand_id());
            map.put("brand_abr", brandsArrayList.get(i).getBrandAbr());
            map.put("brand_sku", Integer.parseInt(brandsArrayList.get(i).getBrandSku()));
            map.put("brand_order", Integer.parseInt(brandsArrayList.get(i).getBrandOrder()));
//            list.add(map);
            listShopBrand.add(shopBrandData);
            skuAvailable += Integer.parseInt(brandsArrayList.get(i).getBrandSku());
            skuorder += Integer.parseInt(brandsArrayList.get(i).getBrandOrder());
            Log.d("brand", "skuAvailable CODE: " + skuAvailable);
            Log.d("brand", "skuOrder CODE: " + skuorder);

        }
        Gson gson=new Gson();
        String test = gson.toJson(listShopBrand);
        Log.d("tag", "putTerminologyPharmacySearch CODE: " + test);
        ShopResponse shopResponse=new ShopResponse(shopKey,shopName,shopAddress,shopCategory,shopLat,shopLng,
                "",encodedImageShop,skuAvailable,skuorder,"remarks","false",empID,shopDSR,date,time,shopLocationRegion,"");
        shopDataRepository.insert(shopResponse);
        shopBrandDataRepository.insert(listShopBrand);
//
//        startViewActivity();

//        apiInterface = ClientApi.getClient().create(ApiInterface.class);
//        Call<BrandModel> call = apiInterface.postShop(imgByte,
//                shopName,
//                shopAddress,
//                shopCategory,
//                empID,
//                shopLat,
//                shopLng,
//                shopKey,
//                skuAvailable,
//                skuorder,
//                shopDSR,
//                date,
//                time,
//                shopAddressDetail,
//                shopLocationRegion
//
//        );
//        call.enqueue(new Callback<BrandModel>() {
//            @Override
//            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {
//
//                if (response.isSuccessful()) {
//                    Gson gson = new Gson();
//                    String test = gson.toJson(response.body());
//
//                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
//                    Call<BrandModel> call2 = apiInterface.postShopBrand(list);
//                    call2.enqueue(new Callback<BrandModel>() {
//                        @Override
//                        public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {
//
//                            if (response.isSuccessful()) {
//                                Gson gson = new Gson();
//                                String test = gson.toJson(response.body());
//
//                                Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
//
//
//                            }
//                            else {
//                                Log.d(tag, String.valueOf(response.code()));
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<BrandModel> call, Throwable t) {
//                            Log.d(tag, "Failure: " + t.getMessage());
//
//                        }
//                    });
//
//
//                }
//                else {
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





//        Map<String, Object> docData = new HashMap<>();
//        docData.put(Constant.SHOP_NAME, shopName);
//        docData.put(Constant.SHOP_ADDED_USERNAME, shopAddedUserName);
//        docData.put(Constant.SHOP_ADDRESS, shopAddress);
//        docData.put(Constant.SHOP_DSR, shopDSR);
//        docData.put(Constant.SHOP_ADDRESS_DETAIL, shopAddressDetail);
//        docData.put(Constant.SHOP_CATEGORY, shopCategory);
//        docData.put(Constant.SHOP_LATITUDE, shopLat);
//        docData.put(Constant.SHOP_LONGITUDE, shopLng);
//        docData.put(Constant.SHOP_LOCATION_TIMESTAMP, shoplocationTimeStamp);
//        docData.put(Constant.SHOP_IMAGE, imgUri);
//        docData.put(Constant.SHOP_USER_ID, shopUserID);
//        docData.put(Constant.SHOP_BIT, true);
//        docData.put(Constant.USER_EMPID, empID);
//        docData.put(Constant.USER_DESIGNATION, user_designation);
//        docData.put(Constant.USER_LAST_LOGIN, empID);
//        docData.put("shopRemarks", etRemarks.getText().toString());
//        docData.put("date", date);
//        docData.put("day", "");
//        docData.put("month", "");
//        docData.put("year", "");
//        docData.put("ShopAddTime", currentTime);
//        docData.put("Brand", list);
//        docData.put("skuAvailable",skuAvailable);
//        docData.put("skuorder",skuorder);
//        docData.put("server_timestamp", FieldValue.serverTimestamp());

//
//        db.collection("shopTest").document()
//                .set(docData)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("TAG", "DocumentSnapshot successfully written!");
//                        hideProgressbar();
////                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(ctx, "Shop Added Successfully", Toast.LENGTH_LONG).show();
//                        startViewActivity();
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error Saving shop", e);
//                    }
//                });

    }

    private void addShopImage(byte[] datas)
    {
//        String path = "daldaEagleEyeShopImagesTest/"+empID+"_"+System.currentTimeMillis() + ".jpg";
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//        StorageReference reference = storageReference.child(path);
//        UploadTask uploadTask = reference.putBytes(datas);
//        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.i(TAG, "Image was saved");
//                Task<Uri> res= taskSnapshot.getMetadata().getReference().getDownloadUrl();
//                res.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        Log.d(TAG,uri.toString());
//                        addShopData(uri.toString());
//
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "Image wasn't saved. Exception: "+e.getMessage());
//                addShopData(defaultImg);
//
//            }
//        });
    }


//    public void prepareBrandData() {
//        Brands brands = new Brands("DBP", 3);
//        brandsList.add(brands);
//
//        brands = new Brands("DCCNO", 5);
//        brandsList.add(brands);
//
//        brands = new Brands("DCO", 7);
//        brandsList.add(brands);
//
//        brands = new Brands("DCO", 5);
//        brandsList.add(brands);
//
//        brands = new Brands("CORN OIL", 6);
//        brandsList.add(brands);
//
//        brands = new Brands("EX.VIRGIN", 4);
//        brandsList.add(brands);
//
//        brands = new Brands("PCO", 4);
//        brandsList.add(brands);
//
//        brands = new Brands("POMACE", 4);
//        brandsList.add(brands);
//
//        brands = new Brands("DSFO", 3);
//        brandsList.add(brands);
//
//        brands = new Brands("MBP", 4);
//        brandsList.add(brands);
//
//        brands = new Brands("MCO", 3);
//        brandsList.add(brands);
//
//        brands = new Brands("TBP", 8);
//        brandsList.add(brands);
//
//        brands = new Brands("TCO", 4);
//        brandsList.add(brands);
//
//        brands = new Brands("DCS", 3);
//        brandsList.add(brands);
//
//        brands = new Brands("DKO", 15);
//        brandsList.add(brands);
//
//        brands = new Brands("BASH", 3);
//        brandsList.add(brands);
//
//        brands = new Brands("CUPSHUP", 3);
//        brandsList.add(brands);
//        brands = new Brands("KNOCKOUT", 15);
//        brandsList.add(brands);
//
//        brandAdapter.notifyDataSetChanged();
//    }

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

    private void startViewActivity()
    {
        Intent intent=new Intent(ctx, ActViewShop.class);
        startActivity(intent);
        finish();
    }
    private  void getBrandData() {
        repository = new BrandDataRepository(getApplication());
        brandDataViewModel = new ViewModelProvider(this).get(BrandDataViewModel.class);
        RecyclerView rvList = findViewById(R.id.rvBrandList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rvList.setLayoutManager(layoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        brandAdapter = new BrandAdapter(list);
        brandDataViewModel.getAllBrandData.observe(this, new Observer<List<BrandData>>() {
            @Override
            public void onChanged(List<BrandData> brands) {
                rvList.setAdapter(brandAdapter);
                brandAdapter.getAllDatas(brands);
                Log.d("main Brand", "onChanged: " + brands);
            }
        });
    }
    private  void getBrandDataList(){
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<BrandModel> call = apiInterface.getBrandData();
        call.enqueue(new Callback<BrandModel>() {
            @Override
            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));

                    List<BrandData> brandData= response.body().getResult();
                    recyclerView = findViewById(R.id.rvBrandList);
                    brandAdapter = new BrandAdapter(ctx,brandData);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(brandAdapter);

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


    private void getImg(String imageStoragePath)
    {
        Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] b = stream.toByteArray();
        encodedImageShop = Base64.encodeToString(b, Base64.DEFAULT);
    }
}
