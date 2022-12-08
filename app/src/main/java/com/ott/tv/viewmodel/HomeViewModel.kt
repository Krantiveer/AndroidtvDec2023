package com.ott.tv.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.ott.tv.R
import com.ott.tv.model.InnerListModel
import com.ott.tv.model.MainFragmentListModel

class HomeViewModel : ViewModel() {
    val data: MutableLiveData<MutableList<MainFragmentListModel>> = MutableLiveData()

    fun setData() {
        val dataList = mutableListOf<MainFragmentListModel>()
        val list = mutableListOf<InnerListModel>()

        list.add(InnerListModel(R.drawable.default_video, "videoCat1"))
        list.add(InnerListModel(R.drawable.default_video, "videoCat2"))
        list.add(InnerListModel(R.drawable.default_video, "videoCat3"))
        list.add(InnerListModel(R.drawable.default_video, "videoCat4"))
        list.add(InnerListModel(R.drawable.default_video, "videoCat5"))
        list.clear()
        dataList.add(MainFragmentListModel("Tv Shows", list))
        list.add(InnerListModel(R.drawable.default_video, "video1"))
        list.add(InnerListModel(R.drawable.default_video, "video2"))
        list.add(InnerListModel(R.drawable.default_video, "video3"))
        list.add(InnerListModel(R.drawable.default_video, "video4"))
        list.add(InnerListModel(R.drawable.default_video, "video5"))
        list.clear()
        dataList.add(MainFragmentListModel("Tv Videos", list))
        dataList.add(MainFragmentListModel("Fav Videos", list))
        dataList.add(MainFragmentListModel("Mostly Watched", list))
        dataList.add(MainFragmentListModel("Continue Watching", list))
        data.postValue(dataList)
    }
}