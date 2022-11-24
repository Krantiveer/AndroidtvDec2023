package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileDetails implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("google_id")
    @Expose
    private String google_id;
    @SerializedName("facebook_id")
    @Expose
    private String facebook_id;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    protected UserProfileDetails(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        lastname = in.readString();
        image = in.readString();
        email = in.readString();
        google_id = in.readString();
        facebook_id = in.readString();
        mobile = in.readString();
    }

    public static final Creator<UserProfileDetails> CREATOR = new Creator<UserProfileDetails>() {
        @Override
        public UserProfileDetails createFromParcel(Parcel in) {
            return new UserProfileDetails(in);
        }

        @Override
        public UserProfileDetails[] newArray(int size) {
            return new UserProfileDetails[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(lastname);
        dest.writeString(image);
        dest.writeString(email);
        dest.writeString(google_id);
        dest.writeString(facebook_id);
        dest.writeString(mobile);
    }
}
