package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CategoryType implements Parcelable {
   //0 means not added 1 means added

  //  @Field("id") String id, @Field("type") String type, @Field("value") String value


    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    @SerializedName("isFocused")
    @Expose
    private boolean isFocused;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("icon")
    @Expose
    private String icon;

    protected CategoryType(Parcel in) {
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        displayName = in.readString();
        thumbnail = in.readString();
        icon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        dest.writeString(displayName);
        dest.writeString(thumbnail);
        dest.writeString(icon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryType> CREATOR = new Creator<CategoryType>() {
        @Override
        public CategoryType createFromParcel(Parcel in) {
            return new CategoryType(in);
        }

        @Override
        public CategoryType[] newArray(int size) {
            return new CategoryType[size];
        }
    };

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}