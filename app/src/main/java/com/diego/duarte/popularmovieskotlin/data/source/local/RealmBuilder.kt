package com.diego.duarte.popularmovieskotlin.data.source.local

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import io.reactivex.rxjava3.core.Observable
import io.realm.Realm
import io.realm.RealmResults


class RealmBuilder {

    fun getFavoritesMovies(): Observable<RealmResults<Movie>>{

        return Observable.create { emitter ->
            val realm = Realm.getDefaultInstance()
            val results = realm.where(Movie::class.java).findAllAsync()

            emitter.onNext(results.freeze())

        }

    }

    fun saveFavoriteMovie(movie: Movie): Observable<Boolean>{

        return Observable.create{ emitter ->
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.executeTransactionAsync{
                it.insertOrUpdate(movie)
                emitter.onNext(true)
            }
            realm.commitTransaction()
            realm.close()

        }

    }



}