package com.diego.duarte.popularmovieskotlin.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.models.Movie

class MoviesAdapter(private val movies: ArrayList<Movie>) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val posterView: ImageView

        init {
            posterView = itemView.findViewById(R.id.movie_image_poster)
        }
        fun bind(movie: Movie){
            Glide
                .with(itemView.context)
                .load(itemView.context.getString(R.string.api_image_url) + movie.poster_path)
                .into(posterView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.MoviesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(movies[position])

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addItem(movie: Movie){
        movies.add(movie)
        notifyItemInserted(itemCount)
    }
}