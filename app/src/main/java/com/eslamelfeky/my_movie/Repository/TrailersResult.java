package com.eslamelfeky.my_movie.Repository;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TrailersResult implements Parcelable {
    int id;
    List<Trailer> results;


    protected TrailersResult(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<TrailersResult> CREATOR = new Creator<TrailersResult>() {
        @Override
        public TrailersResult createFromParcel(Parcel in) {
            return new TrailersResult(in);
        }

        @Override
        public TrailersResult[] newArray(int size) {
            return new TrailersResult[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }
}
