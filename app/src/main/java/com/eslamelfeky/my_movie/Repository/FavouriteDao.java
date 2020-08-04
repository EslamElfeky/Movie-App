package com.eslamelfeky.my_movie.Repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FavouriteDao {

    @Insert
    void insert(Favourite favourite);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Favourite favourite);
    @Query("delete from favourite_table where movie_id=:movie_id_detail")
    void delete(String movie_id_detail);
    @Query("Select * from favourite_table")
    LiveData<List<Favourite>> getAllFavourite();
    @Query("Select movie_id from favourite_table where movie_id=:movie_id_detail")
     String getFavouriteId(String movie_id_detail);



}
