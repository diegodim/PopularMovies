package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.movie.view.MovieView
import com.diego.duarte.popularmovieskotlin.movies.MoviesModel
import com.diego.duarte.popularmovieskotlin.movies.view.MoviesView

class MoviePresenter (private val model: MovieModel, private val view: MovieView) : BasePresenter(){

    fun showMovie()
    {
        view.showMovie(model.getMovieIntent())
    }
}