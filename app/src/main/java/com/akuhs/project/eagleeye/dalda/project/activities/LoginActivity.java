package com.akuhs.project.eagleeye.dalda.project.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.store.ActStoreDashboard;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.authenticate.AuthenticateResponse;
import com.akuhs.project.eagleeye.dalda.project.model.authenticate.AuthenticateResult;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.akuhs.project.eagleeye.dalda.project.utils.SessionManager;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.getConnectionType;
import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.showError;

public class LoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private EditText etEmail, etPassword;
    private ProgressBar progressBar;
    Context ctx = LoginActivity.this;
    Button btnLogin;
    String email, password;
    SharedPreferences sharedpreferences;
    private RelativeLayout linearLayout;
    ProgressDialog progressDialog;
    private SessionManager session;
    private ApiInterface apiInterface;
    private String tag = "test";


    int CAMERA = 150;
    private String user_device_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_login);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linearLayout);
        sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        openPermission();
        // Session manager
        session = new SessionManager(ctx);
//        getConnectionType(ctx);
//        Log.d(tag+"offline", String.valueOf(getConnectionType(ctx)));
//        UtilsCamera.requestCameraPermission(ctx);


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, ActStoreDashboard.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (getConnectionType(ctx))
                {
                    createUser(email, password);

                }
                else
                {
                    Log.d(tag+"offline", String.valueOf(getConnectionType(ctx)));
                    Log.d(tag+"offline", String.valueOf(getConnectionType(ctx)));
                    localLogin(email,password);
                }

            }
        });

