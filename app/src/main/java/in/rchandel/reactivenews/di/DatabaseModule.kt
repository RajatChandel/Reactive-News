package `in`.rchandel.reactivenews.di

import `in`.rchandel.reactivenews.db.NewsDb
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDb(context: Context) : NewsDb {
        return Room.databaseBuilder(context, NewsDb::class.java, "NewsDb").build()
    }
}