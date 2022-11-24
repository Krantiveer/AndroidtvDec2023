package com.ott.tv.model.phando;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.Episode;
import com.ott.tv.model.home_content.GenreResource;
import com.ott.tv.model.home_content.LatestMovieSingleDetailList;

import java.util.List;

public class SeasonList {


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



    @SerializedName("created_at")
    @Expose
    private String created_at;

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

    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;








/*

    @SerializedName("seasons_id")
    @Expose
    private String seasonsId;
    @SerializedName("seasons_name")
    @Expose
    private String seasonsName;


    public String getSeasonsId() {
        return seasonsId;
    }

    public void setSeasonsId(String seasonsId) {
        this.seasonsId = seasonsId;
    }

    public String getSeasonsName() {
        return seasonsName;
    }

    public void setSeasonsName(String seasonsName) {
        this.seasonsName = seasonsName;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }*/
}
