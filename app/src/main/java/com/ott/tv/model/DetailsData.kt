package com.ott.tv.model


import com.google.gson.annotations.SerializedName

/**
 * Created by Vikas Kumar Singh on 22/04/20.
 */
data class DetailsData(
    @SerializedName("actors")
    val actors: List<String>? = null,
    @SerializedName("ad_url_desktop")
    val adUrlDesktop: String? = null,
    @SerializedName("ad_url_mobile")
    val adUrlMobile: String? = null,
    @SerializedName("ad_url_mobile_app")
    val adUrlMobileApp: String? = null,
    @SerializedName("audio_language")
    val audioLanguage: List<String>? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("detail")
    val detail: String? = null,
    @SerializedName("document_media_id")
    val documentMediaId: Int? = null,
    @SerializedName("is_live")
    val is_live: Int? = null,
    @SerializedName("genres")
    val genres: List<String>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_dislike")
    val isDislike: Int? = null,
    @SerializedName("is_like")
    val isLike: Int? = null,
    @SerializedName("is_like_dislike")
    val isLikeDislike: Int? = null,
    @SerializedName("is_watchlist")
    val isWatchlist: Int? = null,
    @SerializedName("is_wishlist")
    val isWishlist: Int? = null,
    @SerializedName("last_watch_time")
    val lastWatchTime: Int? = null,
    @SerializedName("maturity_rating")
    val maturityRating: String? = null,
    @SerializedName("media_id")
    val mediaId: String? = null,
    @SerializedName("media_type")
    val mediaType: String? = null,
    @SerializedName("media_url")
    val mediaUrl: String? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("poster")
    val poster: String? = null,
    @SerializedName("poster_vertical")
    val posterVertical: String? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("related")
    val related: List<ContentList>? = null,
    @SerializedName("released")
    val released: String? = null,
    @SerializedName("tags")
    val tags: String? = null,
    @SerializedName("content_type")
    val contentType: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("thumbnail_vertical")
    val thumbnailVertical: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("trailers")
    val trailers: List<ContentList>? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("vast_url")
    val vastUrl: String? = null,
    @SerializedName("url")
    val url: String? = null,
    val next_media: NextMedia? = null,
    val intro: IntroInfo? = null
)

data class NextMedia(
    val id: Int,
    val thumbnail: String,
    val type: String,
    val next_episode_start_time: Long
)

data class IntroInfo(
    val endTime: Long,
    val startTime: Long
)