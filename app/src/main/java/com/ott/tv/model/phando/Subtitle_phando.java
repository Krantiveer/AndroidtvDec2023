package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subtitle_phando implements Parcelable {

    @SerializedName("language_code")
    @Expose
    private String language_code;

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("other_url")
    @Expose
    private String other_url;

    protected Subtitle_phando(Parcel in) {
        language_code = in.readString();
        language = in.readString();
        url = in.readString();
        other_url = in.readString();
    }

    public static final Creator<Subtitle_phando> CREATOR = new Creator<Subtitle_phando>() {
        @Override
        public Subtitle_phando createFromParcel(Parcel in) {
            return new Subtitle_phando(in);
        }

        @Override
        public Subtitle_phando[] newArray(int size) {
            return new Subtitle_phando[size];
        }
    };

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOther_url() {
        return other_url;
    }

    public void setOther_url(String other_url) {
        this.other_url = other_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(language_code);
        dest.writeString(language);
        dest.writeString(url);
        dest.writeString(other_url);
    }
}
