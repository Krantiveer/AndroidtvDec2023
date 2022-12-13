package com.ott.tv.ui.activity

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.ott.tv.R
import com.ott.tv.fragments.SearchFragmentPhando


class SearchActivity_Phando : FragmentActivity() {
    companion object {
        lateinit var progressBar: ProgressBar
        lateinit var accestoken: String
        var indexOfRow: Int = 0
    }

    lateinit var searchFragment: SearchFragmentPhando
    lateinit var sharedPreferences: SharedPreferences
    lateinit var btnsearch: ImageButton
    lateinit var editText: EditText
  //  lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_phando)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        editText = findViewById(R.id.edtsearch)
        btnsearch = findViewById(R.id.btnsearch)

      /*  FirebaseApp.initializeApp(this);
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent("SEARCH_SCREEN", Bundle.EMPTY)
*/
        searchFragment =
            supportFragmentManager.findFragmentById(R.id.searchFragment) as SearchFragmentPhando
        progressBar = findViewById(R.id.progress_search)






        sharedPreferences = getSharedPreferences("LoginData", Activity.MODE_PRIVATE);
        accestoken = sharedPreferences.getString("access_token", "")!!



        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                if (editText.text.toString().equals("")) {
                    Toast.makeText(
                        applicationContext,
                        "please type keyword for Search",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    var searchText: String
                    searchText = editText.text.toString()
                //    searchFragment.loadData(searchText, accestoken)
                    searchFragment.getQueryData(searchText)
                    btnsearch.requestFocus()
                }
                true
            } else {
                false
            }
        }


        btnsearch.setOnClickListener {
            var searchText: String
            if (editText.text.toString().equals("")) {
                Toast.makeText(
                    applicationContext,
                    "please type keyword for Search",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                searchText = editText.text.toString()
              //  searchFragment.loadData(searchText, accestoken)
                searchFragment.getQueryData(searchText)

                // firebaseAnalytics.logEvent("SEARCH_RESULT", Bundle.EMPTY)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        editText.requestFocus()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                if (searchFragment!!.requireView().hasFocus())
                {
                    searchFragment!!.requireView().clearFocus()
                  //  edtsearch.requestFocus()
                    editText.requestFocus()
                    editText.setText("")
                    true
                } else {
                    super.onKeyDown(keyCode, event)
                }
            }


            else -> super.onKeyUp(keyCode, event)

        }
    }
}




