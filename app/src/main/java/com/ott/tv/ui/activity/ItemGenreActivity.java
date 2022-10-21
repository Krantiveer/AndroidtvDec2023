package com.ott.tv.ui.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.ott.tv.R;

public class ItemGenreActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_genre);
        findViewById(R.id.item_genre_fragment).setBackgroundColor(getResources().getColor(R.color.black_color));
    }
}
