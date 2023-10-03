package com.ahmed.areebmovies.utils

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ahmed.areebmovies.R
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


fun String?.alternate(alt: String = Constants.General.DASH_TEXT): String {
    return if (!this?.trim().isNullOrEmpty()) this?.trim().toString() else alt
}

fun ImageView.setNetworkImage(imageUrl: String?){
    Glide
        .with(this.context)
        .load(Constants.URL.getImageUrl(imageUrl))
        .fitCenter()
        .placeholder(R.drawable.ic_downloading)
        .error(R.drawable.ic_error)
        .into(this)
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
