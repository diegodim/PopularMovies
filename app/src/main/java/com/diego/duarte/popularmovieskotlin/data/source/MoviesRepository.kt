package com.diego.duarte.popularmovieskotlin.data.source

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.api.RetrofitBuilder
import com.diego.duarte.popularmovieskotlin.data.source.local.RealmBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor() {

    fun getMoviesByPopularity( page: Int) = RetrofitBuilder().buildRetrofit()?.getPopularMovies(page)

    fun getMoviesByRating( page: Int) = RetrofitBuilder().buildRetrofit()?.getTopMovies(page)

    fun getMovieVideos(id: Int) = RetrofitBuilder().buildRetrofit()?.getMovieVideos(id)

    fun getMoviesByFavorite() = RealmBuilder().getFavoritesMovies()

    fun saveMovieAsFavorite (movie: Movie) = RealmBuilder().saveFavoriteMovie(movie)

    //fun deleteMovieFromFavorites (movie: Movie)

}