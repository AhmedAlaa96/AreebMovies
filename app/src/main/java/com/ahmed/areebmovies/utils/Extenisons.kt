package com.ahmed.areebmovies.utils

import android.service.autofill.FieldClassification.Match
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


fun String?.alternate(alt: String = Constants.General.DASH_TEXT): String {
    return if (!this?.trim().isNullOrEmpty()) this?.trim().toString() else alt
}

fun <T : Any?, L : SharedFlow<T>> LifecycleOwner.observe(sharedFlow: L, body: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            sharedFlow.collect {
                body.invoke(it)
            }
        }
    }
}

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(sharedFlow: L, body: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            sharedFlow.observe(this@observe) {
                body.invoke(it)
            }
        }
    }
}
