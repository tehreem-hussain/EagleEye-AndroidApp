package com.akuhs.project.eagleeye.dalda.project.activities.store;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.akuhs.project.eagleeye.dalda.BuildConfig;
import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.ActAddShopData;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.attendance.AttendanceModel;
import com.akuhs.project.eagleeye.dalda.project.model.attendance.AttendanceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.authenticate.AuthenticateResponse;
import com.akuhs.project.eagleeye.dalda.project.model.authenticate.AuthenticateResult;
import com.akuhs.project.eagleeye.dalda.project.services.FetchAddressIntentService;
import com.akuhs.project.eagleeye.dalda.project.utils.CameraUtils;
import com.akuhs.project.eagleeye.dalda.project.utils.ClientApi;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akuhs.project.eagleeye.dalda.project.utils.CameraUtils.checkPermissions;
import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.MEDIA_TYPE_IMAGE;
import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.getDateInFormat;

public class ActUserAttendance extends BaseActivity {

    private Button btnNext;
    private EditText editShopAddressdetail;
    private TextView txtLat, txtLng, txtDate;

    private TextView tvCheckInLocation,tvCheckInDate,tvCheckInLatLng;
    private TextView tvAttendanceTitle;
    private TextView tvCheckOutLocation,tvCheckOutDate,tvCheckOutLatLng;
    private LinearLayout llCheckedIn,llCheckedOut;
    private TextView tvDateToday;

    private EditText editTextAddress;
    private RadioButton radioButton;
    private static final String TAG = ActAddShopData.class.getSimpleName();
    private RelativeLayout relativeLayoutRoot;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    private static final int PICK_IMAGE_REQUEST = 1000;
    private static final int GALLERY_IMAGE_PICK = 1000;
    private Context ctx = ActUserAttendance.this;

    // Activity request codes

