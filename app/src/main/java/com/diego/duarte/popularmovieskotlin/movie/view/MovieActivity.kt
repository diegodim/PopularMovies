package com.diego.duarte.popularmovieskotlin.movie.view

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.Downsampler
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diego.duarte.popularmovieskotlin.BuildConfig
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.android.AndroidInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MovieActivity : AppCompatActivity(), MovieView {

    companion object {
        const val INTENT_EXTRA_MOVIE = "user_movie"
    }

    @Inject
    lateinit var presenter: MoviePresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageBackdrop: ImageView
    private lateinit var imagePoster: ImageView
    private lateinit var textSynopsis: TextView
    private lateinit var textTitle: TextView
    private lateinit var textDate: TextView
    private lateinit var textScore: TextView
    private lateinit var textVotes: TextView
    private lateinit var rateScore: RatingBar
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        collapsingToolbarLayout = findViewById(R.id.movie_collapsing_toolbar)
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        initializeRecyclerView()
        imageBackdrop = findViewById(R.id.movie_image_backdrop)
        imagePoster = findViewById(R.id.movie_image_poster)
        textSynopsis = findViewById(R.id.movie_text_synopsis)
        textTitle = findViewById(R.id.movie_text_title)
        textDate = findViewById(R.id.movie_text_release_date)
        textScore = findViewById(R.id.movie_text_rating_score)
        textVotes = findViewById(R.id.movie_text_total_votes)
        rateScore = findViewById(R.id.movie_rating_score)

        presenter.showMovie()
    }

    private fun initializeRecyclerView() {


        recyclerView = findViewById(R.id.movie_rv_videos)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = VideosAdapter(presenter)



    }

    override fun showLoadingDialog() {
        TODO("Not yet implemented")
    }

    override fun hideLoadingDialog() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showMovie(movie: Movie) {
        Glide
            .with(this)
            .load(BuildConfig.TMDB_IMAGE_URL + movie.backdrop_path)
            .placeholder(R.color.white)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageBackdrop)

        Glide
            .with(this)
            .load(BuildConfig.TMDB_IMAGE_URL + movie.poster_path)
            .placeholder(R.drawable.movie_placeholder)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imagePoster)
        rateScore.rating = movie.vote_average/2
        textSynopsis.text = movie.overview
        collapsingToolbarLayout.title = movie.title
        textTitle.text = movie.title
        textScore.text = movie.vote_average.toString()
        textVotes.text = movie.vote_count.toString()
        textDate.text = SimpleDateFormat("dd/MM/yyyy", Locale("pt-br", "America/Sao_Paulo"))
            .format(movie.release_date)
    }

    override fun showVideos(videos: List<Video>) {
        println("key:"+videos[0].key)
        Toast.makeText(this, videos[0].key, Toast.LENGTH_SHORT).show()
        val adapter: VideosAdapter = recyclerView.adapter as VideosAdapter
        adapter.insertItems(videos)


    }
}