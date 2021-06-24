package com.diego.duarte.popularmovieskotlin.data.source

import com.diego.duarte.popularmovieskotlin.data.source.api.RetrofitBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor() {

    fun getMoviesByPopularity( page: Int) = RetrofitBuilder().buildRetrofit()?.getPopularMovies(page)

    fun getMoviesByRating( page: Int) = RetrofitBuilder().buildRetrofit()?.getTopMovies(page)

    fun getMovieVideos(id: Int) = RetrofitBuilder().buildRetrofit()?.getMovieVideos(id)

}