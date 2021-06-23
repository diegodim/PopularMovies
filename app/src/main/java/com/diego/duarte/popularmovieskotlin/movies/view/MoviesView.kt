package com.diego.duarte.popularmovieskotlin.movies.view

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies

interface MoviesView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showError(message: String)
    fun showMovies(movies: Movies)
    fun showMovie(movie: Movie)
}