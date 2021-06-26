package com.diego.duarte.popularmovieskotlin.movies

import android.app.Activity
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.base.BaseObserver
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.RealmResults

class MoviesPresenter (private val repository: MoviesRepository, private val view: MoviesContract.View) : BasePresenter(), MoviesContract.Presenter {


    override fun getPopularMovies(page: Int) {
        val observer = MoviesListObserver<Movies?>()
        val disposable = repository.getMoviesByPopularity(page)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(
            { if (it.isSuccessful)  observer.onNext(it.body())  }, // onNext
            { observer.onError(it) }
        )
        this.addDisposable(disposable!!)
    }

    override fun getTopMovies(page: Int) {
        val observer = MoviesListObserver<Movies?>()
        val disposable = repository.getMoviesByRating(page)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(
            { if (it.isSuccessful)  observer.onNext(it.body())  }, // onNext
            { observer.onError(it) }
        )
        this.addDisposable(disposable!!)
    }


    override fun getFavoriteMovies(){
        val observer = MoviesLocalListObserver()
        val disposable = repository.getMoviesByFavorite()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.trampoline())
            .subscribe(
            { observer.onNext(it) }, // onNext
            { observer.onError(it) }
        )
        this.addDisposable(disposable!!)
    }

    inner class MoviesListObserver<T> : BaseObserver<T>() {
        override fun onNext(t: T) {

            if (t != null) {

                view.showMovies((t as Movies).results)
                view.hideLoadingDialog()

            }

        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }


    }

    inner class MoviesLocalListObserver : BaseObserver<RealmResults<Movie>?>() {
        override fun onNext(t: RealmResults<Movie>?) {
            if (t != null) {
                println("Success:" + t[0].toString())
                view.showMovies(t)
                view.hideLoadingDialog()
            } else {
                view.showError((view as Activity).getString(R.string.message_favorite_not_found))
            }
        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }

    }

}