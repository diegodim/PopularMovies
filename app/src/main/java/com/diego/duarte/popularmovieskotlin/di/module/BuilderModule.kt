package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.di.scope.MovieScope
import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.movie.view.MovieActivity
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @MoviesScope
    @ContributesAndroidInjector(modules = [MoviesModule::class])
    abstract fun bindMoviesActivity(): MoviesActivity

    @MovieScope
    @ContributesAndroidInjector(modules = [MovieModule::class])
    abstract fun bindMovieActivity(): MovieActivity

}