package com.ahmed.areebmovies.ui.moviedetails

import androidx.lifecycle.SavedStateHandle
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.domain.usecases.moviedetails.IMovieDetailsUseCase
import com.ahmed.areebmovies.utils.BaseViewModelUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Test

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class MoviesDetailsViewModelShould : BaseViewModelUnitTest() {

    private val movieId = 1
    private val moviesDetailsResponse: MovieDetailsResponse = mock()
    private val noNetworkError: String = "No Network"
    private val exception = RuntimeException("Something Went Wrong")
    private val moviesDetailsUseCase: IMovieDetailsUseCase = mock()
    private val handle = mock<SavedStateHandle>()
    private val testDispatcher = Dispatchers.Unconfined

    @Test
    fun getMoviesListResponseFromUseCase() = runBlockingTest {
        val viewModel = MovieDetailsViewModel(moviesDetailsUseCase, testDispatcher, handle)
        viewModel.getMovieDetails(movieId)
        verify(moviesDetailsUseCase, times(1)).getMovieDetails(movieId)
    }

    @Test
    fun emitMoviesDetailsResponseFromUseCase() = runBlockingTest {
        val viewModel = initSuccessViewModel()
        test(onEvent = { viewModel.getMovieDetails(movieId) },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
                TestCase.assertEquals(moviesDetailsResponse, response.data)
            })
    }

    @Test
    fun propagateGetMovieDetailsResponseWithError() = runBlockingTest {
        val viewModel = initFailureViewModel()
        test(onEvent = { viewModel.getMovieDetails(movieId) },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.ERROR, response.statusCode)
                TestCase.assertEquals(exception.message, response.error)
            })
    }
    @Test
    fun propagateNoNetworkErrorOnGetMovieDetails() = runBlockingTest {
        val viewModel = initNoNetworkViewModel()
        test(onEvent = { viewModel.getMovieDetails(movieId) },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.NO_NETWORK, response.statusCode)
                TestCase.assertEquals(noNetworkError, response.error)
            })
    }

    private suspend fun initSuccessViewModel(): MovieDetailsViewModel {
        whenever(moviesDetailsUseCase.getMovieDetails(movieId)).thenReturn(
            flow {
                emit(Status.Success(moviesDetailsResponse))
            },
        )

        return MovieDetailsViewModel(
            moviesDetailsUseCase,
            testDispatcher,
            handle
        )
    }

    private suspend fun initFailureViewModel(): MovieDetailsViewModel {
        whenever(moviesDetailsUseCase.getMovieDetails(movieId)).thenReturn(
            flow {
                emit(Status.Error(error = exception.message))
            },
        )
        return MovieDetailsViewModel(
            moviesDetailsUseCase,
            testDispatcher,
            handle
        )
    }

    private suspend fun initNoNetworkViewModel(): MovieDetailsViewModel {
        whenever(moviesDetailsUseCase.getMovieDetails(movieId)).thenReturn(
            flow {
                emit(Status.NoNetwork(error = noNetworkError))
            },
        )

        return MovieDetailsViewModel(
            moviesDetailsUseCase,
            testDispatcher,
            handle
        )
    }

}