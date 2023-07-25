package com.ott.tv.model.home_content



import android.os.Parcel
import android.os.Parcelable

data class SubtitleDataNew(
/*
    val languageCode: String,
*/
    val language: String,
    val url: String,
    /*    val otherUrl: String,
        val mimeType: String*/
) : Parcelable {
    constructor(parcel: Parcel) : this(
/*
        parcel.readString() ?: "",
*/
        parcel.readString() ?: "",
        parcel.readString() ?: ""/*,
        parcel.readString() ?: "",
        parcel.readString() ?: ""*/
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
/*
        parcel.writeString(languageCode)
*/
        parcel.writeString(language)
        parcel.writeString(url)
     /*   parcel.writeString(otherUrl)
        parcel.writeString(mimeType)*/
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubtitleDataNew> {
        override fun createFromParcel(parcel: Parcel): SubtitleDataNew {
            return SubtitleDataNew(parcel)
        }

        override fun newArray(size: Int): Array<SubtitleDataNew?> {
            return arrayOfNulls(size)
        }
    }
}
