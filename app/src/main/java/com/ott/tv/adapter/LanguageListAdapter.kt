package com.perseverance.phando.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.R
import com.ott.tv.model.LanguageItem
class LanguageListAdapter(
    var mContext: Context,
    var mData: List<LanguageItem>,
    var mClick: AdapterClick,
) : RecyclerView.Adapter<LanguageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_language_list, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        holder.txtName.text = item.language
        if (item.languageText != null) {
            holder.txtNameOriginal.text = item.languageText
        }
        holder.imgChecked.visibility = if (item.isLanguageSelected) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener {
            mData[position].isLanguageSelected = !mData[position].isLanguageSelected
            mClick.onItemClick(mData[position])
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName = itemView.findViewById<TextView>(R.id.txtName)
        var txtNameOriginal = itemView.findViewById<TextView>(R.id.txtNameOriginal)
        var imgChecked = itemView.findViewById<ImageView>(R.id.imgChecked)

    }

    interface AdapterClick {
        fun onItemClick(data: LanguageItem)
    }

}