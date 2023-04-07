package com.ott.tv.database.homeContent.converters;

import com.ott.tv.model.home_content.FeaturedTvChannel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FeaturedTvConverter {
    public static String fromArrayList(List<FeaturedTvChannel> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<FeaturedTvChannel> jsonToList(String value){
        Type listType = new TypeToken<List<FeaturedTvChannel>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
