package com.ott.tv.database.homeContent;


import androidx.lifecycle.LiveData;

import com.ott.tv.model.home_content.HomeContent;

import retrofit2.http.Query;

public interface HomeContentDao {
    void insertHomeContentData(HomeContent homeContent);

    void updateHomeContentData(HomeContent homeContent);

    void deleteAllHomeContentData();

    LiveData<HomeContent> getLiveHomeContentData();

    HomeContent getHomeContentData();
}
