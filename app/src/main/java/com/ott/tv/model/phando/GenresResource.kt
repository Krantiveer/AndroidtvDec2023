package com.ott.tv.model.phando


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Vikas Kumar Singh on 20/04/20.
 */
@Parcelize
data class GenresResource(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("poster_orientation")
    val posterOrientation: String? = null
):Parcelable