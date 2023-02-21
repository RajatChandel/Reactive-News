package `in`.rchandel.reactivenews.viewmodels

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.repository.ArticleRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class MainViewModel(val repository: ArticleRepository) : ViewModel() {

    val articleLiveData : LiveData<List<Article>>
    get() = repository.articles

    init {
         viewModelScope.launch {
            repository.getArticles()
         }
    }
}