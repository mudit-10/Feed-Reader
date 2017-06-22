package com.example.mudit.sententia;

import android.util.Log;

import com.example.mudit.sententia.model.RSS;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by mudit on 22/6/17.
 */

public interface Constants {
    String BASE_URL = "http://sententia.in/";

    //Retrofit for parsing
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
}
