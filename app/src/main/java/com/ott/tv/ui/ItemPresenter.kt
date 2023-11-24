package com.ott.tv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.ott.tv.R
import com.ott.tv.model.phando.LatestMovieList
import com.ott.tv.model.phando.ShowWatchlist

class ItemPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_view_new, parent, false)

        val params = view.layoutParams
        params.width = getWidthInPercent(parent!!.context, 32)
        params.height = getHeightInPercent(parent!!.context, 12)

        return ViewHolder(view)

    }

    fun getWidthInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.widthPixels ?: 0
        return (width * percent) / 100
    }

    fun getHeightInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.heightPixels ?: 0
        return (width * percent) / 100
    }


    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {

     //   val content = item as? DataModel.Result.Detail
        val content = item as? LatestMovieList
        val movie = item as LatestMovieList
        //  ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        //  ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
      //  updateCardViewImage(movie.thumbnail)


        val imageview = viewHolder?.view?.findViewById<ImageView>(R.id.poster_image)

        val url = "https://www.themoviedb.org/t/p/w500" + content?.poster
/*        Glide.with(viewHolder?.view?.context!!)
            .load("https://ottsaas-cdn.b-cdn.net/images/64/episodes/thumbnails/thumb_1678974387MWENDO-KASI.jpg")
            .into(imageview!!)*/
        Glide.with(viewHolder?.view?.context!!)
            .load(movie.thumbnail) /*.override(100,300)*/
            .placeholder(R.drawable.poster_placeholder_land)
            .into(imageview!!)
    }


    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
  }