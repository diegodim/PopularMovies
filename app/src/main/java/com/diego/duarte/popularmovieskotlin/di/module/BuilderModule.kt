package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @MoviesScope
    @ContributesAndroidInjector(modules = [MoviesModule::class])
    abstract fun bindMoviesFragment(): MoviesFragment

}