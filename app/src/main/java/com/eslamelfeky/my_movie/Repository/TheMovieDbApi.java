package com.eslamelfeky.my_movie.Repository;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbApi {
    @GET("movie/popular?api_key=yourApiNo&language=en-US")
    Single<MoviesResult> fetchAllPopularMovies(@Query("page") int pageNo);
    @GET("movie/top_rated?api_key=yourApiNo&language=en-US")
    Single<MoviesResult> fetchAllTopRatedMovies(@Query("page") int pageNo);
    @GET("movie/now_playing?api_key=yourApiNo&language=en-US")
    Single<MoviesResult>fetchAllNowPlayingMovies(@Query("page") int pageNo);
    @GET("movie/{id}/videos?api_key=yourApiNof&language=en-US")
    Flowable<TrailersResult>fetchAllTrailers(@Path("id")int id);
    @GET("movie/{id}/reviews?api_key=yourApiNo&language=en-US")
    Flowable<ReviewsResult> fetchAllReviews(@Path("id")int id);
}
