package `in`.rchandel.reactivenews.db

import `in`.rchandel.reactivenews.data.Article
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Article::class], version = 1)
@TypeConverters(SourceTypeConverter::class)
abstract class NewsDb : RoomDatabase(){

    abstract fun getNewsDao() : NewsDao
}