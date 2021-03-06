package com.diego.duarte.popularmovieskotlin.data.source.local

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import io.reactivex.rxjava3.core.Observable
import io.realm.Realm



class RealmBuilder {

    fun getFavoritesMovies(): Observable<List<Movie>>{

        return Observable.create { emitter ->
            val realm = Realm.getDefaultInstance()
            val results = realm.where(Movie::class.java).findAll()
            emitter.onNext(realm.copyFromRealm(results))
            emitter.onComplete()
        }

    }

    fun setFavoriteMovie(movie: Movie): Observable<Movie>{

        return Observable.create{ emitter ->
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.insertOrUpdate(movie)
            realm.commitTransaction()
            realm.close()
            emitter.onNext(movie)
            emitter.onComplete()
        }

    }

    fun deleteFavoriteMovie(movie: Movie): Observable<Movie>{

        return Observable.create { emitter ->
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            realm.where(Movie::class.java)
                .equalTo("id", movie.id)
                .findFirst()?.deleteFromRealm()
            realm.commitTransaction()
            realm.close()
            emitter.onNext(movie)
            emitter.onComplete()
        }

    }

    fun getFavoriteMovie(movie: Movie): Observable<Movie>{

        return Observable.create { emitter ->
            val realm = Realm.getDefaultInstance()

            val results = realm.where(Movie::class.java)
                .equalTo("id", movie.id)
                .findFirst()
            if(results != null)
                emitter.onNext(realm.copyFromRealm(results))
            emitter.onComplete()
        }

    }

}