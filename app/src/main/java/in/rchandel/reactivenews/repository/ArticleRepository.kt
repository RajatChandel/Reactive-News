package `in`.rchandel.reactivenews.repository

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.retrofit.NewsAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val newsAPI: NewsAPI){

    private val _articles = MutableLiveData<List<Article>>()
    val articles : LiveData<List<Article>>
    get() = _articles

    suspend fun getArticles() {
        val result = newsAPI.getArticles()
        if (result.isSuccessful && result.body() != null) {
            _articles.postValue(result.body()!!.articles)
        }
    }
}