package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserProfile implements Parcelable {
   //0 means not added 1 means added

  //  @Field("id") String id, @Field("type") String type, @Field("value") String value



    @SerializedName("user")
    @Expose
    private UserProfileDetails user;

    @SerializedName("is_subscribed")
    @Expose
    private Integer is_subscribed;

    @SerializedName("active")
    @Expose
    private Integer active;

    protected UserProfile(Parcel in) {
        user = in.readParcelable(UserProfileDetails.class.getClassLoader());
        if (in.readByte() == 0) {
            is_subscribed = null;
        } else {
            is_subscribed = in.readInt();
        }
        if (in.readByte() == 0) {
            active = null;
        } else {
            active = in.readInt();
        }
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public UserProfileDetails getUser() {
        return user;
    }

    public void setUser(UserProfileDetails user) {
        this.user = user;
    }

    public Integer getIs_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(Integer is_subscribed) {
        this.is_subscribed = is_subscribed;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        if (is_subscribed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_subscribed);
        }
        if (active == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(active);
        }
    }
}