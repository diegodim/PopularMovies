package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies

interface MoviesContract {
    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showError(message: String)
        fun showMovies(movies: List<Movie>)
        fun onMovieClicked(movie: Movie)
    }
    interface Presenter{
        fun getMovies(navigation: Int, page: Int)
        fun loadNextPage(navigation: Int, page: Int): Int
    }
}