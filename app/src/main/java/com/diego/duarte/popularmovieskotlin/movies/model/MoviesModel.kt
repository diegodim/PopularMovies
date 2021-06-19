package com.diego.duarte.popularmovieskotlin.movies.model

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.collections.ArrayList

class MoviesModel(val repository: MoviesRepository){


    fun getPopularMovies(
        page: Int,
        observer: DisposableObserver<List<Movie>>
    ): @NonNull Disposable? {

        return repository.getMoviesByPopularity(page)?.subscribeOn(Schedulers.computation())
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

    fun getTopMovies(page: Int, observer: DisposableObserver<List<Movie>>): @NonNull Disposable? {


        return repository.getMoviesByRating(page)?.subscribeOn(Schedulers.computation())
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


