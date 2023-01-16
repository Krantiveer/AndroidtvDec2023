package com.ott.tv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ott.tv.adapter.AdapterClickListener
import com.ott.tv.adapter.ClickListener
import com.ott.tv.adapter.MapListAdapter
import com.ott.tv.adapter.StateListAdapter
import com.ott.tv.databinding.FragmentMapBinding
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.CountryApi
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.video_service.PlaybackModel
import com.richpath.RichPath
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragmentUVTV : Fragment(),  ClickListener, AdapterClickListener {


    var dataAdapter: ArrayAdapter<String>? =null
    private var adapter: MapListAdapter? = null
    private var stateAdapter: StateListAdapter? = null
    var binding: FragmentMapBinding? = null
    val EXTRA_VIDEO = "com.oxootv.spagreen.recommendations.extra.MOVIE"

    var type: String? = null
    var is_live: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map, container, false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val stateList: MutableList<String> = ArrayList()
        stateList.add("Uttar Pradesh")
        stateList.add("Andaman and Nicobar Islands")
        stateList.add("Andhra Pradesh")
        stateList.add("Arunachal Pradesh")
        stateList.add("Assam")
        stateList.add("Bihar")
        stateList.add("Chandigarh")
        stateList.add("Chhattisgarh")
        stateList.add("Dadra and Nagar Haveli")
        stateList.add("Daman and Diu")
        stateList.add("New Delhi")
        stateList.add("Goa")
        stateList.add("Gujarat")
        stateList.add("Haryana")
        stateList.add("Himachal Pradesh")
        stateList.add("Jammu and Kashmir")
        stateList.add("Jharkhand")
        stateList.add("Karnataka")
        stateList.add("Kerala")
        stateList.add("Lakshadweep")
        stateList.add("Madhya Pradesh")
        stateList.add("Maharashtra")
        stateList.add("Manipur")
        stateList.add("Meghalaya")
        stateList.add("Mizoram")
        stateList.add("Nagaland")
        stateList.add("Orissa")
        stateList.add("Pondicherry")
        stateList.add("Punjab")
        stateList.add("Rajasthan")
        stateList.add("Sikkim")
        stateList.add("Tamil Nadu")
        stateList.add("Telangana")
        stateList.add("Tripura")
        stateList.add("Uttarakhand")
        stateList.add("West Bengal")

        binding!!.rvItemsStateList.setHasFixedSize(true)
        stateAdapter = StateListAdapter(stateList, this)
        binding!!.rvItemsStateList.adapter = stateAdapter
        binding!!.txtSelectedState.text = "Uttar Pradesh"
        getState(binding!!.txtSelectedState.text.toString())
        val richPath =  binding!!.richPathView.findRichPathByName(binding!!.txtSelectedState.text.toString())
        if (richPath!= null){
            richPath!!.fillColor = resources.getColor(R.color.white)
        }
        binding!!.llSelectedState.setOnClickListener(View.OnClickListener {

            binding!!.slidingPaneLayout.visibility = View.VISIBLE
            binding!!.slidingPaneLayout.requestFocus()
            binding!!.lytTop!!.visibility = View.GONE
        })

    }



    fun setAdapter(data: List<PlaybackModel?>?) {
        val manager = LinearLayoutManager(activity)
        binding!!.rvList.layoutManager = manager
        binding!!.rvList.setHasFixedSize(true)
        adapter = MapListAdapter(data,requireContext(), this)
        binding!!.rvList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onResume() {
        super.onResume()

           /* binding!!.richPathView.findRichPathByName(
                binding!!.txtSelectedState.text.toString()
            )!!.fillColor = resources.getColor(R.color.white)
            getState(binding!!.txtSelectedState.text.toString())*/
    }
    fun visibilityShowHide(view: View){
        view.visibility=if (view.visibility==View.VISIBLE)
        {
            View.INVISIBLE
        }else{
            View.VISIBLE
        }
    }
    fun getState(name: String) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(CountryApi::class.java)
        val call = api.getAllCountry(name)
        call.enqueue(object : Callback<List<PlaybackModel?>> {
            override fun onResponse(
                call: Call<List<PlaybackModel?>>,
                response: Response<List<PlaybackModel?>>
            ) {
                if (response.code() == 200) {
                    val countryList = response.body()

                    if(countryList.isNullOrEmpty()){
                     //   visibilityShowHide(binding!!.rvList)
                        binding?.rvList?.visibility=View.GONE
                        binding?.nodataFound?.visibility=View.VISIBLE

                    }else{
                        binding?.rvList?.visibility=View.VISIBLE
                        binding?.nodataFound?.visibility=View.INVISIBLE

                        setAdapter(countryList)

                    }

                }
            }
            override fun onFailure(call: Call<List<PlaybackModel?>>, t: Throwable) {
                Log.e("onFailure", "error: " + t.localizedMessage)
            }
        })
    }
    override fun onItemClick(data: String) {
        Log.e("@@data", data)
        getState(data)
        binding!!.slidingPaneLayout.visibility = View.GONE
        binding!!.lytTop!!.visibility = View.VISIBLE
        binding!!.lytTop.requestFocus()
        binding!!.txtSelectedState.text = data
        for (models in binding!!.richPathView.findAllRichPaths()) {
            models.fillColor = resources.getColor(R.color.mapColo)
        }
        var richPath: RichPath? =null
        richPath =  binding!!.richPathView.findRichPathByName(data)
        if (richPath!= null){
            richPath!!.fillColor = resources.getColor(R.color.white)
        }
    }
    override fun onItemClick(data: PlaybackModel) {
        val video = data
        video.videoUrl = data.url

        if (type == "M") {
            video.category = "movie"
        } else {
            video.category = "movie"
        }

        video.istrailer = false
        video.isPaid = "free"
        if (is_live.equals("1", ignoreCase = true)) {
            video.islive = is_live;
        } else {
            video.islive = "0"
        }
        val intent = Intent(context, DetailsActivityPhando::class.java)
        if (video.getType() != null) intent.putExtra("type", video.getType())
        if (video.getThumbnail() != null) intent.putExtra(
            "thumbImage",
            video.getThumbnail()
        )
        if (video.getId() != null) intent.putExtra(
            "video_id",
            video.getId().toString()
        )
        if (video.getTitle() != null) intent.putExtra("title", video.getTitle())
        if (video.description != null) intent.putExtra("description", video.description)

        if (video.getIslive() != null) intent.putExtra(
            "is_live",
            video.getIslive().toString()
        )
        startActivity(intent)

      /*  val intent = Intent(context, DetailsActivityPhando::class.java)
        intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video)
        startActivity(intent)*/
    }


}