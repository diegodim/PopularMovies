package com.diego.duarte.popularmovieskotlin.data.source.api


import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {



    @GET("movie/popular?language=pt-BR")
    fun getPopularMovies(
        @Query("page") page: Int
    ): Observable<Response<Movies>>

    @GET("movie/top_rated?language=pt-BR")
    fun getTopMovies(
        @Query("page") page: Int
    ): Observable<Response<Movies>>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): Observable<Response<Videos>>
}