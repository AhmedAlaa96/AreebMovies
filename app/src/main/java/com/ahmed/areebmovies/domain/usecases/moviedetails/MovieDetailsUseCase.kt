package com.ahmed.areebmovies.domain.usecases.moviedetails

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.repositories.moviedetails.IGetMovieDetailsRepository
import com.ahmed.areebmovies.ui.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val mMoviesDetailsRepository: IGetMovieDetailsRepository) :
    BaseUseCase(mMoviesDetailsRepository),
    IMovieDetailsUseCase {


    override fun getMovieDetails(movieId: Int?): Flow<Status<MovieDetailsResponse>> {
        return mMoviesDetailsRepository.getMovieDetails(movieId)
    }
}