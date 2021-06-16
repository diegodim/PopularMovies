package com.diego.duarte.popularmovieskotlin.fragments.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.models.Movie
import com.diego.duarte.popularmovieskotlin.views.adapters.MoviesAdapter



/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment(), MoviesContract.View {
    private lateinit var presenter: MoviesPresenter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MoviesPresenter()
        presenter.view = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movies, container, false)
        // Inflate the layout for this fragment
        initializeRecyclerView(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.loadMovies()

    }

    private fun initializeRecyclerView(view: View) {

        if (view != null) {

            recyclerView = view.findViewById(R.id.movies_recycler)

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(view.context, 2)
            recyclerView.adapter = MoviesAdapter()



            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    presenter.nextPage(recyclerView)
                }
            })
        }

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

    override fun showMovies(movies: ArrayList<Movie>) {
        val adapter: MoviesAdapter = recyclerView.adapter as MoviesAdapter
        for (movie in movies) {
            adapter.addItem(movie)
        }
    }


}