package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.util.schedulers.AppSchedulerProvider
import com.diego.duarte.popularmovieskotlin.util.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Singleton
    @Provides
    fun provideSchedulers() = AppSchedulerProvider() as SchedulerProvider
}