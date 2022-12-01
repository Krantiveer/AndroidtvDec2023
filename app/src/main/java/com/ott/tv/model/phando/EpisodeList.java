package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.Subtitle;

import java.io.Serializable;
import java.util.List;

public class EpisodeList  implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tv_series_id")
    @Expose
    private Integer tv_series_id;
    @SerializedName("season_no")
    @Expose
    private Integer season_no;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("episode_no")
    @Expose
    private Integer episode_no;

    @SerializedName("publish_year")
    @Expose
    private Integer publish_year;
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
    @SerializedName("phando_media_id")
    @Expose
    private String phando_media_id;
    @SerializedName("description")
    @Expose
    private String description;
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

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("duration_str")
    @Expose
    private String duration_str;

    @SerializedName("language_str")
    @Expose
    private String language_str;
    @SerializedName("last_watch_time")
    @Expose
    private Integer last_watch_time;

    protected EpisodeList(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            tv_series_id = null;
        } else {
            tv_series_id = in.readInt();
        }
        if (in.readByte() == 0) {
            season_no = null;
        } else {
            season_no = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            episode_no = null;
        } else {
            episode_no = in.readInt();
        }
        if (in.readByte() == 0) {
            publish_year = null;
        } else {
            publish_year = in.readInt();
        }
        thumbnail = in.readString();
        thumbnail_vertical = in.readString();
        circular_thumbnail = in.readString();
        poster = in.readString();
        poster_vertical = in.readString();
        phando_media_id = in.readString();
        description = in.readString();
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
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        duration_str = in.readString();
        language_str = in.readString();
        if (in.readByte() == 0) {
            last_watch_time = null;
        } else {
            last_watch_time = in.readInt();
        }
    }

    public static final Creator<EpisodeList> CREATOR = new Creator<EpisodeList>() {
        @Override
        public EpisodeList createFromParcel(Parcel in) {
            return new EpisodeList(in);
        }

        @Override
        public EpisodeList[] newArray(int size) {
            return new EpisodeList[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTv_series_id() {
        return tv_series_id;
    }

    public void setTv_series_id(Integer tv_series_id) {
        this.tv_series_id = tv_series_id;
    }

    public Integer getSeason_no() {
        return season_no;
    }

    public void setSeason_no(Integer season_no) {
        this.season_no = season_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisode_no() {
        return episode_no;
    }

    public void setEpisode_no(Integer episode_no) {
        this.episode_no = episode_no;
    }

    public Integer getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(Integer publish_year) {
        this.publish_year = publish_year;
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

    public String getPhando_media_id() {
        return phando_media_id;
    }

    public void setPhando_media_id(String phando_media_id) {
        this.phando_media_id = phando_media_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLanguage_str() {
        return language_str;
    }

    public void setLanguage_str(String language_str) {
        this.language_str = language_str;
    }

    public Integer getLast_watch_time() {
        return last_watch_time;
    }

    public void setLast_watch_time(Integer last_watch_time) {
        this.last_watch_time = last_watch_time;
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
        if (tv_series_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(tv_series_id);
        }
        if (season_no == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(season_no);
        }
        dest.writeString(title);
        if (episode_no == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(episode_no);
        }
        if (publish_year == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(publish_year);
        }
        dest.writeString(thumbnail);
        dest.writeString(thumbnail_vertical);
        dest.writeString(circular_thumbnail);
        dest.writeString(poster);
        dest.writeString(poster_vertical);
        dest.writeString(phando_media_id);
        dest.writeString(description);
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
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(duration_str);
        dest.writeString(language_str);
        if (last_watch_time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(last_watch_time);
        }
    }
}
