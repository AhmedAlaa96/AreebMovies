package com.ahmed.areebmovies.data.models.dao

import androidx.room.*
import com.ahmed.areebmovies.data.models.dto.Movie

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: ArrayList<Movie>)


    @Query("DELETE FROM movies")
    suspend fun clearMoviesList()
}