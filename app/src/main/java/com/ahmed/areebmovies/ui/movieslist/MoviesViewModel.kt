package com.ahmed.areebmovies.ui.movieslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.domain.usecases.movieslist.IMoviesListUseCase
import com.ahmed.areebmovies.ui.base.BasePagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val mIMoviesListUseCase: IMoviesListUseCase,
    handle: SavedStateHandle
) : BasePagingViewModel(handle, mIMoviesListUseCase) {

    @Inject
    @Named("ViewModel")
    lateinit var mainDispatcher: CoroutineDispatcher

    private var moviesResponseStatus: Status<ArrayList<Movie>>? = null
    private var moviesList: ArrayList<Movie>? = null


    private val mPageModel = PageModel()

    private val _moviesResponseMutableSharedFlow = MutableSharedFlow<Status<ArrayList<Movie>>>()
    val moviesResponseSharedFlow = _moviesResponseMutableSharedFlow.asSharedFlow()

    internal fun getMoviesListResponse() {
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(mainDispatcher + handler) {
            if (moviesResponseStatus != null && moviesResponseStatus?.isIdle() != true) {
                setMoviesResponseStatus(moviesResponseStatus!!)
            } else {
                callGetMoviesList(ProgressTypes.MAIN_PROGRESS, false)
            }
        }
    }

    internal fun onScroll() {
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(mainDispatcher + handler) {
            callGetMoviesList(ProgressTypes.PAGING_PROGRESS, false)
        }

    }

    internal fun onRefresh() {
        mPageModel.reset()
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(mainDispatcher + handler) {
            callGetMoviesList(ProgressTypes.PULL_TO_REFRESH_PROGRESS, true)
        }

    }

    internal fun onRetryClicked() {
        mPageModel.reset()
        val handler = CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                setMoviesResponseStatus(Status.Error(error = exception.message))
            }
        }

        viewModelScope.launch(mainDispatcher + handler) {
            callGetMoviesList(ProgressTypes.MAIN_PROGRESS, true)
        }

    }

    private suspend fun callGetMoviesList(progressType: ProgressTypes, shouldClear: Boolean) {
        onGetMoviesSubscribe(progressType)
        mIMoviesListUseCase.getMoviesList(pageModel = mPageModel)
            .onStart {
                showProgress(true, progressType)
            }.onCompletion {
                showProgress(false, progressType)
            }.catch {
                setMoviesResponseStatus(Status.Error(error = it.message))
                showProgress(false, progressType)
            }
            .collect {
                mapMovieListResponse(it, shouldClear)
            }
    }

    private suspend fun mapMovieListResponse(
        moviesResponse: Status<MoviesListResponse>,
        shouldClear: Boolean
    ) {
        when (moviesResponse.statusCode) {
            StatusCode.SUCCESS -> {
                clearData(shouldClear)
                val feedbackList = moviesResponse.data?.movies!!
                val totalCount = moviesResponse.data.totalPages

                val currentList = moviesResponseStatus?.data ?: ArrayList()
                currentList.addAll(feedbackList)

                mPageModel.incrementPageNumber()

                if (totalCount != null && currentList.size >= totalCount)
                    shouldLoadMore = false

                setMoviesResponseStatus(Status.Success(currentList))
            }
            else -> {
                setMoviesResponseStatus(
                    Status.CopyStatus(
                        moviesResponse,
                        moviesResponse.data?.movies
                    )
                )
            }
        }
    }

    private suspend fun clearData(shouldClear: Boolean) {
        shouldClearObservable.emit(shouldClear)
        if (shouldClear) {
            moviesResponseStatus?.data?.clear()
        }
    }

    private fun onGetMoviesSubscribe(progressType: ProgressTypes) {
        showProgress(true, progressType)
        shouldShowError(false)
    }

    private suspend fun setMoviesResponseStatus(moviesResponseStatus: Status<ArrayList<Movie>>) {
        this.moviesResponseStatus = moviesResponseStatus
        _moviesResponseMutableSharedFlow.emit(moviesResponseStatus)
    }


}