package com.ott.tv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.model.LanguageItem
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.network.api.SendOTPApi
import com.ott.tv.utils.PreferenceUtils
import com.perseverance.phando.ui.LanguageListAdapter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class LanguageActivity : AppCompatActivity(), LanguageListAdapter.AdapterClick {

    var mLanguagesList: List<LanguageItem>? = null
    var mUserListAdapter: LanguageListAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnSave: AppCompatButton
    private lateinit var btnSkip: AppCompatButton
    val languageId = StringBuilder()
    var mSelectedList = arrayListOf<LanguageItem>()
    var mUserList: List<LanguageItem>? = null
    private var languages_ids_value = ""
    private var Language: List<LanguageItem>? = null
    private var languages_ids = ""


    private var mClick: ItemClick? = null

    companion object {
        var isOpen = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        recyclerView = findViewById(R.id.rvList)
        btnSave = findViewById(R.id.btnSave)
        btnSkip = findViewById(R.id.btnSkip)

        mLanguagesList = ArrayList<LanguageItem>()
        mUserList = ArrayList<LanguageItem>()
        mSelectedList = ArrayList<LanguageItem>()
        onCreateStuff()
        fetchLanguageData()

        recyclerView.post {
            val firstItemView = recyclerView.getChildAt(0)
            firstItemView?.requestFocus()
        }

        btnSkip.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {

            /*for (data in mSelectedList) {
                if (languageId.isEmpty()) {
                    languageId.append(data.id)
                } else {
                    languageId.append(",${data.id}")
                }


            }*/
            sendlanguage(languages_ids_value)
            gotoMainScreen()


            //  mClick!!.onItemClick(languageId)
            /*
                        val map = HashMap<String, String>()
                        map["languages_ids"] = languageId.toString()
                        val retrofit = RetrofitClient.getRetrofitInstance()
                        val api = retrofit.create(Dashboard::class.java)
                        Constants.IS_FROM_HOME = false
                        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
                            this
                        )
                        val call = api.updateLanguagePreference(accessToken,map)
                        call.enqueue(object : Callback<BaseResponse> {
                            override fun onResponse(
                                call: Call<BaseResponse?>,
                                response: Response<BaseResponse?>
                            ) {
                                if (response?.body() == null) {

                                } else {

                                    val data = response.body()
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                                //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                            }
                        })*/
        }


    }

    private fun sendlanguage(languages_ids: String) {
        val Ids = languages_ids.split(",").filter { it.isNotEmpty() }.joinToString(",").toString()
        val resultString = Ids.replace("+", "")

        val cleanedString = resultString.replace(" ", "")

        Log.i("language@@result", "sendlanguage: "+resultString+"---"+Ids+"----ids--"+cleanedString)


        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(SendOTPApi::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this)
        val call = api.setUserLngPreferances(accessToken, cleanedString)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.code() == 200) {
                    assert(response.body() != null)
                } else {
                    if (response.code() == 401) {
                        //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                        //  ToastMsg(applicationContext).toastIconError(response.message())
                    } else {
                        //  ToastMsg(applicationContext).toastIconError(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                // Log.e(TAG, "onFailure: ", t)
                // ToastMsg(applicationContext).toastIconError(getString(R.string.error_toast) + t)
                //     ToastMsg(applicationContext).toastIconError(getString(R.string.error_toast) + t)
            }
        })
    }


    private fun fetchLanguageData() {


        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        Constants.IS_FROM_HOME = false
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            this
        )
        val call = api.getLanguage(accessToken)
        call.enqueue(object : Callback<List<LanguageItem>?> {
            override fun onResponse(
                call: Call<List<LanguageItem>?>,
                response: Response<List<LanguageItem>?>
            ) {
                if (response.code() == 200) {
                    Language = response.body() as List<LanguageItem>?
                    assert(Language != null)

// Join the selected languages into a comma-separated string
                    val selectedLanguagesAPI =
                        Language!!.filter { it!!.isLanguageSelected }.map { it!!.id }
                            .joinToString(", ")
                    languages_ids_value = selectedLanguagesAPI
                    if (languages_ids_value.isNotEmpty()) {
                        languages_ids_value += ","
                    }
                    Log.i("Languages", "onResponse: --->" + selectedLanguagesAPI)
                }

                if (response?.body() == null) {

                } else {


                    val data = response.body()
                    if (data?.isNotEmpty() == true) {

                        mLanguagesList = data
                        mUserList = mLanguagesList
                        setUserListAdapter()
                        openLanguagePreferenceDialog()
                    }

                }
            }

            override fun onFailure(call: Call<List<LanguageItem>?>, t: Throwable) {
                //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
            }
        })

    }


    private fun openLanguagePreferenceDialog() {
        val languageId = StringBuilder()

        val boolLanguageArray = BooleanArray(mLanguagesList!!.size)


        for (index in mLanguagesList!!.indices) {
            val tempLanguage = mLanguagesList!!.get(index)
            if (mLanguagesList?.contains(tempLanguage)!!) {
                boolLanguageArray[index] = true
            }
        }

    }

    fun onCreateStuff() {
        isOpen = true


        for (data in mUserList!!) {
            if (data.isLanguageSelected) {
                mSelectedList.add(data)
            }
        }

        //   setUserListAdapter()
    }


    private fun setUserListAdapter() {
        mUserListAdapter = LanguageListAdapter(this@LanguageActivity, mLanguagesList!!, this)
        recyclerView.layoutManager = GridLayoutManager(this@LanguageActivity, 5)
        recyclerView.adapter = mUserListAdapter
        recyclerView.itemAnimator=null
        Log.e("@@mLanguagesList", mLanguagesList!![0].language)
    }

    override fun onItemClick(data: LanguageItem) {
        Log.i("@@Languagekranti", "onItemClick: " +data.id+data.isLanguageSelected + languages_ids_value)
        if (data.isLanguageSelected) {
           // mSelectedList.add(data)
                languages_ids = data.id.toString()
                if (languages_ids_value.endsWith(languages_ids)) {
                    languages_ids_value =
                        languages_ids_value.substring(0, languages_ids_value.length - 1)
                } else {
                    if (languages_ids_value.isNotEmpty()) {
                        languages_ids_value += ","
                    }
                    languages_ids_value += languages_ids
                }

        } else {
            languages_ids_value =
                removeValueFromString(languages_ids_value, data.id.toString())

         //   mSelectedList.remove(data)
        }
    }


    private fun removeValueFromString(originalString: String, valueToRemove: String): String {

        val valueListWithoutSpace=originalString.replace(" ","")
        val valuesList = valueListWithoutSpace.split(',')

        val filteredList = valuesList.filter { it != valueToRemove }
        return filteredList.joinToString(",")
    }

    private fun gotoMainScreen() {
        val intent = Intent(applicationContext, NewMainActivity::class.java)
        startActivity(intent)
        finishAffinity()
        overridePendingTransition(R.anim.enter, R.anim.exit)
    }

}

interface ItemClick {
    fun onItemClick(data: StringBuilder)
}
