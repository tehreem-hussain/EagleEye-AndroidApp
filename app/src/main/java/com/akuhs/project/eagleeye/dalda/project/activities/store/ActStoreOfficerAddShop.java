package com.akuhs.project.eagleeye.dalda.project.activities.store;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.akuhs.project.eagleeye.dalda.BuildConfig;
import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.ActAddShopData;
import com.akuhs.project.eagleeye.dalda.project.activities.BaseActivity;
import com.akuhs.project.eagleeye.dalda.project.activities.trademarketing.ActAddTradeMarketing;
import com.akuhs.project.eagleeye.dalda.project.interfaces.ApiInterface;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSales;
import com.akuhs.project.eagleeye.dalda.project.model.shopsalesdata.ShopSalesResponse;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akuhs.project.eagleeye.dalda.project.utils.CameraUtils.checkPermissions;
import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.IMAGE_EXTENSION;
import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.KEY_IMAGE_STORAGE_PATH;
import static com.akuhs.project.eagleeye.dalda.project.utils.Constant.MEDIA_TYPE_IMAGE;

public class ActStoreOfficerAddShop extends BaseActivity {

    private Button btnNext;
    private ImageButton imageButtonShopPic;
    private EditText editShopName;
    private TextView txtLat, txtLng, txtDate,tvCategory;
    private TextView editTextAddress;
    Spinner spinner;
    private RadioGroup radioGroup;
    String selectedRbText;
    private static final String TAG = ActAddShopData.class.getSimpleName();
    private RelativeLayout relativeLayoutRoot;
    private String imgUrl;

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
    private Context ctx = ActStoreOfficerAddShop.this;
    private Uri fileUri;
    private static String imageStoragePath;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    private ImageView img;
    private String encodedImageShop;
    private ApiInterface apiInterface;
    private TextView txtUpdatedOn;
    private Uri fileUris;
    private Bitmap bitmap;
    private byte[] byteImg;
    private ResultReceiver resultReceiver;


    private  String shopId="0";
    private String userRole;
    private String empID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_store_officer_add_shop);
        setupActionBar("Add Shop ", true);

        SharedPreferences sharedPreferences= ctx.getSharedPreferences(Constant.MyPREFERENCES,MODE_PRIVATE);
        userRole= sharedPreferences.getString(Constant.USER_ROLE,"");
        empID= sharedPreferences.getString(Constant.USER_EMPID,"");

        img = findViewById(R.id.imgShop);
        tvCategory= findViewById(R.id.tvCategory);
        txtLat = findViewById(R.id.txtLat);
        txtLng=findViewById(R.id.txtLng);
        txtUpdatedOn = findViewById(R.id.txtDate);
        spinner = (Spinner) findViewById(R.id.spShopList);


        resultReceiver = new AddressResultReceiver(new Handler());
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

        startCamLoc();

        restoreFromBundle(savedInstanceState);
        // initialize the necessary libraries

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
        setLayout();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamLoc();
            }
        });

        getShops();

    }


    private void setLayout() {
        relativeLayoutRoot = findViewById(R.id.relativeLayoutRoot);
        txtLat = findViewById(R.id.txtLat);
        txtLng = findViewById(R.id.txtLng);
        txtDate = findViewById(R.id.txtDate);
        editShopName = findViewById(R.id.edit_shopname);
        editTextAddress = findViewById(R.id.edit_address);
        btnNext = findViewById(R.id.btnNext);
        setBtnNext();
    }

    private void setBtnNext() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDatatoActivity();
            }
        });
    }


    public void sendDatatoActivity() {
        String shopAddress = editTextAddress.getText().toString().trim();
        String shopLat = txtLat.getText().toString().trim();
        String shopLng = txtLng.getText().toString().trim();

        if (validateImage() && validateShop()) {
           if(userRole.equals("so")) {
               Intent intent = new Intent(ctx, ActAddStockPosition.class);
               intent.putExtra(Constant.SHOP_ADDRESS, shopAddress);
               intent.putExtra(Constant.SHOP_LATITUDE, shopLat);
               intent.putExtra(Constant.SHOP_LONGITUDE, shopLng);
               intent.putExtra(Constant.SHOP_IMAGE, byteImg);
               intent.putExtra(Constant.SHOP_ID, shopId);
               intent.putExtra("bmp", bitmap);
               startActivity(intent);
               finish();
           }
           else if(userRole.equals("ba"))
           {
               Intent intent = new Intent(ctx, ActAddTradeMarketing.class);
               intent.putExtra(Constant.SHOP_ADDRESS, shopAddress);
               intent.putExtra(Constant.SHOP_LATITUDE, shopLat);
               intent.putExtra(Constant.SHOP_LONGITUDE, shopLng);
               intent.putExtra(Constant.SHOP_IMAGE, byteImg);
               intent.putExtra(Constant.SHOP_ID, shopId);
               intent.putExtra("bmp", bitmap);
               startActivity(intent);
               finish();
           }


        }

        else {
            Toast.makeText(ctx,"Something went wrong",Toast.LENGTH_LONG).show();

        }
    }

    private boolean validateShop() {
        if(shopId == "0")
        {
            Toast.makeText(ctx,"Please Select Shop",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (editTextAddress.getText().toString() == "") {
            Toast.makeText(ctx,"Please turn on your location",Toast.LENGTH_LONG).show();
            return false;
        }

        else if(txtLat.getText().toString() == "0" ||txtLng.getText().toString() == "0" ) {
            Toast.makeText(ctx,"Please turn on your location",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }




    private boolean validateImage() {
        if (fileUri != null) {
            return true;
        } else {
            showError("Please take picture of shop");
            return false;
        }
    }



    private void showError(String s) {
        Snackbar snackbar = Snackbar.make(relativeLayoutRoot, s, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(ctx, R.color.primary_light));
        snackbar.show();
    }

    private void startCamLoc() {
        if (checkPermissions(getApplicationContext())) {
            captureImage();
            startLocationUpdates();
        }
        else {
            requestCameraPermission(MEDIA_TYPE_IMAGE);
        }
    }

    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                }
            }
        }
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
                                captureImage();
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
                                    rae.startResolutionForResult(ActStoreOfficerAddShop.this, REQUEST_CHECK_SETTINGS);
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
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
                byteImg = stream.toByteArray();
                encodedImageShop = Base64.encodeToString(byteImg, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
                img.setImageBitmap(decodedImage);
                Log.d("encode",byteImg.toString());
                Log.d("encode",encodedImageShop);

//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
//
//                byte[] imageBytes = baos.toByteArray();
//                String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
//                Log.d("ctxIMGStore", base64String);
//                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);



//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                BitmapDrawable ob = new BitmapDrawable(getResources(), photo);
//                imageButtonShopPic.setBackground(ob);
//                img.setVisibility(View.VISIBLE);
//                img.setImageDrawable(ob);
//                Picasso.get().load(fileUri).into(img);
                // Refreshing the gallery
//                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
//                Log.d("ctxIMGStore", imageStoragePath);
//                try {
//                    getImg(fileUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                // successfully captured the image
                // display it in image view
//                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();
            } else {
                // failed to capture image
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
            }
        }

    }

    private void captureImage() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
