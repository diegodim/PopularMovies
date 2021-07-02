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
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.movie.MovieContract
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.diego.duarte.popularmovieskotlin.util.Util
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject


class MovieActivity : BaseActivity(), MovieContract.View {

    companion object {
        const val INTENT_EXTRA_MOVIE = "movie"
    }

    @Inject
    lateinit var presenter: MovieContract.Presenter

    private lateinit var videoRecyclerView: RecyclerView
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
    private lateinit var mainLayout: NestedScrollView
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
        buttonFavorite.setOnClickListener { presenter.favorite() }
        mainLayout = findViewById(R.id.movie_layout)
        appBar = findViewById(R.id.movie_app_bar)

        presenter.getMovie()

    }

    override fun retryClick() {
        presenter.getMovie()
    }

    override fun getContent(): Int {
        return R.layout.activity_movie
    }

    override fun getPresenter(): BasePresenter {
        return presenter as MoviePresenter
    }

    private fun initializeRecyclerView() {

        videoRecyclerView = findViewById(R.id.movie_rv_videos)
        videoRecyclerView.setHasFixedSize(true)
        videoRecyclerView.recycledViewPool.clear()
        videoRecyclerView.setItemViewCacheSize(2)
        val layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        videoRecyclerView.layoutManager = layoutManager
        videoRecyclerView.adapter = VideosAdapter(this)
        videoRecyclerView.layoutManager = layoutManager
        videoRecyclerView.isNestedScrollingEnabled = false

    }

    override fun showLoadingDialog() {

        appBar.setExpanded(false)
        appBar.isActivated = false
        mainLayout.visibility = View.GONE
        showLoading()
    }

    override fun hideLoadingDialog() {
        hideLoading()
        appBar.setExpanded(true)
        appBar.isActivated = true
        mainLayout.visibility = View.VISIBLE

    }

    override fun showError(message: String) {

        appBar.setExpanded(false)
        appBar.isActivated = true
        mainLayout.visibility = View.GONE
        onError(message)
    }

    override fun showMovie(movie: Movie) {
        print(movie.isFavorite.toString())
        Glide
            .with(this)
            .load(this.getString(R.string.url_tmdb_image) + movie.backdropPath)
            .placeholder(R.color.gray)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageBackdrop)

        Glide
            .with(this)
            .load(this.getString(R.string.url_tmdb_image)  + movie.posterPath)
            .placeholder(R.drawable.movie_placeholder)
            .centerInside()
            .set(Downsampler.DECODE_FORMAT, DecodeFormat.PREFER_RGB_565)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imagePoster)

        rateScore.rating = (movie.voteAverage!! / 2)
        textSynopsis.text = movie.overview
        collapsingToolbarLayout.title = movie.title
        textTitle.text = movie.title
        textScore.text = movie.voteAverage.toString()
        textVotes.text = movie.voteCount.toString()
        textDate.text = Util().formateDate(movie.releaseDate)
    }

    override fun showVideos(videos: Videos) {
        val adapter: VideosAdapter = videoRecyclerView.adapter as VideosAdapter
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

        startActivity(chooser)

    }

    override fun showFavorite(checked: Boolean) {
        if(checked)
            buttonFavorite.setImageResource(R.drawable.ic_favorite_24)
        else
            buttonFavorite.setImageResource(R.drawable.ic_favorite_border_24)
    }

}