package com.akuhs.project.eagleeye.dalda.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.akuhs.project.eagleeye.dalda.R;

import pub.devrel.easypermissions.EasyPermissions;

public class ActLogin extends AppCompatActivity {

    String[] perms = {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    int CAMERA = 150;

    Context ctx= this;

    ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);


        if (!EasyPermissions.hasPermissions(ctx, perms)) {
            EasyPermissions.requestPermissions(ActLogin.this, "This app need permissions", CAMERA, perms);
        }
    }
}