package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.movie.view.MovieView
import io.reactivex.rxjava3.disposables.Disposable

import io.reactivex.rxjava3.observers.DisposableObserver

class MoviePresenter (private val model: MovieModel, private val view: MovieView) : BasePresenter(){

    private lateinit var disposable : Disposable

    fun showMovie()
    {
        view.showLoadingDialog()
        view.showMovie(model.getMovieIntent())
        disposable = model.getVideos(model.getMovieIntent().id, VideosListObserver())!!
        this.addDisposable(disposable)

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


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}