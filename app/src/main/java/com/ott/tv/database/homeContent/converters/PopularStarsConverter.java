package com.ott.tv.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.ott.tv.model.home_content.PopularStars;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PopularStarsConverter {
    @TypeConverter
    public static String fromArrayList(List<PopularStars> stars){
        Gson gson = new Gson();
        return gson.toJson(stars);
    }

    @TypeConverter
    public static List<PopularStars> jsonToList(String value){
        Type listType = new TypeToken<List<PopularStars>>() {}.getType();

        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
