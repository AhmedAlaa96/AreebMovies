package com.ahmed.areebmovies.data.repositories.moviedetails

import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.local.LocalDataSource
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.remote.RemoteDataSource
import com.ahmed.areebmovies.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.areebmovies.data.sharedprefrences.PreferencesDataSource
import com.ahmed.areebmovies.utils.BaseUnitTest
import com.ahmed.areebmovies.utils.connection_utils.ConnectionUtils
import com.ahmed.areebmovies.utils.connection_utils.IConnectionUtils
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieDetailsRepositoryShould: BaseUnitTest() {

    private val remoteDataSource: IRemoteDataSource = mock<RemoteDataSource>()
    private val connectionUtils: IConnectionUtils = mock<ConnectionUtils>()
    private val localDataSource: ILocalDataSource = mock<LocalDataSource>()
    private val sharedPreferences: IPreferencesDataSource = mock<PreferencesDataSource>()
    private val movieId = 1
    private val movieDetailsResponse: MovieDetailsResponse = mock()
    private val noNetworkError: String = "No Network"
    private val exception = RuntimeException("Something Went Wrong")

    @Test
    fun emitMovieDetailsResponseFromRemoteDataSource() = runBlockingTest {
        val repository = initSuccessRepository()
        val response = repository.getMovieDetails(movieId).first()
        TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
        TestCase.assertEquals(movieDetailsResponse, response.data)
    }

    @Test
    fun propagateNoNetworkError() = runBlockingTest {
        val repository = initNoNetworkRepository()
        val response = repository.getMovieDetails(movieId).first()
        TestCase.assertEquals(StatusCode.NO_NETWORK, response.statusCode)
        TestCase.assertEquals(noNetworkError, response.error)
    }
    @Test
    fun propagateGetMovieDetailsResponseError() = runBlockingTest {
        val repository = initFailureRepository()
        val response = repository.getMovieDetails(movieId).first()
        TestCase.assertEquals(StatusCode.ERROR, response.statusCode)
        TestCase.assertEquals(exception.message, response.error)
    }


    private suspend fun initSuccessRepository(): IGetMovieDetailsRepository {

        whenever(connectionUtils.isConnected).thenReturn(true)

        whenever(remoteDataSource.getMovieDetails(movieId)).thenReturn(
            movieDetailsResponse
        )
        return GetMovieDetailsRepository(
            connectionUtils,
            remoteDataSource,
            localDataSource,
            sharedPreferences,
            Dispatchers.Unconfined
        )
    }

    private suspend fun initFailureRepository(): IGetMovieDetailsRepository {

        whenever(connectionUtils.isConnected).thenReturn(true)

        whenever(remoteDataSource.getMovieDetails(movieId)).thenThrow(
            exception
        )
        return GetMovieDetailsRepository(
            connectionUtils,
            remoteDataSource,
            localDataSource,
            sharedPreferences,
            Dispatchers.Unconfined
        )
    }

    private fun initNoNetworkRepository(): IGetMovieDetailsRepository {

        whenever(connectionUtils.isConnected).thenReturn(false)

        return GetMovieDetailsRepository(
            connectionUtils,
            remoteDataSource,
            localDataSource,
            sharedPreferences,
            Dispatchers.Unconfined
        )
    }

}