package com.ott.tv.ui.activity;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.ott.tv.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivityTv extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    Log.e("viewAllActivity", "***** keyCode =" + keyCode + "event :" + event);
     /*   return super.onKeyDown(keyCode, event);*/

        // When using DPad, show all the OSD so that focus can move freely
        // from/to ActionBar to/from PlayerController
    switch (keyCode) {
        case KeyEvent.KEYCODE_BACK: return false;
        case KeyEvent.KEYCODE_DPAD_CENTER: return  false;
        case KeyEvent.KEYCODE_DPAD_LEFT: return  false;
        case KeyEvent.KEYCODE_DPAD_RIGHT: return  false;
        case KeyEvent.KEYCODE_DPAD_UP:
        Log.e("SPLASH ACTIVITY", "movieIndex : " );

        return  false;

        case KeyEvent.KEYCODE_DPAD_UP_LEFT: return  false;
        case KeyEvent.KEYCODE_DPAD_UP_RIGHT: return  false;
        case KeyEvent.KEYCODE_DPAD_DOWN: return  false;
        case KeyEvent.KEYCODE_DPAD_DOWN_LEFT: return  false;
        case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT: return  false;
    }
        return super.onKeyDown(keyCode, event);
    }
}
