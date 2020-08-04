package com.eslamelfeky.my_movie.Utils;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.eslamelfeky.my_movie.Repository.Repository;

public class PopularMoviesIntentServices extends IntentService {
    Repository repository= Repository.getInstance();
    int pageNo;

    public PopularMoviesIntentServices(String name) {
        super(name);
    }
    public PopularMoviesIntentServices() {
        super("PopularMoviesIntentServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        pageNo=intent.getIntExtra("pageNo",1);
        repository.fetchAllPopularMovies(pageNo);
    }
}
