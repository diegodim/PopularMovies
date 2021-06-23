package com.diego.duarte.popularmovieskotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Videos(
    val id: Int,
    var results: List<Video>
    ): Parcelable
