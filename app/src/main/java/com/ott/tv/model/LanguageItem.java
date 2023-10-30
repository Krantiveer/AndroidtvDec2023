package com.ott.tv.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LanguageItem implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("language")
    private String language;

    @SerializedName("language_text")
    private String languageText;

    @SerializedName("isLanguageSelected")
    private boolean isLanguageSelected;


    protected LanguageItem(Parcel in) {
        id = in.readInt();
        language = in.readString();
        languageText = in.readString();
        isLanguageSelected = in.readByte() != 0;
    }

    public static final Creator<LanguageItem> CREATOR = new Creator<LanguageItem>() {
        @Override
        public LanguageItem createFromParcel(Parcel in) {
            return new LanguageItem(in);
        }

        @Override
        public LanguageItem[] newArray(int size) {
            return new LanguageItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageText() {
        return languageText;
    }

    public void setLanguageText(String languageText) {
        this.languageText = languageText;
    }

    public boolean isLanguageSelected() {
        return isLanguageSelected;
    }

    public void setLanguageSelected(boolean languageSelected) {
        isLanguageSelected = languageSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(language);
        dest.writeString(languageText);
        dest.writeByte((byte) (isLanguageSelected ? 1 : 0));
    }
// Constructor, getters, and setters
}

