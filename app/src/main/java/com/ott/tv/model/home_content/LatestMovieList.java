
package com.ott.tv.model.home_content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestMovieList {


    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("release")
    @Expose
    private String release;

    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("thumbnail_tv")
    @Expose
    private String thumbnail_tv;

    @SerializedName("thumbnail_vertical")
    @Expose
    private String thumbnail_vertical;

    @SerializedName("is_wishlist")
    @Expose
    private String is_wishlist;

    @SerializedName("is_watchlist")
    @Expose
    private String is_watchlist;

    @SerializedName("is_free")
    @Expose
    private String is_free;

    @SerializedName("is_live")
    @Expose
    private String is_live;

    @SerializedName("keyword")
    @Expose
    private String keyword;

    @SerializedName("poster")
    @Expose
    private String poster;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("price")
    @Expose
    private Integer price;

    @SerializedName("maturity_rating")
    @Expose
    private Integer maturity_rating;


    @SerializedName("phando_media_id")
    @Expose
    private Integer phando_media_id;





}
