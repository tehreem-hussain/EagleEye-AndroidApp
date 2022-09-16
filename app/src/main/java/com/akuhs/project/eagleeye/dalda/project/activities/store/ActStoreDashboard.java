package com.akuhs.project.eagleeye.dalda.project.activities.store;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.ActAddShopData;
import com.akuhs.project.eagleeye.dalda.project.activities.ActViewProductPriceList;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.LoginActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.trademarketing.ActViewTMShops;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActStoreDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context ctx = this;
    String tag = "code";
    RecyclerView rv;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    private View navHeader;
    Toolbar toolbar;
    TextView tvDate;
    boolean doubleBackToExitPressedOnce = false;
    NavigationView navigation;

    private String name;
    private String email;
    private String empID;
    private String designation;
    private String lastLogin;
    private String userID;

    DrawerLayout drawer;
    TextView tvUserName;
    private TextView txtName,txtEmpId,txtLastLogin;
    private SessionManager session;
    private String userRole;
    LinearLayout llAddShop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main2);
        setupMenuBar();

        session = new SessionManager(ctx);

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        SharedPreferences sharedPreferences= ctx.getSharedPreferences(Constant.MyPREFERENCES,MODE_PRIVATE);
        name= sharedPreferences.getString(Constant.USER_NAME,"");
        email= sharedPreferences.getString(Constant.USER_EMAIL,"");
        empID= sharedPreferences.getString(Constant.USER_EMPID,"");
        lastLogin= sharedPreferences.getString(Constant.USER_LAST_LOGIN,"");
        designation= sharedPreferences.getString(Constant.USER_DESIGNATION,"");
        userRole= sharedPreferences.getString(Constant.USER_ROLE,"");



        txtName= findViewById(R.id.txtName);
        txtEmpId=findViewById(R.id.txtEmpId);
        txtLastLogin=findViewById(R.id.txtLastLogin);

        txtName.setText("Hi "+name);
        txtEmpId.setText(""+empID);
        txtLastLogin.setText("");




  if(userRole.equals("so"))
  {
      findViewById(R.id.llAddShop).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActStoreOfficerAddShop.class);
              startActivity(intent);
          }
      });

      findViewById(R.id.llViewShop).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActViewStockShops.class);
              startActivity(intent);
          }
      });

      findViewById(R.id.llAddAttendance).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActUserAttendance.class);
              startActivity(intent);
          }
      });

      findViewById(R.id.llViewPriceList).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActViewProductPriceList.class);
              startActivity(intent);
          }
      });

  }
  else if(userRole.equals("ba"))
  {
      findViewById(R.id.llAddShop).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActStoreOfficerAddShop.class);
              startActivity(intent);
          }
      });

      findViewById(R.id.llViewShop).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActViewTMShops.class);
              startActivity(intent);
          }
      });


      findViewById(R.id.llAddAttendance).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActUserAttendance.class);
              startActivity(intent);
          }
      });

      findViewById(R.id.llViewPriceList).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), ActViewProductPriceList.class);
              startActivity(intent);
          }
      });

  }

  else
  {
      logoutUser();

  }




    }

    public boolean onNavigationItemSelected(MenuItem item) {

        Log.e("000000", "=== "+item.getItemId());


        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            logoutUser();
//            Intent intent = new Intent(ctx, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            //Utils.setSharedPrefDoctor(ctx, null);
//            startActivity(intent);

        }

//        else if (id == R.id.nav_sync) {
//            Intent intent = new Intent(ctx, ActAddShopData.class);
//            startActivity(intent);
//        }





        return true;
    }

    private void setupMenuBar() {

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout = findViewById(R.id.drawerLayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Dashboard");


        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }

    @Override
    protected void onResume() {
        super.onResume();


        navHeader = navigation.getHeaderView(0);

        navigation.getMenu().getItem(0).setChecked(true);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

    }


    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(ctx, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}