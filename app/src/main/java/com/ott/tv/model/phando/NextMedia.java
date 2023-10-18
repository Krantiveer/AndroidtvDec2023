package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NextMedia implements Parcelable {

    private int id;
    private String type;
    private String thumbnail;
    private int nextEpisodeStartTime;

    protected NextMedia(Parcel in) {
        id = in.readInt();
        type = in.readString();
        thumbnail = in.readString();
        nextEpisodeStartTime = in.readInt();
    }

    public static final Creator<NextMedia> CREATOR = new Creator<NextMedia>() {
        @Override
        public NextMedia createFromParcel(Parcel in) {
            return new NextMedia(in);
        }

        @Override
        public NextMedia[] newArray(int size) {
            return new NextMedia[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getNextEpisodeStartTime() {
        return nextEpisodeStartTime;
    }

    public void setNextEpisodeStartTime(int nextEpisodeStartTime) {
        this.nextEpisodeStartTime = nextEpisodeStartTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(thumbnail);
        dest.writeInt(nextEpisodeStartTime);
    }
}
