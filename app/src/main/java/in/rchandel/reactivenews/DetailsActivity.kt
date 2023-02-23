package `in`.rchandel.reactivenews

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.databinding.ActivityDetailsBinding
import `in`.rchandel.reactivenews.viewmodels.DetailsViewModel
import `in`.rchandel.reactivenews.viewmodels.DetailsViewModelFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object {
        const val ARTICLE_KEY = "article_key"
        const val ARTICLE_KEY_BUNDLE = "article_key_bundle"
    }

    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModelFactory

    lateinit var detailsViewModel: DetailsViewModel
    lateinit var article: Article
    var saved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailsToolbar)


        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
        }

        val bundle = intent.getBundleExtra(ARTICLE_KEY_BUNDLE)
        val parcel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable<Article>(ARTICLE_KEY, Article::class.java)
        } else {
            bundle?.getParcelable<Article>(ARTICLE_KEY)
        }

        (application as ReactiveNewsApplication).applicationComponent.inject(this)

        detailsViewModel =
            ViewModelProvider(this, detailsViewModelFactory).get(DetailsViewModel::class.java)

        detailsViewModel.articleLivedData.observe(this) {
            if (it != null) {
                saved = true
                invalidateOptionsMenu()
            }
        }

        article = parcel!!
        initView(article)

        detailsViewModel.getArticleByTitle(parcel.title.toString())
    }

    fun initView(article: Article) {
        Picasso.get().load(article.urlToImage).placeholder(R.drawable.placeholder_webp)
            .into(binding.ivImage)
        binding.title.text = article.title
        binding.content.text = article.content
        binding.description.text = article.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            detailsViewModel.saveArticle(article)
            item.setIcon(R.drawable.favorite_filled)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (saved) {
            menu?.get(0)?.setIcon(R.drawable.favorite_filled)
        }

        return super.onPrepareOptionsMenu(menu)
    }
}