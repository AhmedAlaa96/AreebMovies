package com.ahmed.areebmovies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmed.areebmovies.data.models.dao.MoviesDao
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.data.roommodels.GenresConverter

@TypeConverters(GenresConverter::class)
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}