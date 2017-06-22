package com.example.mudit.sententia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    private static final String extension = "/category/business/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(TAG,extension);

        final View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        Log.d(TAG, "onCreate: Started.");

        Call<RSS> call = feedAPI.getRss(extension);

        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(retrofit2.Call<RSS> call, Response<RSS> response) {

                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                //Log.d(TAG, "onResponse: feed: " + response.body().toString()); // This is used to display everything in pretty format

                List<Item> business_items = response.body().getChannel().getItems();
                //Log.d(TAG, "onResponse: items: " + response.body().getChannel().getItems());

                ArrayList<Post> business_posts = new ArrayList<Post>();
                for (int i = 0; i < business_items.size(); i++) {
                    business_posts.add(new Post(
                            business_items.get(i).getTitle(),
                            "- " + business_items.get(i).getCreator(),
                            business_items.get(i).getPubDate(),
                            business_items.get(i).getContent()
                    ));
                }
                for (int j = 0; j < business_posts.size(); j++) {
                    Log.d(TAG, "onResponse: \n " +
                            "Title: " + business_posts.get(j).getTitle() + "\n " +
                            "Creator: " + business_posts.get(j).getCreator() + "\n " +
                            "PubDate: " + business_posts.get(j).getPubDate() + "\n " +
                            "Content: " + business_posts.get(j).getContent() + "\n ");
                }
                Log.i(TAG, "Business Information successfully saved");

                ListView mListView = (ListView) view.findViewById(R.id.listview1);
                mListView.setNestedScrollingEnabled(true);  // So, it becomes collapsible even when I scroll the list

                CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), R.layout.front_page, business_posts);
                mListView.setAdapter(customListAdapter);
            }

            @Override
            public void onFailure(retrofit2.Call<RSS> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
