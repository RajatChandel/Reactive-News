package `in`.rchandel.reactivenews.adapter

import `in`.rchandel.reactivenews.DetailsActivity
import `in`.rchandel.reactivenews.MainActivity
import `in`.rchandel.reactivenews.R
import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.databinding.ArticleSingleItemBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ArticleAdapter( private val context: Context) : Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(binding : ArticleSingleItemBinding) : ViewHolder(binding.root) {
        val author = binding.tvAuthor
        val time = binding.tvTimestamp
        val source = binding.tvSource
        val image = binding.ivImage
        val headline = binding.tvHeading
        val root = binding.root

        init {
            binding.root.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable(DetailsActivity.ARTICLE_KEY, articleList[adapterPosition])
                intent.putExtra(DetailsActivity.ARTICLE_KEY_BUNDLE, bundle)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as MainActivity, binding.ivImage as View, "image")
                context.startActivity(intent, options.toBundle())
            }
        }

    }

    private var articleList = ArrayList<Article>()

    fun updateList(articles : List<Article>) {
        articleList.clear()
        articleList.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticleSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]

        if(article.author != null) {
            holder.author.text = article.author
        } else {
            holder.author.visibility = View.GONE
        }

        holder.source.text = article.source?.name
        holder.headline.text = article.title

//        Glide.with(context).load(article.urlToImage).placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_webp))
//            .into(holder.image)
        Picasso.get().load(article.urlToImage).placeholder( R.drawable.placeholder_webp).into(holder.image)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}