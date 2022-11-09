package com.ott.tv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.database.homeContent.converters.LatestMovieConverter;
import com.ott.tv.model.Subtitle;
import com.ott.tv.model.home_content.LatestMovie;
import com.ott.tv.model.home_content.LatestMovieList;

import java.io.Serializable;
import java.util.List;

/*data class BrowseData (
    val displayType: String,
    val id: Int?,
    val list: ArrayList<Video>,
    val title: String,
    val image_orientation: Int? = 0,
)*/


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;


public class BrowseData implements Parcelable {
    protected BrowseData(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        displayType = in.readString();
        if (in.readByte() == 0) {
            image_orientation = null;
        } else {
            image_orientation = in.readInt();
        }
    }

    public static final Creator<BrowseData> CREATOR = new Creator<BrowseData>() {
        @Override
        public BrowseData createFromParcel(Parcel in) {
            return new BrowseData(in);
        }

        @Override
        public BrowseData[] newArray(int size) {
            return new BrowseData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(title);
        parcel.writeString(displayType);
        if (image_orientation == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(image_orientation);
        }
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("displayType")
    @Expose
    private String displayType;

    @SerializedName("image_orientation")
    @Expose
    private Integer image_orientation;

    @SerializedName("list")
    @Expose
    private List<LatestMovieList> list;

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

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Integer getImage_orientation() {
        return image_orientation;
    }

    public void setImage_orientation(Integer image_orientation) {
        this.image_orientation = image_orientation;
    }

    public List<LatestMovieList> getList() {
        return list;
    }

    public void setList(List<LatestMovieList> list) {
        this.list = list;
    }
}