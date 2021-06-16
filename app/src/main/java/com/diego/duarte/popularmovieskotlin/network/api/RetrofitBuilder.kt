package com.diego.duarte.popularmovieskotlin.network.api

import com.diego.duarte.popularmovieskotlin.BuildConfig
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitBuilder {

    val BASE_URL: String = "https://api.themoviedb.org/3/"

    fun buildRetrofit(): Retrofit  {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(buildClient())
            .baseUrl(BASE_URL)
            .build()

    }


    private fun buildClient(): OkHttpClient {

        val builder:OkHttpClient.Builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = Level.BODY
        builder.addNetworkInterceptor(interceptor)

        builder.addInterceptor(ApiKeyInterceptor(BuildConfig.TMDB_API_KEY))

        return builder.build()
    }
}
