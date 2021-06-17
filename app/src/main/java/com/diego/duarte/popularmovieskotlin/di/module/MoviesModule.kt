package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.view.fragments.movies.MoviesFragment
import com.diego.duarte.popularmovieskotlin.model.interactors.MoviesInteractor
import com.diego.duarte.popularmovieskotlin.presenter.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.view.fragments.movies.MoviesView
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @MoviesScope
    @Provides
    fun provideModel() = MoviesInteractor()

    @MoviesScope
    @Provides
    fun provideView(fragment: MoviesFragment) = fragment as MoviesView

    @MoviesScope
    @Provides
    fun providePresenter(interactor: MoviesInteractor, view: MoviesView) = MoviesPresenter(interactor, view)

}