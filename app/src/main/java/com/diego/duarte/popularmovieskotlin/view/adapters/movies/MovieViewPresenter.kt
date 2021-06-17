package com.diego.duarte.popularmovieskotlin.view.adapters.movies

import android.view.View
import android.widget.Toast
import com.diego.duarte.popularmovieskotlin.model.data.Movie

class MovieViewPresenter: MovieViewContract.Presenter {

    lateinit var view: View
    private var movies: ArrayList<Movie> = ArrayList()

    override val itemCount: Int
        get() = movies.size

    override fun addItem(movie: Movie) {
        movies.add(movie)

    }

    override fun onItemClicked(pos: Int) {
        val movie = movies[pos]
        Toast.makeText(view.context, movie.title, Toast.LENGTH_SHORT).show()
    }

    override fun onBindItemView(itemView: MovieViewContract.ItemView, pos: Int) {
        itemView.bindItem(movies[pos])
    }
}