    private ApiInterface apiInterface;
    private TextView txtUpdatedOn;
    private ResultReceiver resultReceiver;
    private ProgressDialog progressDialog;
    private String tag="0000";
    private double Lat,Lng;
    private String empID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_attendance);
        txtLat = findViewById(R.id.txtLat);
        txtLng=findViewById(R.id.txtLng);
        txtUpdatedOn = findViewById(R.id.txtDate);
        resultReceiver = new AddressResultReceiver(new Handler());

        SharedPreferences sharedPreferences= ctx.getSharedPreferences(Constant.MyPREFERENCES,MODE_PRIVATE);
        empID= sharedPreferences.getString(Constant.USER_EMPID,"");


        tvCheckInLocation= findViewById(R.id.tvCheckInLocation);
        tvCheckInDate= findViewById(R.id.tvCheckInDate);
        tvCheckInLatLng= findViewById(R.id.tvCheckInLatLng);

        tvCheckOutLocation= findViewById(R.id.tvCheckOutLocation);
        tvCheckOutDate= findViewById(R.id.tvCheckOutDate);
        tvCheckOutLatLng= findViewById(R.id.tvCheckOutLatLng);

        llCheckedOut= findViewById(R.id.llCheckedOut);
        llCheckedIn= findViewById(R.id.llCheckedIn);
        tvAttendanceTitle= findViewById(R.id.tvAttendanceTitle);
        tvDateToday=findViewById(R.id.tvDateToday);

        tvDateToday.setText(""+getDateInFormat());

        setupActionBar("Add Attendance ", true);


        init();
        startLocationUpdates();
        updateLocationUI();
        // Checking availability of the camera
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }


        // initialize the necessary libraries

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
        setLayout();


        findViewById(R.id.btnCheckIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mCurrentLocation != null ) {
                    Lat = mCurrentLocation.getLatitude();
                    Lng = mCurrentLocation.getLongitude();
                    if(Lat !=0 && Lng != 0 && editTextAddress.getText().toString().trim() != null)
                    {
                        checkIn(Lat, String.valueOf(Lng), editTextAddress.getText().toString().trim());

                    }
                }

                else
                {
                    Toast.makeText(ctx,"Location required",Toast.LENGTH_LONG).show();
                }
            }
        });


        findViewById(R.id.btnCheckOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mCurrentLocation != null ) {
                    Lat = mCurrentLocation.getLatitude();
                    Lng = mCurrentLocation.getLongitude();
                    if(Lat !=0 && Lng != 0 && editTextAddress.getText().toString().trim() != null)
                    {
                        checkOut(Lat, String.valueOf(Lng), editTextAddress.getText().toString().trim());

                    }
                }

                else
                {
                    Toast.makeText(ctx,"Location required",Toast.LENGTH_LONG).show();
                }
            }
        });

    getAttendanceChecked();
    }


    private void setLayout() {
        relativeLayoutRoot = findViewById(R.id.relativeLayoutRoot);
        txtLat = findViewById(R.id.txtLat);
        txtLng = findViewById(R.id.txtLng);
        txtDate = findViewById(R.id.txtDate);
        editTextAddress = findViewById(R.id.edit_address);
        editShopAddressdetail = findViewById(R.id.edit_addressDetail);
//        btnNext = findViewById(R.id.btnNext);
        setBtnNext();
    }

    private void setBtnNext() {
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendDatatoActivity();
//            }
//        });
    }


    public void sendDatatoActivity() {

        String shopAddress = editTextAddress.getText().toString().trim();
        String shopAddressDetail = editShopAddressdetail.getText().toString().trim();
        String shopLat = txtLat.getText().toString().trim();
        String shopLng = txtLng.getText().toString().trim();
        String locationTimeStamp = txtDate.getText().toString().trim();

//        if (validateImage() && validateShop(shopName, shopDSR, shopAddress, shopAddressDetail) && validateCategory()) {
//            Log.d("Tag", shopName + shopDSR + shopAddress + shopAddressDetail + shopLat + shopLng + selectedRbText + locationTimeStamp + imgUrl);
//            Intent intent = new Intent(ctx, AddBrandShopActivity.class);
//            intent.putExtra(Constant.SHOP_NAME, shopName);
//            intent.putExtra(Constant.SHOP_DSR, shopDSR);
//            intent.putExtra(Constant.SHOP_ADDRESS, shopAddress);
//            intent.putExtra(Constant.SHOP_ADDRESS_DETAIL, shopAddressDetail);
//            intent.putExtra(Constant.SHOP_CATEGORY, selectedRbText);
//            intent.putExtra(Constant.SHOP_LOCATION_TIMESTAMP, locationTimeStamp);
//            intent.putExtra(Constant.SHOP_LATITUDE, shopLat);
//            intent.putExtra(Constant.SHOP_LONGITUDE, shopLng);
//            intent.putExtra(Constant.SHOP_IMAGE, byteImg);
//            intent.putExtra("bmp", bitmap);
////            intent.putExtra(Constant.SHOP_IMAGE, fileUri);
////            intent.putExtra(Constant.SHOP_IMAGE, encodedImageShop);
//            startActivity(intent);
//            finish();


//        } else {
//
//        }
    }



    private boolean validateShopAddressDetail(String input) {
        if (input.isEmpty()) {
            editShopAddressdetail.setError("Field can't be empty");
            return false;
        } else {
            editShopAddressdetail.setError(null);
            return true;
        }
    }


    private void showError(String s) {
        Snackbar snackbar = Snackbar.make(relativeLayoutRoot, s, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(ctx, R.color.primary_light));
        snackbar.show();
    }


    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            mRequestingLocationUpdates = true;
                            startLocationUpdates();
                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

//                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(ActUserAttendance.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

//                                Toast.makeText(ctx, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(ctx);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

    }

    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

