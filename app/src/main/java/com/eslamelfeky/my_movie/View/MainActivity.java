package com.eslamelfeky.my_movie.View;

import androidx.lifecycle.ViewModelProviders;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.eslamelfeky.my_movie.Utils.myWidget;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.eslamelfeky.my_movie.Repository.MoviesResult;
import com.eslamelfeky.my_movie.R;
import com.eslamelfeky.my_movie.Utils.TheMoviesAdapter;
import com.eslamelfeky.my_movie.ViewModel.MainViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_movies_list)
    RecyclerView moviesList;
    @BindView(R.id.bottom_bar)
    BottomNavigationView  bottomBar;
    TheMoviesAdapter moviesAdapter;
    MainViewModel mainViewModel;
    int pageNo;
    int menuItemid=R.id.action_recent;
    SharedPreferences sharedPreferences;
    Gson gson=new Gson();
    private static FirebaseAnalytics firebaseAnalytics;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        pageNo=1;
        mainViewModel=ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.observeMovies().observe(this, moviesResult -> {

                        if(pageNo==1||menuItemid==R.id.action_favorite){
                            initMovieList(moviesResult);
                              pageNo=2;
                        }else{

                            pageNext(moviesResult);
                        }
                    if (menuItemid == R.id.action_favorite && moviesResult!=null ) {
                        sharedPreferences=this.getSharedPreferences("pre_widget", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("moviesResult",
                                gson.toJson(moviesResult)).apply();
                        updateWidget();
                    }




        }
        );



    }

    private void pageNext(MoviesResult moviesResult) {
        moviesAdapter.addMovies(moviesResult.getResults());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        moviesAdapter.notifyDataSetChanged();
       // moviesList.setAdapter(moviesAdapter);
         //pageNo++;
    }
    private void updateWidget() {

        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),
                myWidget.class));
        myWidget myWidget = new myWidget();
        myWidget.onUpdate(this, AppWidgetManager.getInstance(this),ids);
    }


    void init(){
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    bottomBar.setOnNavigationItemSelectedListener(menuItem -> {
        menuItemid=menuItem.getItemId();
        switch (menuItem.getItemId()){
            case  R.id.action_recent:
                pageNo=1;
                moviesAdapter.clear();
                    mainViewModel.fetchAllNowPlayingMovies(pageNo);

                break;
            case  R.id.action_popular:
                moviesAdapter.clear();
                pageNo=1;
                    mainViewModel.fetchAllPopularMovies(pageNo);

                break;
            case  R.id.action_top_rated:
                moviesAdapter.clear();
                pageNo=1;
                    mainViewModel.fetchAllTopRatedMovies(pageNo);

                break;
            case  R.id.action_favorite:
                pageNo=1;
                moviesAdapter.clear();
               moviesList.setAdapter(moviesAdapter);
             mainViewModel.getAllFavourite();


                break;
        }
        return true;
    });
}
    private void initMovieList(final MoviesResult moviesResult) {

        GridLayoutManager layoutManager;
        layoutManager= new GridLayoutManager(this,2);
        moviesList.setLayoutManager(layoutManager);

    moviesAdapter=new TheMoviesAdapter(moviesResult);
    moviesList.setAdapter(moviesAdapter);
    moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
            if (!recyclerView.canScrollVertically(1)) {
                Log.d("a7a3a7a","pastVisibleItems "+pastVisibleItems
                        +"totalItemCount "+totalItemCount+"pageNo "+pageNo);

                switch (menuItemid) {
                    case R.id.action_recent:
                        mainViewModel.fetchAllNowPlayingMovies(pageNo);

                        break;
                    case R.id.action_popular:
                        mainViewModel.fetchAllPopularMovies(pageNo);

                        break;
                    case R.id.action_top_rated:
                        mainViewModel.fetchAllTopRatedMovies(pageNo);

                        break;


                }
                pageNo++;
            }

        }
    });
    moviesAdapter.setOnItemClickListener((position,movies )-> {
        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("MovieObject",  movies.get(position));
        intent.putExtra("ButtomBarId",bottomBar.getSelectedItemId());
        startActivity(intent);
    });


}

    @Override
    protected void onResume() {
        super.onResume();
        pageNo=1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        pageNo=1;
    }
}
