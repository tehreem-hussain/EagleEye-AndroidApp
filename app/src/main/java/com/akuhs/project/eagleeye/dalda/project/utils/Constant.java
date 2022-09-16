package com.akuhs.project.eagleeye.dalda.project.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.akuhs.project.eagleeye.dalda.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    public  final  static String COLLECTION_NODE_USERS="users";
    public  final  static String COLLECTION_NODE_USERS_LASTLOGIN="lastLogin";

    //Shared Preference Key
    public static final String MyPREFERENCES = "MyPrefsShare" ;
    public static final String USER_NAME = "nameKey";
    public static final String USER_PHONE = "phoneKey";
    public static final String USER_EMAIL = "emailKey";
    public static final String USER_LAST_LOGIN = "lastloginKey";
    public static final String USER_PASSWORD = "passwordKey";
    public static final String USER_ID= "userID";
    public static final String USER_DESIGNATION= "designationKey";
    public static final String USER_LOCATION_REGION = "user_region";
    public static final String DATABASE_NAME = "eagleeye_database";
    public static final String USER_LOGIN_BIT = "user_login_bit";
    public static final String SHOP_ID = "shop_id";
    public static final String USER_ROLE = "user_role";

    private static  final String PACKAGE_NAME = "com.dalda.eagleeye";
    public  static final String RESULT_DATA_KEY= PACKAGE_NAME+".RESULT_DATA_KEY";
    public  static final String RECEIVER= PACKAGE_NAME+".RECEIVER";
    public  static final String LOCATION_DATA_EXTRA= PACKAGE_NAME+".LOCATION_DATA_EXTRA";
    public  static  final int SUCCESS_RESULT=1;
    public  static final int FAILURE_RESULT= 0;

//    public static final String BASE_URL = "http://a16619cf9de8.ngrok.io/restapi/";
    public static final String BASE_URL = "http://webapis.dfleagleeye.com/dfleagleeye/";


    //Shop data key
    public static final String SHOP_NAME="shopName";
    public static final String SHOP_DSR="shopDSR";
    public static final String SHOP_ADDRESS="shopAddress";
    public static final String SHOP_ADDRESS_DETAIL="shopAddressDetail";
    public static final String SHOP_LATITUDE="shopLatitude";
    public static final String SHOP_LONGITUDE="shopLongitude";
    public static final String SHOP_IMAGE="shopImage";
    public static final String SHOP_CATEGORY="shopCategory";
    public static final String SHOP_LOCATION_TIMESTAMP="shopLocationTimestamp";
    public static final String SHOP_USER_ID= "shopUserID";
    public static final String SHOP_BIT= "shopBit";
    public static final String SHOP_ADDED_USERNAME= "shopAddedUserName";
    public static final String SHOP_GEOPOINT= "shopGEOpoint";

    public static final String USER_EMPID ="userEmpID" ;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_GALLERY = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Dalda_EagleEye_Images";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";


    public static String getDateInFormat() {


        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat fmtOut = new SimpleDateFormat("EEE, dd MMM");
            return fmtOut.format(new Date());
        }catch (Exception e){

            return "-";
        }

    }

    public static void showError(RelativeLayout linearLayout, String msg, Context ctx)
    {
        Snackbar snackbar = Snackbar.make(linearLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(ctx, R.color.primary_light));
        snackbar.show();
    }


    public static boolean getConnectionType(Context context) {
        boolean result = false; // Returns connection type. 0: none; 1: mobile data; 2: wifi
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = true;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }


    public static boolean isNetworkAvailable(Context context) {
        if(context == null)  return false;


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }


}
