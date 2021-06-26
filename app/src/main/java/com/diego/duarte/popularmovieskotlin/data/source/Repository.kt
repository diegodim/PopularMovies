package com.diego.duarte.popularmovieskotlin.data.source

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Singleton


interface Repository {

    fun getMoviesByPopularity( page: Int): Observable<Response<Movies>>

    fun getMoviesByRating( page: Int): Observable<Response<Movies>>

    fun getMovieVideos(id: Int): Observable<Response<Videos>>

    fun getMoviesByFavorite(): Observable<RealmResults<Movie>>

    fun saveMovieAsFavorite (movie: Movie): Observable<Boolean>


}