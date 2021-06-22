package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.movie.view.MovieView
import com.diego.duarte.popularmovieskotlin.movie.view.VideoItemView
import com.diego.duarte.popularmovieskotlin.movie.view.VideosAdapter
import com.diego.duarte.popularmovieskotlin.movies.MoviesModel
import com.diego.duarte.popularmovieskotlin.movies.view.MovieItemView
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import io.reactivex.rxjava3.observers.DisposableObserver

class MoviePresenter (private val model: MovieModel, private val view: MovieView) : BasePresenter(){

    private var videos: List<Video> = ArrayList()

    val itemCount: Int get() = videos.size

    fun showMovie()
    {
        view.showMovie(model.getMovieIntent())
        model.getVideos(model.getMovieIntent().id, VideosListObserver())

    }

    fun setList(listVideos: List<Video>) {
        videos = videos + listVideos
    }

    fun onBindItemView(itemView: VideoItemView, position: Int) {
        itemView.bindItem(videos[position])
    }


    inner class VideosListObserver : DisposableObserver<List<Video>>() {
        override fun onNext(t: List<Video>?) {

            if (t != null) {

                view.showVideos(t)
                view.hideLoadingDialog()

            }

        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }

        override fun onComplete() {
            TODO("Not yet implemented")
        }

    }

    fun onRetryClicked() {
        view.showLoadingDialog()
        showMovie()
    }

    fun onTrailerClicked(position: Int) {

    }
}