package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Field;


public class Wishlist implements Parcelable {
   //0 means not added 1 means added

  //  @Field("id") String id, @Field("type") String type, @Field("value") String value

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("value")
    @Expose
    private String value;


    protected Wishlist(Parcel in) {
        id = in.readString();
        type = in.readString();
        value = in.readString();
    }

    public static final Creator<Wishlist> CREATOR = new Creator<Wishlist>() {
        @Override
        public Wishlist createFromParcel(Parcel in) {
            return new Wishlist(in);
        }

        @Override
        public Wishlist[] newArray(int size) {
            return new Wishlist[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(type);
        parcel.writeString(value);
    }
}