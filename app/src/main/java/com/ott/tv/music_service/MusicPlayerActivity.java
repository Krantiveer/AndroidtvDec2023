package com.ott.tv.music_service;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.ott.tv.R;

public class MusicPlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("LoginActivity", "***** keyCode =" + keyCode + "event :" + event);
        /*   return super.onKeyDown(keyCode, event);*/

        // When using DPad, show all the OSD so that focus can move freely
        // from/to ActionBar to/from PlayerController
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:

            case KeyEvent.KEYCODE_DPAD_UP_LEFT:

            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:

            case KeyEvent.KEYCODE_DPAD_DOWN:

            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:

            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_LEFT:

            case KeyEvent.KEYCODE_DPAD_RIGHT:

            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("LoginActivity", "movieIndex : ");
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}