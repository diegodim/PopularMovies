package com.diego.duarte.popularmovieskotlin.view.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.model.data.Movie
import com.diego.duarte.popularmovieskotlin.presenter.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.view.adapters.movies.MoviesAdapter
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment(), MoviesView {


    private lateinit var recyclerView: RecyclerView

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
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeRecyclerView(view)
        presenter.getMovies()

    }

    private fun initializeRecyclerView(view: View) {


        recyclerView = view.findViewById(R.id.movies_recycler)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        recyclerView.adapter = MoviesAdapter()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                presenter.getNextMoviesPage(recyclerView)
            }
        })


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
        val adapter: MoviesAdapter = recyclerView.adapter as MoviesAdapter

        adapter.insertItem(movie)

    }


}