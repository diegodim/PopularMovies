package com.diego.duarte.popularmovieskotlin.data.model

import kotlinx.android.parcel.Parcelize


data class Movies(
    var page: Int,
    var results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
