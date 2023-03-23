package com.ott.tv.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    private String success;

    protected CouponModel(Parcel in) {
        status = in.readString();
        message = in.readString();
        success = in.readString();
    }

    public static final Creator<CouponModel> CREATOR = new Creator<CouponModel>() {
        @Override
        public CouponModel createFromParcel(Parcel in) {
            return new CouponModel(in);
        }

        @Override
        public CouponModel[] newArray(int size) {
            return new CouponModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeString(success);
    }
}
