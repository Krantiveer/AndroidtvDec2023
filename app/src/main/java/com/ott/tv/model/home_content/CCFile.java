package com.ott.tv.model.home_content;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CCFile implements Parcelable {

    @SerializedName("language_code")
    private String languageCode;

    @SerializedName("language")
    private String language;

    @SerializedName("url")
    private String url;

    @SerializedName("other_url")
    private String otherUrl;

    @SerializedName("mime_type")
    private String mimeType;

    protected CCFile(Parcel in) {
        languageCode = in.readString();
        language = in.readString();
        url = in.readString();
        otherUrl = in.readString();
        mimeType = in.readString();
    }

    public static final Creator<CCFile> CREATOR = new Creator<CCFile>() {
        @Override
        public CCFile createFromParcel(Parcel in) {
            return new CCFile(in);
        }

        @Override
        public CCFile[] newArray(int size) {
            return new CCFile[size];
        }
    };

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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

    public String getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(languageCode);
        dest.writeString(language);
        dest.writeString(url);
        dest.writeString(otherUrl);
        dest.writeString(mimeType);
    }
}
