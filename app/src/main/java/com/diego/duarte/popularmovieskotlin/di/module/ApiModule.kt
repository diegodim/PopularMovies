package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideMoviesRepository() = MoviesRepository() as Repository
}