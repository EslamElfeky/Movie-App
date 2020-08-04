package com.eslamelfeky.my_movie.Utils;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.eslamelfeky.my_movie.R;
import com.eslamelfeky.my_movie.Repository.Movie;
import com.eslamelfeky.my_movie.Repository.MoviesResult;
import com.eslamelfeky.my_movie.Repository.Repository;
import com.eslamelfeky.my_movie.View.MainActivity;
import com.eslamelfeky.my_movie.ViewModel.MainViewModel;
import com.google.gson.Gson;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class myWidget extends AppWidgetProvider {
   SharedPreferences sharedPreferences;
   Gson gson=new Gson();
   static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("MyWidgetItemFactory",""+1000);
        views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        sharedPreferences=context.getSharedPreferences("pre_widget",Context.MODE_PRIVATE);
        String movie=sharedPreferences.getString("moviesResult",null);
        MoviesResult moviesResult=gson.fromJson(movie,MoviesResult.class);
        String movieList="Favorite List\n\n";

        for(Movie movieObj:moviesResult.getResults()){
            movieList+=movieObj.getTitle()+"\n";
        }
        for (int appWidgetId : appWidgetIds) {
            views.setTextViewText(R.id.tv_widget,movieList);


            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

