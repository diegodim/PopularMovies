package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.base.BaseObserver
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.movies.domain.GetFavoriteMovies
import com.diego.duarte.popularmovieskotlin.movies.domain.GetPopularMovies
import com.diego.duarte.popularmovieskotlin.movies.domain.GetTopMovies
import com.diego.duarte.popularmovieskotlin.util.schedulers.SchedulerProvider
import retrofit2.Response


class MoviesPresenter (
    repository: Repository,
    private val view: MoviesContract.View,
    schedulerProvider: SchedulerProvider
)
    : BasePresenter(), MoviesContract.Presenter {


    private val popularMovies: GetPopularMovies =
        GetPopularMovies(repository,
        schedulerProvider.io(),
        schedulerProvider.ui())

    private val topMovies: GetTopMovies =
        GetTopMovies(repository,
            schedulerProvider.io(),
            schedulerProvider.ui())

    private val favoriteMovies: GetFavoriteMovies =
        GetFavoriteMovies(repository,
            schedulerProvider.trampoline(),
            schedulerProvider.ui())

    private var isLoading = false

    override fun getMovies(navigation: Int, page: Int){
        isLoading = true
        if(page == 1)
            view.showLoadingDialog()

        when (navigation) {
            0 -> getPopularMovies(page)
            1 -> getTopMovies(page)
            2 -> getFavoriteMovies()
        }
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

        val disposable = popularMovies.execute(MoviesListObserver(), GetPopularMovies.Params(page))
        this.addDisposable(disposable)
    }

    private fun getTopMovies(page: Int) {
        val disposable = topMovies.execute(MoviesListObserver(), GetTopMovies.Params(page))
        this.addDisposable(disposable)
    }


    private fun getFavoriteMovies(){
        val disposable = favoriteMovies.execute(MoviesLocalListObserver(), null)
        this.addDisposable(disposable)
    }

    inner class MoviesListObserver: BaseObserver<Response<Movies>>() {
        override fun onNext(t: Response<Movies>) {
            if(t.isSuccessful) {
                view.showMovies(t.body()!!.results)
                view.hideLoadingDialog()
                isLoading = false
            }
            else{
                //TODO a enum of error
                view.showError("")
            }
        }
        override fun onError(e: Throwable?) {
            //TODO a enum of error
            view.showError("")
        }
    }

    inner class MoviesLocalListObserver : BaseObserver<List<Movie>>() {
        override fun onNext(t: List<Movie>) {

            if (t.isNotEmpty()) {
                view.showMovies(t)
                view.hideLoadingDialog()
            } else {
                //TODO a enum of error
                view.showError("")
            }
        }

        override fun onError(e: Throwable?) {
            //TODO a enum of error
            view.showError("")
        }

    }

}