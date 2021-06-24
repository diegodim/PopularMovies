package com.diego.duarte.popularmovieskotlin.movie.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.Downsampler
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.base.BaseActivity
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MovieActivity : BaseActivity(), MovieView {

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
    private lateinit var buttonFavorite: FloatingActionButton

    private lateinit var appBar: AppBarLayout
    private lateinit var layout: NestedScrollView
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.movie_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
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
        buttonFavorite = findViewById(R.id.movie_fb_favorite)
        buttonFavorite.setOnClickListener { presenter.setFavorite() }
        layout = findViewById(R.id.movie_layout)
        appBar = findViewById(R.id.movie_app_bar)

        presenter.showMovie()

    }

    override fun retryClick() {
        presenter.showMovie()
    }

    override fun getContent(): Int {
        return R.layout.activity_movie
    }

    private fun initializeRecyclerView() {


        recyclerView = findViewById(R.id.movie_rv_videos)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.clear()
        recyclerView.setItemViewCacheSize(2)
        val layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = VideosAdapter(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.isNestedScrollingEnabled = false

    }

    override fun showLoadingDialog() {

        appBar.setExpanded(false, false)
        appBar.isActivated = false
        layout.visibility = View.GONE
        showLoading()
    }

    override fun hideLoadingDialog() {
        hideLoading()
        appBar.setExpanded(true, false)
        appBar.isActivated = true
        layout.visibility = View.VISIBLE

    }

    override fun showError(message: String) {

        appBar.setExpanded(false, false)
        appBar.isActivated = true
        layout.visibility = View.GONE
        onError(message)
    }

    override fun showMovie(movie: Movie) {
        Glide
            .with(this)
            .load(this.getString(R.string.url_tmdb_image) + movie.backdrop_path)
            .placeholder(R.color.gray)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageBackdrop)

        Glide
            .with(this)
            .load(this.getString(R.string.url_tmdb_image)  + movie.poster_path)
            .placeholder(R.drawable.movie_placeholder)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imagePoster)
        rateScore.rating = movie.vote_average!!/2
        textSynopsis.text = movie.overview
        collapsingToolbarLayout.title = movie.title
        textTitle.text = movie.title
        textScore.text = movie.vote_average.toString()
        textVotes.text = movie.vote_count.toString()
        if(movie.release_date != null)
        textDate.text = SimpleDateFormat("dd/MM/yyyy", Locale("pt-br", "America/Sao_Paulo"))
            .format(movie.release_date!!)
    }

    override fun showVideos(videos: Videos) {
        val adapter: VideosAdapter = recyclerView.adapter as VideosAdapter
        adapter.insertItems(videos)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onVideoClicked(video: Video) {
        val playVideoIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.url_youtube_video) + video.key)
        )
        val chooser = Intent.createChooser(playVideoIntent, "Open With")

        if (playVideoIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        cacheDir.deleteRecursively()
        super.onDestroy()
    }

}