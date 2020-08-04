package com.eslamelfeky.my_movie.Utils;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.eslamelfeky.my_movie.Repository.Repository;

public class NowPlayingMoviesIntentServices extends IntentService {
   Repository repository= Repository.getInstance();
   int pageNo;
    public NowPlayingMoviesIntentServices(String name) {
        super(name);
    }
    public NowPlayingMoviesIntentServices() {
        super("NowPlayingMoviesIntentServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        pageNo=intent.getIntExtra("pageNo",1);
        repository.fetchAllNowPlayingMovies(pageNo);

    }
}
