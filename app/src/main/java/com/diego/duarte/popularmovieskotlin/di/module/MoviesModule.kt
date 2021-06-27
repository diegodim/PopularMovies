package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesActivity
import com.diego.duarte.popularmovieskotlin.util.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @MoviesScope
    @Provides
    fun provideView(activity: MoviesActivity) = activity as MoviesContract.View

    @MoviesScope
    @Provides
    fun providePresenter(repository: Repository,
                         view: MoviesContract.View,
                         schedulerProvider: SchedulerProvider)
    = MoviesPresenter(repository, view, schedulerProvider) as MoviesContract.Presenter

}