package com.ott.tv.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.ott.tv.databinding.ActivityNewMainBinding
import com.ott.tv.databinding.LayoutMenuBinding
import com.ott.tv.fragments.*


class NewMainActivity : FragmentActivity() {
    private lateinit var binding: ActivityNewMainBinding
    private var itemBinding: LayoutMenuBinding? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.menuSection.id, MenuFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(binding.browserSection.id, HomeFragmentNewUI())
            .commit()

    }

    fun onMenuFocus(onFocus: Boolean) {
        if (onFocus) {
            Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()

            binding.slidingPaneLayout.openPane()
        } else {
            Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()

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
        } else if (title.equals("Search")) {
            // binding.tvTitle.isVisible = true
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