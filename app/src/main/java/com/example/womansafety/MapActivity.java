package com.example.womansafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MapActivity extends AppCompatActivity {
    WebView myWebView;
    ProgressBar loadingprogress;
    String url = "https://www.google.com/maps/@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");

        loadingprogress = findViewById(R.id.progressbar);
        myWebView =  findViewById(R.id.webview);

        if (latitude.isEmpty()&&longitude.isEmpty()){
            String url = "https://www.google.com/maps/@";
            Log.d("url",url);
            myWebView.loadUrl(url);
        }else{
            String murl = url+latitude+","+longitude+",17z";
            Log.d("murl",murl);
            myWebView.loadUrl(murl);
        }

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                loadingprogress.setVisibility(View.VISIBLE);
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                loadingprogress.setVisibility(View.GONE);
            }
        });
    }
}
