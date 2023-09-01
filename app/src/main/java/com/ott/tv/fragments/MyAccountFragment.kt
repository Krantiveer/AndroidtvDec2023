package com.ott.tv.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ott.tv.BuildConfig
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.database.DatabaseHelper
import com.ott.tv.databinding.FragmentMyAccountBinding
import com.ott.tv.model.phando.UserProfile
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.ui.activity.LoginChooserActivity
import com.ott.tv.ui.activity.SearchActivity_Phando
import com.ott.tv.utils.CMHelper
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccountFragment : Fragment() {
    private var userProfile: UserProfile? = null
    private var binding: FragmentMyAccountBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_account, container, false
        )
        val view = binding!!.getRoot()
        binding!!.lytTopCard.visibility = View.GONE
        binding!!.progressBar.visibility = View.VISIBLE
        binding!!.signOutButton.setOnClickListener { signOut() }
        userProfileDataFromServer
        binding!!.builVersion.text = "App Version:" + BuildConfig.VERSION_CODE
        return view
    }

    private fun signOut() {
        if (context != null && activity != null) {
            val databaseHelper = DatabaseHelper(context)
            if (PreferenceUtils.getInstance().getAccessTokenPref(context) !== "") {
                val editor = context!!.getSharedPreferences(
                    Constants.USER_LOGIN_STATUS,
                    Context.MODE_PRIVATE
                ).edit()
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false)
                editor.apply()
                databaseHelper.deleteUserData()
                PreferenceUtils.clearSubscriptionSavedData(context)
                PreferenceUtils.getInstance().setAccessTokenNPref(context, "")
                startActivity(Intent(context, LoginChooserActivity::class.java))
                activity!!.finish()
            }
        }
    }

    private val userProfileDataFromServer: Unit
        private get() {
            binding!!.lytTopCard.visibility = View.GONE
            binding!!.progressBar.visibility = View.VISIBLE
            if (activity != null) {
                Constants.IS_FROM_HOME = false
                val retrofit = RetrofitClient.getRetrofitInstance()
                val api = retrofit.create(Dashboard::class.java)
                val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
                    context
                )
                val call = api.getUserProfileAPI(accessToken)
                call.enqueue(object : Callback<UserProfile?> {
                    override fun onResponse(
                        call: Call<UserProfile?>,
                        response: Response<UserProfile?>
                    ) {
                        if (response.isSuccessful) {
                            binding!!.progressBar.visibility = View.GONE
                            binding!!.lytTopCard.visibility = View.VISIBLE
                            if (response.code() == 200 && response.body() != null) {
                                userProfile = response.body()
                                if (activity != null) {
                                    Glide.with(activity!!)
                                        .load(userProfile!!.user.image)
                                        .error(R.drawable.user_icon_demo)
                                        .placeholder(R.drawable.user_icon_demo)
                                        .into(binding!!.imguser)
                                }
                                binding!!.userName.text = userProfile!!.user.name
                                if (userProfile!!.user.email != null) {
                                    binding!!.userEmailTv.visibility = View.VISIBLE
                                    binding!!.userEmailTv.text = userProfile!!.user.email
                                }
                                if (userProfile!!.is_subscribed == 0) {
                                    binding!!.tvnoPlan.visibility = View.VISIBLE
                                    binding!!.userSubId.visibility = View.VISIBLE
                                    binding!!.lytPlan.visibility = View.GONE
                                    binding!!.lytPlan2.visibility = View.GONE
                                    binding!!.expireDateTv.visibility = View.GONE
                                    binding!!.llMediaPlan.visibility = View.GONE
                                    binding!!.myPackageName.visibility = View.GONE
                                    binding!!.myPackagePrice.visibility = View.GONE
                                } else {
                                    binding!!.userSubId.visibility = View.VISIBLE
                                    binding!!.myPackageName.visibility = View.VISIBLE
                                    binding!!.myPackagePrice.visibility = View.VISIBLE
                                    binding!!.userSubId.text =
                                        "Subscriber ID : " + userProfile!!.current_subscription.id
                                    binding!!.myPackageName.text = userProfile!!.package_name

                                    binding!!.myPackagePrice.text =userProfile!!.current_subscription!!.plan!!.currency+"  "+
                                        /*"Rs. "*/  userProfile!!.current_subscription.price.toString()


                                    binding!!.tvnoPlan.visibility = View.GONE
                                    binding!!.expireDateTv.visibility = View.VISIBLE
                                    binding!!.llMediaPlan.visibility = View.VISIBLE
                                    binding!!.lytPlan.visibility = View.VISIBLE
                                    binding!!.lytPlan2.visibility = View.VISIBLE
                                    if (userProfile!!.getsubscription_end_date() != null) {
                                        binding!!.myPackageDateTime.text =
                                            "Expires on " + userProfile!!.getsubscription_end_date()
                                    }
                                }
                                if (userProfile!!.user.mobile != null) {
                                    if(!userProfile!!.user.mobile.isEmpty()){
                                        binding!!.tvMobileNumber.text = userProfile!!.user.mobile

                                    }else{
                                        binding!!.tvMobileNumber.visibility=View.GONE
                                        binding!!.viewlineSecond?.visibility=View.GONE

                                    }
                                }
                            } else if (response.errorBody() != null) {
                                Toast.makeText(
                                    context,
                                    response.errorBody().toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                SearchActivity_Phando.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    context,
                                    "Sorry! Something went wrong. Please try again after some time",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (userProfile!!.is_review == 1) {
                                binding!!.userSubId.visibility = View.INVISIBLE
                            } else if (userProfile!!.is_review == 0) {
                                binding!!.userSubId.visibility = View.VISIBLE
                            }
                        } else {
                            if (response.code() == 401) {
                                //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                                signOut()
                                //     Toast.makeText(getContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(
                                    context,
                                    "Sorry! Something went wrong. Please try again after some time",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                            //  Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();

                            //CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                        }
                    }

                    override fun onFailure(call: Call<UserProfile?>, t: Throwable) {
                        t.printStackTrace()
                        CMHelper.setSnackBar(requireView(), t.message, 2)
                        binding!!.progressBar.visibility = View.GONE
                        binding!!.lytTopCard.visibility = View.GONE
                    }
                })
            }
        }
}