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
import androidx.core.content.ContextCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailsToolbar)


        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

       val bundle = intent.getBundleExtra(ARTICLE_KEY_BUNDLE)
        val parcel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable<Article>(ARTICLE_KEY, Article::class.java)
        } else {
            bundle?.getParcelable<Article>(ARTICLE_KEY)
        }

        (application as ReactiveNewsApplication).applicationComponent.inject(this)

        detailsViewModel = ViewModelProvider(this, detailsViewModelFactory).get(DetailsViewModel::class.java)

        detailsViewModel.articleLivedData.observe(this) {
            Glide.with(this).load(it.urlToImage).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder_webp))
                .into(binding.ivImage)
            binding.title.text = it.title
            binding.content.text = it.content
        }

        initView(parcel!!)

        //detailsViewModel.getArticleByTitle(parcel?.title.toString())
    }

    fun initView(article: Article) {
//        Glide.with(this).load(article.urlToImage).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder_webp))
//            .into(binding.ivImage)
        Picasso.get().load(article.urlToImage).placeholder( R.drawable.placeholder_webp).into(binding.ivImage)
        binding.title.text = article.title
        binding.content.text = article.content
        binding.description.text = article.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }
}