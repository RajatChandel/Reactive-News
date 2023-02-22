package `in`.rchandel.reactivenews

import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.databinding.ActivityDetailsBinding
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    val ARTICLE_KEY = "article_key"
    val ARTICLE_KEY_BUNDLE = "article_key_bundle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


       val bundle = intent.getBundleExtra(ARTICLE_KEY_BUNDLE)
        val parcel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable<Article>(ARTICLE_KEY, Article::class.java)
        } else {
            bundle?.getParcelable<Article>(ARTICLE_KEY)
        }


    }
}