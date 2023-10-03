package com.ahmed.areebmovies.utils

object Constants {
    object SharedPreference {
        const val SHARED_PREF_NAME = "my_shared_pref"
    }

    object General {
        const val EMPTY_TEXT = ""
        const val DASH_TEXT = "-"
    }


    object Network {
        const val CONNECT_TIMEOUT = 5L
        const val READ_TIMEOUT = 5L
        const val WRITE_TIMEOUT = 5L
    }

    object URL {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val GET_MOVIES = "discover/movie"
        const val GET_MOVIE_DETAILS = "movie/{movieId}?language=en-US"
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        fun getImageUrl(posterPath: String?): String?{
            return if(posterPath.isNullOrEmpty()) null
            else "$IMAGE_BASE_URL$posterPath"
        }
    }

    object QueryParams {
        const val PAGE = "page"
    }

    object Headers {
        const val AUTHORIZATION = "Authorization"
        const val ACCEPT = "accept"
        const val AUTHORIZATION_VALUE =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZTY5YzZhYzA5MDM0YTI3N2NkZGIyYjhhNmYyNjFhNyIsInN1YiI6IjY0ZTYyZjRjOTBlYTRiMDBjNzM4ODVmNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wfAILB4WSIrXDectu-MgnSXHf9AZPiea4HE_9TNPTMg"
        const val ACCEPT_VALUE = "application/json"
    }

    object ViewsTags {
        const val RECYCLER_VIEW_MOVIES = "RECYCLER_VIEW_MOVIES"
    }
}