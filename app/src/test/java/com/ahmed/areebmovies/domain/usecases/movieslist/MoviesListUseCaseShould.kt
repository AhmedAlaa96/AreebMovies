package com.ahmed.areebmovies.domain.usecases.movieslist

import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.repositories.movieslist.GetMoviesListRepository
import com.ahmed.areebmovies.data.repositories.movieslist.IGetMoviesListRepository
import com.ahmed.areebmovies.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class MoviesListUseCaseShould : BaseUnitTest() {

    private val getMoviesListRepository: IGetMoviesListRepository = mock<GetMoviesListRepository>()
    private val moviesListResponse: MoviesListResponse = mock()
    private val pageModel = PageModel()
    private val noNetworkError: String = "No Network"
    private val exception = RuntimeException("Something Went Wrong")

    @Test
    fun getMoviesListResponseFromRepository() = runBlockingTest {
        val getMoviesUseCase = MoviesListUseCase(getMoviesListRepository)
        getMoviesUseCase.getMoviesList(pageModel)
        verify(getMoviesListRepository, times(1)).getMoviesList(pageModel)
    }


    @Test
    fun emitMoviesListResponseFromRepository() = runBlockingTest {
        val useCase = initSuccessUseCase()
        val response = useCase.getMoviesList(pageModel).first()
        TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
        TestCase.assertEquals(moviesListResponse, response.data)
    }

    @Test
    fun propagateNoNetworkError() = runBlockingTest {
        val useCase = initNoNetworkUseCase()
        val response = useCase.getMoviesList(pageModel).first()
        TestCase.assertEquals(StatusCode.NO_NETWORK, response.statusCode)
        TestCase.assertEquals(noNetworkError, response.error)
    }

    @Test
    fun propagateGetMoviesListResponseError() = runBlockingTest {
        val useCase = initFailureUseCase()
        val response = useCase.getMoviesList(pageModel).first()
        TestCase.assertEquals(StatusCode.ERROR, response.statusCode)
        TestCase.assertEquals(exception.message, response.error)
    }


    private suspend fun initSuccessUseCase(): IMoviesListUseCase {
        whenever(getMoviesListRepository.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.Success(moviesListResponse))
            },
        )

        return MoviesListUseCase(
            getMoviesListRepository
        )
    }

    private suspend fun initSuccessButNoDataUseCase(): IMoviesListUseCase {
        whenever(getMoviesListRepository.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.Success(moviesListResponse.copy(movies = arrayListOf())))
            },
        )

        return MoviesListUseCase(
            getMoviesListRepository
        )
    }

    private suspend fun initFailureUseCase(): IMoviesListUseCase {
        whenever(getMoviesListRepository.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.Error(error = exception.message))
            },
        )

        return MoviesListUseCase(
            getMoviesListRepository
        )
    }

    private suspend fun initNoNetworkUseCase(): IMoviesListUseCase {
        whenever(getMoviesListRepository.getMoviesList(pageModel)).thenReturn(
            flow {
                emit(Status.NoNetwork(error = noNetworkError))
            },
        )

        return MoviesListUseCase(
            getMoviesListRepository
        )
    }

}