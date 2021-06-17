package com.diego.duarte.popularmovieskotlin.api

import com.diego.duarte.popularmovieskotlin.BuildConfig
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {



    fun buildRetrofit(): Retrofit  {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(buildClient())
            .baseUrl(BuildConfig.TMDB_URL)
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
