package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.movie.view.MovieView
import com.diego.duarte.popularmovieskotlin.movie.view.VideoItemView
import io.reactivex.rxjava3.disposables.Disposable

import io.reactivex.rxjava3.observers.DisposableObserver

class MoviePresenter (private val model: MovieModel, private val view: MovieView) : BasePresenter(){

    private var videos = Videos(0, ArrayList())
    private lateinit var getVideos : Disposable
    val itemCount: Int get() = videos.results.size

    fun showMovie()
    {
        view.showMovie(model.getMovieIntent())
        getVideos = model.getVideos(model.getMovieIntent().id, VideosListObserver())!!

    }

    fun setList(listVideos: Videos) {
        videos.results += listVideos.results
    }

    fun onBindItemView(itemView: VideoItemView, position: Int) {
        itemView.bindItem(videos.results[position])
    }


    inner class VideosListObserver : DisposableObserver<Videos>() {
        override fun onNext(t: Videos?) {

            if (t != null) {

                view.showVideos(t)


            }

        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }

        override fun onComplete() {

            view.hideLoadingDialog()
        }

    }

    fun onRetryClicked() {
        view.showLoadingDialog()
        showMovie()
    }

    fun onTrailerClicked(position: Int) {

        view.openVideo(videos.results[position])

    }

    override fun onDestroy() {
        super.onDestroy()
        getVideos.dispose()
    }
}