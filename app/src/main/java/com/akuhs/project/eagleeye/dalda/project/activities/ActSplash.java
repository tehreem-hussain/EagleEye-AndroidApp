package com.akuhs.project.eagleeye.dalda.project.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;

import com.akuhs.project.eagleeye.dalda.BuildConfig;
import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.adapters.BrandAdapter;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandModel;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceModel;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.repository.ProductPriceRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.CameraUtils;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.utils.UtilsCamera;
import com.akuhs.project.eagleeye.dalda.project.viewmodel.ProductPriceViewModel;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akuhs.project.eagleeye.dalda.project.utils.CameraUtils.checkPermissions;

public class ActSplash extends AppCompatActivity {

    private ApiInterface apiInterface;
    private Context ctx = ActSplash.this;
    private String tag="code";
    private ProductPriceRepository repository;
    private BrandDataRepository brandRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eagleye_act_splash);

        Log.d(tag+"offline", String.valueOf(Constant.getConnectionType(ctx)));
        Log.d(tag+"offlineIsNetwork", String.valueOf(Constant.isNetworkAvailable(ctx)));
        repository = new ProductPriceRepository(getApplication());
        brandRepository= new BrandDataRepository(getApplication());
//        getproductPriceList();
//        getBrandData();
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    Intent intent = new Intent(ActSplash.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 2000);



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
                    Intent intent = new Intent(ActSplash.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

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

    private  void getBrandDataList() {
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
    private  void getBrandData(){
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
                    brandRepository.insert(brandData);
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


}
