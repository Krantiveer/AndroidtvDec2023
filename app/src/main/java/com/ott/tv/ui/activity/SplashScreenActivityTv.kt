package com.ott.tv.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.VideoView
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.ott.tv.R
import com.ott.tv.fragments.TvSplashFragment
import com.ott.tv.utils.PreferenceUtils

@SuppressLint("CustomSplashScreen")
class SplashScreenActivityTv : Activity() {

    lateinit var sharedPreferences: SharedPreferences
    var accestoken: String? = null

    private val TAG = SplashScreenActivityTv::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        Log.e(TAG, "Screen : ${SplashScreenActivityTv::class.java.simpleName}")


        val path = "android.resource://" + packageName + "/" + R.raw.splashvideio
        findViewById<VideoView>(R.id.imageView).setVideoURI(Uri.parse(path))
        findViewById<VideoView>(R.id.imageView).start()

        findViewById<VideoView>(R.id.imageView).setOnCompletionListener { openHome() }


    }

    private fun openHome() {

        if (PreferenceUtils.isLoggedIn(this)) {
            val intent = Intent(this, NewMainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginChooserActivity::class.java)
            startActivity(intent)
        }
            this.finishAffinity()


    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("viewAllActivity", "***** keyCode =" + keyCode + "event :" + event)
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> return false
            KeyEvent.KEYCODE_DPAD_CENTER -> return false
            KeyEvent.KEYCODE_DPAD_LEFT -> return false
            KeyEvent.KEYCODE_DPAD_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_UP -> {
                Log.e("SPLASH ACTIVITY", "movieIndex : ")
                return false
            }
            KeyEvent.KEYCODE_DPAD_UP_LEFT -> return false
            KeyEvent.KEYCODE_DPAD_UP_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_DOWN -> return false
            KeyEvent.KEYCODE_DPAD_DOWN_LEFT -> return false
            KeyEvent.KEYCODE_DPAD_DOWN_RIGHT -> return false
        }
        return super.onKeyDown(keyCode, event)
    }
}