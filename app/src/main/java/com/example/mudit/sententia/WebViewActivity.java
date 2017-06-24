package com.example.mudit.sententia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;

import java.io.IOException;

/**
 * Created by mudit on 23/6/17.
 */

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    public static String FACEBOOK_URL = "https://www.facebook.com/SententiaMedia1/";
    public static String PAGE_ID = "SententiaMedia1";

    WebView webview;
    Handler uiHandler = new Handler();
    String link;
    ProgressBar progressBar;
    TextView loadingText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        webview = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.webviewLoadingProgressBar);
        ImageView back_button = (ImageView) findViewById(R.id.back_button);
        loadingText = (TextView) findViewById(R.id.progressText);
        Log.d(TAG, "onCreate: Started.");

        progressBar.setVisibility(View.VISIBLE);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        link = intent.getStringExtra("@string/link");
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        try {
            webview.setWebViewClient(new MyWebViewClient());
            new BackgroundWorker().execute();
        }
        catch(Exception e)
        {
            Log.i(TAG,e.getMessage());
        }

//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl(link);
//
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                progressBar.setVisibility(View.GONE);
//                loadingText.setText("");
//            }
//        });

        ImageView fb = (ImageView) findViewById(R.id.fb);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(WebViewActivity.this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        ImageView twitter = (ImageView) findViewById(R.id.twitter);

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/SententiaMedia1"));
                startActivity(intent);
            }
        });

        ImageView insta = (ImageView) findViewById(R.id.insta);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://instagram.com/_u/sententiamedia1"));
                startActivity(intent);
            }
        });
    }

    // load links in WebView instead of default browser
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @RequiresApi(21)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return false;
        }
    }

    //Does the main part, works Asynchranously
    private class BackgroundWorker extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            getClipped();
            return null;
        }

        public void getClipped() {

            try {
                Document htmlDocument = Jsoup.connect(link).get();
                //Element element = htmlDocument.select("#content > div.herald-section.container").first();
                Document.OutputSettings settings = htmlDocument.outputSettings();

                //Solves the character-encoding issue

                settings.prettyPrint(false);
                settings.escapeMode(Entities.EscapeMode.extended);
                settings.charset("ASCII");

                Element element = htmlDocument.select("div.col-lg-10.col-md-10.col-sm-10").first();
                // replace body with selected element
                if(element!=null) {
                    Log.i(TAG,"Here");
                    htmlDocument.body().empty().append(element.toString());
                    final String html = htmlDocument.toString();

                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            webview.loadData(html, "text/html", "UTF-8");
                            progressBar.setVisibility(View.GONE);
                            loadingText.setText("");
                        }
                    });
                }
            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
                finish();
            }
        }
    }
    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}