//        if (file != null) {
//            imageStoragePath = file.getAbsolutePath();
//        }
//
//        fileUri = CameraUtils.getOutputMediaFileUri(ctx, file);
//        Log.d("ctx", fileUri.toString());
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

        if (checkPermissions(ctx)) {

//            File file;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }
//            if (file != null) {
//                imageStoragePath = file.getAbsolutePath();
//            }


            file = new File(ctx.getExternalCacheDir(),
                    System.currentTimeMillis() + ".jpg");

            fileUri = FileProvider.getUriForFile(ctx,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            fileUris = Uri.fromFile(file);
//            Uri fileUris2 = Uri.fromFile(fileUri.);

//            Toast.makeText(ctx, fileUri.toString(), Toast.LENGTH_LONG).show();
//            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
            Log.d("File", String.valueOf(ctx.getExternalCacheDir()));
            Log.d("FileURIS", fileUris.toString());
            Log.d("FileURISORi", fileUri.toString());

//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

        }



    }

    private void previewCapturedImage() {
        try {

            img.setVisibility(View.VISIBLE);
//            Picasso.get().load(fileUri).into(img);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void getImg(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] b = stream.toByteArray();
        encodedImageShop = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("encode",encodedImageShop);
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



    private void getShops() {
        apiInterface = ClientApi.getClient().create(ApiInterface.class);

        Call<ShopSales> call = apiInterface.getShopSales(empID);
        call.enqueue(new Callback<ShopSales>() {
            @Override
            public void onResponse(Call<ShopSales> call, Response<ShopSales> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = gson.toJson(response.body());

                    Log.d("tehreem", "Get CODE: " + test + String.valueOf(response.code()));

                    List<ShopSalesResponse> list = response.body().getResult();
                    //String array to store all the book names
                    String[] items = new String[list.size()];
                    List<ShopSalesResponse> shopNameList=new ArrayList<>();


                    //Traversing through the whole list to get all the names
                    for(int i=0; i<list.size(); i++){
                        //Storing names to string array
                        items[i] = list.get(i).getShopName();
                        shopNameList.add(new ShopSalesResponse(list.get(i).getShopDataId(),list.get(i).getShopName()));
                    }

                    ArrayAdapter<String> adapter;
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
                    //setting adapter to spinner
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ShopSalesResponse model = list.get(position);
                            shopId=model.getShopDataId();
                            String title=model.getShopName();
                            tvCategory.setText(model.getShopCategory());
                            Log.d("tehreem",shopId+title);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    Log.d("tag", String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<ShopSales> call, Throwable t) {
                Log.d("tag", "Failure: " + t.getMessage());

            }
        });

    }




}