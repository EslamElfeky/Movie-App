package com.eslamelfeky.my_movie.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslamelfeky.my_movie.R;


public class TheTrailersViewHolder extends RecyclerView.ViewHolder {
  ImageView imageView;
  TextView textView;
    public TheTrailersViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView= itemView.findViewById(R.id.iv_trailer_img);
        textView=itemView.findViewById(R.id.tv_trailer_title);

    }
}
