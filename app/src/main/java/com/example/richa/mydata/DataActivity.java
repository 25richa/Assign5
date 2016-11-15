package com.example.richa.mydata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DataActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        TextView textView= (TextView) findViewById(R.id.textView);
        String aa= (String) getIntent().getExtras().get("txt_out");
        textView.setText(aa);
    }
    }


