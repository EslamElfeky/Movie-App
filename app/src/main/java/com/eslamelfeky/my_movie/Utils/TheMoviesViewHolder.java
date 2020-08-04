package com.eslamelfeky.my_movie.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eslamelfeky.my_movie.R;

public class TheMoviesViewHolder extends RecyclerView.ViewHolder {

    ImageView moviePoster;

    public TheMoviesViewHolder(@NonNull View itemView ) {
        super(itemView);
       moviePoster=(ImageView) itemView.findViewById(R.id.iv_poster);

    }
}
