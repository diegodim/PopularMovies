package com.diego.duarte.popularmovieskotlin.view.adapters.movies

import com.diego.duarte.popularmovieskotlin.model.data.Movie

interface MovieViewContract {


    interface ItemView {
        fun bindItem(movie: Movie)
    }

    interface Presenter {

        val itemCount: Int
        fun addItem(movie: Movie)
        fun onItemClicked(pos: Int)
        fun onBindItemView(itemView: ItemView, pos: Int)
    }

}