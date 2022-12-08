package com.ott.tv.ui.activity

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import com.ott.tv.databinding.ActivityNewMainBinding
import com.ott.tv.databinding.LayoutMenuBinding
import com.ott.tv.fragments.HomeFragment
import com.ott.tv.fragments.HomeFragmentNewUI
import com.ott.tv.fragments.MenuFragment


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


    fun onMenuSelection(type :String){
        Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()


/*
        var bundle : Bundle?=null
        bundle!!.putInt("menu", 1)
        bundle!!.putString("type", type)
*/

        val bundle = bundleOf(
            "menu" to 1,
            "type" to type

        )
        val newFragment = HomeFragment()
        newFragment.setArguments(bundle)

        supportFragmentManager.beginTransaction()
            .replace(binding.browserSection.id, newFragment)
            .commit()





    }
}