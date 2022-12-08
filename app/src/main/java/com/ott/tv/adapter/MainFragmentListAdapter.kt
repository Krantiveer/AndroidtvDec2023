package com.ott.tv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.databinding.MainFragmentListBinding
import com.ott.tv.model.MainFragmentListModel

class MainFragmentListAdapter(val context: Context, val dataSet: MutableList<MainFragmentListModel>) :
    RecyclerView.Adapter<MainFragmentListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MainFragmentListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binder(dataModel: MainFragmentListModel) {
            binding.model = dataModel
       //     binding.videosList.adapter = VideosListAdapter(context,dataModel.dataInnerList!!)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MainFragmentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}