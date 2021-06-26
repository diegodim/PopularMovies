package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BaseObserver
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviePresenter (private val repository: MoviesRepository,
                      val movie: Movie, private
                      val view: MovieContract.View) : BasePresenter(), MovieContract.Presenter{



    override fun getMovie() {
        val observer= VideosListObserver()
        view.showLoadingDialog()
        view.showMovie(movie)
        val disposable = repository.getMovieVideos(movie.id!!)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { if (it.isSuccessful) observer.onNext(it.body()!!)},          // onNext
                { observer.onError(it) }, // onError
                { observer.onComplete()}   // onComplete
            )
        this.addDisposable(disposable!!)

    }

    override fun setFavorite(){
        val observer = MovieSaveObserver()

        val disposable = repository.saveMovieAsFavorite(movie)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { observer.onNext(it)},          // onNext
                { observer.onError(it)}, // onError
                { observer.onComplete()}   // onComplete)
            )
        this.addDisposable(disposable)
    }

    inner class MovieSaveObserver: BaseObserver<Boolean>(){
        override fun onNext(t: Boolean) {
            //TODO
            //println("Success:" + t.toString())
        }

        override fun onError(e: Throwable?) {
            //TODO
            println("Error"+ e?.message.toString())

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