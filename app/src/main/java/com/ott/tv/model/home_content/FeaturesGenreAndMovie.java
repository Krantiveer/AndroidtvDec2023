
package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.phando.LatestMovieList;

import java.util.List;

public class FeaturesGenreAndMovie implements Parcelable {
    @SerializedName("genre_id")
    @Expose
    private String genreId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("genere_size")
    String viewType;

    @SerializedName("videos")
    @Expose
    private List<LatestMovieList> videos = null;
    @SerializedName("viewallName")
    @Expose
    private String viewallName;

    protected FeaturesGenreAndMovie(Parcel in) {
        genreId = in.readString();
        name = in.readString();
        description = in.readString();
        slug = in.readString();
        url = in.readString();
        viewType = in.readString();
        videos = in.createTypedArrayList(LatestMovieList.CREATOR);
        viewallName = in.readString();
    }

    public static final Creator<FeaturesGenreAndMovie> CREATOR = new Creator<FeaturesGenreAndMovie>() {
        @Override
        public FeaturesGenreAndMovie createFromParcel(Parcel in) {
            return new FeaturesGenreAndMovie(in);
        }

        @Override
        public FeaturesGenreAndMovie[] newArray(int size) {
            return new FeaturesGenreAndMovie[size];
        }
    };

    public String getViewallName() {
        return viewallName;
    }

    public void setViewallName(String viewallName) {
        this.viewallName = viewallName;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<LatestMovieList> getVideos() {
        return videos;
    }

    public void setVideos(List<LatestMovieList> videos) {
        this.videos = videos;
    }

    public String getViewType() {
        return viewType;
    }
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(genreId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(slug);
        dest.writeString(url);
        dest.writeString(viewType);
        dest.writeTypedList(videos);
        dest.writeString(viewallName);
    }
}
