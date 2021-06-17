package com.diego.duarte.popularmovieskotlin.view.adapters.movies

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.model.data.Movie

class MovieViewHolder(itemView: View, presenter: MovieViewPresenter) : RecyclerView.ViewHolder(itemView), MovieViewContract.ItemView {

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
            .load(itemView.context.getString(R.string.api_image_url) + movie.poster_path)
            .placeholder(R.color.white)
            .into(posterView)
    }
}