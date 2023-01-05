package com.example.currentnews.data.local

import androidx.room.*
import com.example.currentnews.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsEntityList: List<NewsEntity>)

    @Query("SELECT * FROM news_table")
    suspend fun getAllNewsHeadlines(): List<NewsEntity>

    @Query("DELETE FROM news_table")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM news_table WHERE id=:id ")
    fun loadSingle(id: String): NewsEntity
}