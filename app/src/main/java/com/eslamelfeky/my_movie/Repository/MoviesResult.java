package com.eslamelfeky.my_movie.Repository;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MoviesResult implements Parcelable {


    int page;
    int total_results;
    int total_pages;
    List<Movie>results;
    date dates;

    public MoviesResult(List<Movie> results) {
        this.results = results;
    }

    protected MoviesResult(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<MoviesResult> CREATOR = new Creator<MoviesResult>() {
        @Override
        public MoviesResult createFromParcel(Parcel in) {
            return new MoviesResult(in);
        }

        @Override
        public MoviesResult[] newArray(int size) {
            return new MoviesResult[size];
        }
    };

    public date getDates() {
        return dates;
    }

    public void setDates(date dates) {
        this.dates = dates;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(total_results);
        dest.writeInt(total_pages);
        dest.writeTypedList(results);
    }
}
