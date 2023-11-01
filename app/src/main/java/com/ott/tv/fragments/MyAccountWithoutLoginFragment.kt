package com.ott.tv.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ott.tv.BuildConfig
import com.ott.tv.Config
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.database.DatabaseHelper
import com.ott.tv.databinding.FragmentMyAccountBinding
import com.ott.tv.databinding.FragmentMyAccountWithoutloginBinding
import com.ott.tv.model.phando.UserProfile
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.ui.activity.LoginChooserActivity
import com.ott.tv.ui.activity.LoginMobileActivity
import com.ott.tv.ui.activity.SearchActivity_Phando
import com.ott.tv.ui.activity.SplashScreenActivityTv
import com.ott.tv.utils.CMHelper
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccountWithoutLoginFragment : Fragment() {
    private var userProfile: UserProfile? = null
    private var binding: FragmentMyAccountWithoutloginBinding? = null
    private val TAG = SplashScreenActivityTv::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_account_withoutlogin, container, false
        )
        val view = binding!!.getRoot()
        binding!!.progressBar.visibility = View.GONE
        binding!!.signInButton.setOnClickListener {
        PreferenceUtils.getInstance().setG0TO_LOGINPref(context,"true")
            Log.i(TAG, "@@digiana "+PreferenceUtils.getInstance().getG0TO_LOGINPref(context))
            val intent = Intent(context, SplashScreenActivityTv::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        userProfileDataFromServer
        binding!!.builVersion.text = "App Version:" + BuildConfig.VERSION_CODE
        return view
    }

    private fun signOut() {

        if (context != null && activity != null) {
            val databaseHelper = DatabaseHelper(context)
            if (PreferenceUtils.getInstance().getAccessTokenPref(context).isEmpty()) {
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
            }else{
                val intent = Intent(context, SplashScreenActivityTv::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }
        }
    }
    fun userdefault(){

                Glide.with(this)
                    .load(R.drawable.user_icon_demo)
                    .error(R.drawable.user_icon_demo)
                    .placeholder(R.drawable.user_icon_demo)
                    .into(binding!!.imguser)

               binding!!.userName.text = "DEFAULT USER"
                binding!!.userEmailTv.visibility = View.VISIBLE
                binding!!.userEmailTv.text ="DemoUser**@***.com"

                binding!!.tvnoPlan.visibility = View.VISIBLE
                binding!!.userSubId.visibility = View.VISIBLE
                binding!!.lytPlan.visibility = View.GONE
                binding!!.lytPlan2.visibility = View.GONE
                binding!!.expireDateTv.visibility = View.GONE
                binding!!.llMediaPlan.visibility = View.GONE
                binding!!.myPackageName.visibility = View.GONE
                binding!!.myPackagePrice.visibility = View.GONE



            binding!!.tvMobileNumber.text = "+91 9876****1"



    }

    private val userProfileDataFromServer: Unit
        private get() {
            if (activity != null) {
                Constants.IS_FROM_HOME = false
                val retrofit = RetrofitClient.getRetrofitInstance()
                val api = retrofit.create(Dashboard::class.java)
                val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
                    context
                )

                userdefault()
            }
        }
}