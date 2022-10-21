
package com.ott.tv.model.home_content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Video implements Serializable {

    @SerializedName("videos_id")
    @Expose
    private String videosId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("genre_id")
    @Expose
    private String genre_id;

    @SerializedName("image_link")
    @Expose
    private String imageLink;

    @SerializedName("action_type")
    @Expose
    private String actionType;


    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("slug")
    @Expose
    private String slug;


    @SerializedName("release")
    @Expose
    private String release;

    @SerializedName("language")
    @Expose
    private String language;


    @SerializedName("is_tvseries")
    @Expose
    private String isTvseries;

    @SerializedName("is_paid")
    @Expose
    private String isPaid;

    @SerializedName("runtime")
    @Expose
    private String runtime;

    private String runtime_in_minutes;

    @SerializedName("video_quality")
    @Expose
    private String videoQuality;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    @SerializedName("poster_url")
    @Expose
    private String posterUrl;

    private String video_view_type;
    private String genre;
    private String trailler_youtube_source;
    private String trailer_aws_source;

    public String getTrailer_aws_source() {
        return trailer_aws_source;
    }

    public void setTrailer_aws_source(String trailer_aws_source) {
        this.trailer_aws_source = trailer_aws_source;
    }

    public String getTrailler_youtube_source() {
        return trailler_youtube_source;
    }

    public void setTrailler_youtube_source(String trailler_youtube_source) {
        this.trailler_youtube_source = trailler_youtube_source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(String genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getVideo_view_type() {
        return video_view_type;
    }

    public void setVideo_view_type(String video_view_type) {
        this.video_view_type = video_view_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    private String type;

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getIsTvseries() {
        return isTvseries;
    }

    public void setIsTvseries(String isTvseries) {
        this.isTvseries = isTvseries;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ContinueWatchMinutes continue_watch_minutes;

    public static class ContinueWatchMinutes {
        public String videos_id;

        public String getLast_watched_at() {
            return last_watched_at;
        }

        public void setLast_watched_at(String last_watched_at) {
            this.last_watched_at = last_watched_at;
        }

        public String video_type;
        public String last_watched_at;
    }

    public String getRuntime_in_minutes() {
        return runtime_in_minutes;
    }

    public void setRuntime_in_minutes(String runtime_in_minutes) {
        this.runtime_in_minutes = runtime_in_minutes;
    }
}
