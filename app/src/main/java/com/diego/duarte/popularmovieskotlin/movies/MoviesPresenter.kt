package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MoviesPresenter (private val model: MoviesModel, private val view: MoviesView) : BasePresenter() {


    private lateinit var getMovies: Disposable



    fun getPopularMovies(page: Int) {
        //view.showLoadingDialog()
        getMovies = model.getPopularMovies(page, MoviesListObserver())!!
    }

    fun getTopMovies(page: Int) {
        //view.showLoadingDialog()
        getMovies = model.getTopMovies(page, MoviesListObserver())!!
    }


    inner class MoviesListObserver : DisposableObserver<Movies>() {
        override fun onNext(t: Movies?) {

            if (t != null) {

                view.showMovies(t)
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

    override fun onDestroy() {
        super.onDestroy()
        getMovies.dispose()
    }

}