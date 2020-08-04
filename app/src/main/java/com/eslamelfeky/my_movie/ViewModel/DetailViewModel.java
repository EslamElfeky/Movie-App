package com.eslamelfeky.my_movie.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.eslamelfeky.my_movie.Repository.Favourite;
import com.eslamelfeky.my_movie.Repository.Repository;
import com.eslamelfeky.my_movie.Repository.ReviewsResult;
import com.eslamelfeky.my_movie.Repository.TrailersResult;

import io.reactivex.Completable;
import io.reactivex.Single;

public  class DetailViewModel extends AndroidViewModel {
    private Repository repository = Repository.getInstance();
    private Context context;

    public DetailViewModel(@NonNull Application application , int id) {
        super(application);
        context=application.getApplicationContext();
        repository.initDatabaseDAO(application);
            fetchAllTrailers(id);
            fetchAllReviews(id);

    }



    private boolean isNetworkConnected(Context AplicationContext) {

        ConnectivityManager cm = (ConnectivityManager) AplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public   void fetchAllTrailers(int id){

        if (isNetworkConnected(context)){
            repository.fetchAllTrailers(id);
        }else{
            Toast.makeText(context.getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }

    }
     public   void fetchAllReviews(int id){

         if (isNetworkConnected(context)){
             repository.fetchAllReviews(id);
         }else{
             Toast.makeText(context.getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
         }

     }

    public Completable insert(final Favourite favourite){
        return repository.insert(favourite);
    }

    public Completable  update(final Favourite favourite){
        return repository.update(favourite);
    }

    public Completable  delete(final String movie_id_detail){ return repository.delete(movie_id_detail); }

    public Single<String> getFavouriteById(String id){
        return repository.getFavouriteById(id);
    }

    public LiveData<TrailersResult> observeTrailers(){
        return repository.observeTrailers();
    }
    public LiveData<ReviewsResult> observeReviews(){return repository.observeReviews();}
    public void getAllFavourite(){ repository.getAllFavourite(); }


}
