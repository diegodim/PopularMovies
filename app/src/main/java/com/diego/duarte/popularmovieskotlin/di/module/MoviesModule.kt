package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesFragment
import com.diego.duarte.popularmovieskotlin.movies.model.MoviesModel
import com.diego.duarte.popularmovieskotlin.movies.presenter.MoviesPresenter
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
    fun provideView(fragment: MoviesFragment) = fragment as MoviesView

    @MoviesScope
    @Provides
    fun providePresenter(model: MoviesModel, view: MoviesView) = MoviesPresenter(model, view)

}