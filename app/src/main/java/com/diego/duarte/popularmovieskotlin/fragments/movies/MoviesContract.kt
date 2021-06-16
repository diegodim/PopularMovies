package com.diego.duarte.popularmovieskotlin.fragments.movies

import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.models.Movie

interface MoviesContract {

    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showError(message: String)
        fun showMovies(movies: ArrayList<Movie>)
    }
    interface Presenter {
        fun loadMovies()
        fun nextPage(recyclerView: RecyclerView)
    }

}