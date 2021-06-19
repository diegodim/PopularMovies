package com.diego.duarte.popularmovieskotlin.movies.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.movie.view.MovieFragment
import com.diego.duarte.popularmovieskotlin.movies.presenter.MoviesPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment(), MoviesView, BottomNavigationView.OnNavigationItemSelectedListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var viewError: View
    private lateinit var viewLoading: View
    private lateinit var buttonError: Button
    private lateinit var textError: TextView

    @Inject lateinit var presenter: MoviesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        initializeRecyclerView(view)
        bottomNavigation = view.findViewById(R.id.movie_nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        viewError = view.findViewById(R.id.view_error_layout)
        viewLoading = view.findViewById(R.id.view_loading_layout)
        textError = view.findViewById(R.id.view_error_txt_cause)
        buttonError = view.findViewById(R.id.view_error_btn_retry)
        buttonError.setOnClickListener { presenter.onRetryClicked() }
        showLoadingDialog()
        presenter.getPopularMovies()
        return view
    }

    private fun initializeRecyclerView(view: View) {


        recyclerView = view.findViewById(R.id.movies_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.clear();
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
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
        Toast.makeText(activity, movie.title, Toast.LENGTH_SHORT).show()
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val fragment = MovieFragment()
        fragmentTransaction?.replace(R.id.main_fragment, fragment)
        fragmentTransaction?.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.most_popular -> {

                presenter.firstPage()
                bottomNavigation.menu.getItem(2).isChecked = true
                this.view?.let { initializeRecyclerView(it) }
                presenter.getPopularMovies()

            }
            R.id.top_rated -> {
                presenter.firstPage()
                bottomNavigation.menu.getItem(1).isChecked = true
                this.view?.let { initializeRecyclerView(it) }
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