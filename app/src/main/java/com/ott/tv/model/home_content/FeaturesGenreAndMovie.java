
package com.ott.tv.model.home_content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeaturesGenreAndMovie {
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
    private List<Video> videos = null;
    @SerializedName("viewallName")
    @Expose
    private String viewallName;

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

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getViewType() {
        return viewType;
    }
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
}
