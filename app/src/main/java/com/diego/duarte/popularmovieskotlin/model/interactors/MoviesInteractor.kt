package com.diego.duarte.popularmovieskotlin.model.interactors

import com.diego.duarte.popularmovieskotlin.model.data.Movie
import com.diego.duarte.popularmovieskotlin.api.ApiService
import com.diego.duarte.popularmovieskotlin.api.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesInteractor{


    fun requestMovies(page: Int, isSuccessful: (ArrayList<Movie>) -> Unit, isFailure: (String) -> Unit) {
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