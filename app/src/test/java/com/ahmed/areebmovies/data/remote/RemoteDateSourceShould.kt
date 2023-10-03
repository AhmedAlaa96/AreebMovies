package com.ahmed.areebmovies.data.remote

import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.retrofit.ApiInterface
import com.ahmed.areebmovies.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDateSourceShould : BaseUnitTest() {

    private val apiInterface: ApiInterface = mock()
    private val moviesListResponse: MoviesListResponse = mock()
    private val movieDetailsResponse: MovieDetailsResponse = mock()
    private val pageModel = PageModel()
    private val movieId: Int = 1

    @Test
    fun getMoviesListResponseFromAPI() = runBlockingTest {
        val remoteDataSource = RemoteDataSource(apiInterface)
        remoteDataSource.getMoviesList(pageModel)
        verify(apiInterface, times(1)).getMoviesList(pageModel.page)
    }

    @Test
    fun emitMoviesListResponseFromAPIService() = runBlockingTest {
        val remoteDataSource = initSuccessRemoteDataSource()
        assertEquals(moviesListResponse, remoteDataSource.getMoviesList(pageModel))
    }

    @Test
    fun getMoviesDetailsResponseFromAPI() = runBlockingTest {
        val remoteDataSource = RemoteDataSource(apiInterface)
        remoteDataSource.getMovieDetails(movieId)
        verify(apiInterface, times(1)).getMovieDetails(movieId)
    }

    @Test
    fun emitMoviesDetailsResponseFromAPIService() = runBlockingTest {
        val remoteDataSource = initSuccessRemoteDataSource()
        assertEquals(movieDetailsResponse, remoteDataSource.getMovieDetails(movieId))
    }

    private fun initSuccessRemoteDataSource(): IRemoteDataSource {
        runBlocking {
            whenever(apiInterface.getMoviesList(pageModel.page)).thenReturn(moviesListResponse)
            whenever(apiInterface.getMovieDetails(movieId)).thenReturn(movieDetailsResponse)
        }
        return RemoteDataSource(apiInterface)
    }

}