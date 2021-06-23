package com.diego.duarte.popularmovieskotlin.movies

import androidx.recyclerview.widget.GridLayoutManager
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.movies.view.MovieItemView
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MoviesPresenter (private val model: MoviesModel, private val view: MoviesView) : BasePresenter() {

    private var navigation = 0
    //private var page: Int = 1
    private var isLoading = false
    private var movies = Movies(1, ArrayList(), 0,0)
    private lateinit var getMovies: Disposable

    val itemCount: Int get() = movies.results.size


    fun getPopularMovies() {
        navigation = 0
        isLoading = true
        getMovies = model.getPopularMovies(movies.page, MoviesListObserver())!!
    }

    fun getTopMovies() {
        navigation = 1
        isLoading = true
        getMovies = model.getTopMovies(movies.page, MoviesListObserver())!!
    }

    fun firstPage(){
        movies = Movies(1, ArrayList(), 0,0)
        view.showLoadingDialog()
    }

    fun getNextMoviesPage(layoutManager: GridLayoutManager) {

        if(!isLoading){
                if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 11) {
                    movies.page++
                    if (navigation == 0)
                        getPopularMovies()
                    if (navigation == 1)
                        getTopMovies()
                }
            }
    }

    fun setList(listMovies: Movies) {
        movies.results += listMovies.results
        movies.page = listMovies.page

    }

    fun onRetryClicked()
    {
        view.showLoadingDialog()
        if(navigation == 0)
            getPopularMovies()
        if(navigation == 1)
            getTopMovies()

    }

    fun onMovieClicked(pos: Int) {
        view.showMovie(movies.results[pos])

    }

    fun onBindItemView(itemView: MovieItemView, pos: Int) {
        itemView.bindItem(movies.results[pos])
    }

    inner class MoviesListObserver : DisposableObserver<Movies>() {
        override fun onNext(t: Movies?) {

            if (t != null) {


                view.showMovies(t)
                view.hideLoadingDialog()

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