package com.example.currentnews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currentnews.core.common.Constants
import com.example.currentnews.data.local.entity.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            return when (val temp = instance) {
                null -> synchronized(this) {
                    Room.databaseBuilder(
                        context.applicationContext, NewsDatabase::class.java,
                        Constants.DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                else -> temp
            }
        }
    }
}