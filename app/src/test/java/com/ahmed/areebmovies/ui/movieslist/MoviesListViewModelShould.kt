package com.ahmed.areebmovies.ui.movieslist

import androidx.lifecycle.SavedStateHandle
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.domain.usecases.movieslist.IMoviesListUseCase
import com.ahmed.areebmovies.domain.usecases.movieslist.MoviesListUseCase
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
class MoviesListViewModelShould : BaseViewModelUnitTest() {

    private val pageModel = PageModel()
    private val moviesListResponse: MoviesListResponse = mock()
    private val noNetworkError: String = "No Network"
    private val exception = RuntimeException("Something Went Wrong")
    private val moviesListUseCase: IMoviesListUseCase = mock()
    private val handle = mock<SavedStateHandle>()
    private val testDispatcher = Dispatchers.Unconfined

    @Test
    fun getMoviesListResponseFromUseCase() = runBlockingTest {
        val viewModel = MoviesViewModel(moviesListUseCase, testDispatcher, handle)
        viewModel.getMoviesListResponse()
        verify(moviesListUseCase, times(1)).getMoviesList(pageModel)
    }

    @Test
    fun emitMoviesListResponseFromUseCaseOnGetMoviesListCalled() = runBlockingTest {
        val viewModel = initSuccessViewModel()
        test(onEvent = { viewModel.getMoviesListResponse() },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
                TestCase.assertEquals(moviesListResponse.movies, response.data)
            })
    }

    @Test
    fun propagateGetMoviesListResponseErrorOnGetMoviesListCalled() = runBlockingTest {
        val viewModel = initFailureViewModel()
        test(onEvent = { viewModel.getMoviesListResponse() },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.ERROR, response.statusCode)
                TestCase.assertEquals(exception.message, response.error)
            })
    }
    @Test
    fun propagateNoNetworkErrorOnGetMoviesListCalled() = runBlockingTest {
        val viewModel = initNoNetworkViewModel()
        test(onEvent = { viewModel.getMoviesListResponse() },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.NO_NETWORK, response.statusCode)
                TestCase.assertEquals(noNetworkError, response.error)
            })
    }

    @Test
    fun emitMoviesListResponseFromUseCaseOnRefreshCalled() = runBlockingTest {
        val viewModel = initSuccessViewModel()
        test(onEvent = { viewModel.onRefresh() },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
                TestCase.assertEquals(moviesListResponse.movies, response.data)
            })
    }

    @Test
    fun emitMoviesListResponseFromUseCaseOnScrollCalled() = runBlockingTest {
        val viewModel = initSuccessViewModel()
        test(onEvent = { viewModel.onScroll() },
            onState = {
                val response =
                    viewModel.moviesResponseSharedFlow.first()
                TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
                TestCase.assertEquals(moviesListResponse.movies, response.data)
            })
    }


    private suspend fun initSuccessViewModel(): MoviesViewModel {
        whenever(moviesListUseCase.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.Success(moviesListResponse))
            },
        )

        return MoviesViewModel(
            moviesListUseCase,
            testDispatcher,
            handle
        )
    }

    private suspend fun initFailureViewModel(): MoviesViewModel {
        whenever(moviesListUseCase.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.Error(error = exception.message))
            },
        )
        return MoviesViewModel(
            moviesListUseCase,
            testDispatcher,
            handle
        )
    }

    private suspend fun initNoNetworkViewModel(): MoviesViewModel {
        whenever(moviesListUseCase.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.NoNetwork(error = noNetworkError))
            },
        )

        return MoviesViewModel(
            moviesListUseCase,
            testDispatcher,
            handle
        )
    }

}