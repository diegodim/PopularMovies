package com.diego.duarte.popularmovieskotlin.data.source.api


import com.diego.duarte.popularmovieskotlin.data.model.Movies
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {



    @GET("discover/movie?language=pt-BR")
    fun getPopularMovies(
        @Query("page") page: Int
    ): Observable<Response<Movies>>

    @GET("discover/movie?language=pt-BR&sort_by=vote_count.desc")
    fun getTopMovies(
        @Query("page") page: Int
    ): Observable<Response<Movies>>

}