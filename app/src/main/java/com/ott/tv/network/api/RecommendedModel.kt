package com.ott.tv.network.api

import com.google.gson.annotations.SerializedName


data class RecommendedModel(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("displayType") var displayType: String? = null,
    @SerializedName("image_orientation") var imageOrientation: Int? = null,
    @SerializedName("list") var list: ArrayList<ListRecommend> = arrayListOf()

)

data class GenresResource(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster_orientation") var posterOrientation: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null

)


data class Trailers(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("duration_str") var durationStr: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("reference") var reference: String? = null,
    @SerializedName("file_url") var fileUrl: String? = null,
    @SerializedName("media_url") var mediaUrl: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("phando_media_id") var phandoMediaId: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("thumbnail_medium") var thumbnailMedium: String? = null,
    @SerializedName("thumbnail_small") var thumbnailSmall: String? = null,
    @SerializedName("thumbnail_large") var thumbnailLarge: String? = null,
    @SerializedName("share_url") var shareUrl: String? = null

)

data class ListRecommend(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("thumbnail_vertical") var thumbnailVertical: String? = null,
    @SerializedName("circular_thumbnail") var circularThumbnail: String? = null,
    @SerializedName("poster") var poster: String? = null,
    @SerializedName("poster_vertical") var posterVertical: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("is_free") var isFree: Int? = null,
    @SerializedName("is_live") var isLive: Int? = null,
    @SerializedName("is_youtube") var isYoutube: Int? = null,
    @SerializedName("phando_media_id") var phandoMediaId: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("detail") var detail: String? = null,
    @SerializedName("keyword") var keyword: String? = null,
    @SerializedName("rating") var rating: Int? = null,
    @SerializedName("youtube_url") var youtubeUrl: String? = null,
    @SerializedName("maturity_rating") var maturityRating: String? = null,
    @SerializedName("genres") var genres: ArrayList<String> = arrayListOf(),
    @SerializedName("language_str") var languageStr: String? = null,
    @SerializedName("genres_resource") var genresResource: ArrayList<GenresResource> = arrayListOf(),
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("duration_str") var durationStr: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("publish_year") var publishYear: Int? = null,
    @SerializedName("last_watch_time") var lastWatchTime: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("trailers") var trailers: ArrayList<Trailers> = arrayListOf(),
    @SerializedName("serial_no") var serialNo: String? = null

)
