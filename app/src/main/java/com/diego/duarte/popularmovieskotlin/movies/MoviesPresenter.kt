package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.realm.RealmList
import io.realm.RealmResults

class MoviesPresenter (private val model: MoviesModel, private val view: MoviesView) : BasePresenter() {


    private lateinit var disposable: Disposable



    fun getPopularMovies(page: Int) {
        //view.showLoadingDialog()
        disposable = model.getPopularMovies(page, MoviesListObserver())!!
        this.addDisposable(disposable)
    }

    fun getTopMovies(page: Int) {
        //view.showLoadingDialog()
        disposable = model.getTopMovies(page, MoviesListObserver())!!
        this.addDisposable(disposable)
    }


    fun getFavoriteMovies(){
        disposable = model.getFavoriteMovies( MoviesLocalListObserver())!!
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
        override fun onNext(it: RealmResults<Movie>?) {
            if (it != null) {
                if(it[0]!=null) {
                    view.showMovies(it)
                    view.hideLoadingDialog()
                }
                else{
                    //TODO
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