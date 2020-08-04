package com.eslamelfeky.my_movie.View;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eslamelfeky.my_movie.*;
import com.eslamelfeky.my_movie.Repository.Favourite;
import com.eslamelfeky.my_movie.Repository.Movie;
import com.eslamelfeky.my_movie.Repository.ReviewsResult;
import com.eslamelfeky.my_movie.Repository.TrailersResult;
import com.eslamelfeky.my_movie.Utils.TheReviewsAdapter;
import com.eslamelfeky.my_movie.Utils.TheTrailersAdaptar;
import com.eslamelfeky.my_movie.ViewModel.DetailFactoryViewModel;
import com.eslamelfeky.my_movie.ViewModel.DetailViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class DetailActivity extends AppCompatActivity {
    Movie movieData;
    @BindView(R.id.app_bar_image)
    ImageView imageAppBar;
    @BindView(R.id.iv_Poster_detail)
    ImageView imagePoster;
   @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout titleAppBar;
    @BindView(R.id.lv_trailers)
    RecyclerView trailersList;
    @BindView(R.id.lv_reviews)
    RecyclerView reviewsList;
    @BindView(R.id.bt_favourite)
    Button btButton;
   @BindView(R.id.adView)
   AdView mAdView;
    TheTrailersAdaptar trailersAdapter;
    TheReviewsAdapter reviewsAdapter;
    DetailViewModel detailviewModel;
    boolean  isEmpty;
    int buttomBarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ButterKnife.bind(this);
        Intent intent=getIntent();
        movieData=intent.getParcelableExtra("MovieObject");
        buttomBarId=intent.getIntExtra("ButtomBarId",R.id.action_favorite);
        detailviewModel=ViewModelProviders.of(this,new DetailFactoryViewModel(this.getApplication(),movieData.getId())).get(DetailViewModel.class);
        initView();
         detailviewModel.observeTrailers().observe(this, trailersResult -> initTrailerList(trailersResult));
        detailviewModel.observeReviews().observe(this, reviewsResult -> initReviewList(reviewsResult));


    }



    private void initView() {

        checkMovieIsExist(String.valueOf(movieData.getId()));
        String BaseUrl="https://image.tmdb.org/t/p/w600_and_h900_bestv2";
        Uri url=Uri.parse(BaseUrl+movieData.getBackdrop_path());
        Picasso.get().load(url).fit().into(imageAppBar);
        url=Uri.parse(BaseUrl+movieData.getPoster_path());
        Picasso.get().load(url).into(imagePoster);
        imagePoster.setContentDescription(movieData.getTitle());
        tvRate.setText("Vote\n"+String.valueOf(movieData.getVote_average()));
        tvDate.setText("Release date\n"+movieData.getRelease_date());
        tvDescription.setText("Overview:\n"+movieData.getOverview());
        titleAppBar.setTitle(movieData.getTitle());

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }


    private void initTrailerList(final TrailersResult trailersResult) {

        trailersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));


        trailersAdapter=new TheTrailersAdaptar(trailersResult);
        trailersList.setAdapter(trailersAdapter);


        trailersAdapter.setOnItemClickListener(position -> {
            Intent intent=new Intent(DetailActivity.this,TrailerActivity.class);
            intent.putExtra("TrailerObject",  trailersResult.getResults().get(position));
            startActivity(intent);
        });


    }
    private void initReviewList(final ReviewsResult reviewsResult) {

        reviewsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        reviewsAdapter=new TheReviewsAdapter(reviewsResult);
        reviewsList.setAdapter(reviewsAdapter);


    }


    public void btFavourite_click(View view) {


        if (isEmpty==true){
            Favourite favourite=new Favourite(movieData.getTitle(),String.valueOf(movieData.getId()),
                    movieData.getPoster_path(),movieData.getOverview(),movieData.getVote_average(),movieData.getRelease_date(),movieData.getBackdrop_path());
            detailviewModel.insert(favourite).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {
                    changeFavouriteState(false);
                    isEmpty=false;
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }else{

            detailviewModel.delete(String.valueOf(movieData.getId())).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {
                    changeFavouriteState(true);
                    isEmpty=true;
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    void changeFavouriteState(boolean isEmpty){
        Drawable drawable=this.getApplicationContext().getResources().getDrawable( R.drawable.ic_favorite_black_24dp );
        if (isEmpty==false){
            drawable.setColorFilter(new PorterDuffColorFilter(0xffff0d00,PorterDuff.Mode.SRC_IN));
            btButton.setBackground(drawable);

        }else{
            drawable.setColorFilter(new PorterDuffColorFilter(0xffffffff,PorterDuff.Mode.SRC_IN));
            btButton.setBackground(drawable);

        }
    }


    public void checkMovieIsExist(String movieId){

        detailviewModel.getFavouriteById(movieId).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Log.d("a7a",s);
                if(s.isEmpty()||s==null){
                    changeFavouriteState(true);
                    isEmpty=true;

                }
                else {
                    changeFavouriteState(false);
                    isEmpty=false;
                }
            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof NullPointerException){
                    changeFavouriteState(true);
                    isEmpty=true;
                }else{Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();}


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(buttomBarId==R.id.action_favorite){
            detailviewModel.getAllFavourite();
        }
    }
}
