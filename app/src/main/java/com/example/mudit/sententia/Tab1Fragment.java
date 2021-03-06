package com.example.mudit.sententia;

import android.content.Intent;
import android.os.Bundle;
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

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";
    private static final String extension = "";

//    long startTime;
//    long elapsedTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Log.d(TAG, "onCreate: Started.");

//        startTime = System.currentTimeMillis();

        final View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        Call<RSS> call = feedAPI.getRss(extension);
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(retrofit2.Call<RSS> call, Response<RSS> response) {

                try {

                    //Log.d(TAG, "onResponse: feed: " + response.body().toString()); | This is used to display everything in pretty format
                    //Log.d(TAG, "onResponse: Server Response: " + response.toString());

                    List<Item> items = response.body().getChannel().getItems();
                    //Log.d(TAG, "onResponse: items: " + response.body().getChannel().getItems());

                    final ArrayList<Post> posts = new ArrayList<Post>();
                    for (int i = 0; i < items.size(); i++) {
                        posts.add(new Post(
                                items.get(i).getTitle(),
                                "- " + items.get(i).getCreator(),
//                                items.get(i).getPubDate(),
                                items.get(i).getContent(),
                                items.get(i).getLink(),
                                extractImageUrl(items.get(i).getContent())
                        ));
                    }
//                    for(int j = 0; j<posts.size(); j++) {
//                        Log.d(TAG, "onResponse: \n " +
//                                "Title: " + posts.get(j).getTitle() + "\n " +
//                                "Creator: " + posts.get(j).getCreator() + "\n " +
//                                "PubDate: " + posts.get(j).getPubDate() + "\n "+
//                                "Content: " + posts.get(j).getContent() + "\n "+
//                                "Link: " + posts.get(j).getLink() + "\n " +
//                                "img: " + posts.get(j).getImg_url() + "\n ");
//                    }
                    //Log.i(TAG, "Home Information successfully saved");

                    ListView mListView = (ListView) view.findViewById(R.id.listview1);
                    mListView.setNestedScrollingEnabled(true);  // So, it becomes collapsible even when I scroll the list

                    CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), R.layout.front_page, posts);

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
                            //Log.d(TAG, "onItemClick: Clicked: " + posts.get(position).toString());
                            Intent intent = new Intent(getActivity(), QuickReads.class);
                            intent.putExtra("@string/title", posts.get(position).getTitle());
//                            intent.putExtra("@string/creator", posts.get(position).getCreator());
//                            intent.putExtra("@string/pubDate", posts.get(position).getPubDate());
                            intent.putExtra("@string/content", posts.get(position).getContent());
                            intent.putExtra("@string/link", posts.get(position).getLink());
                            intent.putExtra("@string/image_url", posts.get(position).getImg_url());
                            intent.putExtra("@string/category", "Home");
                            startActivity(intent);
                        }
                    });
                }
                catch (NullPointerException e)
                {
                    Toast.makeText(getActivity(), "Home stories could't be loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RSS> call, Throwable t) {
                //Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage() );
                Toast.makeText(getActivity(), "Error: Home story could't be loaded, please try again"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
