
package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;


import com.ott.tv.database.homeContent.converters.CountryConverter;
import com.ott.tv.database.homeContent.converters.FeaturedTvConverter;
import com.ott.tv.database.homeContent.converters.FeaturesGenreConverter;
import com.ott.tv.database.homeContent.converters.GenreConverter;
import com.ott.tv.database.homeContent.converters.LatestMovieConverter;
import com.ott.tv.database.homeContent.converters.PopularStarsConverter;
import com.ott.tv.database.homeContent.converters.SliderTypeConverter;
import com.ott.tv.database.homeContent.converters.TvSeriesConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeContent implements Parcelable {
    private int homeContentId;

    @SerializedName("slider")
    @Expose
    private Slider slider;

    @SerializedName("all_country")
    @Expose
    private List<AllCountry> allCountry = null;

    @SerializedName("all_genre")
    @Expose
    private List<AllGenre> allGenre = null;

    @SerializedName("featured_tv_channel")
    @Expose
    private List<FeaturedTvChannel> featuredTvChannel = null;

    @SerializedName("latest_movies")
    @Expose
    private List<LatestMovie> latestMovies = null;

    @SerializedName("latest_tvseries")
    @Expose
    private List<LatestTvseries> latestTvseries = null;

    @SerializedName("features_genre_and_movie")
    @Expose
    private List<FeaturesGenreAndMovie> featuresGenreAndMovie = null;

    private List<FeaturesGenreAndMovie> featuresGenreAndMovieSlider = null;

    @SerializedName("popular_stars")
    @Expose
    private List<PopularStars> popularStarsList = null;

    protected HomeContent(Parcel in) {
        homeContentId = in.readInt();
    }

    public static final Creator<HomeContent> CREATOR = new Creator<HomeContent>() {
        @Override
        public HomeContent createFromParcel(Parcel in) {
            return new HomeContent(in);
        }

        @Override
        public HomeContent[] newArray(int size) {
            return new HomeContent[size];
        }
    };

    public int getHomeContentId() {
        return homeContentId;
    }

    public void setHomeContentId(int homeContentId) {
        this.homeContentId = homeContentId;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public List<AllCountry> getAllCountry() {
        return allCountry;
    }

    public void setAllCountry(List<AllCountry> allCountry) {
        this.allCountry = allCountry;
    }

    public List<AllGenre> getAllGenre() {
        return allGenre;
    }

    public void setAllGenre(List<AllGenre> allGenre) {
        this.allGenre = allGenre;
    }

    public List<FeaturedTvChannel> getFeaturedTvChannel() {
        return featuredTvChannel;
    }

    public void setFeaturedTvChannel(List<FeaturedTvChannel> featuredTvChannel) {
        this.featuredTvChannel = featuredTvChannel;
    }

    public List<LatestMovie> getLatestMovies() {
        return latestMovies;
    }

    public void setLatestMovies(List<LatestMovie> latestMovies) {
        this.latestMovies = latestMovies;
    }

    public List<LatestTvseries> getLatestTvseries() {
        return latestTvseries;
    }

    public void setLatestTvseries(List<LatestTvseries> latestTvseries) {
        this.latestTvseries = latestTvseries;
    }

    public List<FeaturesGenreAndMovie> getFeaturesGenreAndMovie() {
        return featuresGenreAndMovie;
    }

    public void setFeaturesGenreAndMovie(List<FeaturesGenreAndMovie> featuresGenreAndMovie) {
        this.featuresGenreAndMovie = featuresGenreAndMovie;
    }

    public List<FeaturesGenreAndMovie> getFeaturesGenreAndMovieSlider() {
        return featuresGenreAndMovieSlider;
    }

    public void setFeaturesGenreAndMovieSlider(List<FeaturesGenreAndMovie> featuresGenreAndMovieSlider) {
        this.featuresGenreAndMovieSlider = featuresGenreAndMovieSlider;
    }

    public List<PopularStars> getPopularStarsList() {
        return popularStarsList;
    }

    public void setPopularStarsList(List<PopularStars> popularStarsList) {
        this.popularStarsList = popularStarsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(homeContentId);
    }
}
