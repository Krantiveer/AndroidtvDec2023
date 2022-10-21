package com.ott.tv.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FeaturesGenreConverter {
    @TypeConverter
    public static String fromList(List<FeaturesGenreAndMovie> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<FeaturesGenreAndMovie> jsonToList(String value){
        Type listType = new TypeToken<List<FeaturesGenreAndMovie>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
