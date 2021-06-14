package com.diego.duarte.popularmovieskotlin.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitBuilder {


    fun buildRetrofit(baseUrl: String): Retrofit  {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildClient())
            .baseUrl(baseUrl)
            .build()
    }


    private fun buildClient(): OkHttpClient {

        val builder:OkHttpClient.Builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = Level.BODY
        builder.addNetworkInterceptor(interceptor)
        return builder.build()
    }
}