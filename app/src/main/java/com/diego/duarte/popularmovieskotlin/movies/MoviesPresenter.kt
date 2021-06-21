package com.diego.duarte.popularmovieskotlin.movies

import androidx.recyclerview.widget.GridLayoutManager
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movies.view.MovieItemView
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MoviesPresenter (private val model: MoviesModel, private val view: MoviesView) : BasePresenter() {

    private var navigation = 0
    private var page: Int = 1
    private var isLoading = false
    private var movies: List<Movie> = ArrayList()
    private lateinit var getMovies: Disposable

    val itemCount: Int get() = movies.size


    fun getPopularMovies() {
        navigation = 0
        isLoading = true
        getMovies = model.getPopularMovies(page, MoviesListObserver())!!
    }

    fun getTopMovies() {
        navigation = 1
        isLoading = true
        getMovies = model.getTopMovies(page, MoviesListObserver())!!
    }

    fun firstPage(){
        page = 1
        movies = ArrayList()
        view.showLoadingDialog()
    }

    fun getNextMoviesPage(layoutManager: GridLayoutManager) {

        if(!isLoading)
        if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 11) {
            page++
            if(navigation == 0)
                getPopularMovies()
            if(navigation == 1)
                getTopMovies()
        }
    }

    fun setList(listMovies: List<Movie>) {
        movies = movies + listMovies

    }

    fun onRetryClicked()
    {
        view.showLoadingDialog()
        if(navigation == 0)
            getPopularMovies()
        if(navigation == 1)
            getTopMovies()

    }

    fun onItemClicked(pos: Int) {
        view.showMovie(movies[pos])

    }

    fun onBindItemView(itemView: MovieItemView, pos: Int) {
        itemView.bindItem(movies[pos])
    }

    inner class MoviesListObserver : DisposableObserver<List<Movie>>() {
        override fun onNext(t: List<Movie>?) {

            if (t != null) {

                view.hideLoadingDialog()
                view.showMovies(t)

            }
            isLoading = false
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