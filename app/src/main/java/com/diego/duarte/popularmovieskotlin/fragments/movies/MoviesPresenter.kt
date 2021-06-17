package com.diego.duarte.popularmovieskotlin.fragments.movies

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.models.Movie

class MoviesPresenter : MoviesContract.Presenter {

    lateinit var view: MoviesContract.View
    private val interactor = MoviesInteractor()
    private var page: Int = 1

    override fun getMovies() {

        val successfulCallback: (ArrayList<Movie>) -> Unit = {
            //view.hideLoadingDialog()
            for (movie in it) {
                view.showMovie(movie)
            }
        }

        val failureCallback: (String) -> Unit = {
            //view.hideLoadingDialog()
            view.showError(it)
        }
        interactor.requestMovies(page, successfulCallback, failureCallback)
    }

    override fun getNextMoviesPage(recyclerView: RecyclerView) {
        val layoutManager: GridLayoutManager = recyclerView.layoutManager as GridLayoutManager

        if ( layoutManager.findLastVisibleItemPosition() == (layoutManager!!.itemCount -1)) {
            page++
            getMovies()

        }
    }

}