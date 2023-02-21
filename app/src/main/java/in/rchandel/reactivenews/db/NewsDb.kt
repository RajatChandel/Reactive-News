package `in`.rchandel.reactivenews.db

import `in`.rchandel.reactivenews.data.Article
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1)
abstract class NewsDb : RoomDatabase(){

    abstract fun getNewsDao() : NewsDao
}