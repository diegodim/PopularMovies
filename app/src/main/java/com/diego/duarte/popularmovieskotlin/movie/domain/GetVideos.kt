package com.diego.duarte.popularmovieskotlin.movie.domain

import com.diego.duarte.popularmovieskotlin.base.UseCase
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import retrofit2.Response

class GetVideos(val repository: Repository,
                mThreadExecutor: Scheduler,
                mPostExecutionThread: Scheduler
):
    UseCase<Response<Videos>,
            GetVideos.Params>(mThreadExecutor, mPostExecutionThread) {

    data class Params constructor(val id: Int)

    override fun buildUseCaseObservable(params: Params?): Observable<Response<Videos>> {
        return repository.getMovieVideos(params!!.id)
    }
}