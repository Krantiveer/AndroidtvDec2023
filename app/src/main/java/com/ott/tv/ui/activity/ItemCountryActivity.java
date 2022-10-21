package com.ott.tv.ui.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.ott.tv.R;

public class ItemCountryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_country);
        findViewById(R.id.item_country_fragment).setBackgroundColor(getResources().getColor(R.color.black_color));
    }
}
