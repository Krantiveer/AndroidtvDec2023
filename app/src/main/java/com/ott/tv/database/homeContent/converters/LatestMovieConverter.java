package com.ott.tv.database.homeContent.converters;


import com.ott.tv.model.home_content.LatestMovie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LatestMovieConverter {
    public static String fromList(List<LatestMovie> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }


    public static List<LatestMovie> jsonToList(String value){
        Type listType = new TypeToken<List<LatestMovie>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
