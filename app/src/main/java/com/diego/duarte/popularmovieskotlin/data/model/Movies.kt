package com.diego.duarte.popularmovieskotlin.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class Movies(
    var page: Int,
    var results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
