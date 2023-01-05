package com.example.currentnews.di

import android.content.Context
import com.example.currentnews.data.local.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.currentnews.data.local.NewsDao
import com.example.currentnews.data.local.NewsDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCurrencyDatabase(@ApplicationContext appContext: Context): NewsDatabase =
        NewsDatabase.getInstance(appContext)

    @Provides
    @Singleton
    fun provideCurrencyDAO(newsDb: NewsDatabase): NewsDao =
        newsDb.newsDao()

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

}