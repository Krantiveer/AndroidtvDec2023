package com.ott.tv.ui.activity

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.StateSet
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.google.gson.annotations.SerializedName
import com.ott.tv.BuildConfig
import com.ott.tv.MapFragmentUVTV
import com.ott.tv.R
import com.ott.tv.databinding.ActivityNewMainBinding
import com.ott.tv.databinding.LayoutMenuBinding
import com.ott.tv.fragments.*
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.AppInfo
import com.ott.tv.network.api.Dashboard
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController


class NewMainActivity : FragmentActivity() {
    private lateinit var binding: ActivityNewMainBinding
    private var itemBinding: LayoutMenuBinding? = null


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Creating an extended library configuration.
        /*  // Creating an extended library configuration.
          val config = YandexMetricaConfig.newConfigBuilder("45c548e2-21f1-4386-934b-3059b7d28b56").build()
          // Initializing the AppMetrica SDK.
          // Initializing the AppMetrica SDK.
          YandexMetrica.activate(applicationContext, config)
          // Automatic tracking of user activity.
          // Automatic tracking of user activity.
          YandexMetrica.enableActivityAutoTracking(application)
  */

        binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            applicationContext
        )
        val call: Call<AppInfo> = api.getAppInfo("androidtv", accessToken)
        call.enqueue(object : Callback<AppInfo?> {
            override fun onResponse(call: Call<AppInfo?>, response: Response<AppInfo?>) {
                if (response.code() == 200) {

                    onGetAppInfoSuccess(response.body()!!)
                    Log.i("appinfo", "onResponse: " + response.body()!!.player_logo_enable +"--"+ response.body()!!.playerLogo)
                    PreferenceUtils.getInstance().setWatermarkLogoUrlPref(
                        this@NewMainActivity,
                        response.body()!!.playerLogo
                    )
                    PreferenceUtils.getInstance().setWatermarkEnablePref(
                        this@NewMainActivity,
                        response.body()!!.player_logo_enable
                    )


                    //  homeContent.setHomeContentId(1);
                    //   homeContent.getSlider();
                    //  loadSliderRows(homeContent.getSlider().getSlideArrayList());

                    //   loadRows();
                } else if (response.code() == 401) {

                    // signOut();
                } else if (response.errorBody() != null) {
                    if (AccessController.getContext() != null) {
                        Toast.makeText(
                            applicationContext,
                            "sorry! Something went wrong. Please try again after some time" + response.errorBody(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //  CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                } else {
                    if (AccessController.getContext() != null) {
                        Toast.makeText(
                            applicationContext,
                            "sorry! Something went wrong. Please try again after some time",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<AppInfo?>, t: Throwable) {
                //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                if (AccessController.getContext() != null) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        })

        supportFragmentManager.beginTransaction().replace(binding.menuSection.id, MenuFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(binding.browserSection.id, HomeFragmentNewUI())
            .commit()

    }

    private fun onGetAppInfoSuccess(appInfo: AppInfo) {
        val storeVersion = appInfo.currentVersion
        val forceUpdate = appInfo.isForceUpdate
        PreferenceUtils.getInstance().setNpawEnablePref(this, appInfo.isnpawEnable)
        PreferenceUtils.getInstance().setNpawAccountKeyPref(this, appInfo.npawAccountKey)

        var currentVersion = 0
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            currentVersion = pInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        Log.e("Store app version: $storeVersion", "")
        Log.e("Current app version: $currentVersion", "")

        if (currentVersion < storeVersion) {
            // Need to update application
            val dialog =
                androidx.appcompat.app.AlertDialog.Builder(this, R.style.MaterialDialogSheet)
            dialog.setTitle("Update Available")
            dialog.setMessage("A new version of " + getString(R.string.app_name) + " is available on App Store. Do you want to update?")
            dialog.setCancelable(false)

            dialog.setPositiveButton("Yes, update") { dialog, which ->
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                } catch (ex: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                }

                this@NewMainActivity.finish()
            }

            if (!forceUpdate) {
                dialog.setNegativeButton("No, leave it!") { dialog, which -> }
            }
            dialog.setIcon(android.R.drawable.ic_dialog_alert)
            dialog.show()
        }
    }

    fun onMenuFocus(onFocus: Boolean) {
        if (onFocus) {
            //        Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()
            binding.slidingPaneLayout.openPane()
        } else {
            //      Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()
            binding.slidingPaneLayout.closePane()
            // Toast.makeText(this, "close", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        if (!binding.slidingPaneLayout.closePane()) {
            binding.slidingPaneLayout.openPane();

        } else {
            // binding.slidingPaneLayout.openPane();
            val dialog: Dialog
            dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_dialog_exit)
            dialog.setCancelable(true)
            val button_no = dialog.findViewById<View>(R.id.button_no) as Button
            button_no.background = getSelectorDrawable()
            button_no.setOnClickListener { dialog.dismiss() }

            val button_yes = dialog.findViewById<View>(R.id.button_yes) as Button
            button_yes.background = getSelectorDrawable()
            button_yes.setOnClickListener {
                dialog.dismiss()
                this.finishAffinity()
            }
            dialog.show()
        }
    }

    private fun getSelectorDrawable(): StateListDrawable? {
        val out = StateListDrawable()
        out.addState(
            intArrayOf(android.R.attr.state_focused), createFocusedDrawable(
                Color.parseColor("#EF3C23")
            )
        )
        out.addState(
            StateSet.WILD_CARD,
            createNormalDrawable(Color.parseColor("#80858B"))
        )
        return out
    }

    private fun createFocusedDrawable(color: Int): GradientDrawable? {
        val out = GradientDrawable()
        out.setColor(color)
        return out
    }

    private fun createNormalDrawable(color: Int): GradientDrawable? {
        val out = GradientDrawable()
        out.setColor(color)
        return out
    }

    fun onMenuSelection(type: String,type_id: String, title: String, gener_id: String) {
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

            } else {

                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*       val intent = Intent(this, SearchActivity_Phando::class.java)
                   startActivity(intent)
                   return*/
        }
        binding.tvTitle.text = title
        if (title.isEmpty()) {
            binding.tvTitle.isVisible = false
        } else if (type.equals("home") || type.equals("profile")) {
            // binding.tvTitle.isVisible = true
            binding.tvTitle.isVisible = false
        } else {
            binding.tvTitle.isVisible = true
        }
        val bundle = bundleOf(

            "menu" to 1,
            "gener_id" to gener_id,
            "type" to type,
            "type_id" to type_id

        )
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

                val newFragment = SearchPhandoUVTVFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
            } else {
                PreferenceUtils.getInstance().setWatchListPref(this, 0)
                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*


            */
        }
        else if (type.equals("viewall")) {
            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()

        } else if (type.equals("genre")) {
            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("watchlist") || type.equals("Watchlist")) {
            val newFragment = ShowWatchlistFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("profile")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = MyAccountFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("uvtv-bharat")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = MapFragmentUVTV()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("home")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragmentNewUI()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }
    }
    fun onMenuSelection(type: String, title: String, gener_id: String) {
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

            } else {

                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*       val intent = Intent(this, SearchActivity_Phando::class.java)
                   startActivity(intent)
                   return*/
        }
        binding.tvTitle.text = title
        if (title.isEmpty()) {
            binding.tvTitle.isVisible = false
        } else if (type.equals("home") || type.equals("profile")) {
            // binding.tvTitle.isVisible = true
            binding.tvTitle.isVisible = false
        } else {
            binding.tvTitle.isVisible = true
        }
        val bundle = bundleOf(

            "menu" to 1,
            "gener_id" to gener_id,
            "type" to type

        )
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

                val newFragment = SearchPhandoUVTVFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
            } else {
                PreferenceUtils.getInstance().setWatchListPref(this, 0)
                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*


            */
        }
        else if (type.equals("viewall")) {
            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()

    /*    } else if (type.equals("genre")) {
            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
*/
        } else if (type.equals("watchlist") || type.equals("Watchlist")) {
            val newFragment = ShowWatchlistFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("profile")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)

            val newFragment = MyAccountFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("uvtv-bharat")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = MapFragmentUVTV()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("home")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragmentNewUI()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("NewMainActivity", "***** keyCode =" + keyCode + "event :" + event)
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                onBackPressed()
                return true
            }

            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_DPAD_UP_LEFT, KeyEvent.KEYCODE_DPAD_UP_RIGHT, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_DOWN_LEFT, KeyEvent.KEYCODE_DPAD_DOWN_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_UP -> {
                Log.e("NewMainActivity", "movieIndex : ")
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}