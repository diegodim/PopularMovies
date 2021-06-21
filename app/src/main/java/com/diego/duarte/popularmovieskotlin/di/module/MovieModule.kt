package com.diego.duarte.popularmovieskotlin.di.module

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.di.scope.MovieScope
import com.diego.duarte.popularmovieskotlin.movie.MovieModel
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.diego.duarte.popularmovieskotlin.movie.view.MovieActivity
import com.diego.duarte.popularmovieskotlin.movie.view.MovieView
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun provideMovie(activity: MovieActivity): Movie {
        if (!activity.intent.hasExtra(MovieActivity.INTENT_EXTRA_MOVIE)) {
            throw IllegalArgumentException("Activity is missing extra user movie parameter")
        }
        return activity.intent.extras?.getParcelable(MovieActivity.INTENT_EXTRA_MOVIE)!!
    }

    @MovieScope
    @Provides
    fun provideModel(repository: MoviesRepository, movie: Movie) = MovieModel(repository, movie)

    @MovieScope
    @Provides
    fun provideView(activity: MovieActivity) = activity as MovieView

    @MovieScope
    @Provides
    fun providePresenter(model: MovieModel, view: MovieView) = MoviePresenter(model, view)
}