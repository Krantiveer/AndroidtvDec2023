package com.ott.tv.database.homeContent.converters;


import com.ott.tv.model.home_content.LatestTvseries;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TvSeriesConverter {
    public static String fromList(List<LatestTvseries> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<LatestTvseries> jsonToList(String value){
        Type listType = new TypeToken<List<LatestTvseries>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
