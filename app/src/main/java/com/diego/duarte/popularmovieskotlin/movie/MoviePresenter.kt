package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BaseObserver
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.util.schedulers.SchedulerProvider

class MoviePresenter (private val repository: Repository,
                      private val movie: Movie,
                      private val view: MovieContract.View,
                      private val schedulerProvider: SchedulerProvider
)
    : BasePresenter(), MovieContract.Presenter{



    override fun getMovie() {
        movie.isFavorite = false
        view.showLoadingDialog()
        view.showMovie(movie)
        getFavorite()
        getVideos()

    }

    override fun favorite(){

        if(!movie.isFavorite) {
            setFavorite()
        }else{
            deleteFavorite()
        }
    }

    private fun getVideos(){
        val observer= VideosListObserver()
        val disposable = repository.getMovieVideos(movie.id!!)
            .subscribeOn(schedulerProvider.io())
            ?.observeOn(schedulerProvider.ui())
            ?.subscribe(
                { if (it.isSuccessful) observer.onNext(it.body()!!)}, // onNext
                { observer.onError(it) }, // onError
                { observer.onComplete()}   // onComplete
            )
        this.addDisposable(disposable!!)
    }

    private fun setFavorite(){
        val observer = MovieFavoriteObserver()
        movie.isFavorite = true
        val disposable = repository.saveMovieAsFavorite(movie)
            .subscribeOn(schedulerProvider.trampoline())
            .observeOn(schedulerProvider.ui())
            .subscribe { observer.onNext(it) }  // onNext

        this.addDisposable(disposable)
    }

    private fun deleteFavorite(){
        val observer = MovieFavoriteObserver()
        movie.isFavorite = false
        val disposable = repository.deleteMovieAsFavorite(movie)
            .subscribeOn(schedulerProvider.trampoline())
            .observeOn(schedulerProvider.ui())
            .subscribe { observer.onNext(it) }

        this.addDisposable(disposable)
    }

    private fun getFavorite(){
        val observer = MovieFavoriteObserver()
        val disposable = repository.getMovieFromFavorite(movie)
            .subscribeOn(schedulerProvider.trampoline())
            .observeOn(schedulerProvider.ui())
            .subscribe{ observer.onNext(it.isFavorite) }

        this.addDisposable(disposable)
    }



    inner class MovieFavoriteObserver: BaseObserver<Boolean>(){
        override fun onNext(t: Boolean) {
            view.showFavorite(t)
            movie.isFavorite = t
        }
    }


    inner class VideosListObserver: BaseObserver<Videos>() {
        override fun onNext(t: Videos) {

            view.showVideos(t)
        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }

        override fun onComplete() {

            view.hideLoadingDialog()
        }

    }

}