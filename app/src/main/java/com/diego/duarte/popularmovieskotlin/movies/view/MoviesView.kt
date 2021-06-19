package com.diego.duarte.popularmovieskotlin.movies.view

import com.diego.duarte.popularmovieskotlin.data.model.Movie

interface MoviesView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showError(message: String)
    fun showMovies(movies: List<Movie>)
}