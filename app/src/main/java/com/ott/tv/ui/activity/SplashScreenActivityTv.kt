package com.ott.tv.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.VideoView
import com.ott.tv.BuildConfig
import com.ott.tv.Config
import com.ott.tv.R
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.AppInfo
import com.ott.tv.network.api.Dashboard
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController

@SuppressLint("CustomSplashScreen")
class SplashScreenActivityTv : Activity() {

    lateinit var sharedPreferences: SharedPreferences
    var accestoken: String? = null

    private val TAG = SplashScreenActivityTv::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            applicationContext
        )
        val call: Call<AppInfo> = api.getAppInfo("androidtv", accessToken)
        call.enqueue(object : Callback<AppInfo?> {
            override fun onResponse(call: Call<AppInfo?>, response: Response<AppInfo?>) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: " + response.body()!!.websiteurl)
                    PreferenceUtils.getInstance().setWebsiteUrlPref(
                        this@SplashScreenActivityTv,
                        response.body()!!.websiteurl
                    )
                } else if (response.code() == 401) {

                    // signOut();
                } else if (response.errorBody() != null) {
                    //  CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                } else {
                    if (AccessController.getContext() != null) {
                    }
                }
            }

            override fun onFailure(call: Call<AppInfo?>, t: Throwable) {
                //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                if (AccessController.getContext() != null) {
                    //      Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        })


        if (BuildConfig.FLAVOR.equals(
                "mitwa_tv",
                ignoreCase = true
            ) || BuildConfig.FLAVOR.equals(
                "uvtv",
                ignoreCase = true
            ) || BuildConfig.FLAVOR.equals(
                "adnott",
                ignoreCase = true
            ) || BuildConfig.FLAVOR.equals("amuzi", ignoreCase = true) || BuildConfig.FLAVOR.equals(
                "darshott",
                ignoreCase = true
            ) || BuildConfig.FLAVOR.equals("omtvott", ignoreCase = true)
        ) {
            findViewById<LinearLayout>(R.id.splash_screen_ll).visibility == View.INVISIBLE

            val path = "android.resource://" + packageName + "/" + R.raw.splashvideio
            findViewById<VideoView>(R.id.imageView).setVideoURI(Uri.parse(path))
            findViewById<VideoView>(R.id.imageView).start()
            findViewById<VideoView>(R.id.imageView).setOnCompletionListener {
                openHome()

            }

        } else {
            findViewById<LinearLayout>(R.id.splash_screen_ll).visibility = View.VISIBLE
            if (BuildConfig.FLAVOR.equals("candor", ignoreCase = true) || BuildConfig.FLAVOR.equals(
                    "naaptolott"
                ) || BuildConfig.FLAVOR.equals("omtvott")
            ) {
                findViewById<ImageView>(R.id.splash_img_view).visibility = View.INVISIBLE

            }
            //  findViewById<TextView>(R.id.builVersion).setText("App Version:" + BuildConfig.VERSION_CODE)

            openHomeFun()
            Log.e(TAG, "Screen : ${SplashScreenActivityTv::class.java.simpleName}")
        }


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

    private fun openHomeFun() {
        Handler().postDelayed({
            if (this != null) {
                if (PreferenceUtils.isLoggedIn(this)) {
                    val intent = Intent(this, NewMainActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, LoginChooserActivity::class.java)
                    startActivity(intent)
                    /*Intent intent = new Intent(getContext(), LeanbackActivity.class);
                    startActivity(intent);
                    */
                }
                this.finishAffinity()
                this.overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }, 1000)

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