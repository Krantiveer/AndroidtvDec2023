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
