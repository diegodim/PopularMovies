package com.diego.duarte.popularmovieskotlin.movie.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
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
import com.google.android.material.appbar.AppBarLayout
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
    private lateinit var viewError: View
    private lateinit var viewLoading: View
    private lateinit var buttonError: Button
    private lateinit var textError: TextView
    private lateinit var appBar: AppBarLayout
    private lateinit var layout: NestedScrollView
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

        layout = findViewById(R.id.movie_layout)
        appBar = findViewById(R.id.movie_app_bar)
        viewError = findViewById(R.id.view_error_layout)
        viewLoading = findViewById(R.id.view_loading_layout)
        textError = findViewById(R.id.view_error_txt_cause)
        buttonError = findViewById(R.id.view_error_btn_retry)
        buttonError.setOnClickListener { presenter.onRetryClicked() }
        showLoadingDialog()
        presenter.showMovie()

    }

    private fun initializeRecyclerView() {


        recyclerView = findViewById(R.id.movie_rv_videos)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.clear()
        recyclerView.setItemViewCacheSize(20)
        val layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = VideosAdapter(presenter)
        recyclerView.layoutManager = layoutManager
        recyclerView.isNestedScrollingEnabled = false

    }

    override fun showLoadingDialog() {
        viewLoading.visibility = View.VISIBLE
        appBar.setExpanded(false)
        appBar.isActivated = false
        layout.visibility = View.GONE
        viewError.visibility = View.GONE
    }

    override fun hideLoadingDialog() {
        viewLoading.visibility = View.GONE
        appBar.setExpanded(true)
        appBar.isActivated = true
        layout.visibility = View.VISIBLE
        viewError.visibility = View.GONE
    }

    override fun showError(message: String) {
        viewLoading.visibility = View.GONE
        appBar.setExpanded(false)
        appBar.isActivated = true
        layout.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        textError.text = message
    }

    override fun showMovie(movie: Movie) {
        Glide
            .with(this)
            .load(this.getString(R.string.url_tmdb_image) + movie.backdrop_path)
            .placeholder(R.color.gray)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageBackdrop)

        Glide
            .with(this)
            .load(this.getString(R.string.url_tmdb_image)  + movie.poster_path)
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
        //println("key:"+videos[0].key)
        Toast.makeText(this, videos[0].key, Toast.LENGTH_SHORT).show()
        val adapter: VideosAdapter = recyclerView.adapter as VideosAdapter
        adapter.insertItems(videos)


    }
}