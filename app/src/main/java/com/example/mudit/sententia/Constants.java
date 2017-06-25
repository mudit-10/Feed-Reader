package com.example.mudit.sententia;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by mudit on 22/6/17.
 */

public interface Constants {
    String BASE_URL = "http://sententia.in/";

    //Setting TimeOut
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    //Retrofit for parsing
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    //feedAPI object created
    FeedAPI feedAPI = retrofit.create(FeedAPI.class);
}
