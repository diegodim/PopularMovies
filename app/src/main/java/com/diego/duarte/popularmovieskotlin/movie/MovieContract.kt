package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.data.model.Videos

interface MovieContract {
    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showError(message: String)
        fun showMovie(movie: Movie)
        fun showVideos(videos: Videos)
        fun onVideoClicked(video: Video)
        fun showFavorite(checked:Boolean)
    }
    interface Presenter
    {
        fun getMovie()
        fun favorite()
    }
}