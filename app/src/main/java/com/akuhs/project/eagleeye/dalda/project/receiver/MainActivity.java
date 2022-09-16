package com.akuhs.project.eagleeye.dalda.project.receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akuhs.project.eagleeye.dalda.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCheck = (Button) findViewById(R.id.btn_check);

        // Manually checking internet connection
        checkConnection();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manually checking internet connection
                checkConnection();
            }
        });
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        TextView txt= findViewById(R.id.txtDate);

        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.BLUE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }
//
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
txt.setText(message);
txt.setTextColor(color);
//        View sbView = snackbar.getView();
////        TextView textView = (TextView) sbView.findViewById(android.support.v4.R.id.snackbar_text);
////        textView.setTextColor(color);
//        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}