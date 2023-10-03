package com.ahmed.areebmovies.data.local

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.data.room.AppDatabase
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appDatabase: AppDatabase) : ILocalDataSource {
    override suspend fun getAllMovies(): Status<ArrayList<Movie>> {
        val moviesList = appDatabase.moviesDao().getAllMovies()?.toCollection(arrayListOf())
        return if (!moviesList.isNullOrEmpty()) {
            Status.OfflineData(data = moviesList, error = null)
        } else
            Status.NoData(error = "No Data")
    }

    override suspend fun insertMovies(movies: ArrayList<Movie>) {
        deleteAllMovies()
        appDatabase.moviesDao().insertAll(movies)
    }

    private suspend fun deleteAllMovies() {
        appDatabase.moviesDao().clearMoviesList()
    }
}