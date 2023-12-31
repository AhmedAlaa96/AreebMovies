package com.ahmed.areebmovies.domain.usecases.moviedetails

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.ui.base.IBaseUseCase
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsUseCase: IBaseUseCase {
    fun getMovieDetails(movieId: Int?): Flow<Status<MovieDetailsResponse>>
}