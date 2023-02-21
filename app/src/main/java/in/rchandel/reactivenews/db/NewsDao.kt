package `in`.rchandel.reactivenews.db

import `in`.rchandel.reactivenews.data.Article
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert
    suspend fun addArticles(articles: List<Article>)

    @Query("SELECT * From Article")
    suspend fun getArticles() : List<Article>
}