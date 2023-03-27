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


    public CurrentSubscription getCurrent_subscription() {
        return current_subscription;
    }

    public void setCurrent_subscription(CurrentSubscription current_subscription) {
        this.current_subscription = current_subscription;
    }

    @SerializedName("current_subscription")
    @Expose
    private CurrentSubscription current_subscription;

    @SerializedName("package_name")
    @Expose
    private String package_name;

    public String getPackage_name() {
        return package_name;
    }

    @SerializedName("subscription_end_date")
    @Expose
    private String subscription_end_date;

    public String getsubscription_end_date() {
        return subscription_end_date;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    @SerializedName("is_subscribed")
    @Expose
    private Integer is_subscribed;

    @SerializedName("active")
    @Expose
    private Integer active;

    @SerializedName("is_review")
    @Expose
    private Integer is_review;

    public Integer getIs_review() {
        return is_review;
    }

    public void setIs_review(Integer is_review) {
        this.is_review = is_review;
    }
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

