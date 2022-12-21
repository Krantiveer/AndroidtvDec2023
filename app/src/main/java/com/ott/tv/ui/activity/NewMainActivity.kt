package com.ott.tv.ui.activity

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.ott.tv.Constants
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
        binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            applicationContext
        )

        val call: Call<AppInfo> = api.getAppInfo( "Android",accessToken)
        call.enqueue(object : Callback<AppInfo?> {
            override fun onResponse(call: Call<AppInfo?>, response: Response<AppInfo?>) {
                if (response.code() == 200) {

                    onGetAppInfoSuccess(response.body()!!)

                    //  homeContent.setHomeContentId(1);
                    //   homeContent.getSlider();
                    //  loadSliderRows(homeContent.getSlider().getSlideArrayList());

                    //   loadRows();
                    /*if (movieList.size() <= 0) {

                    }

                    for (BrowseData movie : movieList) {

                    }
                    //   movies.addAll(movieList);*/
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
            val dialog = androidx.appcompat.app.AlertDialog.Builder(this, R.style.MaterialDialogSheet)
            dialog.setTitle("Update Available")
            dialog.setMessage("A new version of " + getString(R.string.app_name) + " is available on Play Store. Do you want to update?")
            dialog.setCancelable(false)

            dialog.setPositiveButton("Yes, update") { dialog, which ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")))
                } catch (ex: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
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

        }
        /*if (rowsContainerFocused) {
            toggleHeadersFragment(true)
            rowsContainerFocused = false
            if (Constants.IS_FROM_HOME) {
                focus = 1
            }
            headersFragment.getView().requestFocus()

        }*/ else {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { dialog, id -> finishAffinity() }
                .setNegativeButton(

                    "No"
                ) { dialog, id -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }
    }


    fun onMenuSelection(type: String, title: String) {
        //   Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()


/*
        var bundle : Bundle?=null
        bundle!!.putInt("menu", 1)
        bundle!!.putString("type", type)
*/

        binding.tvTitle.text = title
        if (title.isEmpty()) {
            binding.tvTitle.isVisible = false
        } else if (type.equals("search")||type.equals("home")||type.equals("profile")) {
            // binding.tvTitle.isVisible = true
            binding.tvTitle.isVisible = false
        } else {
            binding.tvTitle.isVisible = true
        }
        val bundle = bundleOf(
            "menu" to 1,
            "type" to type

        )
        if (type.equals("search") || type.equals("Search")) {
            val intent = Intent(this, SearchActivity_Phando::class.java)
            startActivity(intent)
        }
        else if (type.equals("watchlist") || type.equals("Watchlist")) {
            val newFragment = ShowWatchlistFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }else if(type.equals("profile")){
            val newFragment = MyAccountFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }else if(type.equals("home")){
            val newFragment = HomeFragmentNewUI()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }

        else {
            val newFragment = HomeFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("LoginActivity", "***** keyCode =" + keyCode + "event :" + event)
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                onBackPressed()
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_DPAD_UP_LEFT, KeyEvent.KEYCODE_DPAD_UP_RIGHT, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_DOWN_LEFT, KeyEvent.KEYCODE_DPAD_DOWN_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_UP -> {
                Log.e("LoginActivity", "movieIndex : ")
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}