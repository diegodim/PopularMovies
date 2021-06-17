package com.diego.duarte.popularmovieskotlin.fragments.movies

import com.diego.duarte.popularmovieskotlin.models.Movie
import com.diego.duarte.popularmovieskotlin.models.Movies
import com.diego.duarte.popularmovieskotlin.network.api.ApiService
import com.diego.duarte.popularmovieskotlin.network.api.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class MoviesInteractor: MoviesContract.Interactor {


    override fun requestMovies(page: Int, isSuccessful: (ArrayList<Movie>) -> Unit, isFailure: (String) -> Unit) {
        val request = RetrofitBuilder().buildRetrofit().create(ApiService::class.java)
        request
            .getMovies(page)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    if (it.isSuccessful) {
                        val results = it.body()!!.results as ArrayList<Movie>
                        isSuccessful(results)

                    }

                },          // onNext
                {
                    isFailure(it.message.toString())
                }, // onError
                { println("Complete") }   // onComplete
            )
    }
}