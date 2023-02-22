package `in`.rchandel.reactivenews

import `in`.rchandel.reactivenews.adapter.ArticleAdapter
import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.databinding.ActivityMainBinding
import `in`.rchandel.reactivenews.viewmodels.MainViewModel
import `in`.rchandel.reactivenews.viewmodels.MainViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.chip.Chip
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory : MainViewModelFactory

    var cat = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setOnRefreshListener {
            mainViewModel.getArticlesByCategory(cat)
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if(checkedIds.isNotEmpty()) {
                val selected = group.findViewById<Chip>(checkedIds.last())
                if (selected.text.toString() != "All") {
                    cat = selected.text.toString()
                    mainViewModel.getArticlesByCategory(cat)
                } else {
                    mainViewModel.getArticles()
                }
            }
        }

        val adapter = ArticleAdapter(this)
        binding.rvArticles.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvArticles.adapter = adapter
        (application as ReactiveNewsApplication).applicationComponent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.articleLiveData.observe(this) {
            adapter.updateList(it)
            binding.swipeRefresh.isRefreshing = false
        }

    }
}