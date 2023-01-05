package com.example.currentnews.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.example.currentnews.core.utils.Helper
import com.example.currentnews.data.local.entity.NewsEntity

data class ArticleDto(
    @SerializedName("source")
    val sourceDto: SourceDto?,
    val title: String,
    @SerializedName("description")
    val description: String?,
    val author: String?,
    @SerializedName("url")
    val newsUrl: String,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    @SerializedName("content")
    val content: String?,
    val publishedAt: String
) {
    fun toNewsEntity(): NewsEntity {

        return NewsEntity(
            newsTitle = title,
            newsDescription = description?:"Read more ...",
            newsAuthor = author ?: "News Correspondent",
            newsUrl = newsUrl,
            newsImage = imageUrl?:"",
            sourceName = validateSourceName(),
            newsPublishedAt = Helper.toReadableDate(publishedAt)
        )
    }

    private fun validateSourceName(): String {
        return if (sourceDto?.name == null) {
            "N/A"
        } else {
            sourceDto.name
        }
    }
}