package com.diego.duarte.popularmovieskotlin.presenter

import androidx.annotation.CallSuper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun onCreate() {
    }

    @CallSuper
    open fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}