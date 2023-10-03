package com.ahmed.areebmovies.retrofit

import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET(Constants.URL.GET_MOVIES)
    suspend fun getMoviesList(@Query(Constants.QueryParams.PAGE) page: Int): MoviesListResponse

    @GET(Constants.URL.GET_MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("movieId") movieId: Int?): MovieDetailsResponse

}
