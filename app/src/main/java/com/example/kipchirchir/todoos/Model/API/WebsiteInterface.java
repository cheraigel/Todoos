package com.example.kipchirchir.todoos.Model.API;

import com.example.kipchirchir.todoos.Model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebsiteInterface {
    @GET("top-headlines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
            );
}
