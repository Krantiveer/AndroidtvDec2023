package com.ott.tv.model.phando;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentSubscription implements Parcelable {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("package_name")
    @Expose
    private String package_name;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @SerializedName("price")
    @Expose
    private Integer price;

    @SerializedName("payment_id")
    @Expose
    private String payment_id;

    protected CurrentSubscription(Parcel in) {
        id = in.readString();
        payment_id = in.readString();
    }

    public static final Creator<CurrentSubscription> CREATOR = new Creator<CurrentSubscription>() {
        @Override
        public CurrentSubscription createFromParcel(Parcel in) {
            return new CurrentSubscription(in);
        }

        @Override
        public CurrentSubscription[] newArray(int size) {
            return new CurrentSubscription[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(payment_id);
    }
}