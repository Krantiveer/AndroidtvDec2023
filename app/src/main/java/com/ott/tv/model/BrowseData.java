package com.ott.tv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.phando.LatestMovieList;

import java.util.List;

/*data class BrowseData (
    val displayType: String,
    val id: Int?,
    val list: ArrayList<Video>,
    val title: String,
    val image_orientation: Int? = 0,
)*/


import android.os.Parcel;
import android.os.Parcelable;


public class BrowseData implements Parcelable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_code")
    @Expose
    private Integer status_code;
    @SerializedName("message")
    @Expose
    private Integer message;
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("displayType")
    @Expose
    private String displayType;

    @SerializedName("image_orientation")
    @Expose
    private Integer image_orientation;

    @SerializedName("list")
    @Expose
    private List<LatestMovieList> list;

    protected BrowseData(Parcel in) {
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            status_code = null;
        } else {
            status_code = in.readInt();
        }
        if (in.readByte() == 0) {
            message = null;
        } else {
            message = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        displayType = in.readString();
        if (in.readByte() == 0) {
            image_orientation = null;
        } else {
            image_orientation = in.readInt();
        }
        list = in.createTypedArrayList(LatestMovieList.CREATOR);
    }

    public static final Creator<BrowseData> CREATOR = new Creator<BrowseData>() {
        @Override
        public BrowseData createFromParcel(Parcel in) {
            return new BrowseData(in);
        }

        @Override
        public BrowseData[] newArray(int size) {
            return new BrowseData[size];
        }
    };

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Integer getImage_orientation() {
        return image_orientation;
    }

    public void setImage_orientation(Integer image_orientation) {
        this.image_orientation = image_orientation;
    }

    public List<LatestMovieList> getList() {
        return list;
    }

    public void setList(List<LatestMovieList> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        if (status_code == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status_code);
        }
        if (message == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(message);
        }
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(displayType);
        if (image_orientation == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(image_orientation);
        }
        dest.writeTypedList(list);
    }
}