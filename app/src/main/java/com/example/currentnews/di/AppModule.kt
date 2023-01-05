package com.example.currentnews.di

import android.content.Context
import androidx.annotation.StringRes
import com.example.currentnews.domain.repository.NewsRepository
import com.example.currentnews.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.currentnews.core.common.DispatcherProvider
import com.example.currentnews.core.utils.ConnectivityObserver
import com.example.currentnews.core.utils.NetworkConnectivityObserver
import com.example.currentnews.data.local.NewsDao
import com.example.currentnews.data.remote.api.NewsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi, newsDao: NewsDao): NewsRepository =
        NewsRepositoryImpl(api, newsDao)

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkObserver(@ApplicationContext context: Context): ConnectivityObserver =
        NetworkConnectivityObserver(context)

    @Singleton
    class ResourcesProvider @Inject constructor(
        @ApplicationContext private val context: Context
    ) {
        fun getString(@StringRes stringResId: Int): String {
            return context.getString(stringResId)
        }
    }
}