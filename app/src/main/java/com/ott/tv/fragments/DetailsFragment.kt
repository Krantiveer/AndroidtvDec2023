package com.ott.tv.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.ott.tv.R
import com.ott.tv.model.phando.ShowWatchlist
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.ui.presenter.CardPresenter_ShowRelatedVideo_vertical


/**
 * Created by Vikas Kumar Singh on 20/04/20.
 */
class DetailsFragment : BrowseSupportFragment() {

    private val TAG = DetailsFragment::class.java.simpleName
    private lateinit var sharedPreferences: SharedPreferences
    private var accestoken: String? = null
    private var rowAdapter: ArrayObjectAdapter? = null
    private var cardPresenterHeader: HeaderItem? = null
    private var cardRowAdapter: ArrayObjectAdapter? = null
    private var z = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            requireActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        accestoken = sharedPreferences.getString("access_token", null)

        headersState = BrowseSupportFragment.HEADERS_DISABLED
        isHeadersTransitionOnBackEnabled = true

        setupEventListeners()
        this.getView()?.setBackgroundColor(Color.TRANSPARENT);

    }

    fun setData() {
        var listRowPresenter = ListRowPresenter(FocusHighlight.ZOOM_FACTOR_NONE, false)
        listRowPresenter.selectEffectEnabled = false
        rowAdapter = ArrayObjectAdapter(listRowPresenter)


        /*if (DetailsActivityPhando.detailsData!= null){
            if (DetailsActivityPhando.detailsData!!.trailers!!.isNotEmpty()) {
                cardPresenterHeader = HeaderItem(z.toLong(), "Trailers")
                var cardPresenter = CardPresenter()
                cardRowAdapter = ArrayObjectAdapter(cardPresenter)
                for (element in DetailsActivityPhando.detailsData!!.trailers!!) {
                    cardRowAdapter!!.add(element)
                }
                rowAdapter!!.add(ListRow(cardPresenterHeader, cardRowAdapter))
                z++
            }
        }*/


//        if (DetailsActivity.detailsData!!.ccFiles!!.isNotEmpty())
//        {
//            for (element in DetailsActivity.detailsData!!.ccFiles!!)
//            {
//                cardRowAdapter!!.add(element)
//            }
//        }
        if (DetailsActivityPhando.singleDetailsRelated!!.list.related.isNotEmpty()) {

            cardPresenterHeader = HeaderItem(z.toLong(), "More like this")
            var cardPresenter = CardPresenter_ShowRelatedVideo_vertical()
            cardRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (element in DetailsActivityPhando.singleDetailsRelated!!.list!!.related!!) {
                cardRowAdapter!!.add(element)
            }
            rowAdapter!!.add(ListRow(cardPresenterHeader, cardRowAdapter))
        }
        adapter = rowAdapter
        /*  if (DetailsActivityPhando.tvResume.visibility == View.VISIBLE) {
              DetailsActivityPhando.tvResume.requestFocus()
          } else {
              DetailsActivityPhando.tvWatchNow.requestFocus()
          }*/
    }

    private fun setupEventListeners() {
        setOnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            DetailsActivityPhando.indexOfRow = rowsSupportFragment.selectedPosition
            DetailsActivityPhando.indexOfItem =
                ((row as ListRow).adapter as ArrayObjectAdapter).indexOf(item)
            Log.i(TAG, "setupEventListeners:don ")
            Log.e(
                TAG,
                "    ${DetailsActivityPhando.indexOfRow}         ${DetailsActivityPhando.indexOfItem}"
            )
        }

        setOnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->

            Log.e("@@item", item.toString())
            if (item is ShowWatchlist) {
                if (item.type == "M" && item.is_live.toString().equals("0", ignoreCase = true)) {
                    val intent = Intent(activity, DetailsActivityPhando::class.java)
                    if (item.type != null) intent.putExtra("type", item.type)

                    if (item.thumbnail != null) intent.putExtra(
                        "thumbImage", item.thumbnail
                    )
                    if (item.id != null) intent.putExtra(
                        "video_id", item.id.toString()
                    )
                    if (item.title != null) intent.putExtra(
                        "title", item.title
                    )
                    if (item.detail != null) intent.putExtra(
                        "description", item.detail
                    )
                    if (item.release_date != null) intent.putExtra(
                        "release", item.release_date
                    )
                    if (item.duration_str != null) intent.putExtra(
                        "duration", item.duration_str
                    )
                    if (item.maturity_rating != null) intent.putExtra(
                        "maturity_rating", item.maturity_rating
                    )


                    if (item.is_free != null) intent.putExtra(
                        "ispaid", item.is_free.toString()
                    )
                    if (item.language_str != null) intent.putExtra(
                        "language_str", item.language_str
                    )
                    if (item.is_live != null) intent.putExtra(
                        "is_live", item.is_live
                    )
                    if (item.rating != null) intent.putExtra(
                        "rating", item.rating.toString()
                    )
                    if (item.trailers != null && item.trailers.size > 0 && item.trailers.get(0) != null && item.trailers.get(
                            0
                        ).media_url != null
                    ) {
                        intent.putExtra("trailer", item.trailers.get(0).media_url)
                    }

//kranti
                    if (item.genres != null) {
                        if (item.genres.size > 0) {
                            var genres: String
                            genres = item.genres.get(0)
                            for (i in 1 until item.genres.size) {
                                genres = genres + "," + item.genres.get(i)
                            }
                            intent.putExtra("genres", genres)
                        }
                    }
                    startActivity(intent)

                }
                if (item.type == "T") {
                    val intent = Intent(activity, DetailsActivityPhando::class.java)
                    if (item.type != null) intent.putExtra(
                        "type", item.type
                    )
                    if (item.thumbnail != null) intent.putExtra(
                        "thumbImage", item.thumbnail
                    )
                    if (item.id != null) intent.putExtra(
                        "video_id", item.id.toString()
                    )
                    if (item.title != null) intent.putExtra(
                        "title", item.title
                    )
                    if (item.detail != null) intent.putExtra(
                        "description", item.detail
                    )
                    if (item.release_date != null) intent.putExtra(
                        "release", item.release_date
                    )
                    if (item.duration_str != null) intent.putExtra(
                        "duration", item.duration_str
                    )
                    if (item.maturity_rating != null) intent.putExtra(
                        "maturity_rating", item.maturity_rating
                    )
                    if (item.is_free != null) intent.putExtra(
                        "ispaid", item.is_free.toString()
                    )
                    if (item.language_str != null) intent.putExtra(
                        "language_str", item.language_str
                    )
                    if (item.is_live != null) intent.putExtra(
                        "is_live", item.is_live.toString()
                    )
                    if (item.rating != null) intent.putExtra(
                        "rating", item.rating.toString()
                    )
                    if (item.trailers != null && item.trailers.size > 0 && item.trailers.get(0) != null && item.trailers.get(
                            0
                        ).media_url != null
                    ) {
                        intent.putExtra("trailer", item.trailers.get(0).media_url)
                    }
                    if (item.genres != null) {
                        var genres: String
                        genres = item.genres.get(0)
                        for (i in 1 until item.genres.size) {
                            genres = genres + "," + item.genres.get(i)
                        }
                        intent.putExtra("genres", genres)
                    }
                    startActivity(intent)
                }

                if (item.type == "M" && item.is_live.toString().equals("1", ignoreCase = true)) {
                    val intent = Intent(activity, DetailsActivityPhando::class.java)
                    if (item.type != null) intent.putExtra(
                        "type", item.type
                    )
                    if (item.thumbnail != null) intent.putExtra(
                        "thumbImage", item.thumbnail
                    )
                    if (item.id != null) intent.putExtra(
                        "video_id", item.id.toString()
                    )
                    if (item.title != null) intent.putExtra(
                        "title", item.title
                    )
                    if (item.detail != null) intent.putExtra(
                        "description", item.detail
                    )
                    if (item.release_date != null) intent.putExtra(
                        "release", item.release_date
                    )
                    if (item.duration_str != null) intent.putExtra(
                        "duration", item.duration_str
                    )
                    if (item.maturity_rating != null) intent.putExtra(
                        "maturity_rating", item.maturity_rating
                    )
                    if (item.is_free != null) intent.putExtra(
                        "ispaid", item.is_free.toString()
                    )
                    if (item.language_str != null) intent.putExtra(
                        "language_str", item.language_str
                    )
                    if (item.is_live != null) intent.putExtra(
                        "is_live", item.is_live.toString()
                    )
                    if (item.rating != null) intent.putExtra(
                        "rating", item.rating.toString()
                    )
                    if (item.trailers != null && item.trailers.size > 0 && item.trailers.get(0) != null && item.trailers.get(
                            0
                        ).media_url != null
                    ) {
                        intent.putExtra("trailer", item.trailers.get(0).media_url)
                    }
                    if (item.genres != null) {
                        var genres: String
                        genres = item.genres.get(0)
                        for (i in 1 until item.genres.size) {
                            genres = genres + "," + item.genres.get(i)
                        }
                        intent.putExtra("genres", genres)
                    }
                    startActivity(intent)
                }


            }
        }
    }

}