package com.ott.tv.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class MediaplabackApiResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("mediaCode")
    val mediaCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("purchase_option")
    val purchaseOption: List<PurchaseOption>,
    @SerializedName("status")
    val status: String
)

data class Data(
    @SerializedName("actors")
    val actors: List<Any>,
    @SerializedName("ad_url_desktop")
    val adUrlDesktop: String,
    @SerializedName("ad_url_mobile")
    val adUrlMobile: String,
    @SerializedName("ad_url_mobile_app")
    val adUrlMobileApp: String,
    @SerializedName("analytics_category_id")
    val analyticsCategoryId: String,
    @SerializedName("audio_language")
    val audioLanguage: List<String>,
    @SerializedName("can_share")
    val canShare: Int,
    @SerializedName("cc_files")
    val ccFiles: List<Any>,
    @SerializedName("created_by")
    val createdBy: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("directors")
    val directors: List<Any>,
    @SerializedName("document_media_id")
    val documentMediaId: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("duration_str")
    val durationStr: String,
    @SerializedName("episodes")
    val episodes: List<Any>,
    @SerializedName("ga_advance")
    val gaAdvance: Boolean,
    @SerializedName("ga_code")
    val gaCode: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("intro")
    val intro: Any,
    @SerializedName("is_dislike")
    val isDislike: Int,
    @SerializedName("is_free")
    val isFree: Int,
    @SerializedName("is_like")
    val isLike: Int,
    @SerializedName("is_like_dislike")
    val isLikeDislike: Int,
    @SerializedName("is_live")
    val isLive: Int,
    @SerializedName("is_watchlist")
    val isWatchlist: Int,
    @SerializedName("is_wishlist")
    val isWishlist: Int,
    @SerializedName("last_watch_time")
    val lastWatchTime: Int,
    @SerializedName("maturity_rating")
    val maturityRating: String,
    @SerializedName("maturity_rating_tags")
    val maturityRatingTags: Any,
    @SerializedName("media_id")
    val mediaId: String,
    @SerializedName("media_reference_type")
    val mediaReferenceType: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("next_media")
    val nextMedia: Any,
    @SerializedName("other_credits")
    val otherCredits: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("poster")
    val poster: String,
    @SerializedName("poster_vertical")
    val posterVertical: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("related")
    val related: List<Related>,
    @SerializedName("released")
    val released: String,
    @SerializedName("series")
    val series: Any,
    @SerializedName("share_url")
    val shareUrl: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("thumbnail_vertical")
    val thumbnailVertical: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("trailer_id")
    val trailerId: Int,
    @SerializedName("trailers")
    val trailers: List<Any>,
    @SerializedName("type")
    val type: String,
    @SerializedName("vast_url")
    val vastUrl: String
)

data class PurchaseOption(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("currency_symbol")
    val currencySymbol: String,
    @SerializedName("discount_percentage")
    val discountPercentage: Int,
    @SerializedName("final_points")
    val finalPoints: Int,
    @SerializedName("final_price")
    val finalPrice: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("payment_info")
    val paymentInfo: PaymentInfo,
    @SerializedName("value")
    val value: Int
)

data class AdsWaterfall(
    @SerializedName("adWaterfall_desktop")
    val adWaterfallDesktop: List<Any>,
    @SerializedName("adWaterfall_mobile")
    val adWaterfallMobile: List<Any>,
    @SerializedName("is_waterfall_mobile_desktop")
    val isWaterfallMobileDesktop: Int
)

data class Related(
    @SerializedName("circular_thumbnail")
    val circularThumbnail: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("genres_resource")
    val genresResource: List<GenresResource>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("is_free")
    val isFree: Int,
    @SerializedName("is_live")
    val isLive: Int,
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("last_watch_time")
    val lastWatchTime: Int,
    @SerializedName("maturity_rating")
    val maturityRating: String,
    @SerializedName("phando_media_id")
    val phandoMediaId: String,
    @SerializedName("poster")
    val poster: String,
    @SerializedName("poster_vertical")
    val posterVertical: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("publish_year")
    val publishYear: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("thumbnail_vertical")
    val thumbnailVertical: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)

data class GenresResource(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_orientation")
    val posterOrientation: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(posterOrientation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenresResource> {
        override fun createFromParcel(parcel: Parcel): GenresResource {
            return GenresResource(parcel)
        }

        override fun newArray(size: Int): Array<GenresResource?> {
            return arrayOfNulls(size)
        }
    }
}

data class PaymentInfo(
    @SerializedName("media_id")
    val mediaId: Int,
    @SerializedName("payment_type")
    val paymentType: String,
    @SerializedName("type")
    val type: String
)

