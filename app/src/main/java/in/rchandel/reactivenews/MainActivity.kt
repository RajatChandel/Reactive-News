package `in`.rchandel.reactivenews

import `in`.rchandel.reactivenews.adapter.ArticleAdapter
import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.databinding.ActivityMainBinding
import `in`.rchandel.reactivenews.utils.NetworkUtils
import `in`.rchandel.reactivenews.viewmodels.MainViewModel
import `in`.rchandel.reactivenews.viewmodels.MainViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
    lateinit var mainViewModelFactory: MainViewModelFactory

    private var cat = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startShimmer()
        binding.swipeRefresh.setOnRefreshListener {
            if (cat != "All") {
                mainViewModel.getArticlesByCategory(cat)
            } else {
                mainViewModel.getArticles()
            }
            startShimmer()
        }

        if (!NetworkUtils.isOnline(this)) {
            binding.chipGroup.visibility = View.INVISIBLE
        }
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val selected = group.findViewById<Chip>(checkedIds.last())
                if (selected.text.toString() != "All") {
                    cat = selected.text.toString()
                    mainViewModel.getArticlesByCategory(cat)
                } else {
                    mainViewModel.getArticles()
                }
                startShimmer()
            }
        }

        //recycler View
        val adapter = ArticleAdapter(this)
        binding.rvArticles.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvArticles.adapter = adapter

        //Dagger
        (application as ReactiveNewsApplication).applicationComponent.inject(this)

        //view Model
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        //Observer
        mainViewModel.articleLiveData.observe(this) {
            if (!NetworkUtils.isOnline(this)) {
                binding.chipGroup.visibility = View.INVISIBLE
            } else {
                binding.chipGroup.visibility = View.VISIBLE
            }
            if(it.isEmpty()) {
                Toast.makeText(this, "Error shown", Toast.LENGTH_LONG).show()
                showError()
            } else {
             hideError()
            }
            adapter.updateList(it)
            stopShimmer()
            binding.swipeRefresh.isRefreshing = false
        }

    }

    private fun startShimmer() {
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        binding.swipeRefresh.visibility = View.GONE
        hideError()
    }

    private fun stopShimmer() {
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
        binding.swipeRefresh.visibility = View.VISIBLE
    }

    private fun showError() {
       // binding.group.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
    }

    private fun hideError() {
     //   binding.group.visibility = View.VISIBLE
        binding.error.visibility = View.GONE
    }
}