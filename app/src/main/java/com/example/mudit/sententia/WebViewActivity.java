package com.example.mudit.sententia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.example.mudit.sententia.R.id.none;

/**
 * Created by mudit on 23/6/17.
 */

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        final WebView webview = (WebView) findViewById(R.id.webview);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.webviewLoadingProgressBar);
        final TextView loadingText = (TextView) findViewById(R.id.progressText);
        Log.d(TAG, "onCreate: Started.");

        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String link = intent.getStringExtra("@string/link");
        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    webview.loadUrl("javascript:(function() { " +
                            "document.getElementsByTagName('head')[0].style.display= 'none';" +
                            "})()");
                    Log.i(TAG, "javascript:(function() { " +
                            "document.getElementsByTagName('head')[0].style.display= 'none';" +
                            "})()"
                    );
                }
                catch(Exception e)
                {
                    Log.i(TAG, e.getMessage());
                }
                progressBar.setVisibility(View.GONE);
                loadingText.setText("");
            }
        });
        webview.loadUrl(link);
    }
}
