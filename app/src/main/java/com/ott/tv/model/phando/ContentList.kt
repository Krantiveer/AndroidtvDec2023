package com.ott.tv.model.phando

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Vikas Kumar Singh on 20/04/20.
 */
@Parcelize
data class ContentList(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("detail")
    val detail: String? = null,
    @SerializedName("duration")
    val duration: Int? = null,
    @SerializedName("genres")
    val genres: ArrayList<String>? = null,
    @SerializedName("genres_resource")
    val genresResource: ArrayList<GenresResource>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_free")
    val isFree: Int = 1,
    @SerializedName("keyword")
    val keyword: String? = null,
    @SerializedName("last_watch_time")
    val lastWatchTime: Int = 0,
    @SerializedName("is_wishlist")
    val isWishlist: Int = 0,
    @SerializedName("maturity_rating")
    val maturityRating: String? = null,
    @SerializedName("phando_media_id")
    val phandoMediaId: String? = null,
    @SerializedName("file_url")
    val fileUrl: String? = null,
    @SerializedName("media_url")
    val mediaUrl: String? = null,
    @SerializedName("is_live")
    val is_live: Int? = null,
    @SerializedName("poster")
    val poster: String? = null,

    @SerializedName("thumbnail_tv")
    val thumbnail_tv: String? = null,

    @SerializedName("poster_vertical")
    val posterVertical: String? = null,
    @SerializedName("price")
    val price: Int? = null,
//    @SerializedName("publish_year")
//    val publishYear: Int? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("thumbnail_vertical")
    val thumbnailVertical: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("audio_language")
    val audioLanguage: List<String>? = null,
    @SerializedName("episode_no")
    val episodeNo: Int? = null,
    @SerializedName("season_no")
    val seasonNo: Int? = null,
    @SerializedName("tv_series_id")
    val tvSeriesId: Int? = null
) : Parcelable
//
//@Parcelize
//data class ContentList(
//    var id: Int? = 0,
//    var title: String? = "-",
//    var type: String? = "-",
//    val circular_thumbnail: String? = "-",
//    var thumbnail: String? = "-",
//    var description: String? = "-",
//    val detail: String? = "-",
//    val duration: Int? = 0,
//    val poster: String? = "-",
//    val media_url: String? = "-",
//    val genres: List<String>? = null,
//    var is_free: Int? = null,
//    var is_wishlist: Int? = null,
//    val is_live: Int? = null,
//    val keyword: String? = "-",
//    val last_watch_time: Int? = null,
//    val maturity_rating: String? = "-",
//    val poster_vertical: String? = "-",
//    val price: Int? = null,
//    var rating: Int? = null,
//    val thumbnail_vertical: String? = "-"
//) : Parcelable
//
