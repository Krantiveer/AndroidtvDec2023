
package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.phando.ShowWatchlist;

import java.util.ArrayList;
import java.util.List;

public class LatestMovieSingleDetailList implements Parcelable {

    @SerializedName("media_url")
    @Expose
    private String media_url;

    @SerializedName("youtube_url")
    @Expose
    private String youtube_url;
    @SerializedName("is_youtube")
    @Expose
    private Integer is_youtube;
    @SerializedName("is_live")
    @Expose
    private Integer is_live;

    @SerializedName("is_free")
    @Expose
    private Integer is_free;


    @SerializedName("is_watchlist")
    @Expose
    private String is_watchlist;
    @SerializedName("is_wishlist")
    @Expose
    private String is_wishlist;
    @SerializedName("duration_str")
    @Expose
    private String duration_str;
    @SerializedName("external_url")
    @Expose
    private String external_url;

    @SerializedName("cc_files")
    @Expose
    private CCFile[] ccFiles;
    public String getExternal_url() {
        return external_url;
    }

    public void setExternal_url(String external_url) {
        this.external_url = external_url;
    }


    @SerializedName("related")
    @Expose
    private ArrayList<ShowWatchlist> related;

    public Integer getIs_free() {
        return is_free;
    }

    public void setIs_free(Integer is_free) {
        this.is_free = is_free;
    }

    public String getDuration_str() {
        return duration_str;
    }

    public void setDuration_str(String duration_str) {
        this.duration_str = duration_str;
    }

    public CCFile[] getCcFiles() {
        return ccFiles;
    }

    public void setCcFiles(CCFile[] ccFiles) {
        this.ccFiles = ccFiles;
    }

    protected LatestMovieSingleDetailList(Parcel in) {
        media_url = in.readString();
        youtube_url = in.readString();
        if (in.readByte() == 0) {
            is_youtube = null;
        } else {
            is_youtube = in.readInt();
        }
        if (in.readByte() == 0) {
            is_live = null;
        } else {
            is_live = in.readInt();
        }if (in.readByte() == 0) {
            is_free = null;
        } else {
            is_free = in.readInt();
        }
        is_watchlist = in.readString();
        is_wishlist = in.readString();
        related = in.createTypedArrayList(ShowWatchlist.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(media_url);
        dest.writeString(youtube_url);
        if (is_youtube == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_youtube);
        }
        if (is_live == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_live);
        }   if (is_free == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_free);
        }
        dest.writeString(is_watchlist);
        dest.writeString(is_wishlist);
        dest.writeTypedList(related);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    public Integer getIs_youtube() {
        return is_youtube;
    }

    public void setIs_youtube(Integer is_youtube) {
        this.is_youtube = is_youtube;
    }

    public Integer getIs_live() {
        return is_live;
    }

    public void setIs_live(Integer is_live) {
        this.is_live = is_live;
    }

    public String getIs_watchlist() {
        return is_watchlist;
    }

    public void setIs_watchlist(String is_watchlist) {
        this.is_watchlist = is_watchlist;
    }

    public String getIs_wishlist() {
        return is_wishlist;
    }

    public void setIs_wishlist(String is_wishlist) {
        this.is_wishlist = is_wishlist;
    }

    public ArrayList<ShowWatchlist> getRelated() {
        return related;
    }

    public void setRelated(ArrayList<ShowWatchlist> related) {
        this.related = related;
    }
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

