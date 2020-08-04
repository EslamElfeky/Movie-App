package com.eslamelfeky.my_movie.Repository;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_table")
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "movie_title")
    String movieTitle;
    @ColumnInfo(name = "movie_id")
    String movieId;
    @ColumnInfo(name = "movie_poster")
    String moviePoster;
    @ColumnInfo(name = "movie_synopsis")
    String movieSynopsis;
    @ColumnInfo(name = "movie_user_rating")
    double movieUserRating;
    @ColumnInfo(name = "movie_release_date")
    String movieReleaseDate;
    @ColumnInfo(name = "movie_backdrop_path")
    String MovieBackdropPath;


    public Favourite(String movieTitle, String movieId, String moviePoster, String movieSynopsis, double movieUserRating, String movieReleaseDate, String MovieBackdropPath) {
        this.movieTitle = movieTitle;
        this.movieId = movieId;
        this.moviePoster = moviePoster;
        this.movieSynopsis = movieSynopsis;
        this.movieUserRating = movieUserRating;
        this.movieReleaseDate = movieReleaseDate;
        this.MovieBackdropPath=MovieBackdropPath;
    }

    public String getMovieBackdropPath() {
        return MovieBackdropPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public double getMovieUserRating() {
        return movieUserRating;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }
}
