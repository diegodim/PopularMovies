package com.diego.duarte.popularmovieskotlin.api


import com.diego.duarte.popularmovieskotlin.model.data.Movies
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {



    @GET("discover/movie?language=pt-BR")
    fun getMovies(
        @Query("page") page: Int
    ): Observable<Response<Movies>>

}