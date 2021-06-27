package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.di.scope.MovieScope
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.diego.duarte.popularmovieskotlin.movie.view.MovieActivity
import com.diego.duarte.popularmovieskotlin.movie.MovieContract
import com.diego.duarte.popularmovieskotlin.util.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun provideMovie(activity: MovieActivity): Movie {
        if (!activity.intent.hasExtra(MovieActivity.INTENT_EXTRA_MOVIE)) {
            throw IllegalArgumentException("Activity is missing extra movie parameter")
        }
        return activity.intent.extras?.getParcelable(MovieActivity.INTENT_EXTRA_MOVIE)!!
    }


    @MovieScope
    @Provides
    fun provideView(activity: MovieActivity) = activity as MovieContract.View

    @MovieScope
    @Provides
    fun providePresenter(repository: Repository,
                         movie: Movie,
                         view: MovieContract.View,
                         schedulerProvider: SchedulerProvider
    )
    = MoviePresenter(repository, movie, view, schedulerProvider) as MovieContract.Presenter
}