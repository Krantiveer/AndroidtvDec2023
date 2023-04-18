
package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OttServiceListModel implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("slug")
    @Expose
    private String slug;


    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;


    protected OttServiceListModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        slug = in.readString();
        videos = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<OttServiceListModel> CREATOR = new Creator<OttServiceListModel>() {
        @Override
        public OttServiceListModel createFromParcel(Parcel in) {
            return new OttServiceListModel(in);
        }

        @Override
        public OttServiceListModel[] newArray(int size) {
            return new OttServiceListModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(slug);
        dest.writeTypedList(videos);
    }
}