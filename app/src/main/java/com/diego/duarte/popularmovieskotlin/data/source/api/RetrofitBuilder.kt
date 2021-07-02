package com.diego.duarte.popularmovieskotlin.data.source.api

import com.diego.duarte.popularmovieskotlin.BuildConfig
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitBuilder {

    private val baseUrl = "https://api.themoviedb.org/3/"

    fun buildRetrofit(): ApiService? {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(buildClient())
            .baseUrl(baseUrl)
            .build().create(ApiService::class.java)

    }


    private fun buildClient(): OkHttpClient {

        val builder:OkHttpClient.Builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = Level.BODY
        builder.addNetworkInterceptor(interceptor)

        builder.addInterceptor(ApiKeyInterceptor(BuildConfig.TMDB_API_KEY))
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)

        return builder.build()
    }
}
