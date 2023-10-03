package com.ahmed.areebmovies.data.remote

import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.retrofit.ApiInterface

class RemoteDataSource(private val mRetrofitInterface: ApiInterface) : IRemoteDataSource {
    override suspend fun getMoviesList(pageModel: PageModel): MoviesListResponse {
        return mRetrofitInterface.getMoviesList(pageModel.page)
    }

    override suspend fun getMovieDetails(movieId: Int?): MovieDetailsResponse {
        return mRetrofitInterface.getMovieDetails(movieId)
    }

}