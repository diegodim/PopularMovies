package com.diego.duarte.popularmovieskotlin.movies.model

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.api.RetrofitBuilder
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import kotlin.collections.ArrayList

class MoviesModel(val repository: MoviesRepository){


    fun  getPopularMovies(page: Int, observer: DisposableObserver<List<Movie>>): @NonNull Disposable? {

        val dispose= repository.getMoviesByPopularity(page)?.subscribeOn(Schedulers.computation())
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

        return dispose
    }

    fun  getTopMovies(page: Int, observer: DisposableObserver<List<Movie>>): @NonNull Disposable? {


        val dispose= repository.getMoviesByRating(page)?.subscribeOn(Schedulers.computation())
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
        return dispose

    }

}


