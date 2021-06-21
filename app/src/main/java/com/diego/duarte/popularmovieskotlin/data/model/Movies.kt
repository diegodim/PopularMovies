package com.diego.duarte.popularmovieskotlin.data.model

import kotlinx.android.parcel.Parcelize


data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
)
