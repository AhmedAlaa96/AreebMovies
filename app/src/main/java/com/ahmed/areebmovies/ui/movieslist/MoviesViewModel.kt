package com.ahmed.areebmovies.ui.movieslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.domain.usecases.movieslist.IMoviesListUseCase
import com.ahmed.areebmovies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val mIMoviesListUseCase: IMoviesListUseCase,
    handle: SavedStateHandle
) :
    BaseViewModel(handle, mIMoviesListUseCase) {


    private var moviesResponseStatus: Status<MoviesListResponse>? = null


    private val mPageModel = PageModel()

    private val _moviesResponseMutableSharedFlow = MutableSharedFlow<Status<MoviesListResponse>>()
    val moviesResponseSharedFlow = _moviesResponseMutableSharedFlow.asSharedFlow()

    internal fun getMatchesResponse() {
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(Dispatchers.Main + handler) {
            if (moviesResponseStatus != null && moviesResponseStatus?.isIdle() != true) {
                setMoviesResponseStatus(moviesResponseStatus!!)
            } else {
                callGetMatches(ProgressTypes.MAIN_PROGRESS)
            }
        }
    }

    private suspend fun callGetMatches(progressType: ProgressTypes) {
        onGetMoviesSubscribe(progressType)
        mIMoviesListUseCase.getMoviesList(pageModel = mPageModel)
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

    private suspend fun setMoviesResponseStatus(moviesResponseStatus: Status<MoviesListResponse>) {
        this.moviesResponseStatus = moviesResponseStatus
        _moviesResponseMutableSharedFlow.emit(moviesResponseStatus)
    }


}