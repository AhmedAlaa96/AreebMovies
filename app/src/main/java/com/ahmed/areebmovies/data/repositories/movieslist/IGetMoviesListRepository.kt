package com.ahmed.areebmovies.data.repositories.movieslist

import com.ahmed.areebmovies.data.models.Status
import com.ahmed.areebmovies.data.models.dto.MoviesListResponse
import com.ahmed.areebmovies.data.models.PageModel
import com.ahmed.areebmovies.ui.base.IBaseRepository
import kotlinx.coroutines.flow.Flow

interface IGetMoviesListRepository : IBaseRepository {
    fun getMoviesList(pageModel: PageModel): Flow<Status<MoviesListResponse>>
}