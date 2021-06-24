package com.diego.duarte.popularmovieskotlin.data.model

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val release_date: Date?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Float,
    val vote_count: Int,
    ): Parcelable, RealmObject()

