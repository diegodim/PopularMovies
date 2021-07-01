package com.diego.duarte.popularmovieskotlin.data.source

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.api.RetrofitBuilder
import com.diego.duarte.popularmovieskotlin.data.source.local.RealmBuilder

class MoviesRepository: Repository {

    override fun getMoviesByPopularity(page: Int) = RetrofitBuilder().buildRetrofit()?.getPopularMovies(page)!!

    override fun getMoviesByRating(page: Int) = RetrofitBuilder().buildRetrofit()?.getTopMovies(page)!!

    override fun getMovieVideos(id: Int) = RetrofitBuilder().buildRetrofit()?.getMovieVideos(id)!!

    override fun getMoviesByFavorite() = RealmBuilder().getFavoritesMovies()

    override fun setMovieAsFavorite (movie: Movie) = RealmBuilder().setFavoriteMovie(movie)

    override fun deleteMovieAsFavorite (movie: Movie) = RealmBuilder().deleteFavoriteMovie(movie)

    override fun getMovieFromFavorite(movie: Movie) = RealmBuilder().getFavoriteMovie(movie)

}