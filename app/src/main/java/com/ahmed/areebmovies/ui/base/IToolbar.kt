package com.ahmed.areebmovies.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

interface IToolbar {
    var mToolbar: Toolbar?

    fun setToolbar(
        context: Context, title: String? = null, setHomeAsUpEnabled: Boolean = false,
        navigationIconDrawable: Int = emptyNavigationIcon
    ) {
        if (mToolbar == null)
            return

        if (navigationIconDrawable != emptyNavigationIcon)
            mToolbar?.setNavigationIcon(navigationIconDrawable)

        mToolbar?.title = title

        (context as AppCompatActivity).setSupportActionBar(mToolbar)

        context.supportActionBar?.setDisplayHomeAsUpEnabled(setHomeAsUpEnabled)
    }

    fun setToolbarNavigationIcon(iconId: Int): Toolbar? {
        mToolbar?.setNavigationIcon(iconId)
        return mToolbar
    }

    fun setToolbarTitle(title: String): Toolbar? {
        mToolbar?.title = title
        return mToolbar
    }

    fun setToolbarSubTitle(subtitle: String): Toolbar? {
        mToolbar?.subtitle = subtitle
        return mToolbar
    }

    private val emptyNavigationIcon
        get() = -1
}