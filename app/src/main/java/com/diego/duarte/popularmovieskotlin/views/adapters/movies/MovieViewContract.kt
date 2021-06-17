package com.diego.duarte.popularmovieskotlin.views.adapters.movies

import android.content.Context
import android.view.View
import com.diego.duarte.popularmovieskotlin.models.Movie

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