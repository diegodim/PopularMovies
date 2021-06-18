package com.diego.duarte.popularmovieskotlin.di.component

import android.app.Application
import com.diego.duarte.popularmovieskotlin.di.module.BuilderModule
import com.diego.duarte.popularmovieskotlin.base.BaseApp
import com.diego.duarte.popularmovieskotlin.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class,
    BuilderModule::class])
interface AppComponent: AndroidInjector<BaseApp> {

    override fun inject(app: BaseApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

}