package `in`.rchandel.reactivenews.retrofit

import `in`.rchandel.reactivenews.data.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getArticles(@Query("apiKey") apiKey : String) : Response<ArticleResponse>

    @GET("top-headlines")
    suspend fun getArticlesByCategory(@Query("apiKey") apiKey : String, @Query("category") category: String) : Response<ArticleResponse>
}