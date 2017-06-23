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
import android.widget.Toast;

import com.example.mudit.sententia.model.RSS;
import com.example.mudit.sententia.model.item.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.example.mudit.sententia.Constants.feedAPI;
import static com.example.mudit.sententia.Constants.retrofit;

/**
 * Created by mudit on 19/6/17.
 */

public class Tab5Fragment extends Fragment {
    private static final String TAG = "Tab5Fragment";
    private static final String extension = "/category/technology/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        Log.d(TAG, "onCreate: Started.");

        Call<RSS> call = feedAPI.getRss(extension);

        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(retrofit2.Call<RSS> call, Response<RSS> response) {

                //Log.d(TAG, "onResponse: feed: " + response.body().toString()); | This is used to display everything in pretty format
                //Log.d(TAG, "onResponse: Server Response: " + response.toString());
                List<Item> tech_items = response.body().getChannel().getItems();

                //Log.d(TAG, "onResponse: items: " + response.body().getChannel().getItems());

                final ArrayList<Post> tech_posts = new ArrayList<Post>();
                for (int i = 0; i < tech_items.size(); i++) {
                    tech_posts.add(new Post(
                            tech_items.get(i).getTitle(),
                            "- " + tech_items.get(i).getCreator(),
                            tech_items.get(i).getPubDate(),
                            tech_items.get(i).getContent(),
                            tech_items.get(i).getLink()
                    ));
                }
//                for (int j = 0; j < tech_posts.size(); j++) {
//                    Log.d(TAG, "onResponse: \n " +
//                            "Title: " + tech_posts.get(j).getTitle() + "\n " +
//                            "Creator: " + tech_posts.get(j).getCreator() + "\n " +
//                            "PubDate: " + tech_posts.get(j).getPubDate() + "\n " +
//                            "Content: " + tech_posts.get(j).getContent() + "\n ");
//                }
                Log.i(TAG, "Tech Information successfully saved");

                ListView mListView = (ListView) view.findViewById(R.id.listview1);
                mListView.setNestedScrollingEnabled(true);  // So, it becomes collapsible even when I scroll the list

                CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), R.layout.front_page, tech_posts);
                mListView.setAdapter(customListAdapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "onItemClick: Clicked: " + tech_posts.get(position).toString());
                        Intent intent = new Intent(getActivity(), QuickReads.class);
                        intent.putExtra("@string/title", tech_posts.get(position).getTitle());
                        intent.putExtra("@string/creator", tech_posts.get(position).getCreator());
                        intent.putExtra("@string/pubDate", tech_posts.get(position).getPubDate());
                        intent.putExtra("@string/content", tech_posts.get(position).getContent());
                        intent.putExtra("@string/content", tech_posts.get(position).getLink());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<RSS> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(getActivity(), "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
