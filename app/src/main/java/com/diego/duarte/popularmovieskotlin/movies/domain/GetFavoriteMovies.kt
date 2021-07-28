package com.diego.duarte.popularmovieskotlin.movies.domain

import com.diego.duarte.popularmovieskotlin.base.UseCase
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler


class GetFavoriteMovies(val repository: Repository,
                         mThreadExecutor: Scheduler,
                         mPostExecutionThread: Scheduler):
    UseCase<List<Movie>,
            GetFavoriteMovies.Params>(mThreadExecutor, mPostExecutionThread) {

    data class Params constructor(val page: Int)

    override fun buildUseCaseObservable(params: Params?): Observable<List<Movie>> {
        return repository.getMoviesByFavorite()
    }
}