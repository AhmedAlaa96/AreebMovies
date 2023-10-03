package com.ahmed.areebmovies.ui.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.utils.alternate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BasePagingViewModel(
    handle: SavedStateHandle,
    vararg mUseCases: IBaseUseCase
) : BaseViewModel(handle, *mUseCases) {

    protected var shouldLoadMore: Boolean = true
    protected var isLoading: Boolean = false
    private var noNetworkToastCount = 0
    var shouldClearObservable = MutableSharedFlow<Boolean>()
    public override fun onCleared() {
        isLoading = false
        showProgress(false, ProgressTypes.PAGING_PROGRESS)
        super.onCleared()
    }

    private fun incrementNoNetworkCount() {
        noNetworkToastCount++
    }

    fun resetNoNetworkCount() {
        noNetworkToastCount = 0
    }

    private fun showNoNetworkErrorForPaging(error: String?) {
        incrementNoNetworkCount()
        showToastMessage(error.alternate())
    }

    private fun <T> handleNoNetworkErrorForPaging(status: Status<T>) {
        if (status.isNoNetwork()) {
            if (noNetworkToastCount == 0) {
                showNoNetworkErrorForPaging(status.error)
            }
        } else {
            showToastMessage(status.error)
        }
    }

    fun <T> handleStatusErrorWithExistingData(progressType: ProgressTypes, status: Status<T>) {
        viewModelScope.launch {
            if (progressType == ProgressTypes.PAGING_PROGRESS) {
                handleNoNetworkErrorForPaging(status)
            } else {
                showToastMessage(status.error)
            }
        }

    }
}
