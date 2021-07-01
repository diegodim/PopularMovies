package com.diego.duarte.popularmovieskotlin.movie.domain

import com.diego.duarte.popularmovieskotlin.base.UseCase
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

class GetFavoriteMovie (val repository: Repository,
                        mThreadExecutor: Scheduler,
                        mPostExecutionThread: Scheduler
):
    UseCase<Movie,
            GetFavoriteMovie.Params>(mThreadExecutor, mPostExecutionThread) {

    data class Params constructor(val movie: Movie)

    override fun buildUseCaseObservable(params: Params?): Observable<Movie> {
        return repository.getMovieFromFavorite(params!!.movie)
    }
}