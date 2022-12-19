package com.ott.tv.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorInt
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.ott.tv.model.ContentList
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.ui.activity.PlayerActivity
import com.ott.tv.ui.presenter.CardPresenter_ShowRelatedVideo_vertical


/**
 * Created by Vikas Kumar Singh on 20/04/20.
 */
class DetailsFragment : BrowseSupportFragment(){

    private val TAG = DetailsFragment::class.java.simpleName
    private lateinit var sharedPreferences: SharedPreferences
    private var accestoken: String? = null
    private var rowAdapter: ArrayObjectAdapter? = null
    private var cardPresenterHeader: HeaderItem? = null
    private var cardRowAdapter: ArrayObjectAdapter? = null
    private var z = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE)
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
        if(DetailsActivityPhando.singleDetailsRelated!!.list.related.isNotEmpty()){

            cardPresenterHeader = HeaderItem(z.toLong(), "More like this")
            var cardPresenter =
                CardPresenter_ShowRelatedVideo_vertical()
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
            Log.e(TAG, "    ${DetailsActivityPhando.indexOfRow}         ${DetailsActivityPhando.indexOfItem}")
        }

        setOnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->

            if (item is ContentList) {
                if (row.headerItem.name.equals("trailers", true)) {
                    var intent = Intent(this.activity, PlayerActivity::class.java)
                    intent.putExtra("Id", DetailsActivityPhando.detailsData!!.documentMediaId.toString())
                    intent.putExtra("VideoUrl", item.mediaUrl)
                    //intent.putExtra("vast_url", DetailsActivity.detailsData!!.vastUrl)

                    Log.d("zzz", "mediaUrl" + item.mediaUrl);
                    intent.putExtra("is_live", item.is_live)

                    intent.putExtra("Time", "0")
                    /*if (DetailsActivity.detailsData!!.ccFiles?.size!! > 0) {
                        intent.putExtra("vtt", DetailsActivity.detailsData!!.ccFiles?.get(0)?.url)
                        Log.d("zzz", "vtt" + DetailsActivity.detailsData!!.ccFiles?.get(0)?.url)
                    } else {*/
                    intent.putExtra("vtt", "")
                    //}
                    intent.putExtra("Title", item.title)
                    intent.putExtra("Ratings", item.rating)
                    intent.putExtra("Details", item.detail)
                    intent.putExtra("Description", item.description)
                    startActivity(intent)
                } else {
                    DetailsActivityPhando.contentList = item
                    rowAdapter!!.clear()
                    Glide.with(requireActivity()!!)
                        .load(item.poster)
                        .into(DetailsActivityPhando.ivLogo)
                    DetailsActivityPhando.tvTitle.text = item.title
                    DetailsActivityPhando.tvDescription.text = item.description

                    if (item.isFree == 1) {
                        if (item.rating?.toString().isNullOrEmpty()) {
                            DetailsActivityPhando.textView.text = "${item.maturityRating} | Free"
                        } else {
                            DetailsActivityPhando.textView.text =
                                "${item.rating ?: ""} | ${item.maturityRating} | Free"
                        }
                    } else {
                        if (item.rating?.toString().isNullOrEmpty()) {
                            DetailsActivityPhando.textView.text = "${item.maturityRating} | Premium"
                        } else {
                            DetailsActivityPhando.textView.text =
                                "${item.rating ?: ""} | ${item.maturityRating} | Premium"
                        }

                    }
                }
            }
        }
    }

}