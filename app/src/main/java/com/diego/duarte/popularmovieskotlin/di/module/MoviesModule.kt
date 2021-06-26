package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesActivity
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @MoviesScope
    @Provides
    fun provideView(activity: MoviesActivity) = activity as MoviesContract.View

    @MoviesScope
    @Provides
    fun providePresenter(repository: MoviesRepository, view: MoviesContract.View)
    = MoviesPresenter(repository, view) as MoviesContract.Presenter

}