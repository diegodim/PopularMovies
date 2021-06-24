package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieModel(private val repository: MoviesRepository, val movie: Movie) {

    fun getVideos(
        id: Int,
        observer: DisposableObserver<Videos>
    ): @NonNull Disposable? {

        return repository.getMovieVideos(id)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    if (it.isSuccessful) {
                        observer.onNext(it.body())
                    }

                },          // onNext
                {
                    observer.onError(it)
                    println("Error:"+it.message.toString())
                }, // onError
                {
                    println("Complete")
                    observer.onComplete()
                }   // onComplete
            )
    }

    fun setFavoriteMovie(movie: Movie, observer: DisposableObserver<Boolean>):
            @NonNull Disposable? {
        return repository.saveMovieAsFavorite(movie)?.subscribeOn(AndroidSchedulers.mainThread())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                {

                    observer.onNext(it)},          // onNext
                {
                    observer.onError(it)

                }, // onError
                {

                    observer.onComplete()
                }   // onComplete)
            )
    }

    fun getMovieIntent(): Movie {
        return movie
    }


}