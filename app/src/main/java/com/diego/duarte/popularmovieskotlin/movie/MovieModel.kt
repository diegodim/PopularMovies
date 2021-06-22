package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieModel(private val repository: MoviesRepository, val movie: Movie) {

    fun getVideos(
        id: Int,
        observer: DisposableObserver<List<Video>>
    ): @NonNull Disposable? {

        return repository.getMovieVideos(id)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    if (it.isSuccessful) {
                        observer.onNext(it.body()!!.results)
                    }

                },          // onNext
                {
                    observer.onError(it)
                    println("Error:"+it.message.toString())
                }, // onError
                { println("Complete") }   // onComplete
            )
    }

    fun getMovieIntent(): Movie {
        return movie;
    }


}