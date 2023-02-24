package `in`.rchandel.reactivenews.repository

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.db.NewsDb
import `in`.rchandel.reactivenews.retrofit.NewsAPI
import `in`.rchandel.reactivenews.utils.Constants
import `in`.rchandel.reactivenews.utils.NetworkUtils
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val newsAPI: NewsAPI,
    private val newsDb: NewsDb,
    private val context: Context
) {

    private val _articles = MutableLiveData<List<Article>>()
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article
    val articles: LiveData<List<Article>>
        get() = _articles

    suspend fun getArticles() {
        if (NetworkUtils.isOnline(context)) {
            try {
                val result = newsAPI.getArticles(Constants.API_KEY, Constants.country)
                if (result.isSuccessful && result.body() != null) {
                    _articles.postValue(result.body()!!.articles)
                } else {
                    _articles.postValue(newsDb.getNewsDao().getArticles())
                }
            } catch (exception: Exception) {
                _articles.postValue(newsDb.getNewsDao().getArticles())
            }

        } else {
            _articles.postValue(newsDb.getNewsDao().getArticles())
        }
    }

    suspend fun getArticlesByCategory(category: String) {
        if (NetworkUtils.isOnline(context)) {
            try {
                val result =
                    newsAPI.getArticlesByCategory(Constants.API_KEY, category, Constants.country)
                if (result.isSuccessful && result.body() != null) {
                    _articles.postValue(result.body()!!.articles)
                } else {
                _articles.postValue(newsDb.getNewsDao().getArticles())
            }
            } catch(exception : Exception) {
                _articles.postValue(newsDb.getNewsDao().getArticles())
            }
        } else {
            _articles.postValue(newsDb.getNewsDao().getArticles())
        }
    }

    suspend fun addArticle(article: Article) {
        if(newsDb.getNewsDao().getArticleByTitle(article.title) == null) {
            newsDb.getNewsDao().addArticle(article)
        }
    }

    suspend fun getArticleByTitle(title: String) {
        _article.postValue(newsDb.getNewsDao().getArticleByTitle(title))
    }

}