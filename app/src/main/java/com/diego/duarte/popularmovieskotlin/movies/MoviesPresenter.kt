package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.realm.RealmResults

class MoviesPresenter (private val model: MoviesModel, private val view: MoviesView) : BasePresenter() {


   // private lateinit var disposable: Disposable



    fun getPopularMovies(page: Int) {
        //view.showLoadingDialog()
        val disposable = model.getPopularMovies(page, MoviesListObserver())!!
        this.addDisposable(disposable)
    }

    fun getTopMovies(page: Int) {
        //view.showLoadingDialog()
        val disposable = model.getTopMovies(page, MoviesListObserver())!!
        this.addDisposable(disposable)
    }


    fun getFavoriteMovies(){
        val disposable = model.getFavoriteMovies( MoviesLocalListObserver())!!
        this.addDisposable(disposable)
    }

    inner class MoviesListObserver : DisposableObserver<Movies>() {
        override fun onNext(t: Movies?) {

            if (t != null) {

                view.showMovies(t.results)
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

    inner class MoviesLocalListObserver : DisposableObserver<RealmResults<Movie>>() {
        override fun onNext(t: RealmResults<Movie>?) {
            if (t != null) {
                if(t[0]!=null) {
                    println("Success:" + t[0].toString())
                    view.showMovies(t)
                    view.hideLoadingDialog()
                }
                else{
                    view.showError("Nenhum favorito encontrado.")
                }
            }
        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }

        override fun onComplete() {
            TODO("Not yet implemented")
        }

    }


}