package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.home_content.LatestMovieList;
import com.ott.tv.model.home_content.LatestMovieSingleDetailList;

import java.util.List;


public class MediaplaybackData implements Parcelable{

    /*val data: MediaMetadata,



    val note: String?,
    val message: String,
    val purchase_option: List<PurchaseOption>,
    val status: String
    */
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mediaCode")
    @Expose
    private String mediaCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private LatestMovieSingleDetailList list;

    protected MediaplaybackData(Parcel in) {
        status = in.readString();
        mediaCode = in.readString();
        message = in.readString();
        list = in.readParcelable(LatestMovieSingleDetailList.class.getClassLoader());
    }

    public static final Creator<MediaplaybackData> CREATOR = new Creator<MediaplaybackData>() {
        @Override
        public MediaplaybackData createFromParcel(Parcel in) {
            return new MediaplaybackData(in);
        }

        @Override
        public MediaplaybackData[] newArray(int size) {
            return new MediaplaybackData[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMediaCode() {
        return mediaCode;
    }

    public void setMediaCode(String mediaCode) {
        this.mediaCode = mediaCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LatestMovieSingleDetailList getList() {
        return list;
    }

    public void setList(LatestMovieSingleDetailList list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(mediaCode);
        dest.writeString(message);
        dest.writeParcelable(list, flags);
    }
}