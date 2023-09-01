package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Plan implements Parcelable {
    private int id;
    private String planId;
    private String name;
    private String currency;
    private double amount;
    private String interval;
    private int intervalCount;
    private Integer trialPeriodDays;
    private Integer screen;
    private Integer features;
    private int status;
    private int publisherId;
    private int createdBy;
    private int updatedBy;

    private int packageType;
    private String ottplayLoginId;
    private String ottplayApiToken;
    private String ottplayOperCode;
    private int isPremium;
    private String packageNote;

    protected Plan(Parcel in) {
        id = in.readInt();
        planId = in.readString();
        name = in.readString();
        currency = in.readString();
        amount = in.readDouble();
        interval = in.readString();
        intervalCount = in.readInt();
        if (in.readByte() == 0) {
            trialPeriodDays = null;
        } else {
            trialPeriodDays = in.readInt();
        }
        if (in.readByte() == 0) {
            screen = null;
        } else {
            screen = in.readInt();
        }
        if (in.readByte() == 0) {
            features = null;
        } else {
            features = in.readInt();
        }
        status = in.readInt();
        publisherId = in.readInt();
        createdBy = in.readInt();
        updatedBy = in.readInt();
        packageType = in.readInt();
        ottplayLoginId = in.readString();
        ottplayApiToken = in.readString();
        ottplayOperCode = in.readString();
        isPremium = in.readInt();
        packageNote = in.readString();
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(int intervalCount) {
        this.intervalCount = intervalCount;
    }

    public Integer getTrialPeriodDays() {
        return trialPeriodDays;
    }

    public void setTrialPeriodDays(Integer trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getFeatures() {
        return features;
    }

    public void setFeatures(Integer features) {
        this.features = features;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int packageType) {
        this.packageType = packageType;
    }

    public String getOttplayLoginId() {
        return ottplayLoginId;
    }

    public void setOttplayLoginId(String ottplayLoginId) {
        this.ottplayLoginId = ottplayLoginId;
    }

    public String getOttplayApiToken() {
        return ottplayApiToken;
    }

    public void setOttplayApiToken(String ottplayApiToken) {
        this.ottplayApiToken = ottplayApiToken;
    }

    public String getOttplayOperCode() {
        return ottplayOperCode;
    }

    public void setOttplayOperCode(String ottplayOperCode) {
        this.ottplayOperCode = ottplayOperCode;
    }

    public int getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
    }

    public String getPackageNote() {
        return packageNote;
    }

    public void setPackageNote(String packageNote) {
        this.packageNote = packageNote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(planId);
        dest.writeString(name);
        dest.writeString(currency);
        dest.writeDouble(amount);
        dest.writeString(interval);
        dest.writeInt(intervalCount);
        if (trialPeriodDays == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trialPeriodDays);
        }
        if (screen == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(screen);
        }
        if (features == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(features);
        }
        dest.writeInt(status);
        dest.writeInt(publisherId);
        dest.writeInt(createdBy);
        dest.writeInt(updatedBy);
        dest.writeInt(packageType);
        dest.writeString(ottplayLoginId);
        dest.writeString(ottplayApiToken);
        dest.writeString(ottplayOperCode);
        dest.writeInt(isPremium);
        dest.writeString(packageNote);
    }
// Getter and Setter methods for all fields
}