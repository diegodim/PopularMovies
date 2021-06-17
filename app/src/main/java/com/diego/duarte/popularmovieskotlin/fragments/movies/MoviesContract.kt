package com.diego.duarte.popularmovieskotlin.fragments.movies

import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.models.Movie
import com.diego.duarte.popularmovieskotlin.models.Movies
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface MoviesContract {

    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showError(message: String)
        fun showMovie(movie: Movie)
    }
    interface Presenter {
        fun getMovies()
        fun getNextMoviesPage(recyclerView: RecyclerView)
    }

    interface Interactor {
        fun requestMovies(
            page: Int,
            isSuccessful: (ArrayList<Movie>) -> Unit,
            isFailure: (String) -> Unit
        )
    }


}