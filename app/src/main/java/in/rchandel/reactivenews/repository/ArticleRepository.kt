package `in`.rchandel.reactivenews.repository

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.db.NewsDb
import `in`.rchandel.reactivenews.retrofit.NewsAPI
import `in`.rchandel.reactivenews.utils.Constants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val newsAPI: NewsAPI, private val newsDb: NewsDb){

    private val _articles = MutableLiveData<List<Article>>()
    val articles : LiveData<List<Article>>
    get() = _articles

    suspend fun getArticles() {
        val result = newsAPI.getArticles(Constants.API_KEY)
        if (result.isSuccessful && result.body() != null) {
            _articles.postValue(result.body()!!.articles)
        }
    }

    suspend fun getArticlesByCategory(category: String) {
        val result = newsAPI.getArticlesByCategory(Constants.API_KEY, category)
        if (result.isSuccessful && result.body() != null) {
            _articles.postValue(result.body()!!.articles)
        }
    }

    suspend fun addArticle(article: Article) {
        newsDb.getNewsDao().addArticle(article)
    }
}