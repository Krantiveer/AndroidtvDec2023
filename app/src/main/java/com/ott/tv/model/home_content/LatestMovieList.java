
package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class LatestMovieList implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    private String thumbnail;
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

    @SerializedName("release_date")
    @Expose
    private String release_date;

    @SerializedName("publish_year")
    @Expose
    private Integer publish_year;

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

    protected LatestMovieList(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
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
        release_date = in.readString();
        if (in.readByte() == 0) {
            publish_year = null;
        } else {
            publish_year = in.readInt();
        }
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
        parcel.writeString(description);
        parcel.writeString(thumbnail);
        parcel.writeString(thumbnail_vertical);
        parcel.writeString(circular_thumbnail);
        parcel.writeString(poster);
        parcel.writeString(poster_vertical);
        parcel.writeString(created_at);
        if (is_free == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(is_free);
        }
        if (is_live == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(is_live);
        }
        parcel.writeString(phando_media_id);
        parcel.writeString(type);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(price);
        }
        parcel.writeString(detail);
        parcel.writeString(keyword);
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rating);
        }
        parcel.writeString(maturity_rating);
        if (duration == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(duration);
        }
        parcel.writeString(release_date);
        if (publish_year == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(publish_year);
        }
        if (last_watch_time == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(last_watch_time);
        }
        if (is_active == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(is_active);
        }
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(Integer publish_year) {
        this.publish_year = publish_year;
    }

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
}
