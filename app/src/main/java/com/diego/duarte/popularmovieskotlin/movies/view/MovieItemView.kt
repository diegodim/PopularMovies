package com.diego.duarte.popularmovieskotlin.movies.view

import com.diego.duarte.popularmovieskotlin.data.model.Movie

interface MovieItemView {

    fun bindItem(movie: Movie)


}