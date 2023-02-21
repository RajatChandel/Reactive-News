package `in`.rchandel.reactivenews.retrofit

import `in`.rchandel.reactivenews.data.HeadlineResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getHeadlines() : Response<HeadlineResponse>
}