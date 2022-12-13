package com.ott.tv.fragments

import android.R
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ott.tv.Constants
import com.ott.tv.adapter.MenuListAdapter
import com.ott.tv.databinding.FragmentMenuBinding
import com.ott.tv.model.phando.CategoryType
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.ui.activity.NewMainActivity
import com.ott.tv.utils.ICallback
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController


class MenuFragment : Fragment(), MenuListAdapter.AdapterClick {
    private lateinit var binding: FragmentMenuBinding
    var isHome : Boolean = false
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
                Toast.makeText(requireActivity(), "closeactivity", Toast.LENGTH_SHORT).show()
                binding.imgHome.requestFocus()

            } else {
                val activity = requireActivity() as NewMainActivity
                activity.onMenuFocus(true)
                Toast.makeText(requireActivity(), "openactivity", Toast.LENGTH_SHORT).show()

            }

            binding.menuRecycler.setOnClickListener {
                Toast.makeText(
                    requireActivity(),
                    "click",
                    Toast.LENGTH_SHORT
                )
            }

        }


        binding.searchIcon.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val activity = requireActivity() as NewMainActivity
                binding.imgHome.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
                activity.onMenuFocus(false)
                Toast.makeText(requireActivity(), "closeactivity", Toast.LENGTH_SHORT).show()

            } else {
                val activity = requireActivity() as NewMainActivity
                binding.imgHome.setColorFilter(ContextCompat.getColor(requireContext(), R.color.holo_red_dark));
                activity.onMenuFocus(true)
                Toast.makeText(requireActivity(), "openactivity", Toast.LENGTH_SHORT).show()

            }
            binding.menuRecycler.setOnClickListener {
                Toast.makeText(
                    requireActivity(),
                    "click",
                    Toast.LENGTH_SHORT
                )
            }
        }
        binding.searchIcon.setOnClickListener {
            val activity = requireActivity() as NewMainActivity
            activity.onMenuSelection("Search", "")
        }
    }
    private fun fetchCategory() {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        Constants.IS_FROM_HOME = false
        val accessToken =
            "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(context)
        val call = api.getCategoryList(accessToken)
        call.enqueue(object : Callback<List<CategoryType>> {
            override fun onResponse(
                call: Call<List<CategoryType>>,
                response: Response<List<CategoryType>>
            ) {
                if (response.code() == 200) {

                    menu = response.body()



                    setAdapter()
                    /* binding.menuRecycler.apply {
                         *//*adapter = MenuListAdapter(requireActivity(), menu!!, object : ICallback {
                                override fun delegate(any: Any) {
                                    val activity = requireActivity() as NewMainActivity
                                    if (any != null && any is Boolean) {
                                        activity.onMenuFocus(any)
                                    } else {
                                        activity.onMenuFocus(false)
                                    }
                                }

                            })*//*

                    }*/


                    //  homeContent.setHomeContentId(1);
                    //   homeContent.getSlider();
                    //  loadSliderRows(homeContent.getSlider().getSlideArrayList());

                    //   loadRows();
                    /*if (movieList.size() <= 0) {

                    }

                    for (BrowseData movie : movieList) {

                    }
                    //   movies.addAll(movieList);*/
                } else if (response.code() == 401) {

                    // signOut();
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
    private fun setAdapter() {

        val mListAdapter = MenuListAdapter(requireContext(), menu!!, this, object : ICallback {
            override fun delegate(any: Any) {
                val activity = requireActivity() as NewMainActivity
                if (any != null && any is Boolean) {
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
        activity.onMenuSelection(data.type.toString(), data.displayName)


        /*  val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
          ft.replace(binding.browserSection.id, HomeFragmentNewUI(), "NewFragmentTag")
          ft.commit()*/
    }


}