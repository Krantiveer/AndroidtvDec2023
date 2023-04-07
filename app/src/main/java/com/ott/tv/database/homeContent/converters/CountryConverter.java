package com.ott.tv.database.homeContent.converters;


import com.ott.tv.model.home_content.AllCountry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CountryConverter {
    public static String fromArrayList(List<AllCountry> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<AllCountry> jsonToList(String value){
        Type listType = new TypeToken<List<AllCountry>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
