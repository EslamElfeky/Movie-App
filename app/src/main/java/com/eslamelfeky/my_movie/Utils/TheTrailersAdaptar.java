package com.eslamelfeky.my_movie.Utils;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eslamelfeky.my_movie.R;
import com.eslamelfeky.my_movie.Repository.Trailer;
import com.eslamelfeky.my_movie.Repository.TrailersResult;
import com.squareup.picasso.Picasso;

public class TheTrailersAdaptar extends RecyclerView.Adapter<TheTrailersViewHolder>  {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    TrailersResult trailersResult;
    private OnItemClickListener mListener;

    public TheTrailersAdaptar(TrailersResult trailersResult ) {
        this.trailersResult = trailersResult;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public TheTrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_row_layout,viewGroup,false);
        TheTrailersViewHolder viewHolder=new TheTrailersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TheTrailersViewHolder theTrailersViewHolder, int i) {
       final int postion=i;
        Trailer trailer=trailersResult.getResults().get(postion);
        Uri uri=Uri.parse("http://i3.ytimg.com/vi/"+trailer.getKey()+"/maxresdefault.jpg");
        Picasso.get().load(uri).fit().into(theTrailersViewHolder.imageView);
        theTrailersViewHolder.textView.setText(trailer.getName());
        theTrailersViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){

                    if(postion!=RecyclerView.NO_POSITION){
                        mListener.onItemClick(postion);
                    }
                }
            }

        });
        /*theTrailersViewHolder.trailerView.setWebViewClient(new WebViewClient());
        theTrailersViewHolder.trailerView.getSettings().setJavaScriptEnabled(true);
        theTrailersViewHolder.trailerView.loadUrl("https://www.youtube.com/embed/"+trailersResult.getResults().get(i).getKey());*/

    }

    @Override
    public int getItemCount() {
        return trailersResult.getResults().size();
    }
}
