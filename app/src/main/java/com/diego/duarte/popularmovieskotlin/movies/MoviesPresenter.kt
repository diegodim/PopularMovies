package com.diego.duarte.popularmovieskotlin.movies

import android.app.Activity
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.base.BaseObserver
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.util.SchedulerProvider


class MoviesPresenter (private val repository: Repository,
                       private val view: MoviesContract.View,
                       private val schedulerProvider: SchedulerProvider )
    : BasePresenter(), MoviesContract.Presenter {


    private var isLoading = false

    override fun getMovies(navigation: Int, page: Int){
        isLoading = true
        if(page == 1)
            view.showLoadingDialog()
        if(navigation == 0)
            getPopularMovies(page)
        else if(navigation == 1)
            getTopMovies(page)
        else if(navigation == 2)
            getFavoriteMovies()
    }

    override fun loadNextPage(navigation: Int, page: Int): Int {
        var nextPage = page
        if (!isLoading) {
            nextPage++
            getMovies(navigation, nextPage)
        }
        return nextPage
    }

    private fun getPopularMovies(page: Int) {

        val observer = MoviesListObserver()
        val disposable = repository.getMoviesByPopularity(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
            { if (it.isSuccessful)  observer.onNext(it.body()!!)  }, // onNext
            { observer.onError(it) }
        )
        this.addDisposable(disposable!!)
    }

    private fun getTopMovies(page: Int) {
        val observer = MoviesListObserver()
        val disposable = repository.getMoviesByRating(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            ?.subscribe(
            { if (it.isSuccessful)  observer.onNext(it.body()!!)  }, // onNext
            { observer.onError(it) }
        )
        this.addDisposable(disposable!!)
    }


    private fun getFavoriteMovies(){
        val observer = MoviesLocalListObserver()
        val disposable = repository.getMoviesByFavorite()
            .subscribeOn(schedulerProvider.trampoline())
            .observeOn(schedulerProvider.ui())
            .subscribe(
            { observer.onNext(it) }, // onNext
            { observer.onError(it) }
        )
        this.addDisposable(disposable!!)
    }




    inner class MoviesListObserver: BaseObserver<Movies>() {
        override fun onNext(t: Movies) {

            view.showMovies(t.results)
            view.hideLoadingDialog()
            isLoading = false

        }
        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }
    }

    inner class MoviesLocalListObserver : BaseObserver<List<Movie>?>() {
        override fun onNext(t: List<Movie>?) {
            if (t != null) {
                //println("Success:" + t[0].toString())
                view.showMovies(t)
                view.hideLoadingDialog()
            } else {
                view.showError((view as Activity).getString(R.string.error_message_favorite_not_found))
            }
        }

        override fun onError(e: Throwable?) {
            view.showError(e?.message.toString())
        }

    }

}