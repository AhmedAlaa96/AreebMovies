package com.ahmed.areebmovies.data.models.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class MoviesListResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: ArrayList<Movie>?= null,
    @SerializedName("total_pages")
    val totalPages: Int?= null,
    @SerializedName("total_results")
    val totalResults: Int?= null
): Parcelable