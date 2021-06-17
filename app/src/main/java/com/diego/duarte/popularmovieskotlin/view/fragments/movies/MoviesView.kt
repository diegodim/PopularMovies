package com.diego.duarte.popularmovieskotlin.view.fragments.movies

import com.diego.duarte.popularmovieskotlin.model.data.Movie

interface MoviesView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showError(message: String)
    fun showMovie(movie: Movie)
}