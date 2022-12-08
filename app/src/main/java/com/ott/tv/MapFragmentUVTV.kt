package com.ott.tv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.model.CountryModel
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.CountryApi
import com.ott.tv.ui.activity.StateListActivity
import com.ott.tv.utils.PreferenceUtils
import com.richpath.RichPathView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragmentUVTV : Fragment() {

    lateinit var richPathView: RichPathView
    lateinit var llSelectedState: LinearLayout
    lateinit var rvList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        richPathView = view.findViewById(R.id.richPathView)
        llSelectedState = view.findViewById(R.id.llSelectedState)
        rvList = view.findViewById(R.id.rvList)

        richPathView.setOnPathClickListener { richPath ->
            for (models in richPathView.findAllRichPaths()) {
                models.fillColor = resources.getColor(R.color.black_color)
            }
            getState(richPath.name)
            richPath.fillColor = resources.getColor(R.color.white)
        }

        llSelectedState.setOnClickListener {
       /*     val intent = Intent(requireContext(), StateListActivity::class.java)
            requireActivity().startActivity(intent)*/
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceUtils.getInstance().setStateNamePref(requireContext(), "")
    }

    override fun onResume() {
        super.onResume()
        if (PreferenceUtils.getInstance().getStateNamePref(requireContext()).isNotEmpty()) {
            richPathView.findRichPathByName(
                PreferenceUtils.getInstance().getStateNamePref(requireContext())
            )!!.fillColor = resources.getColor(R.color.white)
            getState(PreferenceUtils.getInstance().getStateNamePref(requireContext()))
        }
    }

    fun getState(name: String) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(CountryApi::class.java)
        val call = api.getAllCountry(Config.API_KEY)
        call.enqueue(object : Callback<List<CountryModel?>?> {
            override fun onResponse(
                call: Call<List<CountryModel?>?>,
                response: Response<List<CountryModel?>?>
            ) {
                if (response.code() == 200) {
                    val countryList = response.body()
                }
            }

            override fun onFailure(call: Call<List<CountryModel?>?>, t: Throwable) {
                Log.e("onFailure", "error: " + t.localizedMessage)
            }
        })
    }
}