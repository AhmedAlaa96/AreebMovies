package com.ahmed.areebmovies.data.repositories.moviedetails

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.ui.base.IBaseRepository
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsRepository : IBaseRepository {
    fun getMovieDetails(movieId: Int?): Flow<Status<MovieDetailsResponse>>
}