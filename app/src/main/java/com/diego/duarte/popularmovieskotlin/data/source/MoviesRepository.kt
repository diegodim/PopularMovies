package com.diego.duarte.popularmovieskotlin.data.source

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.api.ApiService
import com.diego.duarte.popularmovieskotlin.data.source.api.RetrofitBuilder
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor() {


    fun getMoviesByPopularity( page: Int) = RetrofitBuilder().buildRetrofit()?.getPopularMovies(page)


    fun getMoviesByRating( page: Int) = RetrofitBuilder().buildRetrofit()?.getTopMovies(page)

}