package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.movies.MoviesModel
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesActivity
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @MoviesScope
    @Provides
    fun provideModel(repository: MoviesRepository) = MoviesModel(repository)

    @MoviesScope
    @Provides
    fun provideView(activity: MoviesActivity) = activity as MoviesView

    @MoviesScope
    @Provides
    fun providePresenter(model: MoviesModel, view: MoviesView) = MoviesPresenter(model, view)

}