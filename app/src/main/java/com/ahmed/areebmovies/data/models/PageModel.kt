package com.ahmed.areebmovies.data.models

data class PageModel(var page: Int = 1) {
    fun incrementPageNumber() {
        this.page++
    }

    fun reset() {
        this.page = 1
    }
}
