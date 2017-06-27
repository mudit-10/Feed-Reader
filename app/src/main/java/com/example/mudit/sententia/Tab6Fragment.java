package com.example.mudit.sententia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mudit.sententia.model.RSS;
import com.example.mudit.sententia.model.item.Item;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mudit.sententia.Constants.feedAPI;

/**
 * Created by mudit on 19/6/17.
 */

public class Tab6Fragment extends Fragment {
    private static final String TAG = "Tab6Fragment";
    private static final String extension = "/category/blog/";

//    long startTime;
//    long elapsedTime;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        startTime = System.currentTimeMillis();
        final View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        //Log.d(TAG, "onCreate: Started.");

        final Call<RSS> call = feedAPI.getRss(extension);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                call.enqueue(new Callback<RSS>() {
                    @Override
                    public void onResponse(retrofit2.Call<RSS> call, Response<RSS> response) {

                        //Log.d(TAG, "onResponse: feed: " + response.body().toString()); | This is used to display everything in pretty format
                        //Log.d(TAG, "onResponse: Server Response: " + response.toString());
                        try {
                            final List<Item> blog_items = response.body().getChannel().getItems();

                            //Log.d(TAG, "onResponse: items: " + response.body().getChannel().getItems());

                            final ArrayList<Post> blog_posts = new ArrayList<Post>();
                            for (int i = 0; i < blog_items.size(); i++) {
                                blog_posts.add(new Post(
                                        blog_items.get(i).getTitle(),
                                        "- " + blog_items.get(i).getCreator(),
        //                            blog_items.get(i).getPubDate(),
                                        blog_items.get(i).getContent(),
                                        blog_items.get(i).getLink(),
                                        extractImageUrl(blog_items.get(i).getContent())
                                ));
                            }
        //                for (int j = 0; j < blog_posts.size(); j++) {
        //                    Log.d(TAG, "onResponse: \n " +
        //                            "Title: " + blog_posts.get(j).getTitle() + "\n " +
        //                            "Creator: " + blog_posts.get(j).getCreator() + "\n " +
        //                            "PubDate: " + blog_posts.get(j).getPubDate() + "\n " +
        //                            "Content: " + blog_posts.get(j).getContent() + "\n ");
        //                }
                            //Log.i(TAG, "Blog Information successfully saved");

                            ListView mListView = (ListView) view.findViewById(R.id.listview1);
                            mListView.setNestedScrollingEnabled(true);  // So, it becomes collapsible even when I scroll the list

                            CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), R.layout.front_page, blog_posts);

                            //Loading Animation
                            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.loadingProgressBar);
                            TextView progressText = (TextView) view.findViewById(R.id.progressText);
                            progressBar.setVisibility(View.GONE);
                            progressText.setText("");

                            mListView.setAdapter(customListAdapter);

        //                    elapsedTime = System.currentTimeMillis() - startTime;
        //                    Log.i(TAG, "Total elapsed http request/response time in milliseconds: " + elapsedTime);

                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //Log.d(TAG, "onItemClick: Clicked: " + blog_posts.get(position).toString());
                                    Intent intent = new Intent(getActivity(), QuickReads.class);
                                    intent.putExtra("@string/title", blog_posts.get(position).getTitle());
        //                        intent.putExtra("@string/creator", blog_posts.get(position).getCreator());
        //                        intent.putExtra("@string/pubDate", blog_posts.get(position).getPubDate());
                                    intent.putExtra("@string/content", blog_posts.get(position).getContent());
                                    intent.putExtra("@string/link", blog_posts.get(position).getLink());
                                    intent.putExtra("@string/image_url", blog_posts.get(position).getImg_url());
                                    intent.putExtra("@string/category", "Blog");
                                    startActivity(intent);
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(),"Blog stories could't be loaded, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<RSS> call, Throwable t) {
                        //Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                        Toast.makeText(getActivity(),"Blog stories could't be loaded, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 7750);
        return view;
    }
    private String extractImageUrl(String description) {
        Document document = Jsoup.parse(description);
        Elements imgs = document.select("img");

        for (Element img : imgs) {
            if (img.hasAttr("src")) {
                return img.attr("src");
            }
        }
        // no image URL
        return "";
    }
}
