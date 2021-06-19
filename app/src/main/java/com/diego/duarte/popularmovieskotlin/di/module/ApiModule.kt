package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.di.scope.MoviesScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @MoviesScope
    @Provides
    fun provideMoviesClient(): MoviesRepository = MoviesRepository()
}