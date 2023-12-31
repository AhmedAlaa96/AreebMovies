package com.ahmed.areebmovies.domain.usecases.movieslist

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.data.repositories.movieslist.IGetMoviesListRepository
import com.ahmed.areebmovies.ui.base.BaseUseCase
import com.ahmed.areebmovies.utils.DateTimeHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class MoviesListUseCase @Inject constructor(private val mMoviesListRepository: IGetMoviesListRepository) :
    BaseUseCase(mMoviesListRepository),
    IMoviesListUseCase {

    override fun getMoviesList(pageModel: PageModel): Flow<Status<MoviesListResponse>> {
        return mMoviesListRepository.getMoviesList(pageModel)
            .mapLatest(::mapMoviesListStatus)
    }

    private fun mapMoviesListStatus(moviesListResponse: Status<MoviesListResponse>): Status<MoviesListResponse> {
        return when (validateResponse(moviesListResponse)) {
            StatusCode.SUCCESS -> {
                if (moviesListResponse.data?.movies.isNullOrEmpty())
                   Status.NoData(error = "No Data")
                else {
                   moviesListResponse
                }
            }
            else -> {
                moviesListResponse
            }
        }
    }

   override fun getLocalMovies(): Flow<Status<MoviesListResponse>> {
        return mMoviesListRepository.getLocalMoviesList()
   }

    override fun insertMoviesList(movies: ArrayList<Movie>): Flow<Unit> {
        return mMoviesListRepository.insertMoviesList(movies)
    }
}