package com.ott.tv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;

public class SearchContent implements SerializedName{
    private String id;
    private String title;
    private String description;
    private String type;
    private String streamUrl;
    private String streamFrom;
    private String thumbnailUrl;
    private String posterUrl;


    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("video_quality")
    @Expose
    private String videoQuality;


    @SerializedName("release")
    @Expose
    private String release;

    public SearchContent() {
    }

    public SearchContent(String id, String title, String description, String type, String streamUrl, String streamFrom, String thumbnailUrl, String posterUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.streamUrl = streamUrl;
        this.streamFrom = streamFrom;
        this.thumbnailUrl = thumbnailUrl;
        this.posterUrl = posterUrl;
    }

    public SearchContent(String id, String title, String description, String type, String streamUrl, String streamFrom, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.streamUrl = streamUrl;
        this.streamFrom = streamFrom;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamFrom() {
        return streamFrom;
    }

    public void setStreamFrom(String streamFrom) {
        this.streamFrom = streamFrom;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(String videoQuality) {
        this.videoQuality = videoQuality;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public String[] alternate() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
