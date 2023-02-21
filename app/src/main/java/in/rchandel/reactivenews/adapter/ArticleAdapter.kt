package `in`.rchandel.reactivenews.adapter

import `in`.rchandel.reactivenews.R
import `in`.rchandel.reactivenews.data.Article
import `in`.rchandel.reactivenews.databinding.ArticleSingleItemBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class ArticleAdapter(private val articleList: ArrayList<Article>, private val context: Context) : Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(binding : ArticleSingleItemBinding) : ViewHolder(binding.root) {
        val author = binding.tvAuthor
        val time = binding.tvTimestamp
        val source = binding.tvSource
        val image = binding.ivImage
        val headline = binding.tvHeading
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticleSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]

        holder.author.text = article.author
        holder.source.text = article.source?.name
        holder.headline.text = article.title

        Glide.with(context).load(article.urlToImage).placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_webp))
            .into(holder.image)


    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}