
package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.home_content.GenreResource;
import com.ott.tv.model.home_content.LatestMovieSingleDetailList;
import com.ott.tv.model.home_content.Video;

import java.util.List;

public class LatestMovieList implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("viewallTitle")
    @Expose
    private String viewallTitle;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("thumbnail_vertical")
    @Expose
    private String thumbnail_vertical;

    @SerializedName("circular_thumbnail")
    @Expose
    private String circular_thumbnail;

    @SerializedName("poster")
    @Expose
    private String poster;

    @SerializedName("poster_vertical")
    @Expose
    private String poster_vertical;

    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("is_free")
    @Expose
    private Integer is_free;
    @SerializedName("is_live")
    @Expose
    private Integer is_live;
    @SerializedName("phando_media_id")
    @Expose
    private String phando_media_id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("maturity_rating")
    @Expose
    private String maturity_rating;

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("duration_str")
    @Expose
    private String duration_str;

    @SerializedName("release_date")
    @Expose
    private String release_date;
    @SerializedName("language_str")
    @Expose
    private String language_str;/*

    @SerializedName("publish_year")
    @Expose
    private Integer publish_year;
*/
    @SerializedName("last_watch_time")
    @Expose
    private Integer last_watch_time;
    @SerializedName("is_active")
    @Expose
    private Integer is_active;


    @SerializedName("genres_resource")
    @Expose
    private List<GenreResource> list = null;

    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("trailers")
    @Expose
    private List<LatestMovieSingleDetailList> trailers = null;
    @SerializedName("android_link")
    @Expose
    private String android_link;
    private String android_tv_link;
    private String trailer_aws_source;
    @SerializedName("release")
    @Expose
    private String release;

 @SerializedName("videos_id")
    @Expose
    public String videosId;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("video_quality")
    @Expose
    private String videoQuality;

    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @SerializedName("language")
    @Expose
    private String language;
    private String genre;
    private String runtime_in_minutes;
    @SerializedName("viewallName")
    @Expose
    private String viewallName;
    public ContinueWatchMinutes continue_watch_minutes;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("image_link")
    @Expose
    private String imageLink;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("action_type")
    @Expose
    private String actionType;
    @SerializedName("is_tvseries")
    @Expose
    private String isTvseries;
    @SerializedName("is_subscribed")
    @Expose
    private Integer is_subscribed;

    public Integer getIs_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(Integer is_subscribed) {
        this.is_subscribed = is_subscribed;
    }
    /*
    public Integer getIs_Subscribe() {
        return is_Subscribe;
    }

    public void setIs_Subscribe(Integer is_Subscribe) {
        this.is_Subscribe = is_Subscribe;
    }*/

