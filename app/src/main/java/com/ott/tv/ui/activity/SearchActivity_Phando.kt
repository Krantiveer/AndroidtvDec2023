package com.ott.tv.ui.activity

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.R
import com.ott.tv.adapter.ContentAdapter
import com.ott.tv.fragments.SearchFragmentPhando
import com.ott.tv.model.phando.ShowWatchlist
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity_Phando : FragmentActivity() {
    companion object {
        lateinit var progressBar: ProgressBar
        lateinit var accestoken: String
        var indexOfRow: Int = 0
    }

    lateinit var searchFragment: SearchFragmentPhando
    lateinit var rvRecommended: RecyclerView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var btnsearch: ImageButton
    lateinit var editText: EditText
    private val mAdapter: ArrayObjectAdapter? = null
  //  lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_phando)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        editText = findViewById(R.id.edtsearch)
        btnsearch = findViewById(R.id.btnsearch)
        rvRecommended = findViewById(R.id.rvRecommended)

     //   fetchMovieData()

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

        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                var searchText: String
                if (editText.text.toString().equals("")) {
                 //   rvRecommended.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "please type keyword for Search",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                  //  rvRecommended.visibility = View.GONE
                    searchText = editText.text.toString()
                    //  searchFragment.loadData(searchText, accestoken)
                    searchFragment.getQueryData(searchText)
                    // firebaseAnalytics.logEvent("SEARCH_RESULT", Bundle.EMPTY)
                }

            }
        })

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


    private fun fetchMovieData() {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this)

        val call = api.getRecommendedList(accessToken)
        call.enqueue(object : Callback<List<ShowWatchlist?>?> {
            override fun onResponse(
                call: Call<List<ShowWatchlist?>?>,
                response: Response<List<ShowWatchlist?>?>
            ) {
                if (response.code() == 200) {
                    if (response.code() == 200) {
                        Log.e("@@response", response.body().toString())
                        val movieList: List<ShowWatchlist?>? = response.body()

                        rvRecommended.setAdapter(
                            ContentAdapter(movieList, applicationContext,
                                ContentAdapter.OnItemClickListener {




                                }),
                        )

                    }
                }
            }

            override fun onFailure(call: Call<List<ShowWatchlist?>?>, t: Throwable) {
                Log.e("Genre Item", "code: " + t.localizedMessage)
            }
        })
    }

}




