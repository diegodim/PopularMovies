package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository

class MovieModel(val repository: MoviesRepository, val movie: Movie) {

    fun getMovieIntent(): Movie {
        return movie;
    }
}