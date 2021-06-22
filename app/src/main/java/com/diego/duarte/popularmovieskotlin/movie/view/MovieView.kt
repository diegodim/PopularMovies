package com.diego.duarte.popularmovieskotlin.movie.view

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video

interface MovieView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showError(message: String)
    fun showMovie(movie: Movie)
    fun showVideos(videos: List<Video>)
}