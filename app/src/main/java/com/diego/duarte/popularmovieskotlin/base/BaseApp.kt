package com.diego.duarte.popularmovieskotlin.base

import com.diego.duarte.popularmovieskotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm

class BaseApp: DaggerApplication() {

    private lateinit var injector: AndroidInjector<out DaggerApplication>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return injector
    }

    override fun onCreate() {
        Realm.init(this)
        injector = DaggerAppComponent.builder().application(this).build()
        super.onCreate()


    }
}