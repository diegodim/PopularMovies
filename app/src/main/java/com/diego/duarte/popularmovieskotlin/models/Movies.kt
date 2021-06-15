package com.diego.duarte.popularmovieskotlin.models

data class Movies(
    val page: Int,
    val results: ArrayList<Movie>,
    val total_pages: Int,
)
