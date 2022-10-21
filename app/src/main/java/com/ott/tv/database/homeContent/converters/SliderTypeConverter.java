package com.ott.tv.database.homeContent.converters;

import androidx.room.TypeConverter;
import com.ott.tv.model.home_content.Slider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SliderTypeConverter {
    @TypeConverter
    public static String fromArrayList(Slider slider){
        Gson gson = new Gson();
        return gson.toJson(slider);
    }

    @TypeConverter
    public static Slider jsonToList(String value){
        Type listType = new TypeToken<Slider>() {}.getType();

        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
