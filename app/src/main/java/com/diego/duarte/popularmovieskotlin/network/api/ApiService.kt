package com.diego.duarte.popularmovieskotlin.network.api


import com.diego.duarte.popularmovieskotlin.models.Movies
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    @GET("discover/movie?")
    fun getMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<Response<Movies>>

}