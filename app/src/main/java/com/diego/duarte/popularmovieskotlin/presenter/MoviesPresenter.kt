package com.diego.duarte.popularmovieskotlin.presenter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.model.data.Movie
import com.diego.duarte.popularmovieskotlin.model.interactors.MoviesInteractor
import com.diego.duarte.popularmovieskotlin.view.fragments.movies.MoviesView

class MoviesPresenter (private val interator: MoviesInteractor, private val view: MoviesView) : BasePresenter() {

    //lateinit var view: MoviesContract.View
    //private val interactor = MoviesInterator()
    private var page: Int = 1
    private var isLoading = false

    fun getMovies() {
        isLoading = true
        val successfulCallback: (ArrayList<Movie>) -> Unit = {
            //view.hideLoadingDialog()
            for (movie in it) {
                view.showMovie(movie)
            }
            isLoading = false
        }

        val failureCallback: (String) -> Unit = {
            //view.hideLoadingDialog()
            view.showError(it)
        }
        interator.requestMovies(page, successfulCallback, failureCallback)
    }

    fun getNextMoviesPage(recyclerView: RecyclerView) {
        val layoutManager: GridLayoutManager = recyclerView.layoutManager as GridLayoutManager

        if(!isLoading)
        if ( layoutManager.findLastVisibleItemPosition() == (layoutManager!!.itemCount -1)) {
            page++
            getMovies()

        }
    }

}