package com.ahmed.areebmovies.utils.view_state

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.reflect.KFunction0

// Copyright (c) 2020 Link Development All rights reserved.
class SaveStateLifecycleObserver(
    private val saveState: KFunction0<Unit>,
    private val setViewsTags: KFunction0<Unit>
) : LifecycleObserver {

    /**
     * Will be invoked at onViewDestroy to save the views states.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        saveState.invoke()
    }

    /**
     * Will be invoked at onActivityCreated to save the views states.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityCreated() {
        setViewsTags.invoke()
    }
}
