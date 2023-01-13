package com.ott.tv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ott.tv.R
import com.ott.tv.model.CountryModel
import com.ott.tv.model.phando.LatestMovieList
import com.ott.tv.network.api.StateChannelResponse
import com.ott.tv.video_service.PlaybackModel

class MapListAdapter(
    private val mList: List<PlaybackModel?>?,
    private val mContext: Context,
    private val mClickListener: AdapterClickListener
) :
    RecyclerView.Adapter<MapListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_map_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mData = mList!![position]
        Glide.with(mContext)
            .load(mData!!.thumbnail)
            .placeholder(R.drawable.poster_placeholder_land)
            .error(R.drawable.poster_placeholder_land)
            .into(holder.imageView)

        holder.title.text = mData.title

        holder.itemView.setOnClickListener {
            mClickListener.onItemClick(mData)
        }

    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_thumbnail)
        val title: TextView = itemView.findViewById(R.id.title)
    }
}

interface AdapterClickListener {
    fun onItemClick(data: PlaybackModel)
}
