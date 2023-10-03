package com.ahmed.areebmovies.data.remote

import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse

interface IRemoteDataSource {

    suspend fun getMoviesList(pageModel: PageModel): MoviesListResponse
    suspend fun getMovieDetails(movieId: Int?): MovieDetailsResponse
}