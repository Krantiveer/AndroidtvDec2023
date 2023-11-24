package com.ott.tv.model.SliderHome;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.phando.LatestMovieList;

public class SliderData implements Parcelable {

    // image url is used to
// store the url of image
    private String imgUrl;
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    private  LatestMovieList videoContent ;

    protected SliderData(Parcel in) {
        imgUrl = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
    }

    public static final Creator<SliderData> CREATOR = new Creator<SliderData>() {
        @Override
        public SliderData createFromParcel(Parcel in) {
            return new SliderData(in);
        }

        @Override
        public SliderData[] newArray(int size) {
            return new SliderData[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Constructor method.
    public SliderData(String imgUrl, String title , LatestMovieList videoContent) {
        this.imgUrl = imgUrl;
        this.title=title;
        this.videoContent=videoContent;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(imgUrl);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
    }

    public LatestMovieList getVideoContent() {
        return videoContent;
    }

    public void setVideoContent(LatestMovieList videoContent) {
        this.videoContent = videoContent;
    }
}