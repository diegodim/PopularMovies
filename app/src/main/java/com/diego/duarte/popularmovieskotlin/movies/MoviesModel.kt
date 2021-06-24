package com.diego.duarte.popularmovieskotlin.movies

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesModel(private val repository: MoviesRepository){


    fun getPopularMovies(
        page: Int,
        observer: DisposableObserver<Movies>
    ): @NonNull Disposable? {

        return repository.getMoviesByPopularity(page)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { if (it.isSuccessful)  observer.onNext(it.body())  }, // onNext
                { observer.onError(it) }, // onError
                { println("Complete") }   // onComplete
            )
    }

    fun getTopMovies(page: Int, observer: DisposableObserver<Movies>): @NonNull Disposable? {


        return repository.getMoviesByRating(page)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {if (it.isSuccessful) observer.onNext(it.body()) },// onNext
                {observer.onError(it) }, // onError
                { println("Complete") }   // onComplete
            )

    }

}


