package com.diego.duarte.popularmovieskotlin.data.source.local

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults


class RealmBuilder {

    fun getFavoritesMovies(): Flowable<RealmResults<Movie>>{
        //TODO
        return Flowable.create({ emitter ->
            val realm = Realm.getDefaultInstance()
            val results = realm.where(Movie::class.java).findAllAsync()

            val listener: RealmChangeListener<RealmResults<Movie>> =
                RealmChangeListener<RealmResults<Movie>> {
                    if (!emitter.isCancelled && results.isLoaded) {
                        emitter.onNext(results)

                    }
                }
            emitter.setDisposable(Disposable.fromRunnable {
                results.removeChangeListener(listener)
                realm.close()
            })
            results.addChangeListener(listener)
        }, BackpressureStrategy.LATEST)

    }

    fun saveFavoriteMovie(movie: Movie): Flowable<Boolean>{
        //TODO
        return Flowable.create({ emitter ->
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val managedMovie = realm.copyToRealm(movie) // Persist unmanaged objects
            realm.insertOrUpdate(managedMovie)
            emitter.onNext(true)
            realm.commitTransaction()
            realm.close()

        }, BackpressureStrategy.LATEST)

    }



}