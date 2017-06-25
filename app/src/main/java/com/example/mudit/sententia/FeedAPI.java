package com.example.mudit.sententia;

import com.example.mudit.sententia.model.RSS;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mudit on 20/6/17.
 */

//Retrofit turns an http api into an interface which can be used by the retrofit object we create later on

public interface FeedAPI {
    String BASE_URL = "http://sententia.in";

    //Static
//  @GET("feed")
//  Call<RSS> getRss();

    //Dynamic URLs
    @GET("{rss_name}/feed")
    Call<RSS> getRss(@Path(value = "rss_name", encoded = true) String rss_name);
}
