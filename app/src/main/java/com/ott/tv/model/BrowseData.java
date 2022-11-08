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


public class BrowseData implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("displayType")
    @Expose
    private String displayType;

    @SerializedName("image_orientation")
    @Expose
    private String image_orientation;

    @ColumnInfo(name = "list")
    @TypeConverters(LatestMovieConverter.class)
    @SerializedName("list")
    @Expose
    private List<LatestMovieList> list = null;












}