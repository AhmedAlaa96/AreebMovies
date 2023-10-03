package com.ahmed.areebmovies.ui.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.domain.usecases.moviedetails.IMovieDetailsUseCase
import com.ahmed.areebmovies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val mIMovieDetailsUseCase: IMovieDetailsUseCase,
    handle: SavedStateHandle
) :
    BaseViewModel(handle, mIMovieDetailsUseCase) {


    private var moviesResponseStatus: Status<MovieDetailsResponse>? = null


    private val _moviesResponseMutableSharedFlow = MutableSharedFlow<Status<MovieDetailsResponse>>()
    val moviesResponseSharedFlow = _moviesResponseMutableSharedFlow.asSharedFlow()

    internal fun getMovieDetails(movieId: Int?) {
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(Dispatchers.Main + handler) {
            if (moviesResponseStatus != null && moviesResponseStatus?.isIdle() != true) {
                setMoviesResponseStatus(moviesResponseStatus!!)
            } else {
                callGetMovieDetails(ProgressTypes.MAIN_PROGRESS, movieId)
            }
        }
    }

    private suspend fun callGetMovieDetails(progressType: ProgressTypes, movieId: Int?) {
        onGetMoviesSubscribe(progressType)
        mIMovieDetailsUseCase.getMovieDetails(movieId = movieId)
            .onStart {
                showProgress(true, progressType)
            }.onCompletion {
                showProgress(false)
            }.collect {
                setMoviesResponseStatus(it)
            }
    }

    private fun onGetMoviesSubscribe(progressType: ProgressTypes) {
        showProgress(true, progressType)
        shouldShowError(false)
    }

    private suspend fun setMoviesResponseStatus(moviesResponseStatus: Status<MovieDetailsResponse>) {
        this.moviesResponseStatus = moviesResponseStatus
        _moviesResponseMutableSharedFlow.emit(moviesResponseStatus)
    }


}