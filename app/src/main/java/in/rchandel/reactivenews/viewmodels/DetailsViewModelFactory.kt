package `in`.rchandel.reactivenews.viewmodels

import `in`.rchandel.reactivenews.repository.ArticleRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(private val repository: ArticleRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}