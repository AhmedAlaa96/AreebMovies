package com.ahmed.areebmovies.ui.base

import android.view.View
import androidx.lifecycle.*
import com.ahmed.areebmovies.data.models.LoadingModel
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.view_state.BaseViewStateModel
import com.ahmed.areebmovies.utils.alternate
import com.ahmed.areebmovies.utils.view_state.ViewStateHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private var mSavedStateHandle: SavedStateHandle,
    private vararg var mUseCases: IBaseUseCase
) : ViewModel() {

    var loadingObservable = MutableSharedFlow<LoadingModel>()
    var errorViewObservable = MutableSharedFlow<Boolean>()
    var showToastObservable = MutableSharedFlow<String>()

    protected fun showProgress(
        shouldShow: Boolean,
        progressType: ProgressTypes = ProgressTypes.MAIN_PROGRESS
    ) {
        viewModelScope.launch {
            loadingObservable.emit(LoadingModel(shouldShow, progressType))
        }
    }

    protected fun shouldShowError(shouldShow: Boolean) {
        viewModelScope.launch {
            errorViewObservable.emit(shouldShow)
        }
    }

    protected fun showToastMessage(message: Any?, vararg args: Any?) {
        viewModelScope.launch {

            if (message is String)
                showToastObservable.emit(message)
            else
                showToastObservable.emit(message.toString().alternate())
        }
    }

    private fun <T> getStateLiveData(key: String, initialValue: T? = null): MutableLiveData<T?>? {
        if (!mSavedStateHandle.contains(key)) return null

        val liveData = mSavedStateHandle.getLiveData(key, initialValue)
        mSavedStateHandle.remove<T>(key)
        return liveData
    }

    /**
     * saves the views states using the viewModel SavedStateHandle.
     */
    fun saveStates(vararg views: View) {
        ViewStateHelper.saveViewState(mSavedStateHandle, *views)
    }


    fun <T> showErrorTitle(status: Status<T>) {
        val errorTitle = status.error
        showToastMessage(errorTitle)
    }

    fun restoreViewState(
        owner: LifecycleOwner,
        vararg views: View,
        onNoSavedState: ((tag: String) -> Unit)? = null
    ) {
        for (view in views) {
            val stateLiveData = getStateLiveData<Any>(view.tag as String)
            if (stateLiveData != null)
                stateLiveData.observe(owner,
                    androidx.lifecycle.Observer {
                        restoreViewState(view, it as BaseViewStateModel)
                    })
            else
                onNoSavedState?.invoke(view.tag as String)
        }
    }

    private fun restoreViewState(view: View, value: BaseViewStateModel) {
        ViewStateHelper.restoreView(view, value)
    }


}
