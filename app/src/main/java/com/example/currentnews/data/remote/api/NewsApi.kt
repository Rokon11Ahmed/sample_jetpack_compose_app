package com.example.currentnews.data.remote.api

import com.example.currentnews.data.remote.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getNewsHeadlines(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int
    ): NewsDto

    @GET("v2/everything")
    suspend fun getNewsData(
        @Query("q") query: String,
    ):NewsDto
}