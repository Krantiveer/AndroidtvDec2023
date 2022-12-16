package com.ott.tv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedMovie implements Parcelable {

    @SerializedName("videos_id")
    @Expose
    private String videosId;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("country")
    @Expose
    private String country;
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
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("video_quality")
    @Expose
    private String videoQuality;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @SerializedName("video_view_type")
    @Expose
    private String video_view_type;

    protected RelatedMovie(Parcel in) {
        videosId = in.readString();
        genre = in.readString();
        country = in.readString();
        title = in.readString();
        description = in.readString();
        slug = in.readString();
        release = in.readString();
        runtime = in.readString();
        videoQuality = in.readString();
        thumbnailUrl = in.readString();
        posterUrl = in.readString();
        isPaid = in.readString();
        video_view_type = in.readString();
    }

    public static final Creator<RelatedMovie> CREATOR = new Creator<RelatedMovie>() {
        @Override
        public RelatedMovie createFromParcel(Parcel in) {
            return new RelatedMovie(in);
        }

        @Override
        public RelatedMovie[] newArray(int size) {
            return new RelatedMovie[size];
        }
    };

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getVideo_view_type() {
        return video_view_type;
    }

    public void setVideo_view_type(String video_view_type) {
        this.video_view_type = video_view_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videosId);
        dest.writeString(genre);
        dest.writeString(country);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(slug);
        dest.writeString(release);
        dest.writeString(runtime);
        dest.writeString(videoQuality);
        dest.writeString(thumbnailUrl);
        dest.writeString(posterUrl);
        dest.writeString(isPaid);
        dest.writeString(video_view_type);
    }
}
