package com.example.mudit.sententia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.example.mudit.sententia.R.id.none;

/**
 * Created by mudit on 23/6/17.
 */

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";

    WebView webview;
    Handler uiHandler = new Handler();
    String link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        webview = (WebView) findViewById(R.id.webview);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.webviewLoadingProgressBar);
        final TextView loadingText = (TextView) findViewById(R.id.progressText);
        Log.d(TAG, "onCreate: Started.");

        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        link = intent.getStringExtra("@string/link");

        try {

            webview.setWebViewClient(new MyWebViewClient());
            new BackgroundWorker().execute();
        }
        catch(Exception e)
        {
            Log.i(TAG,e.getMessage());
        }

//        webview.getSettings().setJavaScriptEnabled(true);
//
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                try {
//                    webview.loadUrl("javascript:(function() { " +
//                            "document.getElementsByTagName('head')[0].style.display= 'none'; " +
//                            "})()");
//                    }
//                catch(Exception e)
//                {
//                    Log.i(TAG, e.getMessage());
//                }
//                progressBar.setVisibility(View.GONE);
//                loadingText.setText("");
//            }
//        });
//        webview.loadUrl(link);
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
                        }
                    });
                }
            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
                finish();
            }
        }
    }
}