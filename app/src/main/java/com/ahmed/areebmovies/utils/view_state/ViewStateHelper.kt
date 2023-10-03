package com.ahmed.areebmovies.utils.view_state

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ahmed.areebmovies.data.models.view_state.BaseViewStateModel
import com.ahmed.areebmovies.data.models.view_state.ScrollViewState
import com.ahmed.areebmovies.data.models.view_state.TextViewState
import com.ahmed.areebmovies.data.models.view_state.ViewPagerState

object ViewStateHelper {

    /**
     * Sets the view tag to be used as an identifier for it when saving and restoring the state.
     *
     * @param view The view to assign the tag to.
     * @param tag The tag to assign to the view.
     */
    fun setViewTag(view: View, tag: String) {
        view.tag = tag
    }

    /**
     * Updates the ViewModels' SaveStateHandle with the views states using [saveView] to get different views states.
     *
     * Note: The saved state views should be assigned tags.
     *
     * @param state The state of the viewModel.
     * @param views The views we want to save it's state.
     */
    fun saveViewState(state: SavedStateHandle, vararg views: View) {
        for (view in views) {
            val viewTag = view.tag as String?
            if (viewTag != null) {
                val viewState = saveView(view)
                state.set(viewTag, viewState)
            }
        }
    }

    /**
     * Checks different views types and returns the view's state model extending from [BaseViewStateModel] based on the view type.
     *
     * @param view The view we want to save it's state.
     * @return returns the View state Model extending from [BaseViewStateModel].
     */
    private fun saveView(view: View): BaseViewStateModel {
        when (view) {
            is TextView -> {
                val textViewState = TextViewState(view.text, view.ellipsize, view.maxLines)
                return BaseViewStateModel(view.getVisibility(), textViewState)
            }
            is NestedScrollView -> {
                val scrollViewState = ScrollViewState(view.getScrollY())
                return BaseViewStateModel(view.getVisibility(), scrollViewState)
            }
            is ScrollView -> {
                val scrollViewState = ScrollViewState(view.getScrollY())
                return BaseViewStateModel(view.getVisibility(), scrollViewState)
            }
            is HorizontalScrollView -> {
                val scrollViewState = ScrollViewState(view.getScrollX())
                return BaseViewStateModel(view.getVisibility(), scrollViewState)
            }
            is ViewPager -> {
                val viewPagerState = ViewPagerState(view.currentItem)
                return BaseViewStateModel(view.getVisibility(), viewPagerState)
            }
            is RecyclerView -> {
                val onSaveInstanceState =
                    (view.layoutManager as LinearLayoutManager).onSaveInstanceState()
                return BaseViewStateModel(view.getVisibility(), onSaveInstanceState)
            }
            else -> {
                return BaseViewStateModel(view.visibility, null)
            }
        }
    }

    /**
     * Checks the view type and applies the view's state model [value] based on the view type.
     *
     * @param view The view we want to restore it's state.
     * @param value the Value that we want to restore.
     */
    fun restoreView(view: View, value: BaseViewStateModel) {
        view.visibility = value.visibility
        when (view) {
            is TextView -> {
                val textViewState: TextViewState = value.data as TextViewState
                textViewState.restoreState(view)
            }
            is NestedScrollView -> {
                val scrollViewState = value.data as ScrollViewState
                view.setScrollY(scrollViewState.scroll)
            }
            is ScrollView -> {
                val scrollViewState = value.data as ScrollViewState
                view.setScrollY(scrollViewState.scroll)
            }
            is HorizontalScrollView -> {
                val scrollViewState = value.data as ScrollViewState
                view.setScrollX(scrollViewState.scroll)
            }
            is ViewPager -> {
                val viewPagerState = value.data as ViewPagerState
                view.currentItem = viewPagerState.currentItem
            }
            is RecyclerView -> {
                if (view.layoutManager is LinearLayoutManager) {
                    val state = value.data
                    (view.layoutManager as LinearLayoutManager).onRestoreInstanceState(state)
                }
            }
        }
    }
}
