package `in`.rchandel.reactivenews.di

import `in`.rchandel.reactivenews.retrofit.NewsAPI
import `in`.rchandel.reactivenews.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsApi(retrofit: Retrofit) : NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }
}