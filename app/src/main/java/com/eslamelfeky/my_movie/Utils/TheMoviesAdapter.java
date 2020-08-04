package com.eslamelfeky.my_movie.Utils;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eslamelfeky.my_movie.Repository.Movie;
import com.eslamelfeky.my_movie.Repository.MoviesResult;
import com.eslamelfeky.my_movie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TheMoviesAdapter extends RecyclerView.Adapter<TheMoviesViewHolder> {

    MoviesResult moviesResult;
    List<Movie> movies=new ArrayList<>();
    int movieListCount=0;

    public void clear() {
        movieListCount=0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position,List<Movie> movies);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TheMoviesAdapter(MoviesResult moviesResult) {
        this.moviesResult = moviesResult;
        movies.addAll(moviesResult.getResults());
        movieListCount+=movies.size();
    }
    public void addMovies(List<Movie> movies){
       this.movies.addAll(movies);
        movieListCount+=movies.size();
    }

    @NonNull
    @Override
    public TheMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_row_layout,viewGroup,false);
        TheMoviesViewHolder viewHolder=new TheMoviesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TheMoviesViewHolder theMoviesViewHolder, int i) {
       final int postion=i;
     Movie  movie=movies.get(i);
        Uri uri=Uri.parse("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+movie.getPoster_path());

        Picasso.get().load(uri).error(R.drawable.ic_popular_now).placeholder(R.drawable.ic_popular_now)
                .into(theMoviesViewHolder.moviePoster);
        theMoviesViewHolder.moviePoster.setContentDescription(movie.getTitle());
        theMoviesViewHolder.moviePoster.setNextFocusForwardId(theMoviesViewHolder.moviePoster.getId());
        theMoviesViewHolder.moviePoster.setOnClickListener(v -> {
            if(mListener!=null){

                if(postion!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(postion,movies);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return movieListCount;
    }
}
