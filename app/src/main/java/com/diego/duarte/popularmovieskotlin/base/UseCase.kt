package com.diego.duarte.popularmovieskotlin.base

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver

abstract class UseCase<T, Params>(
    private val mThreadExecutor: Scheduler,
    private val mPostExecutionThread: Scheduler
) {

    abstract fun buildUseCaseObservable(params: Params?): Observable<T>?


    fun execute(observer: DisposableObserver<T>?, params: Params?): Disposable {
        val observable: Observable<T> = buildUseCaseObservable(params)
            ?.subscribeOn(mThreadExecutor)
            ?.observeOn(mPostExecutionThread) as Observable<T>
        return observable.subscribeWith(observer!!)
    }


}