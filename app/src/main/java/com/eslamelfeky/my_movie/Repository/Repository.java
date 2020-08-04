package com.eslamelfeky.my_movie.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    TheMovieDbApi theMovieDbApi;
    private  static Repository repository =null;
    private FavouriteDao dao;
    List<Movie> temp=new ArrayList<>() ;
    private MutableLiveData<MoviesResult> AllPopularMovies;
    private MutableLiveData<MoviesResult> AllTopRatedMovies;
    private MutableLiveData<MoviesResult> AllNowPlayingMovies;
    private LiveData<TrailersResult> AllTrailers;
    private LiveData<ReviewsResult> AllReviews;
    private LiveData<List<Favourite>> allFavourite;
    private MediatorLiveData<MoviesResult> Movies;
    private MediatorLiveData<TrailersResult> Trailers;
    private MediatorLiveData<ReviewsResult> Reviews;
    private MediatorLiveData<List<Movie>>movieList;



    public static Repository getInstance(){
        if (repository ==null) {
           return repository = new Repository();
        }else
            {
            return repository;
            }
    }


    public Repository() {
        Movies= new MediatorLiveData<>();
        Trailers=new MediatorLiveData<>();
        Reviews=new MediatorLiveData<>();
       movieList=new MediatorLiveData<>();
        AllPopularMovies=new MutableLiveData<>();
        AllTopRatedMovies=new MutableLiveData<>();
        AllNowPlayingMovies=new MutableLiveData<>();
        theMovieDbApi=ApiClient.getClient().create(TheMovieDbApi.class);
        initMovie();

    }

    private void initMovie() {
        Movies.addSource(AllPopularMovies, moviesResults -> {
            Movies.setValue(moviesResults);
        });
        Movies.addSource(AllTopRatedMovies, moviesResults -> {
            Movies.setValue(moviesResults);
        });
        Movies.addSource(AllNowPlayingMovies, moviesResults -> {
            Movies.setValue(moviesResults);
        });

    }

    public void initDatabaseDAO(Application application) {
        FavouriteDatabase database=FavouriteDatabase.getInstance(application);
        dao = database.favouriteDao();


    }

    public Completable  insert(final Favourite favourite){
     return Completable.fromRunnable(()->dao.insert(favourite)).subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable  update(final Favourite favourite){
        return Completable.fromRunnable(()->dao.update(favourite)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable  delete(final String movie_id_detail){
        return Completable.fromRunnable(()->dao.delete(movie_id_detail)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<String> getFavouriteById(String id){
        Single<String> favouriteById= Single.fromCallable(()->dao.getFavouriteId(id)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return favouriteById;
    }

    public void getAllFavourite(){
        allFavourite = dao.getAllFavourite();

           Movies.addSource(allFavourite, favourites -> {
               temp.clear();
                   for (Favourite f : favourites) {
                       Movie movie = new Movie(f.getMoviePoster(), f.getMovieSynopsis(), f.getMovieReleaseDate()
                               , Integer.parseInt(f.getMovieId()), f.getMovieTitle(), f.getMovieUserRating(),f.MovieBackdropPath);
                       temp.add(movie);
                   }
               Log.d("a7a",String.valueOf(temp.size()));
               MoviesResult  moviesResult=new MoviesResult(temp);
               Movies.setValue(moviesResult);
               Movies.removeSource(allFavourite);

           });


    }


    public void fetchAllPopularMovies(int pageNo){



     theMovieDbApi.fetchAllPopularMovies(pageNo).subscribeOn(Schedulers.io())
            .subscribe(new SingleObserver<MoviesResult>() {

               @Override
               public void onSubscribe(Disposable d) {

               }

               @Override
               public void onSuccess(MoviesResult moviesResult) {

                   AllPopularMovies.postValue(moviesResult);

               }

               @Override
            public void onError(Throwable t) {
                Log.d("a7a", "onError: "+t.getMessage());
            }



        });







    }
  public  void fetchAllTopRatedMovies(int pageNo){



      theMovieDbApi.fetchAllTopRatedMovies(pageNo).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<MoviesResult>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onSuccess(MoviesResult moviesResult) {
              AllTopRatedMovies.postValue(moviesResult);
          }

          @Override
          public void onError(Throwable e) {

          }
      });





    }
   public void fetchAllNowPlayingMovies(int pageNo){

       theMovieDbApi.fetchAllNowPlayingMovies(pageNo).subscribeOn(Schedulers.io())
               .subscribe(new SingleObserver<MoviesResult>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onSuccess(MoviesResult moviesResult) {
                       AllNowPlayingMovies.postValue(moviesResult);
                   }

                   @Override
                   public void onError(Throwable e) {

                   }
               });




    }
    public void fetchAllTrailers(int id){

        AllTrailers=LiveDataReactiveStreams.fromPublisher(theMovieDbApi.fetchAllTrailers(id).subscribeOn(Schedulers.io()));
        Trailers.addSource(AllTrailers, trailersResult -> {
            Trailers.setValue(trailersResult);
            Trailers.removeSource(AllTrailers);
        });


    }
    public void fetchAllReviews(int id){

        AllReviews=LiveDataReactiveStreams.fromPublisher(theMovieDbApi.fetchAllReviews(id).subscribeOn(Schedulers.io()));
        Reviews.addSource(AllReviews, reviewsResult -> {
            Reviews.setValue(reviewsResult);
            Reviews.removeSource(AllReviews);
        });


    }

    public LiveData<MoviesResult> observeMovies(){
        return Movies;
    }
    public LiveData<TrailersResult> observeTrailers(){ return Trailers;}
    public LiveData<ReviewsResult> observeReviews(){ return Reviews;}




}
