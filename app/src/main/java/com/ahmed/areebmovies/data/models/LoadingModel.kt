package com.ahmed.areebmovies.data.models

import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

data class LoadingModel(
        val shouldShow: Boolean = false,
        val progressType: ProgressTypes = ProgressTypes.MAIN_PROGRESS,
        var loadingProgressView: ProgressBar? = null,
        var loadingFullProgressView: ConstraintLayout? = null,
        var pagingProgressView: ProgressBar? = null,
        var pullToRefreshProgressView: SwipeRefreshLayout? = null,
)
