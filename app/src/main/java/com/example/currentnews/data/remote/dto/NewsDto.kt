package com.example.currentnews.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("articles")
    val articleList: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)