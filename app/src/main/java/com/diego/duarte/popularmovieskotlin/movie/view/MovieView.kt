package com.diego.duarte.popularmovieskotlin.movie.view

import com.diego.duarte.popularmovieskotlin.data.model.Movie

interface MovieView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showError(message: String)
    fun showMovie(movie: Movie)
}