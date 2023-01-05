package com.example.currentnews.domain.repository

import com.example.currentnews.core.utils.Resource
import com.example.currentnews.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllNewsHeadlines(query: String): Flow<Resource<List<News>>>
    fun getSingleNewsById(id: String): Flow<Resource<News>>
}