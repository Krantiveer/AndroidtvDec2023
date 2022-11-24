package com.ott.tv.ui.activity;

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.R
import com.ott.tv.adapter.AdapterClickListener
import com.ott.tv.adapter.StateListAdapter
import com.ott.tv.countrycodepicker.CountryCodeActivity
import com.ott.tv.utils.PreferenceUtils


class StateListActivity : AppCompatActivity(), AdapterClickListener {

    lateinit var rvList: RecyclerView
    lateinit var search: EditText

    var arraylist = arrayListOf<String>()

    fun setArrayList() {
        arraylist.add("Andhra Pradesh")
        arraylist.add("Arunachal Pradesh")
        arraylist.add("Assam")
        arraylist.add("Bihar")
        arraylist.add("Chhattisgarh")
        arraylist.add("Goa")
        arraylist.add("Gujarat")
        arraylist.add("Haryana")
        arraylist.add("Himachal Pradesh")
        arraylist.add("Jammu and Kashmir")
        arraylist.add("Jharkhand")
        arraylist.add("Karnataka")
        arraylist.add("Kerala")
        arraylist.add("Madhya Pradesh")
        arraylist.add("Maharashtra")
        arraylist.add("Manipur")
        arraylist.add("Meghalaya")
        arraylist.add("Mizoram")
        arraylist.add("Nagaland")
        arraylist.add("Odisha")
        arraylist.add("Punjab")
        arraylist.add("Rajasthan")
        arraylist.add("Sikkim")
        arraylist.add("Tamil Nadu")
        arraylist.add("Telangana")
        arraylist.add("Tripura")
        arraylist.add("Uttarakhand")
        arraylist.add("Uttar Pradesh")
        arraylist.add("West Bengal")
        arraylist.add("Andaman and Nicobar Islands")
        arraylist.add("Chandigarh")
        arraylist.add("Dadra and Nagar Haveli")
        arraylist.add("Daman and Diu")
        arraylist.add("Delhi")
        arraylist.add("Lakshadweep")
        arraylist.add("Puducherry")
    }

    var tempArrayList = arrayListOf<String>()
    val mSearchString = MutableLiveData<String>("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_list)
        setArrayList()
        setAdapter()
        tempArrayList = arraylist

        rvList = findViewById(R.id.rvList)
        search = findViewById(R.id.edSearch)

        mSearchString.observe(this, Observer {
            if (it.isNotEmpty()) {
                for (data in arraylist) {
                    if (data.startsWith(it, false)) {
                        tempArrayList.add(data)
                    }
                }
            } else {
                tempArrayList.clear()
            }
            setAdapter()
        })

        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) {
                    mSearchString.value = s.toString()
                }
            }
        })


    }


    private var adapter: StateListAdapter? = null

    fun setAdapter() {
        val manager = LinearLayoutManager(this)
        rvList.layoutManager = manager
        rvList.setHasFixedSize(true)
        adapter = StateListAdapter(tempArrayList, this)
        rvList.adapter = adapter
    }

    override fun onItemClick(data: Any) {
        when (data) {
            is String -> {
                PreferenceUtils.getInstance().setStateNamePref(applicationContext, data)
                finish()
            }
        }
    }
}