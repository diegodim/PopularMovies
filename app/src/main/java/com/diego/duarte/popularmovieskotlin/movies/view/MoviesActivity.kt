package com.diego.duarte.popularmovieskotlin.movies.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movie.view.MovieActivity
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import javax.inject.Inject


class MoviesActivity : AppCompatActivity(), MoviesView,
    BottomNavigationView.OnNavigationItemSelectedListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var viewError: View
    private lateinit var viewLoading: View
    private lateinit var buttonError: Button
    private lateinit var textError: TextView

    @Inject
    lateinit var presenter: MoviesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(findViewById(R.id.movies_toolbar))
        initializeRecyclerView()
        bottomNavigation = findViewById(R.id.movie_nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        viewError = findViewById(R.id.view_error_layout)
        viewLoading = findViewById(R.id.view_loading_layout)
        textError = findViewById(R.id.view_error_txt_cause)
        buttonError = findViewById(R.id.view_error_btn_retry)
        buttonError.setOnClickListener { presenter.onRetryClicked() }
        showLoadingDialog()
        presenter.getPopularMovies()
    }


    private fun initializeRecyclerView() {


        recyclerView = findViewById(R.id.movies_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.clear()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.adapter = MoviesAdapter(presenter)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                presenter.getNextMoviesPage(recyclerView.layoutManager as GridLayoutManager)
            }
        })


    }

    override fun showLoadingDialog() {
        viewLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        viewError.visibility = View.GONE

    }

    override fun hideLoadingDialog() {
        viewLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        viewError.visibility = View.GONE
    }

    override fun showError(message: String) {
        viewLoading.visibility = View.GONE
        recyclerView.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        textError.text = message
    }

    override fun showMovies(movies: List<Movie>) {

        val adapter: MoviesAdapter = recyclerView.adapter as MoviesAdapter
        adapter.insertItems(movies)

    }

    override fun showMovie(movie: Movie) {
        //Toast.makeText(this, movie.title, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(MovieActivity.INTENT_EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.most_popular -> {

                presenter.firstPage()
                bottomNavigation.menu.getItem(2).isChecked = true
                initializeRecyclerView()
                presenter.getPopularMovies()

            }
            R.id.top_rated -> {
                presenter.firstPage()
                bottomNavigation.menu.getItem(1).isChecked = true
                initializeRecyclerView()
                presenter.getTopMovies()

            }
            else -> return false
        }
        return true
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }



}