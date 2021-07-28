package com.diego.duarte.popularmovieskotlin.base

import androidx.annotation.CallSuper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @CallSuper
    open fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}