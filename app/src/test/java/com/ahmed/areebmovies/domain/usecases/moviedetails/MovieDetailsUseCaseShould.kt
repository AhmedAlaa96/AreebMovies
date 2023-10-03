package com.ahmed.areebmovies.domain.usecases.moviedetails

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.data.repositories.moviedetails.GetMovieDetailsRepository
import com.ahmed.areebmovies.data.repositories.moviedetails.IGetMovieDetailsRepository
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
class MovieDetailsUseCaseShould : BaseUnitTest() {

    private val getMovieDetailsRepository: IGetMovieDetailsRepository = mock<GetMovieDetailsRepository>()
    private val movieDetailsResponse: MovieDetailsResponse = mock()
    private val movieId = 1
    private val noNetworkError: String = "No Network"
    private val exception = RuntimeException("Something Went Wrong")

    @Test
    fun getMovieDetailsResponseFromRepository() = runBlockingTest {
        val getMovieDetailsUseCase = MovieDetailsUseCase(getMovieDetailsRepository)
        getMovieDetailsUseCase.getMovieDetails(movieId)
        verify(getMovieDetailsRepository, times(1)).getMovieDetails(movieId)
    }


    @Test
    fun emitMovieDetailsResponseFromRepository() = runBlockingTest {
        val useCase = initSuccessUseCase()
        val response = useCase.getMovieDetails(movieId).first()
        TestCase.assertEquals(StatusCode.SUCCESS, response.statusCode)
        TestCase.assertEquals(movieDetailsResponse, response.data)
    }

    @Test
    fun propagateNoNetworkError() = runBlockingTest {
        val useCase = initNoNetworkUseCase()
        val response = useCase.getMovieDetails(movieId).first()
        TestCase.assertEquals(StatusCode.NO_NETWORK, response.statusCode)
        TestCase.assertEquals(noNetworkError, response.error)
    }

    @Test
    fun propagateGetMovieDetailsResponseError() = runBlockingTest {
        val useCase = initFailureUseCase()
        val response = useCase.getMovieDetails(movieId).first()
        TestCase.assertEquals(StatusCode.ERROR, response.statusCode)
        TestCase.assertEquals(exception.message, response.error)
    }


    private suspend fun initSuccessUseCase(): IMovieDetailsUseCase {
        whenever(getMovieDetailsRepository.getMovieDetails(movieId)).thenReturn(
            flow {
                emit(Status.Success(movieDetailsResponse))
            },
        )

        return MovieDetailsUseCase(
            getMovieDetailsRepository
        )
    }

    private suspend fun initFailureUseCase(): IMovieDetailsUseCase {
        whenever(getMovieDetailsRepository.getMovieDetails(movieId)).thenReturn(
            flow {
                emit(Status.Error(error = exception.message))
            },
        )

        return MovieDetailsUseCase(
            getMovieDetailsRepository
        )
    }

    private suspend fun initNoNetworkUseCase(): IMovieDetailsUseCase {
        whenever(getMovieDetailsRepository.getMovieDetails(movieId)).thenReturn(
            flow {
                emit(Status.NoNetwork(error = noNetworkError))
            },
        )

        return MovieDetailsUseCase(
            getMovieDetailsRepository
        )
    }
}
