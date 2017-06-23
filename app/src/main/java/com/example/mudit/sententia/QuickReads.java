package com.example.mudit.sententia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by mudit on 23/6/17.
 */

public class QuickReads extends AppCompatActivity {
    private static String TAG = "QuickReads";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_reads);

        String title;
        String creator;
        String pubDate;
        String content;
        String link;

        Intent incomingIntent = getIntent();
        title = incomingIntent.getStringExtra("@string/title");
        creator = incomingIntent.getStringExtra("@string/creator");
        pubDate = incomingIntent.getStringExtra("@string/pubDate");
        content = incomingIntent.getStringExtra("@string/content");
        link = incomingIntent.getStringExtra("@string/link");


        TextView contentView = (TextView) findViewById(R.id.contentView);
        TextView titleView = (TextView) findViewById(R.id.titleView);
       contentView.setText(Html.fromHtml(content));
//        titleView.setText(title);

        //contentView.setText(Html.fromHtml(content,new URLImageParser(contentView, this), null));
        titleView.setText(title);
    }
}
