package com.example.currentnews.data.repository

import android.util.Log
import com.example.currentnews.core.utils.Resource
import com.example.currentnews.data.local.NewsDao
import com.example.currentnews.data.remote.api.NewsApi
import com.example.currentnews.domain.model.News
import com.example.currentnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao
) : NewsRepository {


    override fun getAllNewsHeadlines(query: String): Flow<Resource<List<News>>> = flow {

        val staleNewsHeadlines = dao.getAllNewsHeadlines().map { it.toNews() }
        emit(Resource.Loading(data = staleNewsHeadlines))

        try {
            //val allNewsHeadlines = api.getNewsHeadlines("us", 100)
            val allNewsHeadlines = api.getNewsData(query)
            dao.deleteAllNews()
            dao.insert(allNewsHeadlines.articleList.map { it.toNewsEntity() })
            val freshNewsHeadLines = dao.getAllNewsHeadlines().map { it.toNews() }
            emit(Resource.Success(freshNewsHeadLines))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = staleNewsHeadlines
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = staleNewsHeadlines
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "Something went wrong. Please try again!",
                    data = staleNewsHeadlines
                )
            )
        }

    }

    override fun getSingleNewsById(id: String): Flow<Resource<News>> = flow {
        val singleNews = dao.loadSingle(id)
        singleNews?.let { emit(Resource.Success(it.toNews())) }
    }

}