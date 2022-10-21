package com.ott.tv.database.homeContent.converters;

import androidx.room.TypeConverter;
import com.ott.tv.model.home_content.FeaturedTvChannel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FeaturedTvConverter {
    @TypeConverter
    public static String fromArrayList(List<FeaturedTvChannel> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<FeaturedTvChannel> jsonToList(String value){
        Type listType = new TypeToken<List<FeaturedTvChannel>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