//        getUniqueId();
        loadData();

    }

    private void createUser(String email, String password) {
        if (email.isEmpty()) {
            etEmail.setError("Employee ID required");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        getUserLogin(email, password);
    }

    private void loginDetails(String user_location_region, String userName, String designationName) {
        String user_device_id=getUniqueId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm a");
        String user_last_seen = sdf.format(new Date());
        String app_version="v1";
        int currentapiVersion = Build.VERSION.SDK_INT;
//        int currentapiVersionName= Build.VERSION_CODES;

        Field[] fields = Build.VERSION_CODES.class.getFields();
        String codeName = "UNKNOWN";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    codeName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        apiInterface = ClientApi.getClient().create(ApiInterface.class);
        Call<AuthenticateResponse> call = apiInterface.postUserLoginDetails(
                user_location_region,userName,designationName,
                email,user_last_seen,app_version,codeName,currentapiVersion,user_device_id);
        call.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {

                if (response.isSuccessful()) {
                    Log.d(tag, "Get CODE: " + String.valueOf(response.code()));

                } else {
                    Log.d(tag, String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());

            }
        });

    }

    private void getUserLogin(String empId, String password) {
        showProgressbar();
        setbtnFalse();

        if(empId.equals("222222") || empId.equals("04111") )
        {
            user_device_id ="";
        }
        else
        {
            user_device_id=getUniqueId();

        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm a");
        String user_last_seen = sdf.format(new Date());
        String app_version="V2.0";
        int currentapiVersion = Build.VERSION.SDK_INT;
//        int currentapiVersionName= Build.VERSION_CODES;

        Field[] fields = Build.VERSION_CODES.class.getFields();
        String codeName = "UNKNOWN";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    codeName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<AuthenticateResponse> call = apiInterface.getUserAuthenticate(
                empId,
                password,
                app_version,
                codeName,
                currentapiVersion,
                user_device_id);
        call.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    AuthenticateResult usermodel = response.body().getResult();
//                    loginDetails(usermodel.getUser_location_region(),usermodel.getUserName(), usermodel.getDesignationName());
                    startActivityMain(usermodel.getUser_location_region(),usermodel.getDepartmentName(), usermodel.getDesignationName(), usermodel.getRoleType()
                            , usermodel.getUserEmail(), usermodel.getUserEmpId(), usermodel.getUserPassword(), usermodel.getUserName()
                    );
                    hideProgressbar();


                }
                else if(response.code() == 400)
                {
                    hideProgressbar();
                    Toast.makeText(ctx,"Something went wrong",Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d(tag, String.valueOf(response.code()));
                    hideProgressbar();
                    loginFail();
                }

            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();
                loginFail();

            }
        });

    }

    private void setbtnFalse() {
        btnLogin.setClickable(false);
        btnLogin.setEnabled(false);
    }

    private void setbtnTrue() {
        hideProgressbar();
        btnLogin.setClickable(true);
        btnLogin.setEnabled(true);

    }

    private void showProgressbar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loging");
        progressDialog.show();
    }

    private void hideProgressbar() {
        progressDialog.dismiss();
    }

    private void loginFail() {
        Log.d("tagging", "error");
        showError(linearLayout, "Incorrect Employee Id or password", ctx);
        setbtnTrue();
    }

    private void startMainActivity() {
        session.setLogin(true);
        Intent intent = new Intent(ctx, DashBoardActivity.class);
        startActivity(intent);
    }

    private void saveSharedPref() {

    }

    @Override
    public void onStart() {
        super.onStart();
//        openPermission();
        //This checks for the permission;
//        requestCameraPermission();
//        UtilsCamera.requestCameraPermission(ctx);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CAMERA)) {
//
//
//                // You can show your dialog message here but instead I am
//                // showing the grant permission dialog box
//                ActivityCompat.requestPermissions(this, new String[]{
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.ACCESS_FINE_LOCATION,
//                                Manifest.permission.ACCESS_COARSE_LOCATION},
//                        10);
//
//
//            } else {
//
//                //Requesting permission
//                ActivityCompat.requestPermissions(this, new String[]{
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.ACCESS_FINE_LOCATION,
//                                Manifest.permission.ACCESS_COARSE_LOCATION},
//                        10);
//            }
//
//        }

    }
    private void startActivityMain(String location,String departmentName, String designationName, String roleType, String userEmail, String userEmpId, String userPassword, String userName) {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constant.USER_LOGIN_BIT,true).apply();
        editor.putString(Constant.USER_PASSWORD, userPassword);
        editor.putString(Constant.USER_EMAIL, userEmail);
        editor.putString(Constant.USER_ROLE, roleType);
        editor.putString(Constant.USER_NAME, userName);
        editor.putString(Constant.USER_LAST_LOGIN, "");
        editor.putString(Constant.USER_EMPID, userEmpId);
        editor.putString(Constant.USER_DESIGNATION, designationName);
        editor.putString(Constant.USER_LOCATION_REGION, location);
        editor.commit();
        session.setLogin(true);

        Intent intent = new Intent(ctx, ActStoreDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setLastSignIn(String docID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm a");
        String lastseen = sdf.format(new Date());
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        String empId = sharedPreferences.getString(Constant.USER_EMPID, "");
        String password = sharedPreferences.getString(Constant.USER_PASSWORD, "");

        if (!empId.isEmpty() && !password.isEmpty()) {
            etEmail.setText(empId);
            etEmail.setEnabled(false);
            etPassword.setText(password);
        }

    }

    public void localLogin(String email, String password) {
        showProgressbar();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        String empId = sharedPreferences.getString(Constant.USER_EMPID, "");
        String pass = sharedPreferences.getString(Constant.USER_PASSWORD, "");
        Log.d(tag+"offline",email+password);

        if (!empId.isEmpty() && !pass.isEmpty() && empId.equals(email) && pass.equals(password)) {

            Intent intent = new Intent(ctx, ActStoreDashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            hideProgressbar();
        }
        else
        {
            loginFail();
        }
    }

    private String getUniqueId() {
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("tagg", androidId);
        return  androidId;

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
            EasyPermissions.requestPermissions(this, "This app need permissions", CAMERA, perms);

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