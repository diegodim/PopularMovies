package com.diego.duarte.popularmovieskotlin.movies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diego.duarte.popularmovieskotlin.BuildConfig
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movies.presenter.MoviesPresenter

class MoviesAdapter(private val presenter: MoviesPresenter) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), MovieItemView {

        private val posterView: ImageView

        init {
            posterView = itemView.findViewById(R.id.movie_image_poster)
            itemView.setOnClickListener {
                presenter.onItemClicked(adapterPosition)
            }
        }

        override fun bindItem(movie: Movie) {
            Glide
                .with(itemView.context)
                .load(BuildConfig.TMDB_IMAGE_URL + movie.poster_path)
                .placeholder(R.drawable.image_movie_placeholder)
                .into(posterView)
        }
    }
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_movie, parent, false)

        return MovieViewHolder(view)
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