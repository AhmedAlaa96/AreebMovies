package com.ahmed.areebmovies.data.repositories.movieslist

import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.areebmovies.di.IoDispatcher
import com.ahmed.areebmovies.ui.base.BaseRepository
import com.ahmed.areebmovies.utils.connection_utils.IConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMoviesListRepository @Inject constructor(
    private val connectionUtils: IConnectionUtils,
    private val mIRemoteDataSource: IRemoteDataSource,
    private val mILocalDataSource: ILocalDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource, dispatcher),
    IGetMoviesListRepository {
    override fun getMoviesList(pageModel: PageModel): Flow<Status<MoviesListResponse>> {
        return safeApiCalls {
            mIRemoteDataSource.getMoviesList(pageModel)
        }
    }

    override fun getLocalMoviesList(): Flow<Status<MoviesListResponse>> {
        return flow {
            val moviesStatus = mILocalDataSource.getAllMovies()
            if (moviesStatus is Status.OfflineData) {
                emit(
                    Status.OfflineData(
                        MoviesListResponse(movies = moviesStatus.data),
                        error = null
                    )
                )
            } else {
                emit(Status.NoData(error = "No Data"))
            }
        }.flowOn((dispatcher))
    }

    override fun insertMoviesList(movies: ArrayList<Movie>): Flow<Unit> {
        return flow { emit(mILocalDataSource.insertMovies(movies)) }.flowOn((dispatcher))
    }
}