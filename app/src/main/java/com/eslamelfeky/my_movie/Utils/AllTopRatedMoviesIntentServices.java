package com.eslamelfeky.my_movie.Utils;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.eslamelfeky.my_movie.Repository.Repository;

public class AllTopRatedMoviesIntentServices extends IntentService {
    Repository repository= Repository.getInstance();
    int pageNo;

    public AllTopRatedMoviesIntentServices(String name) {
        super(name);
    }
    public AllTopRatedMoviesIntentServices() {
        super("AllTopRatedMoviesIntentServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        pageNo=intent.getIntExtra("pageNo",1);
        repository.fetchAllTopRatedMovies(pageNo);
    }
}
