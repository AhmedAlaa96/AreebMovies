package com.ahmed.areebmovies.data.repositories.moviedetails

import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.shared_prefrences.IPreferencesDataSource
import com.ahmed.areebmovies.ui.base.BaseRepository
import com.ahmed.areebmovies.ui.base.IBaseRepository
import com.ahmed.areebmovies.utils.connection_utils.IConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsRepository @Inject constructor(
    private val connectionUtils: IConnectionUtils,
    private val mIRemoteDataSource: IRemoteDataSource,
    mILocalDataSource: ILocalDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource, dispatcher),
    IGetMovieDetailsRepository {
    override fun getMovieDetails(movieId: Int?): Flow<Status<MovieDetailsResponse>> {
        return safeApiCalls {
            mIRemoteDataSource.getMovieDetails(movieId)
        }
    }
}