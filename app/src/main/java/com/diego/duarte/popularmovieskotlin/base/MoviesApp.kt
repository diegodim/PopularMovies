package com.diego.duarte.popularmovieskotlin.base

import android.app.Application
import com.diego.duarte.popularmovieskotlin.di.component.AppComponent
import com.diego.duarte.popularmovieskotlin.di.component.DaggerAppComponent
import com.diego.duarte.popularmovieskotlin.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MoviesApp: DaggerApplication() {

    lateinit var injector: AndroidInjector<out DaggerApplication>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return injector
    }

    override fun onCreate() {
        injector = DaggerAppComponent.builder().application(this).build()
        super.onCreate()


    }
}