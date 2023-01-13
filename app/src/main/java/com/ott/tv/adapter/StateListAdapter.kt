package com.ott.tv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.R

class StateListAdapter(
    private val mList: List<String>,
    private val mClickListener: ClickListener
) :
    RecyclerView.Adapter<StateListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_state_names, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mData = mList[position]
        holder.title.text = mData

        holder.itemView.setOnClickListener {
            mClickListener.onItemClick(mData)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
    }
}


interface ClickListener {
    fun onItemClick(data: String)
}
