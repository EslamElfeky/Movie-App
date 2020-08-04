package com.eslamelfeky.my_movie.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eslamelfeky.my_movie.R;
import com.eslamelfeky.my_movie.Repository.Review;
import com.eslamelfeky.my_movie.Repository.ReviewsResult;

public class TheReviewsAdapter extends RecyclerView.Adapter<TheReviewsViewHolder> {

    ReviewsResult reviewsResult;

    public TheReviewsAdapter(ReviewsResult reviewsResult) {
        this.reviewsResult = reviewsResult;
    }

    @NonNull
    @Override
    public TheReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_row_layout,viewGroup,false);
        TheReviewsViewHolder theReviewsViewHolder=new TheReviewsViewHolder(view);
        return theReviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TheReviewsViewHolder theReviewsViewHolder, int i) {
        Review review=reviewsResult.getResults().get(i);

        theReviewsViewHolder.userReview.setText(review.getAuthor());
        theReviewsViewHolder.contentReview.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewsResult.getResults().size();
    }
}
