package com.ott.tv.database.homeContent;

import android.content.Context;

import com.ott.tv.model.home_content.HomeContent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@TypeConverters(SliderTypeConverter.class)
public abstract class HomeContentDatabase  {
    private static HomeContentDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract HomeContentDao homeContentDao();
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized HomeContentDatabase getInstance(Context context){
        if (instance == null){
        }
        return instance;
    }
}
