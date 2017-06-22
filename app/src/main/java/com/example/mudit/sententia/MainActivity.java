package com.example.mudit.sententia;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.example.mudit.sententia.model.Channel;
import com.example.mudit.sententia.model.RSS;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://sententia.in/";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"Created");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(6);         /* limit is a fixed integer, doesn't refresh the fragment */
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "HOME");
        adapter.addFragment(new Tab2Fragment(), "BUSINESS");
        adapter.addFragment(new Tab3Fragment(), "POLITICS");
        adapter.addFragment(new Tab4Fragment(), "SPORTS");
        adapter.addFragment(new Tab5Fragment(), "TECH");
        adapter.addFragment(new Tab6Fragment(), "BLOG");
        viewPager.setAdapter(adapter);
    }
}
