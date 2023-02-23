package `in`.rchandel.reactivenews.db

import `in`.rchandel.reactivenews.data.Article
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert
    suspend fun addArticles(articles: List<Article>)

    @Insert
    suspend fun addArticle(articles: Article)

    @Query("SELECT * From Article")
    suspend fun getArticles() : List<Article>

    @Query("SELECT * From Article WHERE Article.title=:title")
    suspend fun getArticleByTitle(title: String) : Article
}