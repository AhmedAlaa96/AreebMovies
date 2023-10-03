package com.ahmed.areebmovies.ui.moviedetails.di

import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.repositories.moviedetails.GetMovieDetailsRepository
import com.ahmed.areebmovies.data.repositories.moviedetails.IGetMovieDetailsRepository
import com.ahmed.areebmovies.data.sharedprefrences.IPreferencesDataSource
import com.ahmed.areebmovies.domain.usecases.moviedetails.IMovieDetailsUseCase
import com.ahmed.areebmovies.domain.usecases.moviedetails.MovieDetailsUseCase
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
abstract class MovieDetailsModule {

    companion object{
        @Singleton
        @Provides
        fun provideMoviesDetailsRepository(
            connectionUtils: IConnectionUtils,
            mIRemoteDataSource: IRemoteDataSource,
            mILocalDataSource: ILocalDataSource,
            mIPreferencesDataSource: IPreferencesDataSource
        ): IGetMovieDetailsRepository {
            return GetMovieDetailsRepository(
                connectionUtils,
                mIRemoteDataSource,
                mILocalDataSource,
                mIPreferencesDataSource
            )
        }
    }

    @Singleton
    @Binds
    abstract fun bindIMoviesDetailsUseCase(movieDetailsUseCase: MovieDetailsUseCase): IMovieDetailsUseCase


}