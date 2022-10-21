package com.ott.tv.model.home_content;

import androidx.appcompat.widget.ViewUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Slider {
    @SerializedName("slider_type")
    @Expose
    private String sliderType;

   // @TypeConverters(SliderTypeConverter.class)
    @SerializedName("slide")
    @Expose
    private ArrayList<Video> slideArrayList = null;

    public String getSliderType() {
        return sliderType;
    }

    public void setSliderType(String sliderType) {
        this.sliderType = sliderType;
    }

    public ArrayList<Video> getSlideArrayList() {
        return slideArrayList;
    }

    public void setSlideArrayList(ArrayList<Video> slideArrayList) {
        this.slideArrayList = slideArrayList;
    }
}
