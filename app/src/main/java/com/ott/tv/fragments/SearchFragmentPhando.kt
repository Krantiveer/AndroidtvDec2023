package com.ott.tv.fragments
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ott.tv.Config
import com.ott.tv.Constants
import com.ott.tv.model.phando.SearchContentPhando
import com.ott.tv.model.phando.ShowWatchlist
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.ui.activity.SearchActivity_Phando.Companion.indexOfRow
import com.ott.tv.ui.activity.SearchActivity_Phando.Companion.progressBar
import com.ott.tv.ui.presenter.CardPresenterSearch
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback


class SearchFragmentPhando : BrowseSupportFragment() {
    private val TAG = SearchFragmentPhando::class.java.simpleName
    private var rowAdapter: ArrayObjectAdapter? = null
    private var cardPresenterHeader: HeaderItem? = null
    private var cardRowAdapter: ArrayObjectAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUIElements()
    }

    private fun setupUIElements() {
        headersState = HEADERS_DISABLED
        isHeadersTransitionOnBackEnabled = true
        setUpEvents()
    }
    fun getQueryData( mQuery:String) {
        val searchtext: String = mQuery
        val retrofit = RetrofitClient.getRetrofitInstance()
        val searchApi = retrofit.create(Dashboard::class.java)
        Constants.IS_FROM_HOME = false
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            context
        )
      //  Log.e(SearchFragment.TAG, "getQueryData: $query")
        val call = searchApi.searchVideo(Config.API_KEY, searchtext, "", "", "50")
        call.enqueue(object : Callback<MutableList<ShowWatchlist>> {
            override fun onResponse(
                call: Call<MutableList<ShowWatchlist>>,
                response: retrofit2.Response<MutableList<ShowWatchlist>>
            ) {
                Log.e(TAG, "response: " + response.code())
                var searchContent  = ArrayList<ShowWatchlist>()
                searchContent= response.body() as ArrayList<ShowWatchlist>
                var movieResult: MutableList<ShowWatchlist> = ArrayList()

                if (response.code() == 200) {


                    var listRowPresenter = ListRowPresenter(FocusHighlight.ZOOM_FACTOR_NONE, false)
                    listRowPresenter.selectEffectEnabled = false
                    rowAdapter = ArrayObjectAdapter(listRowPresenter)


                    var cardPresenter = CardPresenterSearch()
                    cardRowAdapter = ArrayObjectAdapter(cardPresenter)
                    for (j in 0 until searchContent!!.size) {
                        indexOfRow = j
                        cardRowAdapter!!.add(searchContent!![j])
                    }

                    if (searchContent!!.size == 0) {
                        cardPresenterHeader =
                            HeaderItem(0, "No search result found for : " + searchtext.toUpperCase())
                    } else {
                        cardPresenterHeader =
                            HeaderItem(0, "Showing search result for : " + searchtext.toUpperCase())
                    }

                    rowAdapter!!.add(ListRow(cardPresenterHeader, cardRowAdapter))
                    adapter = rowAdapter
                    progressBar.visibility = View.GONE

                                   // loadRows(movieResult, tvSeriesResult, tvResult)
                }
            }

            override fun onFailure(call: Call<MutableList<ShowWatchlist>>, t: Throwable) {
            //    Log.e(, "response : " + t.localizedMessage)
            }
        })
    }


    fun loadData(searchtext: String, accestoken: String) {

        val stringRequest = object : StringRequest(
            Request.Method.GET,
            Config.API_SERVER_URL + "mediasearch?term=" + searchtext + "&device_type=tv",
            Response.Listener { response ->
                Log.e(TAG, "" + response)
                var searchContent =
                    Gson().fromJson(response.toString(), SearchContentPhando::class.java)
                var listRowPresenter = ListRowPresenter(FocusHighlight.ZOOM_FACTOR_NONE, false)
                listRowPresenter.selectEffectEnabled = false
                rowAdapter = ArrayObjectAdapter(listRowPresenter)


                var cardPresenter = CardPresenterSearch()
                cardRowAdapter = ArrayObjectAdapter(cardPresenter)
                for (j in 0 until searchContent!!.size) {
                    indexOfRow = j
                    cardRowAdapter!!.add(searchContent!![j])
                }

                if (searchContent!!.size == 0) {
                    cardPresenterHeader =
                        HeaderItem(0, "No search result found for : " + searchtext.toUpperCase())
                } else {
                    cardPresenterHeader =
                        HeaderItem(0, "Showing search result for : " + searchtext.toUpperCase())
                }

                rowAdapter!!.add(ListRow(cardPresenterHeader, cardRowAdapter))
                adapter = rowAdapter
                progressBar.visibility = View.GONE
            },
            Response.ErrorListener {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG)
                    .show()
                progressBar.visibility = View.GONE

            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["tv-app-name"] = "digiana-tv-app"
                headers["Authorization"] = "Bearer " + accestoken
                return headers
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }


    fun setUpEvents() {
        onItemViewSelectedListener = OnItemViewSelectedListener { viewHolder, o, viewHolder1, row ->
//            if (o is HomeModel) {
//                val content = o
//                textTitle.text = "Title: " + content.title
//                textDescription.text = "Description: " + content.desc
//                textDuration.text = "Episodes: " + content.duration
//                Glide.with(activity).load(content.backgroundposter).into(KidsActivity.backgroundImageposter)

//            }
        }
        onItemViewClickedListener =
            OnItemViewClickedListener { viewHolder, item, viewHolder1, row ->
                if (item is ShowWatchlist) {
                    val content = item
                    val intent = Intent(activity, DetailsActivityPhando::class.java)
                    intent.putExtra("data", content)
                    startActivity(intent)
                }
            }
    }


}
