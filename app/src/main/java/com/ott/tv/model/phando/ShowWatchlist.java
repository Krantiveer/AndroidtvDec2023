
package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.home_content.LatestMovieSingleDetailList;

import java.util.List;

public class ShowWatchlist implements Parcelable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("is_free")
    @Expose
    private Integer is_free;
    @SerializedName("is_live")
    @Expose
    private Integer is_live;
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

    @SerializedName("language_str")
    @Expose
    private String language_str;
    @SerializedName("duration_str")
    @Expose
    private String duration_str;

    @SerializedName("trailers")
    @Expose
    private List<LatestMovieSingleDetailList> trailers = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;

    protected ShowWatchlist(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        thumbnail = in.readString();
        poster = in.readString();
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
        language_str = in.readString();
        duration_str = in.readString();
        trailers = in.createTypedArrayList(LatestMovieSingleDetailList.CREATOR);
        genres = in.createStringArrayList();
    }

    public static final Creator<ShowWatchlist> CREATOR = new Creator<ShowWatchlist>() {
        @Override
        public ShowWatchlist createFromParcel(Parcel in) {
            return new ShowWatchlist(in);
        }

        @Override
        public ShowWatchlist[] newArray(int size) {
            return new ShowWatchlist[size];
        }
    };

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getLanguage_str() {
        return language_str;
    }

    public void setLanguage_str(String language_str) {
        this.language_str = language_str;
    }

    public String getDuration_str() {
        return duration_str;
    }

    public void setDuration_str(String duration_str) {
        this.duration_str = duration_str;
    }

    public List<LatestMovieSingleDetailList> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<LatestMovieSingleDetailList> trailers) {
        this.trailers = trailers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(poster);
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
        dest.writeString(release_date);
        if (publish_year == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(publish_year);
        }
        dest.writeString(language_str);
        dest.writeString(duration_str);
        dest.writeTypedList(trailers);
        dest.writeStringList(genres);
    }
}