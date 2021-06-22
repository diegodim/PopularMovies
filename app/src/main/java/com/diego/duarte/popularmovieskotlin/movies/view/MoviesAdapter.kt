package com.diego.duarte.popularmovieskotlin.movies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.Downsampler
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter

class MoviesAdapter(private val presenter: MoviesPresenter) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), MovieItemView {

        private val imagePoster: ImageView = itemView.findViewById(R.id.item_image_poster)

        init {
            itemView.setOnClickListener {
                presenter.onMovieClicked(adapterPosition)
            }
        }

        override fun bindItem(movie: Movie) {
            Glide
                .with(itemView.context)
                .load(itemView.context.getString(R.string.url_tmdb_image) + movie.poster_path)
                .placeholder(R.drawable.movie_placeholder)
                .centerInside()
                .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imagePoster)
        }
        fun clearVew (){
            Glide.with(itemView.context).clear(imagePoster)
        }
    }
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        presenter.onBindItemView(holder, position)

    }

    override fun getItemCount(): Int {
        return presenter.itemCount
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        holder.clearVew()
    }

    fun insertItems(movies: List<Movie>){
        val count = this.itemCount
        presenter.setList(movies)
        notifyItemRangeInserted(count, movies.size)
    }

}