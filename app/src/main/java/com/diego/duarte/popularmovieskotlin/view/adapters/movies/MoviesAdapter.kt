package com.diego.duarte.popularmovieskotlin.view.adapters.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.model.data.Movie

class MoviesAdapter() : RecyclerView.Adapter<MovieViewHolder>() {

    private val presenter = MovieViewPresenter()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        presenter.view = parent

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return MovieViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        presenter.onBindItemView(holder, position)

    }

    override fun getItemCount(): Int {
        return presenter.itemCount ?: 0
    }

    fun insertItem(movie: Movie){
        presenter.addItem(movie)
        notifyItemInserted(itemCount)
    }
}