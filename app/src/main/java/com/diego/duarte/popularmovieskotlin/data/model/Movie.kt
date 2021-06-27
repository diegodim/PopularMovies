package com.diego.duarte.popularmovieskotlin.data.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
open class Movie(
    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var overview: String? = null,
    var release_date: Date? = null,
    var poster_path: String? = null,
    var backdrop_path: String? = null,
    var vote_average: Float? = null,
    var vote_count: Int? = null,
    var isFavorite: Boolean = false
    ): Parcelable, RealmObject()