/*
    public String getIs_Subscribe() {
        return is_Subscribe;
    }

    public void setIs_Subscribe(String is_Subscribe) {
        this.is_Subscribe = is_Subscribe;
    }
*/

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
    }

    public String getAndroid_tv_link() {
        return android_tv_link;
    }

    public void setAndroid_tv_link(String android_tv_link) {
        this.android_tv_link = android_tv_link;
    }

    public String getIsTvseries() {
        return isTvseries;
    }

    public void setIsTvseries(String isTvseries) {
        this.isTvseries = isTvseries;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public static class ContinueWatchMinutes {
        public String videos_id;
        public String video_type;
        public String last_watched_at;


        public String getLast_watched_at() {
            return last_watched_at;
        }

        public void setLast_watched_at(String last_watched_at) {
            this.last_watched_at = last_watched_at;
        }


    }

    protected LatestMovieList(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        viewallTitle = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        thumbnail_vertical = in.readString();
        circular_thumbnail = in.readString();
        poster = in.readString();
        poster_vertical = in.readString();
        created_at = in.readString();
        if (in.readByte() == 0) {
            is_free = null;
        } else {
            is_free = in.readInt();
        }
        if (in.readByte() == 0) {
            is_live = null;
        } else {
            is_live = in.readInt();
        }
        phando_media_id = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        detail = in.readString();
        keyword = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        maturity_rating = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        duration_str = in.readString();
        release_date = in.readString();
        language_str = in.readString();
        if (in.readByte() == 0) {
            last_watch_time = null;
        } else {
            last_watch_time = in.readInt();
        }
        if (in.readByte() == 0) {
            is_active = null;
        } else {
            is_active = in.readInt();
        }
        genres = in.createStringArrayList();
        trailers = in.createTypedArrayList(LatestMovieSingleDetailList.CREATOR);
        android_link = in.readString();
        trailer_aws_source = in.readString();
        release = in.readString();

        thumbnailUrl = in.readString();
        videoQuality = in.readString();
        isPaid = in.readString();
        language = in.readString();
        genre = in.readString();
        runtime_in_minutes = in.readString();
        viewallName = in.readString();
    }

    public static final Creator<LatestMovieList> CREATOR = new Creator<LatestMovieList>() {
        @Override
        public LatestMovieList createFromParcel(Parcel in) {
            return new LatestMovieList(in);
        }

        @Override
        public LatestMovieList[] newArray(int size) {
            return new LatestMovieList[size];
        }
    };

    public String getAndroid_link() {
        return android_link;
    }

    public void setAndroid_link(String android_link) {
        this.android_link = android_link;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail_vertical() {
        return thumbnail_vertical;
    }

    public void setThumbnail_vertical(String thumbnail_vertical) {
        this.thumbnail_vertical = thumbnail_vertical;
    }

    public String getCircular_thumbnail() {
        return circular_thumbnail;
    }

    public void setCircular_thumbnail(String circular_thumbnail) {
        this.circular_thumbnail = circular_thumbnail;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPoster_vertical() {
        return poster_vertical;
    }

    public void setPoster_vertical(String poster_vertical) {
        this.poster_vertical = poster_vertical;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getIs_free() {
        return is_free;
    }

    public void setIs_free(Integer is_free) {
        this.is_free = is_free;
    }

    public Integer getIs_live() {
        return is_live;
    }

    public void setIs_live(Integer is_live) {
        this.is_live = is_live;
    }

    public String getPhando_media_id() {
        return phando_media_id;
    }

    public void setPhando_media_id(String phando_media_id) {
        this.phando_media_id = phando_media_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getMaturity_rating() {
        return maturity_rating;
    }

    public void setMaturity_rating(String maturity_rating) {
        this.maturity_rating = maturity_rating;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDuration_str() {
        return duration_str;
    }

    public void setDuration_str(String duration_str) {
        this.duration_str = duration_str;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getLanguage_str() {
        return language_str;
    }

    public void setLanguage_str(String language_str) {
        this.language_str = language_str;
    }

/*
    public Integer getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(Integer publish_year) {
        this.publish_year = publish_year;
    }
*/

    public Integer getLast_watch_time() {
        return last_watch_time;
    }

    public void setLast_watch_time(Integer last_watch_time) {
        this.last_watch_time = last_watch_time;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    public List<GenreResource> getList() {
        return list;
    }

    public void setList(List<GenreResource> list) {
        this.list = list;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<LatestMovieSingleDetailList> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<LatestMovieSingleDetailList> trailers) {
        this.trailers = trailers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(viewallTitle);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(thumbnail_vertical);
        dest.writeString(circular_thumbnail);
        dest.writeString(poster);
        dest.writeString(poster_vertical);
        dest.writeString(created_at);
        if (is_free == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_free);
        }
        if (is_live == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_live);
        }
        dest.writeString(phando_media_id);
        dest.writeString(type);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(detail);
        dest.writeString(keyword);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating);
        }
        dest.writeString(maturity_rating);
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(duration_str);
        dest.writeString(release_date);
        dest.writeString(language_str);
        if (last_watch_time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(last_watch_time);
        }
        if (is_active == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_active);
        }
        dest.writeStringList(genres);
        dest.writeTypedList(trailers);
        dest.writeString(android_link);
        dest.writeString(trailer_aws_source);
        dest.writeString(release);
        dest.writeString(thumbnailUrl);
        dest.writeString(videoQuality);
        dest.writeString(isPaid);
        dest.writeString(language);
        dest.writeString(genre);
        dest.writeString(runtime_in_minutes);
        dest.writeString(viewallName);
    }


    public String getViewallTitle() {
        return viewallTitle;
    }

    public void setViewallTitle(String viewallTitle) {
        this.viewallTitle = viewallTitle;
    }

    public String getTrailer_aws_source() {
        return trailer_aws_source;
    }

    public void setTrailer_aws_source(String trailer_aws_source) {
        this.trailer_aws_source = trailer_aws_source;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(String videoQuality) {
        this.videoQuality = videoQuality;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime_in_minutes() {
        return runtime_in_minutes;
    }

    public void setRuntime_in_minutes(String runtime_in_minutes) {
        this.runtime_in_minutes = runtime_in_minutes;
    }

    public String getViewallName() {
        return viewallName;
    }

    public void setViewallName(String viewallName) {
        this.viewallName = viewallName;
    }

    public ContinueWatchMinutes getContinue_watch_minutes() {
        return continue_watch_minutes;
    }

    public void setContinue_watch_minutes(ContinueWatchMinutes continue_watch_minutes) {
        this.continue_watch_minutes = continue_watch_minutes;
    }
}