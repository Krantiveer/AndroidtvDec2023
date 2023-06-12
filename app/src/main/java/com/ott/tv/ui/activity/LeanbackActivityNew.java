package com.ott.tv.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;

/**
 * This parent class contains common methods that run in every activity such as search.
 */
public abstract class LeanbackActivityNew extends FragmentActivity {
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
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
                Log.e("LeanbakActivitynew", "movieIndex : ");
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
