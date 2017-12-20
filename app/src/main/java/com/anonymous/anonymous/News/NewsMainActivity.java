package com.anonymous.anonymous.News;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.News.Model.News;
import com.anonymous.anonymous.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pan on 2017/12/16.
 */

public class NewsMainActivity extends AnonymousBaseActivity {
    DiagonalLayout diagonalLayout;
    KenBurnsView kbv;
    SpotsDialog dialog;
    NewsService mService;
    TextView top_author, top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String location = "Seattle AND (WA OR Washington)", sortBy = "popularity", webHotUrl = "";

    ListNewsAdapter adapter;
    RecyclerView lstArticle;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        // Service
        mService = Common.getNewsService();

        dialog = new SpotsDialog(this);

        // View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(location, true);
            }
        });
        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent articleBody = new Intent(getBaseContext(), NewsArticleActivity.class);
                articleBody.putExtra("webURL", webHotUrl);
                startActivity(articleBody);
            }
        });

        kbv = (KenBurnsView) findViewById(R.id.top_image);
        top_author = (TextView) findViewById(R.id.topAuthor);
        top_title = (TextView) findViewById(R.id.top_title);

        //Add location service here

        loadNews(location, false);

        //bottom nav
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);


    }

    private void loadNews(String location, boolean isRefreshed) {
        if (!isRefreshed) {
            dialog.show();
            mService.getNews(Common.getAPIUrl(location, sortBy, Common.API_Key))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();

                            //get first article
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kbv);

                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return super.onNavigationItemSelected(item);
    }


}



