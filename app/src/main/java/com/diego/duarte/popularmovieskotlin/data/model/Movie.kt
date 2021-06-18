package com.diego.duarte.popularmovieskotlin.data.model

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: Date,
    val poster_path: String
    )