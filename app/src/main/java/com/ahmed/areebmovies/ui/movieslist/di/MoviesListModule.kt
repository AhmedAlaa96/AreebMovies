package com.ahmed.areebmovies.ui.movieslist.di

import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.repositories.movieslist.GetMoviesListRepository
import com.ahmed.areebmovies.data.repositories.movieslist.IGetMoviesListRepository
import com.ahmed.areebmovies.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.areebmovies.domain.usecases.movieslist.IMoviesListUseCase
import com.ahmed.areebmovies.domain.usecases.movieslist.MoviesListUseCase
import com.ahmed.areebmovies.retrofit.RetrofitModule
import com.ahmed.areebmovies.utils.connection_utils.IConnectionUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
abstract class MoviesListModule {

    companion object{
        @Singleton
        @Provides
        fun provideMoviesListingRepository(
            connectionUtils: IConnectionUtils,
            mIRemoteDataSource: IRemoteDataSource,
            mILocalDataSource: ILocalDataSource,
            mIPreferencesDataSource: IPreferencesDataSource
        ): IGetMoviesListRepository {
            return GetMoviesListRepository(
                connectionUtils,
                mIRemoteDataSource,
                mILocalDataSource,
                mIPreferencesDataSource
            )
        }
    }

    @Singleton
    @Binds
    abstract fun bindIMoviesListUseCase(moviesUseCase: MoviesListUseCase): IMoviesListUseCase


}