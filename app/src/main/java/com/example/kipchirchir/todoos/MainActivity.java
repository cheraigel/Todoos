package com.example.kipchirchir.todoos;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kipchirchir.todoos.Model.API.WebsiteAPI;
import com.example.kipchirchir.todoos.Model.API.WebsiteInterface;
import com.example.kipchirchir.todoos.Model.Articles;
import com.example.kipchirchir.todoos.Model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "ce687e68b4ce4fd399f42d70679b8b29";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Articles> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();
    }

    public void LoadJson() {

        WebsiteInterface apiInterface = WebsiteAPI.getWebsiteApi().create(WebsiteInterface.class);

        String country = Utils.getCountry();
        String language = Utils.getLanguage();

        Call<News> call;


        call = apiInterface.getNews(country,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticles();
                    adapter = new Adapter(articles,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {



            }
        });

    }

}