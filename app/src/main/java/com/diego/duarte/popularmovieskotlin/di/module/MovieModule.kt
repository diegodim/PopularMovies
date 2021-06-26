package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.di.scope.MovieScope
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.diego.duarte.popularmovieskotlin.movie.view.MovieActivity
import com.diego.duarte.popularmovieskotlin.movie.MovieContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
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
    fun providePresenter(repository: MoviesRepository, movie: Movie, view: MovieContract.View)
    = MoviePresenter(repository, movie, view) as MovieContract.Presenter
}