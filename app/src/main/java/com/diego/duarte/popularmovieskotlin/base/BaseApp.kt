package com.diego.duarte.popularmovieskotlin.base

import com.diego.duarte.popularmovieskotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApp: DaggerApplication() {

    private lateinit var injector: AndroidInjector<out DaggerApplication>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return injector
    }

    override fun onCreate() {

        injector = DaggerAppComponent.builder().application(this).build()
        Realm.init(this)
        buildRealm()
        super.onCreate()
    }

    private fun buildRealm() {
        val config =  RealmConfiguration.Builder()
            .name("movies.realm")
            .build()
        Realm.getInstance(config)
    }
}