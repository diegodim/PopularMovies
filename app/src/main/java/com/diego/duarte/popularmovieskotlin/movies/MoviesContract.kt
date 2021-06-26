package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies

interface MoviesContract {
    interface View {
        fun hideLoadingDialog()
        fun showError(message: String)
        fun showMovies(movies: List<Movie>)
        fun onMovieClicked(movie: Movie)
    }
    interface Presenter{
        fun onCreate()
        fun getPopularMovies(page: Int)
        fun getTopMovies(page: Int)
        fun getFavoriteMovies()
        fun loadNextPage(navigation: Int, page: Int): Int
    }
}