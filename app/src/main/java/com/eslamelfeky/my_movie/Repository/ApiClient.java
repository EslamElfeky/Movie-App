package com.eslamelfeky.my_movie.Repository;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  static   Retrofit retrofit=null;
  static String BaseUrl="https://api.themoviedb.org/3/";

    public  static Retrofit getClient(){
        if (retrofit==null){
            retrofit= new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();


            return retrofit;
        }
        else {return retrofit;}


    }




}
