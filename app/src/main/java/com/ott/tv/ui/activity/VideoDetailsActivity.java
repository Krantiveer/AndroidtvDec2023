package com.ott.tv.ui.activity;

import android.os.Bundle;

import com.ott.tv.R;

public class VideoDetailsActivity extends LeanbackActivity {
    public static final String TRANSITION_NAME = "t_for_transition";
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String VIDEO = "Video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
    }
}
