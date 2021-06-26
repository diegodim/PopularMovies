package com.diego.duarte.popularmovieskotlin.movies.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.base.BaseActivity
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movie.view.MovieActivity
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class MoviesActivity : BaseActivity(), MoviesContract.View,
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var page = 1
    private var navigation = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView

    companion object {
        const val INTENT_MOVIES_LIST = "movies_list"
        const val INTENT_NAVIGATION = "navigation"
        const val INTENT_PAGE = "movies_page"
    }

    @Inject
    lateinit var presenter: MoviesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_movies)

        setSupportActionBar(findViewById(R.id.movies_toolbar))
        bottomNavigation = findViewById(R.id.movie_nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        initializeRecyclerView()

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            val adapter: MoviesAdapter = recyclerView.adapter as MoviesAdapter
            adapter.insertItems(savedInstanceState.getParcelableArrayList(INTENT_MOVIES_LIST)!!)
            navigation = savedInstanceState.getInt(INTENT_NAVIGATION)
            page = savedInstanceState.getInt(INTENT_PAGE)
            hideLoadingDialog()

        }
        else {
            showLoadingDialog()
            page = 1
            presenter.getPopularMovies(page)
        }
    }

    override fun retryClick() {
        showLoadingDialog()
        if(navigation == 0)
            presenter.getPopularMovies(page)
        if(navigation == 1)
            presenter.getTopMovies(page)
        if(navigation == 2)
            presenter.getFavoriteMovies()
    }

    override fun getContent(): Int {
        return R.layout.activity_movies
    }

    override fun getPresenter(): BasePresenter {
        return presenter as MoviesPresenter
    }


    private fun initializeRecyclerView() {


        recyclerView = findViewById(R.id.movies_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.clear()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setItemViewCacheSize(4)

        recyclerView.adapter = MoviesAdapter(this)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 11) {
                    page = presenter.loadNextPage(navigation, page)
                }
            }
        })
    }

    private fun showLoadingDialog() {

        recyclerView.visibility = View.GONE
        showLoading()

    }

    override fun hideLoadingDialog() {
        hideLoading()
        recyclerView.visibility = View.VISIBLE

    }

    override fun showError(message: String) {

        recyclerView.visibility = View.GONE
        onError(message)
    }

    override fun showMovies(movies: List<Movie>) {

        val adapter: MoviesAdapter = recyclerView.adapter as MoviesAdapter
        adapter.insertItems(movies)


    }

    override fun onMovieClicked(movie: Movie) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(MovieActivity.INTENT_EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        recyclerView.adapter = MoviesAdapter(this)
        presenter.onCreate()
        showLoadingDialog()
        page = 1
        when (item.itemId) {
            R.id.most_popular -> {
                navigation = 0

                bottomNavigation.menu.getItem(navigation).isChecked = true
                presenter.getPopularMovies(page)

            }
            R.id.top_rated -> {
                navigation = 1
                bottomNavigation.menu.getItem(navigation).isChecked = true
                presenter.getTopMovies(page)

            }
            R.id.favorite -> {
                navigation = 2
                bottomNavigation.menu.getItem(navigation).isChecked = true
                presenter.getFavoriteMovies()

            }
            else -> return false
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(INTENT_MOVIES_LIST,
            ArrayList((recyclerView.adapter as MoviesAdapter).getList()))
        outState.putInt(INTENT_NAVIGATION, navigation)
        outState.putInt(INTENT_PAGE, page)
        super.onSaveInstanceState(outState)
    }

}