package com.diego.duarte.popularmovieskotlin.movies.domain

import com.diego.duarte.popularmovieskotlin.base.UseCase
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import retrofit2.Response

class GetTopMovies(val repository: Repository,
                   mThreadExecutor: Scheduler,
                   mPostExecutionThread: Scheduler):
    UseCase<Response<Movies>,
            GetTopMovies.Params>(mThreadExecutor, mPostExecutionThread) {

    data class Params constructor(val page: Int)

    override fun buildUseCaseObservable(params: Params?): Observable<Response<Movies>> {
        return repository.getMoviesByRating(params!!.page)
    }
}