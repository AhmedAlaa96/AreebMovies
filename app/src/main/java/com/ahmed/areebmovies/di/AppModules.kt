package com.ahmed.areebmovies.di

import android.content.Context
import com.ahmed.areebmovies.data.local.ILocalDataSource
import com.ahmed.areebmovies.data.local.LocalDataSource
import com.ahmed.areebmovies.data.remote.IRemoteDataSource
import com.ahmed.areebmovies.data.remote.RemoteDataSource
import com.ahmed.areebmovies.data.shared_prefrences.IPreferencesDataSource
import com.ahmed.areebmovies.data.shared_prefrences.PreferencesDataSource
import com.ahmed.areebmovies.retrofit.ApiInterface
import com.ahmed.areebmovies.utils.connection_utils.ConnectionUtils
import com.ahmed.areebmovies.utils.connection_utils.IConnectionUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @Provides
    @Singleton
    fun provideConnectivity(@ApplicationContext context: Context): IConnectionUtils {
        return ConnectionUtils(context)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(): ILocalDataSource {
        return LocalDataSource()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiInterface: ApiInterface): IRemoteDataSource {
        return RemoteDataSource(apiInterface)
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(
        @ApplicationContext context: Context,
        gson: Gson
    ): IPreferencesDataSource {
        return PreferencesDataSource(context, gson)
    }

    @Provides
    @Singleton
    @Named("Repository")
    fun provideDispatcher(
    ): CoroutineDispatcher {
        return Dispatchers.IO
    }
    @Provides
    @Singleton
    @Named("ViewModel")
    fun provideMainDispatcher(
    ): CoroutineDispatcher {
        return Dispatchers.Main
    }
}