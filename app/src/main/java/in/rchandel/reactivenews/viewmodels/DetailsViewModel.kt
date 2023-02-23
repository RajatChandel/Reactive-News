package `in`.rchandel.reactivenews.viewmodels

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.repository.ArticleRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel (private val articleRepository: ArticleRepository) : ViewModel() {

    val articleLivedData : LiveData<Article>
    get() = articleRepository.article

    fun getArticleByTitle(title: String) {
        viewModelScope.launch {
            articleRepository.getArticleByTitle(title)
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            articleRepository.addArticle(article)
        }
    }

}