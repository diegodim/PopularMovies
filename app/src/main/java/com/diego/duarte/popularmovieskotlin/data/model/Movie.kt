package com.diego.duarte.popularmovieskotlin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
    @SerializedName("release_date")
    var releaseDate: Date? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Float? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    var isFavorite: Boolean = false
    ): Parcelable, RealmObject()

