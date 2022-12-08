package com.ott.tv.fragments

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.model.phando.CategoryType
import com.ott.tv.model.phando.MenuListModel
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController

class MenuVM : ViewModel() {

    val menuModel: MutableLiveData<MutableList<MenuListModel>> = MutableLiveData()

    fun setData() {
        val data = mutableListOf<MenuListModel>()
        data.add(MenuListModel(R.drawable.home_side_nav, "Home"))
        data.add(MenuListModel(R.drawable.moviessidenav, "Movie"))
        data.add(MenuListModel(R.drawable.seriessidenav, "Series"))
        data.add(MenuListModel(R.drawable.map_side_nav, "UVTV Bharat"))
        data.add(MenuListModel(R.drawable.live_sidenav, "Live Channels"))
        data.add(MenuListModel(R.drawable.ic_baseline_flag_24, "Watchlist"))
        data.add(MenuListModel(R.drawable.ic_baseline_exit_to_app_24, "Profile"))
        menuModel.postValue(data)


    }




}