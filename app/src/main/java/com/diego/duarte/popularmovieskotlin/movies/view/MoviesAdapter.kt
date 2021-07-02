package com.diego.duarte.popularmovieskotlin.movies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.Downsampler
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract

class MoviesAdapter(private val moviesView: MoviesContract.View):
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var movies : List<Movie> = ArrayList()

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val imagePoster: ImageView = itemView.findViewById(R.id.item_image_poster)

        init {
            imagePoster.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        fun bindItem(movie: Movie) {
            Glide
                .with(itemView.context)
                .load(itemView.context.getString(R.string.url_tmdb_image) + movie.posterPath)
                .placeholder(R.drawable.movie_placeholder)
                .centerInside()
                .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imagePoster)
        }

        fun clearView (){
            Glide.with(itemView.context).clear(imagePoster)
        }

        override fun onClick(v: View?) {
            moviesView.onMovieClicked(movies[adapterPosition])
        }
    }
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(movies[position])

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        holder.clearView()
    }

    fun insertItems(listMovies: List<Movie>){
        val count = itemCount
        movies = movies + listMovies
        notifyItemRangeInserted(count, movies.size)
    }

    fun getList(): List<Movie> = movies

}