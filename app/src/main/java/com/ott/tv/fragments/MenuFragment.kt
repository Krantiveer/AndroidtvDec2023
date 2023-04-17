package com.ott.tv.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ott.tv.Constants
import com.ott.tv.adapter.MenuListAdapter
import com.ott.tv.database.DatabaseHelper
import com.ott.tv.databinding.FragmentMenuBinding
import com.ott.tv.model.phando.CategoryType
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.ui.activity.LoginChooserActivity
import com.ott.tv.ui.activity.NewMainActivity
import com.ott.tv.utils.ICallback
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController


class MenuFragment : Fragment(), MenuListAdapter.AdapterClick {
    val TAG = "MenuFragment"

    private lateinit var binding: FragmentMenuBinding
    var isHome: Boolean = false

    //val activity = requireActivity() as NewMainActivity
    //   val activity = requireActivity() as NewMainActivity

    var menu: List<CategoryType>? = ArrayList<CategoryType>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MenuFragment)[MenuVM::class.java]
            lifecycleOwner = this@MenuFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fetchCategory()

        binding.menuRecycler.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val activity = requireActivity() as NewMainActivity
                activity.onMenuFocus(false)
                //  binding.imgHome.requestFocus()
            } else {
                val activity = requireActivity() as NewMainActivity
                activity.onMenuFocus(true)

            }

            binding.menuRecycler.setOnClickListener {
                /* Toast.makeText(
                     requireActivity(),
                     "click",
                     Toast.LENGTH_SHORT
                 )*/
            }

        }


    }

    private fun fetchCategory() {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        Constants.IS_FROM_HOME = false
        val accessToken =
            "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(context)
        val call = api.menu(accessToken)
        call.enqueue(object : Callback<List<CategoryType>> {
            override fun onResponse(
                call: Call<List<CategoryType>>,
                response: Response<List<CategoryType>>
            ) {
                if (response.code() == 200) {
                    menu = response.body()
                    setAdapter()

                } else if (response.code() == 401) {

                    signOut();
                } else if (response.errorBody() != null) {
                    if (AccessController.getContext() != null) {
                        Toast.makeText(
                            context,
                            "sorry! Something went wrong. Please try again after some time" + response.errorBody(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //  CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                } else {
                    if (AccessController.getContext() != null) {
                        Toast.makeText(
                            context,
                            "sorry! Something went wrong. Please try again after some time",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<CategoryType>>, t: Throwable) {
                //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                if (AccessController.getContext() != null) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        })
    }

    private fun signOut() {
        if (context != null && activity != null) {
            val databaseHelper = DatabaseHelper(context)
            /*    String userId = databaseHelper.getUserData().getUserId();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
            }*/if (PreferenceUtils.getInstance().getAccessTokenPref(context) !== "") {
                val editor = requireContext().getSharedPreferences(
                    Constants.USER_LOGIN_STATUS,
                    Context.MODE_PRIVATE
                ).edit()
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false)
                editor.apply()
                databaseHelper.deleteUserData()
                PreferenceUtils.clearSubscriptionSavedData(context)
                PreferenceUtils.getInstance().setAccessTokenNPref(context, "")
                startActivity(Intent(context, LoginChooserActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun setAdapter() {

        val mListAdapter = MenuListAdapter(requireContext(), menu!!, this, object : ICallback {
            override fun delegate(any: Any) {
                val activity = requireActivity() as NewMainActivity
                Log.i(TAG, "delegate: menu1")
                if (any is Boolean) {
                    activity.onMenuFocus(any)
                } else {
                    activity.onMenuFocus(false)
                }
            }

        })
        binding.menuRecycler.layoutManager =
            LinearLayoutManager(requireContext())
        binding.menuRecycler.adapter = mListAdapter
    }

    override fun onItemClick(data: CategoryType) {

        Log.e("@@log", data.displayName + data.type + data.isFocused)
        val activity = requireActivity() as NewMainActivity
        if(data.gener_id!=null){
            activity.onMenuSelection(data.type.toString(), data.displayName,data.gener_id)

        }else{
            activity.onMenuSelection(data.type.toString(), data.displayName,"")

        }


    }


}