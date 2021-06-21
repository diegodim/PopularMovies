package com.diego.duarte.popularmovieskotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: Int,
    val key: String,
    val name: String,
    val site: String
): Parcelable
