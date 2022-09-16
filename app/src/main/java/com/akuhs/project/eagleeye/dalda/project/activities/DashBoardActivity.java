package com.akuhs.project.eagleeye.dalda.project.activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandModel;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceModel;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.utils.SessionManager;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private CardView cvManageShop,cvViewShop;
    private Context ctx= DashBoardActivity.this;
    private Toolbar mToolbar;
    private TextView txtEmail,txtName,txtDesignation,txtLocation,txtLastLogin;
    private String email,lastSignIn;
    private String userID;
    SimpleDateFormat sdf;
    String lastLogin;
    private String name,lastseen;
    private SessionManager session;
    private String designation;
    private String empID;
    private BrandDataRepository brandRepository;
    private ApiInterface apiInterface;
    private String tag="code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eagleeye_activity_dash_board);

        session = new SessionManager(ctx);
        brandRepository= new BrandDataRepository(getApplication());


        if (!session.isLoggedIn()) {
            logoutUser();
        }

        cvManageShop=findViewById(R.id.cvManageShop);
        cvViewShop=findViewById(R.id.cvViewShop);

        sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm a");

        SharedPreferences sharedPreferences= ctx.getSharedPreferences(Constant.MyPREFERENCES,MODE_PRIVATE);
        name= sharedPreferences.getString(Constant.USER_NAME,"");
        email= sharedPreferences.getString(Constant.USER_EMAIL,"");
        empID= sharedPreferences.getString(Constant.USER_EMPID,"");
        lastLogin= sharedPreferences.getString(Constant.USER_LAST_LOGIN,"");
        userID= sharedPreferences.getString(Constant.USER_ID,"");
        designation= sharedPreferences.getString(Constant.USER_DESIGNATION,"");

        txtEmail=findViewById(R.id.txtEmail);
        txtName=findViewById(R.id.txtFullName);
        txtLastLogin=findViewById(R.id.txtLastLogin);
        txtDesignation=findViewById(R.id.txtDesignation);
        getBrandData();
        openPermission();

        txtEmail.setText(empID);
        txtName.setText(name);
        txtDesignation.setText("Designation: "+designation);
        if(lastLogin.isEmpty())
        {
            String seen=sdf.format(new Date());
            txtLastLogin.setText("Last Login: "+ seen);
        }
        else
            {
            txtLastLogin.setText("Last Login: "+lastLogin);
        }

        cvManageShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, ActCameraLocation.class);
                startActivity(intent);
            }
        });

        cvViewShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ctx, ActViewShop.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.cvViewPriceList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, ActViewProductPriceList.class);
                startActivity(intent);
            }
        });

    }

    public void loginUserDetails()
    {

    }

    private void setLastSignIn(String docID)
    {
        lastseen = sdf.format(new Date());

        txtLastLogin.setText("Last Seen: "+lastseen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_add_items:
////                onAddItemsClicked();
//                break;
            case R.id.menu_sign_out:
//                AuthUI.getInstance().signOut(this);
//                startSignIn();
                logoutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSignIn() {
        Intent intent=new Intent(ctx, LoginActivity.class);
        startActivity(intent);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        setLastSignIn(userID);
////        loginUserDetails();
//        txtEmail.setText(empID);
//        txtName.setText(name);
//        txtLastLogin.setText("Last Seen: "+lastLogin);
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        setLastSignIn(userID);
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setLastSignIn(userID);
//    }

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(ctx, LoginActivity.class);
        startActivity(intent);
        finish();
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


    @AfterPermissionGranted(123)
    private void openPermission() {
        String[] perms = {Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
//            Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Permission needed",
                    123, perms);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
        }
    }

}