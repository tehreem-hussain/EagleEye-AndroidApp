package com.akuhs.project.eagleeye.dalda.project.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.utils.Constant;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class ActAddShopData extends AppCompatActivity {

    private Button btnNext;
    private ImageButton imageButtonShopPic;
    private EditText editShopName, editShopDSR, editShopAddressdetail;
    private TextView txtLat, txtLng, txtDate;
    private EditText editTextAddress;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    String selectedRbText;
    private static final String TAG = ActAddShopData.class.getSimpleName();
    private Context ctx = ActAddShopData.this;
    private RelativeLayout relativeLayoutRoot;
    private String imgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_shop_screen1);

        relativeLayoutRoot = findViewById(R.id.relativeLayoutRoot);
        txtLat = findViewById(R.id.txtLat);
        txtLng = findViewById(R.id.txtLng);
        txtDate = findViewById(R.id.txtDate);
        editShopName = findViewById(R.id.edit_shopname);
        editShopDSR = findViewById(R.id.edit_dsr);
        editTextAddress = findViewById(R.id.edit_address);
        editShopAddressdetail = findViewById(R.id.edit_addressDetail);
        btnNext = findViewById(R.id.btnNext);
        imageButtonShopPic = findViewById(R.id.imgShop);
        radioGroup = findViewById(R.id.radioGroupCategory);
        setToolBar();
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

    private void setToolBar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    //Method to send data and validate
    public void sendDatatoActivity() {
        String shopName = editShopName.getText().toString().trim();
        String shopDSR = editShopDSR.getText().toString().trim();
        String shopAddress = editTextAddress.getText().toString().trim();
        String shopAddressDetail = editShopAddressdetail.getText().toString().trim();
        String shopLat = txtLat.getText().toString().trim();
        String shopLng = txtLng.getText().toString().trim();
        String locationTimeStamp = txtDate.getText().toString().trim();

        if (validateImage() && validateShop(shopName, shopDSR, shopAddress, shopAddressDetail) && validateCategory()) {
            Log.d("Tag", shopName + shopDSR + shopAddress + shopAddressDetail + shopLat + shopLng + selectedRbText + locationTimeStamp + imgUrl);
            Intent intent = new Intent(ActAddShopData.this, AddBrandShopActivity.class);
            intent.putExtra(Constant.SHOP_NAME, shopName);
            intent.putExtra(Constant.SHOP_DSR, shopDSR);
            intent.putExtra(Constant.SHOP_ADDRESS, shopAddress);
            intent.putExtra(Constant.SHOP_ADDRESS_DETAIL, shopAddressDetail);
            intent.putExtra(Constant.SHOP_CATEGORY, selectedRbText);
            intent.putExtra(Constant.SHOP_LOCATION_TIMESTAMP, locationTimeStamp);
            intent.putExtra(Constant.SHOP_LATITUDE, shopLat);
            intent.putExtra(Constant.SHOP_LONGITUDE, shopLng);
            intent.putExtra(Constant.SHOP_IMAGE, imgUrl);
            startActivity(intent);
            finish();


        } else {

        }
    }

    private boolean validateShop(String shopName, String shopDSR, String address, String shopAddressDetail) {
        if (shopName.isEmpty()) {
            editShopName.setError("required");
            editShopName.requestFocus();
            return false;
        }
        if (shopDSR.isEmpty()) {
            editShopDSR.setError("required");
            editShopDSR.requestFocus();
            return false;
        }
        if (address.isEmpty()) {
            editTextAddress.setError("required");
            editTextAddress.requestFocus();
            return false;
        }
        if (shopAddressDetail.isEmpty()) {
            editShopAddressdetail.setError("required");
            editShopDSR.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateShopName(String emailInput) {
        if (emailInput.isEmpty()) {
            editShopName.setError("Field can't be empty");
            return false;
        }
        else {
            editShopName.setError(null);
            return true;
        }
    }

    private boolean validateShopDSR(String emailInput) {
        if (emailInput.isEmpty()) {
            editShopDSR.setError("Field can't be empty");
            return false;
        } else {
            editShopDSR.setError(null);
            return true;
        }
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

    private boolean validateImage() {
        if (imgUrl != null) {
            return true;
        } else {
            showError("Please take picture of shop");
            return false;
        }
    }

    private boolean validateCategory() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            radioButton = findViewById(selectedRadioButtonId);
            selectedRbText = radioButton.getText().toString();
            return true;

        } else {
            showError("Please Select Category");
            return false;
        }
    }

    private void showError(String s) {
        Snackbar snackbar = Snackbar.make(relativeLayoutRoot, s, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(ctx, R.color.primary_light));
        snackbar.show();
    }


}
