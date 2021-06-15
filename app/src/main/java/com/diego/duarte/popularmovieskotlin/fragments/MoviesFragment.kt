package com.diego.duarte.popularmovieskotlin.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.models.Movies
import com.diego.duarte.popularmovieskotlin.network.api.ApiService
import com.diego.duarte.popularmovieskotlin.network.api.RetrofitBuilder
import com.diego.duarte.popularmovieskotlin.views.adapters.MoviesAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var _page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (container != null) {
            recyclerView = container.findViewById(R.id.movies_recycler)
            val gridLayoutManager = GridLayoutManager(container.context, 2)
            recyclerView?.apply {
                setHasFixedSize(true)
                layoutManager = gridLayoutManager
                adapter = MoviesAdapter()

            }

            loadMovies(_page)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = (gridLayoutManager.itemCount - 1)

                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == totalItemCount) {
                        _page++
                        loadMovies(_page)

                    }


                }
            })
        }
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    private fun loadMovies(page: Int) {

        val client = buildMovies(
            getString(R.string.api_url),
            getString(R.string.api_key),
            getString(R.string.api_language),
            page
        )
        client?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    if (it.isSuccessful) {
                        val adapter: MoviesAdapter = recyclerView.adapter as MoviesAdapter
                        for (result in it.body()?.results!!) {
                            adapter.addItem(result)

                        }
                    }

                },          // onNext
                { e -> println("Erro") }, // onError
                { println("Complete") }   // onComplete
            )
    }

    fun buildMovies(urlAPi: String, apiKey: String, language: String, page: Int):
            @NonNull Observable<Response<Movies>>? {

        val request = RetrofitBuilder().buildRetrofit(urlAPi)
            .create(ApiService::class.java)
        return request.getMovies(apiKey, language, page)

    }


}