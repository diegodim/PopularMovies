package com.diego.duarte.popularmovieskotlin.data.model

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
)
