package com.eslamelfeky.my_movie.ViewModel;


import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;


public class DetailFactoryViewModel implements ViewModelProvider.Factory {
    Application application ;
    int id;

    public DetailFactoryViewModel(@NonNull Application application,@NonNull int id) {
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new DetailViewModel(application,id);
    }
}
