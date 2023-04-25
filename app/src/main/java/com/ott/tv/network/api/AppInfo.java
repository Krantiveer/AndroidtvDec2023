package com.ott.tv.network.api;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TrilokiNath on 19-09-2017.
 */

public class AppInfo implements Parcelable {
    @SerializedName("app_type")
    private String appType;
    @SerializedName("app_version")
    private String currentVersion;
    @SerializedName("force_update")
    private String forceUpdate;
    @SerializedName("npawAccountKey")
    private String npawAccountKey;
    @SerializedName("isnpawEnable")
    private Boolean isnpawEnable;


    protected AppInfo(Parcel in) {
        appType = in.readString();
        currentVersion = in.readString();
        forceUpdate = in.readString();
        npawAccountKey = in.readString();
        byte tmpIsnpawEnable = in.readByte();
        isnpawEnable = tmpIsnpawEnable == 0 ? null : tmpIsnpawEnable == 1;
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

    @Override
    public String toString() {
        return "AppInfo{" +
                "appType='" + appType + '\'' +
                ", currentVersion='" + currentVersion + '\'' +
                ", forceUpdate='" + forceUpdate + '\'' +
                '}';
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public int getCurrentVersion() {
        try {
            return Integer.parseInt(currentVersion);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public boolean isForceUpdate() {
        return "1".equals(forceUpdate);
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(appType);
        dest.writeString(currentVersion);
        dest.writeString(forceUpdate);
        dest.writeString(npawAccountKey);
        dest.writeByte((byte) (isnpawEnable == null ? 0 : isnpawEnable ? 1 : 2));
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public String getNpawAccountKey() {
        return npawAccountKey;
    }

    public void setNpawAccountKey(String npawAccountKey) {
        this.npawAccountKey = npawAccountKey;
    }

    public Boolean getIsnpawEnable() {
        return isnpawEnable;
    }

    public void setIsnpawEnable(Boolean isnpawEnable) {
        this.isnpawEnable = isnpawEnable;
    }
}