//        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            txtLat.setText(
                    ""+ mCurrentLocation.getLatitude()            );
            // giving a blink animation on TextView
            txtLat.setAlpha(0);
            txtLat.animate().alpha(1).setDuration(300);
            txtLng.setText(mCurrentLocation.getLongitude()+"");
            // location last updated time
            txtUpdatedOn.setText("" + mLastUpdateTime);
            fetchAddressfromLatLng(mCurrentLocation);

        }

    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRequestingLocationUpdates && checkPermissions(ctx)) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void fetchAddressfromLatLng(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constant.RECEIVER, resultReceiver);
        intent.putExtra(Constant.LOCATION_DATA_EXTRA, location);
        startService(intent);

    }

    private class AddressResultReceiver extends ResultReceiver {


        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constant.SUCCESS_RESULT) {
                editTextAddress.setText(resultData.getString(Constant.RESULT_DATA_KEY));
            } else {
//                Toast.makeText(DashboardActivityMain.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }

//            progressBarLocation.setVisibility(View.GONE);
        }
    }


    private void checkIn(double txtLat, String txtLng, String txtAddress) {
        showProgressbar("Checking In");

        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<AuthenticateResponse> call = apiInterface.postUserCheckedIn(empID, txtLat,txtLng,txtAddress);
        call.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    AuthenticateResult usermodel = response.body().getResult();
                    hideProgressbar();
                    Toast.makeText(ctx,"Successfully checked In",Toast.LENGTH_LONG).show();
                    getAttendanceChecked();



                } else {
                    Log.d(tag, String.valueOf(response.code()));
                    Log.d(tag, String.valueOf(response.raw()));
                    hideProgressbar();
                    Toast.makeText(ctx,"Already checked In",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();

            }
        });

    }
    private void getAttendanceChecked() {
        showProgressbar("Loading");

        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<AttendanceModel> call = apiInterface.getUserAttendanceChecked(empID);
        call.enqueue(new Callback<AttendanceModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<AttendanceModel> call, Response<AttendanceModel> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    AttendanceResponse attendanceResponse = response.body().getResult();
                    Log.d(tag, "Get CODE: " + attendanceResponse + String.valueOf(response.code()));

                    tvAttendanceTitle.setVisibility(View.VISIBLE);
                    llCheckedIn.setVisibility(View.VISIBLE);

                    tvCheckInLocation.setText(attendanceResponse.getCheckInLocation());
                    tvCheckInDate.setText( attendanceResponse.getCheckIn());
                    tvCheckInLatLng.setText("Latitude:"+attendanceResponse.getCheckInLat() +"\t  Longitude:"+
                            attendanceResponse.getCheckInLng());

                    tvCheckOutLocation.setText(attendanceResponse.getCheckOutLocation());
                    tvCheckOutDate.setText( attendanceResponse.getCheckOut());
                    if(attendanceResponse.getCheckOutLat() != null)
                    {
                        llCheckedOut.setVisibility(View.VISIBLE);
                        tvCheckOutLatLng.setText("Latitude:"+attendanceResponse.getCheckOutLat() +"\t  Longitude:"+
                                attendanceResponse.getCheckOutLng());
                    }


                    hideProgressbar();



                } else {
                    Log.d(tag, String.valueOf(response.code()));
                    Log.d(tag, String.valueOf(response.raw()));
                    hideProgressbar();
//                    Toast.makeText(ctx,"Failed",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<AttendanceModel> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();

            }
        });

    }
    private void checkOut(double txtLat, String txtLng, String txtAddress) {
        showProgressbar("Checking Out");

        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<AuthenticateResponse> call = apiInterface.postUserCheckedOut(empID, txtLat,txtLng,txtAddress);
        call.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());
                    Log.d(tag, "Get CODE: " + test + String.valueOf(response.code()));
                    AuthenticateResult usermodel = response.body().getResult();
                    hideProgressbar();
                    Toast.makeText(ctx,"Successfully checked Out",Toast.LENGTH_LONG).show();
                    getAttendanceChecked();



                } else {
                    Log.d(tag, String.valueOf(response.code()));
                    Log.d(tag, String.valueOf(response.raw()));
                    hideProgressbar();
                    Toast.makeText(ctx,"Already checked Out",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                Log.d(tag, "Failure: " + t.getMessage());
                hideProgressbar();

            }
        });

    }

    private void showProgressbar(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(msg);
        progressDialog.show();
    }

    private void hideProgressbar() {
        progressDialog.dismiss();
    }

}