package com.diego.duarte.popularmovieskotlin.base

import io.reactivex.rxjava3.observers.DisposableObserver

open class BaseObserver<T>: DisposableObserver<T>() {
    override fun onNext(t: T) {
        // no-op by default.
    }

    override fun onError(e: Throwable?) {
        // no-op by default.
    }

    override fun onComplete() {
        // no-op by default.
    }
}