
package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestMovieSingleDetailList implements Parcelable{

    @SerializedName("media_url")
    @Expose
    private String media_url;
    @SerializedName("is_watchlist")
    @Expose
    private String is_watchlist;

    protected LatestMovieSingleDetailList(Parcel in) {
        media_url = in.readString();
        is_watchlist = in.readString();
    }

    public static final Creator<LatestMovieSingleDetailList> CREATOR = new Creator<LatestMovieSingleDetailList>() {
        @Override
        public LatestMovieSingleDetailList createFromParcel(Parcel in) {
            return new LatestMovieSingleDetailList(in);
        }

        @Override
        public LatestMovieSingleDetailList[] newArray(int size) {
            return new LatestMovieSingleDetailList[size];
        }
    };

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getIs_watchlist() {
        return is_watchlist;
    }

    public void setIs_watchlist(String is_watchlist) {
        this.is_watchlist = is_watchlist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(media_url);
        dest.writeString(is_watchlist);
    }


   /* @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

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
    private String language_str;

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
*/

}