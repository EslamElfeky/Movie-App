package com.eslamelfeky.my_movie.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eslamelfeky.my_movie.R;


public class TheReviewsViewHolder extends RecyclerView.ViewHolder {
    TextView userReview,contentReview;
    public TheReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        userReview=itemView.findViewById(R.id.review_user_name_tv);
        contentReview=itemView.findViewById(R.id.review_user_content_tv);

    }
}
