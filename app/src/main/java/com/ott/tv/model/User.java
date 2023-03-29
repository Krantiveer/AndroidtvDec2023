package com.ott.tv.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("join_date")
    @Expose
    private String joinDate;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("error")
    @Expose
    private String error;
    private String is_subscribed;


    public User() {
    }

    protected User(Parcel in) {
        status = in.readString();
        userId = in.readString();
        name = in.readString();
        email = in.readString();
        gender = in.readString();
        joinDate = in.readString();
        lastLogin = in.readString();
        data = in.readString();
        imageUrl = in.readString();
        message = in.readString();
        access_token = in.readString();
        error = in.readString();
        is_subscribed = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getIs_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(String is_subscribed) {
        this.is_subscribed = is_subscribed;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return "User{" +
                "status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", data='" + data + '\'' +
                ", image_url='" + imageUrl + '\'' +
                ", message='" + message + '\'' +
                ", access_token='" + access_token + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(joinDate);
        dest.writeString(lastLogin);
        dest.writeString(data);
        dest.writeString(imageUrl);
        dest.writeString(message);
        dest.writeString(access_token);
        dest.writeString(error);
        dest.writeString(is_subscribed);
    }
}
