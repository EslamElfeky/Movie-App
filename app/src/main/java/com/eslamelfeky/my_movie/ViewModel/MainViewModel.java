package com.eslamelfeky.my_movie.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.eslamelfeky.my_movie.Repository.MoviesResult;
import com.eslamelfeky.my_movie.Repository.Repository;
import com.eslamelfeky.my_movie.Utils.AllTopRatedMoviesIntentServices;
import com.eslamelfeky.my_movie.Utils.NowPlayingMoviesIntentServices;
import com.eslamelfeky.my_movie.Utils.PopularMoviesIntentServices;


public class MainViewModel extends AndroidViewModel {
  private Repository repository = Repository.getInstance();
  private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        repository.initDatabaseDAO(application);
        fetchAllNowPlayingMovies(1);



    }

    private boolean isNetworkConnected(Context AplicationContext) {

        ConnectivityManager cm = (ConnectivityManager) AplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public   void fetchAllPopularMovies(int pageNo){

        if (isNetworkConnected(context)){
            //repository.fetchAllPopularMovies(pageNo);
            Intent intent=new Intent(context, PopularMoviesIntentServices.class);
            intent.putExtra("pageNo",pageNo);
            context.startService(intent);
        }else{
            Toast.makeText(context.getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }


    }
    public   void fetchAllTopRatedMovies(int pageNo){

        if (isNetworkConnected(context)){
            //repository.fetchAllTopRatedMovies(pageNo);
            Intent intent=new Intent(context, AllTopRatedMoviesIntentServices.class);
            intent.putExtra("pageNo",pageNo);
            context.startService(intent);
        }else{
            Toast.makeText(context.getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }


    }
    public   void fetchAllNowPlayingMovies(int pageNo){

        if (isNetworkConnected(context)){
            //repository.fetchAllNowPlayingMovies(pageNo);
            Intent intent=new Intent(context, NowPlayingMoviesIntentServices.class);
            intent.putExtra("pageNo",pageNo);
            context.startService(intent);
        }else{
            Toast.makeText(context.getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }

    }
    public void getAllFavourite(){

       repository.getAllFavourite();
    }


    public LiveData<MoviesResult> observeMovies(){ return repository.observeMovies(); }
   // public LiveData<List<Movie>> observeFavourites(){ return repository.observeFavourites(); }




}
