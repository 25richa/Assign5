package com.example.richa.mydata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity  extends Activity implements AsyncResponse {
    public static String abc;
    public TextView textView;
    public Button GetData;
    public static String Output;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button GetServerData = (Button) findViewById(R.id.GetServerData);
        GetData = (Button) findViewById(R.id.GetData);
        textView = (TextView) findViewById(R.id.output);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("                   My Data");
        GetServerData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    DownloadWebpageTask asyncTask = new DownloadWebpageTask();
                    asyncTask.delegate = MainActivity.this;
                    asyncTask.execute("https://iiitd.ac.in/about").getStatus();

                    // new DownloadWebpageTask().getStatus();

                } else {
                    Log.d("dsf", "No network connection available.");

                }
            }
        });
    }

    @Override
    public void processFinish(final String output) {
        Output = output;
        textView.setText(output.substring(0, 100) + "........");
        GetData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("txt_out", Output);
                startActivity(intent);
            }
        });
    }
}


class DownloadWebpageTask extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate=null;
    @Override
    protected String doInBackground(String... urls) {

        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // textView.setText(result);
        Log.d("dfsdsf", result);
        delegate.processFinish(result);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int len = 90000;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(40000);
            conn.setConnectTimeout(45000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DEBUG_TAG", "The response is: " + response);
            is = conn.getInputStream();
            String contentAsString = readIt(is, len);
            Log.d("helloooodfsdsf", contentAsString);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}