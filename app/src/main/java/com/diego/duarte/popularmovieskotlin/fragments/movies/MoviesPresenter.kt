package com.diego.duarte.popularmovieskotlin.fragments.movies

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.models.Movie
import com.diego.duarte.popularmovieskotlin.models.Movies
import com.diego.duarte.popularmovieskotlin.network.api.ApiService
import com.diego.duarte.popularmovieskotlin.network.api.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class MoviesPresenter : MoviesContract.Presenter {

    lateinit var view: MoviesContract.View
    private var page: Int = 1

    override fun loadMovies() {
        val client = buildMovies()
        client?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    if (it.isSuccessful) {
                        val results = it.body()!!.results as ArrayList<Movie>
                        view.showMovies(results)

                    }

                },          // onNext
                { e -> println("Erro") }, // onError
                { println("Complete") }   // onComplete
            )
    }

    fun buildMovies(): @NonNull Observable<Response<Movies>>? {

        val request = RetrofitBuilder().buildRetrofit().create(ApiService::class.java)
        return request.getMovies(page)

    }

    override fun nextPage(recyclerView: RecyclerView) {
        val layoutManager: GridLayoutManager = recyclerView.layoutManager as GridLayoutManager

        if ( layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager!!.itemCount - 1) {
            page++
            loadMovies()

        }
    }
}