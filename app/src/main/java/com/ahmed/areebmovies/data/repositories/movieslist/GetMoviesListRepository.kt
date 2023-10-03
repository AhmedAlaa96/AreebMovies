package com.ahmed.areebmovies.data.repositories.movieslist

import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.shared_prefrences.IPreferencesDataSource
import com.ahmed.areebmovies.di.IoDispatcher
import com.ahmed.areebmovies.ui.base.BaseRepository
import com.ahmed.areebmovies.utils.connection_utils.IConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesListRepository @Inject constructor(
    private val connectionUtils: IConnectionUtils,
    private val mIRemoteDataSource: IRemoteDataSource,
    mILocalDataSource: ILocalDataSource,
    private val mIPreferencesDataSource: IPreferencesDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher = Dispatchers.IO
): BaseRepository(connectionUtils, mIRemoteDataSource, mIPreferencesDataSource, dispatcher),
    IGetMoviesListRepository {
    override fun getMoviesList(pageModel: PageModel): Flow<Status<MoviesListResponse>> {
        return safeApiCalls {
            mIRemoteDataSource.getMoviesList(pageModel)
        }
    }
}