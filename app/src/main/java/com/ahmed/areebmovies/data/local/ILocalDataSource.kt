package com.ahmed.areebmovies.data.local

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.Movie

interface ILocalDataSource {
    suspend fun getAllMovies(): Status<ArrayList<Movie>>
    suspend fun insertMovies(movies: ArrayList<Movie>)

}