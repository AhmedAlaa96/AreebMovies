package com.ahmed.areebmovies.data.remote

import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel

interface IRemoteDataSource {

    suspend fun getMoviesList(pageModel: PageModel): MoviesListResponse
}