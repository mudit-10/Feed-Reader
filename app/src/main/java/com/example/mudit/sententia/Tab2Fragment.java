package com.example.mudit.sententia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    private static final String extension = "/category/business/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        //Log.d(TAG, "onCreate: Started.");

        Call<RSS> call = feedAPI.getRss(extension);

        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(retrofit2.Call<RSS> call, Response<RSS> response) {

                //Log.d(TAG, "onResponse: Server Response: " + response.toString());
                //Log.d(TAG, "onResponse: feed: " + response.body().toString()); // This is used to display everything in pretty format

                try {
                    List<Item> business_items = response.body().getChannel().getItems();
                    //Log.d(TAG, "onResponse: items: " + response.body().getChannel().getItems());

                    final ArrayList<Post> business_posts = new ArrayList<Post>();
                    for (int i = 0; i < business_items.size(); i++) {
                        business_posts.add(new Post(
                                business_items.get(i).getTitle(),
                                "- " + business_items.get(i).getCreator(),
//                            business_items.get(i).getPubDate(),
                                business_items.get(i).getContent(),
                                business_items.get(i).getLink(),
                                extractImageUrl(business_items.get(i).getContent())
                        ));
                    }
//                for (int j = 0; j < business_posts.size(); j++) {
//                    Log.d(TAG, "onResponse: \n " +
//                            "Title: " + business_posts.get(j).getTitle() + "\n " +
//                            "Creator: " + business_posts.get(j).getCreator() + "\n " +
//                            "PubDate: " + business_posts.get(j).getPubDate() + "\n " +
//                            "Content: " + business_posts.get(j).getContent() + "\n " +
//                            "Link: " + business_posts.get(j).getLink() + "\n " );
//                }
                    //Log.i(TAG, "Business Information successfully saved");

                    ListView mListView = (ListView) view.findViewById(R.id.listview1);
                    mListView.setNestedScrollingEnabled(true);  // So, it becomes collapsible even when I scroll the list

                    CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), R.layout.front_page, business_posts);

                    //Loading Animation
                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.loadingProgressBar);
                    TextView progressText = (TextView) view.findViewById(R.id.progressText);
                    progressBar.setVisibility(View.GONE);
                    progressText.setText("");

                    mListView.setAdapter(customListAdapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Log.d(TAG, "onItemClick: Clicked: " + business_posts.get(position).toString());
                            Intent intent = new Intent(getActivity(), QuickReads.class);
                            intent.putExtra("@string/title", business_posts.get(position).getTitle());
//                        intent.putExtra("@string/creator", business_posts.get(position).getCreator());
//                        intent.putExtra("@string/pubDate", business_posts.get(position).getPubDate());
                            intent.putExtra("@string/content", business_posts.get(position).getContent());
                            intent.putExtra("@string/link", business_posts.get(position).getLink());
                            intent.putExtra("@string/image_url", business_posts.get(position).getImg_url());
                            intent.putExtra("@string/category", "Business");
                            startActivity(intent);
                        }
                    });
                }
                catch (NullPointerException e)
                {
                    Toast.makeText(getActivity(),  "Business stories could't be loaded, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RSS> call, Throwable t) {
                //Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(getActivity(),"Business stories could't be loaded, please try again", Toast.LENGTH_SHORT).show();
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
