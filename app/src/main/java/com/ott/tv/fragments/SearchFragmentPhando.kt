package com.ott.tv.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.*
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
import com.ott.tv.ui.presenter.CardPresenter
import com.ott.tv.ui.presenter.CardPresenterSearch
import com.ott.tv.ui.presenter.CardPresenter_ShowRelatedVideo_vertical
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback


class SearchFragmentPhando : VerticalGridSupportFragment() {
    private val TAG = SearchFragmentPhando::class.java.simpleName
    private var rowAdapter: ArrayObjectAdapter? = null
    private var cardPresenterHeader: HeaderItem? = null
    private var cardRowAdapter: ArrayObjectAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUIElements()
    }

    private fun setupUIElements() {
        /*headersState = HEADERS_DISABLED
        isHeadersTransitionOnBackEnabled = true*/
        setUpEvents()
    }

    fun getQueryData(mQuery: String) {
        val searchtext: String = mQuery
        val retrofit = RetrofitClient.getRetrofitInstance()
        val searchApi = retrofit.create(Dashboard::class.java)
        Constants.IS_FROM_HOME = false
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            context
        )
        //  Log.e(SearchFragment.TAG, "getQueryData: $query")
        val call = searchApi.searchVideo(accessToken, searchtext, "", "", "50")
        call.enqueue(object : Callback<MutableList<ShowWatchlist>> {
            override fun onResponse(
                call: Call<MutableList<ShowWatchlist>>,
                response: retrofit2.Response<MutableList<ShowWatchlist>>
            ) {
                Log.e(TAG, "response: " + response.code())
                var searchContent = ArrayList<ShowWatchlist>()
                var movieResult: MutableList<ShowWatchlist> = ArrayList()
                if (response.code() == 200) {
                    searchContent = response.body() as ArrayList<ShowWatchlist>
                    var listRowPresenter = ListRowPresenter(FocusHighlight.ZOOM_FACTOR_NONE, true)
                    listRowPresenter.selectEffectEnabled = false
                    rowAdapter = ArrayObjectAdapter(listRowPresenter)
                    var cardPresenter = CardPresenter_ShowRelatedVideo_vertical()
                    cardRowAdapter = ArrayObjectAdapter(cardPresenter)
                    for (j in 0 until searchContent!!.size) {
                        indexOfRow = j
                        cardRowAdapter!!.add(searchContent!![j])
                    }
                    if (searchContent!!.size == 0) {
                        cardPresenterHeader =
                            HeaderItem(
                                0,
                                "No search result found for : " + searchtext.toUpperCase()
                            )
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


    fun setUpEvents() {
       /* onItemViewSelectedListener = OnItemViewSelectedListener { viewHolder, o, viewHolder1, row ->
//            if (o is HomeModel) {
//                val content = o
//                textTitle.text = "Title: " + content.title
//                textDescription.text = "Description: " + content.desc
//                textDuration.text = "Episodes: " + content.duration
//                Glide.with(activity).load(content.backgroundposter).into(KidsActivity.backgroundImageposter)

//            }
        }*/
        onItemViewClickedListener =
            OnItemViewClickedListener { viewHolder, item, viewHolder1, row ->
                if (item is ShowWatchlist) {
                    val content = item
                    val intent = Intent(activity, DetailsActivityPhando::class.java)
                    if (content.getType() != null) intent.putExtra(
                        "type",
                        content.getType()
                    )
                    if (content.getThumbnail() != null) intent.putExtra(
                        "thumbImage",
                        content.getThumbnail()
                    )
                    if (content.getId() != null) intent.putExtra(
                        "video_id",
                        content.getId().toString()
                    )
                    if (content.getTitle() != null) intent.putExtra(
                        "title",
                        content.getTitle()
                    )
                    if (content.getDetail() != null) intent.putExtra(
                        "description",
                        content.getDetail()
                    )
                    if (content.getRelease_date() != null) intent.putExtra(
                        "release",
                        content.getRelease_date()
                    )
                    if (content.getDuration_str() != null) intent.putExtra(
                        "duration",
                        content.getDuration_str()
                    )
                    if (content.getMaturity_rating() != null) intent.putExtra(
                        "maturity_rating",
                        content.getMaturity_rating()
                    )
                    if (content.getIs_free() != null) intent.putExtra(
                        "ispaid",
                        content.getIs_free().toString()
                    )
                    if (content.getLanguage_str() != null) intent.putExtra(
                        "language_str",
                        content.getLanguage_str()
                    )
                    if (content.getIs_live() != null) intent.putExtra(
                        "is_live",
                        content.getIs_live()
                    )
                    if (content.getRating() != null) intent.putExtra(
                        "rating",
                        content.getRating().toString()
                    )
                    if (content.getTrailers() != null && content.getTrailers().size > 0 && content.getTrailers()
                            .get(0) != null && content.getTrailers().get(0)
                            .getMedia_url() != null
                    ) {
                        intent.putExtra(
                            "trailer",
                            content.getTrailers().get(0).getMedia_url()
                        )
                    }
                    if (content.getGenres() != null) {
                        var genres: String
                        genres = content.getGenres().get(0)
                        for (i in 1 until content.getGenres().size) {
                            genres = genres + "," + content.getGenres().get(i)
                        }
                        intent.putExtra("genres", genres)
                    }
                    startActivity(intent)
                }

            }

    }
}




