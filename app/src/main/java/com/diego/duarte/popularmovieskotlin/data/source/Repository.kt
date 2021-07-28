package com.diego.duarte.popularmovieskotlin.data.source

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response


interface Repository {

    fun getMoviesByPopularity( page: Int): Observable<Response<Movies>>

    fun getMoviesByRating( page: Int): Observable<Response<Movies>>

    fun getMovieVideos(id: Int): Observable<Response<Videos>>

    fun getMoviesByFavorite(): Observable<List<Movie>>

    fun setMovieAsFavorite (movie: Movie): Observable<Movie>

    fun deleteMovieAsFavorite (movie: Movie): Observable<Movie>

    fun getMovieFromFavorite (movie: Movie): Observable<Movie>
}