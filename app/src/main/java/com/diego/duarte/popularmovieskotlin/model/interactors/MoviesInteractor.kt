package com.diego.duarte.popularmovieskotlin.model.interactors

import com.diego.duarte.popularmovieskotlin.model.data.Movie
import com.diego.duarte.popularmovieskotlin.api.ApiService
import com.diego.duarte.popularmovieskotlin.api.RetrofitBuilder
import com.diego.duarte.popularmovieskotlin.model.data.Movies
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import kotlin.collections.ArrayList

class MoviesInteractor{


    fun getPopularMovies(page: Int, observer: DisposableObserver<ArrayList<Movie>>) {
        val client = RetrofitBuilder().buildRetrofit().create(ApiService::class.java)
        val request =  client.getPopularMovies(page)
        buildMovies(request, observer)

    }

    fun getTopMovies(page: Int, observer: DisposableObserver<ArrayList<Movie>>) {
        val client = RetrofitBuilder().buildRetrofit().create(ApiService::class.java)
        val request =  client.getTopMovies(page)
        buildMovies(request, observer)

    }

    private fun buildMovies(request: Observable<Response<Movies>>, observer: DisposableObserver<ArrayList<Movie>>)
    {
        request.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe(
            {
                if (it.isSuccessful) {
                    val results = it.body()!!.results as ArrayList<Movie>
                    observer.onNext(results)
                }

            },          // onNext
            {
                observer.onError(it)
            }, // onError
            { println("Complete") }   // onComplete
        )
    }
}