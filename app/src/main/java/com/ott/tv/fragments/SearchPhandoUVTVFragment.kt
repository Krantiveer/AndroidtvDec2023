package com.ott.tv.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.adapter.ContentAdapter
import com.ott.tv.adapter.SearchAdapter
import com.ott.tv.database.DatabaseHelper
import com.ott.tv.databinding.ActivitySearchPhandoUvtvBinding
import com.ott.tv.model.phando.ShowWatchlist
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.network.api.ListRecommend
import com.ott.tv.network.api.RecommendedModel
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.ui.activity.ItemCountryActivity
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchPhandoUVTVFragment : Fragment(), SearchAdapter.OnItemClickListenerSearch,
    ContentAdapter.OnItemClickListener {
    companion object {
        lateinit var progressBar: ProgressBar
        lateinit var accestoken: String
        var indexOfRow: Int = 0
    }

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySearchPhandoUvtvBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(com.ott.tv.R.layout.activity_search_phando_uvtv, container, false)
        // your other stuff

        binding = ActivitySearchPhandoUvtvBinding.inflate(layoutInflater)

        fetchMovieData()
        sharedPreferences = requireActivity().getSharedPreferences("LoginData", Activity.MODE_PRIVATE);

        accestoken = sharedPreferences.getString("access_token", "")!!

        view.findViewById<EditText>(R.id.edtsearchh)?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /*Toast.makeText(
                    activity,
                    "please type keyword for Search2",
                    Toast.LENGTH_SHORT
                ).show()*/
            }


            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                var searchText: String
                if ( view.findViewById<EditText>(R.id.edtsearchh).text.toString().equals("")) {
                    view.findViewById<RecyclerView>(R.id.rvRecommended).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.recommendedforyou).visibility = View.VISIBLE
                    view.findViewById<LinearLayout>(R.id.lytFragment).visibility = View.GONE
                    /*Toast.makeText(
                        activity,
                        "please type keyword for Search3",
                        Toast.LENGTH_SHORT
                    ).show()*/
                } else {
                    view.findViewById<RecyclerView>(R.id.rvRecommended).visibility = View.GONE
                    view.findViewById<TextView>(R.id.recommendedforyou).visibility = View.GONE
                    view.findViewById<LinearLayout>(R.id.lytFragment).visibility = View.VISIBLE
                    searchText =  view.findViewById<EditText>(R.id.edtsearchh).text.toString()
                    //getQueryData(searchText)

                    view.findViewById<ProgressBar>(R.id.progressBarSearch).visibility=View.VISIBLE
                    val searchtext: String = searchText
                    val retrofit = RetrofitClient.getRetrofitInstance()
                    val searchApi = retrofit.create(Dashboard::class.java)
                    Constants.IS_FROM_HOME = false
                    val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
                        activity
                    )
                    val call = searchApi.searchVideo(accessToken, searchtext, "", "", "50")
                    call.enqueue(object : Callback<MutableList<ShowWatchlist>> {
                        override fun onResponse(
                            call: Call<MutableList<ShowWatchlist>>,
                            response: Response<MutableList<ShowWatchlist>>
                        ) {
                            Log.e("@@res", "response: " + response.code())
                            var searchContent = ArrayList<ShowWatchlist>()
                            if (response.code() == 200) {
                                searchContent = response.body() as ArrayList<ShowWatchlist>
                                view.findViewById<RecyclerView>(R.id.rvSearch).setAdapter(
                                    SearchAdapter(
                                        searchContent,
                                        context,
                                        this@SearchPhandoUVTVFragment
                                    ),
                                )
                                if (searchContent.size == 0) {

                                    Toast.makeText(
                                        context,
                                        "Try searching for another movies",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    //    CMHelper.setSnackBar(requireView(), , 2);
                                }
                                view.findViewById<ProgressBar>(R.id.progressBarSearch).visibility = View.GONE
                            }
                        }

                        override fun onFailure(call: Call<MutableList<ShowWatchlist>>, t: Throwable) {

                        }
                    })

                }

            }

            override fun afterTextChanged(s: Editable?) {

             /*  first it call

             Toast.makeText(
                    activity,
                    "please type keyword for Search1",
                    Toast.LENGTH_SHORT
                ).show()*/
            }
        })


        return view
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<EditText>(R.id.edtsearchh)?.requestFocus()
    }

    private fun fetchMovieData() {
        binding.progressBarSearch.visibility = View.VISIBLE
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(context)

        val call = api.getRecommendedList(accessToken)
        call.enqueue(object : Callback<List<RecommendedModel?>?> {
            override fun onResponse(
                call: Call<List<RecommendedModel?>?>,
                response: Response<List<RecommendedModel?>?>
            ) {
                if (response.code() == 200) {
                    if (response.code() == 200) {

                        binding.progressBarSearch.visibility = View.GONE
                        var movieList: List<RecommendedModel?>? = response.body()
                        var movieListFinal: List<ListRecommend?>? = null

                        if (movieList!!.size > 0) {
                            movieListFinal = movieList!!.get(0)!!.list
                            binding.rvRecommended.setAdapter(
                                ContentAdapter(
                                    movieListFinal,
                                    context, this@SearchPhandoUVTVFragment
                                ),
                            )

                        }else{
                            binding.recommendedforyou.visibility =View.GONE


                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<RecommendedModel?>?>, t: Throwable) {
                binding.progressBarSearch.visibility = View.GONE
                Log.e("Genre Item", "code: " + t.localizedMessage)
            }
        })
    }


    override fun onItemClick(item: ShowWatchlist?) {
        val videoContent = item as ShowWatchlist
        val video = item as ShowWatchlist
        // Log.i(TAG, "onItemClick: "+king)
        val status = DatabaseHelper(this.activity).activeStatusData.status
        if (video.type == null) {
            video.type = "M"
        }
        run {
            if (video.type.equals("VM", ignoreCase = true)) {
              val  intent = Intent(context, ItemCountryActivity::class.java)
                intent.putExtra("id", video.id)
                if (video.genres != null) {
                    if (video.genres.size > 0) {
                        var genres: String
                        genres = video.genres[0]
                        for (i in 1 until video.genres.size) {
                            genres = genres + "," + video.genres[i]
                        }
                        intent.putExtra("title", genres)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (video.type == "M" && video.is_live.toString()
                    .equals("0", ignoreCase = true)
            ) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                } else {
                    if (video.thumbnail != null) intent.putExtra(
                        "thumbImage",
                        video.thumbnail
                    )
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                } else {
                    if (video.id != null) intent.putExtra(
                        "video_id",
                        video.id.toString()
                    )
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra(
                        "description",
                        video.description
                    )
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                } else {
                    if (video.release_date != null) {
                        intent.putExtra("release", video.release_date)
                    }
                }
                /*   if (videoContent.getRuntime() != null)
       intent.putExtra("duration", videoContent.getRuntime());*/if (video.duration_str != null) intent.putExtra(
                    "duration",
                    video.duration_str
                )
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra(
                    "ispaid",
                    video.is_free.toString()
                )
                if (video.language_str != null) intent.putExtra(
                    "language_str",
                    video.language_str
                )
                if (video.is_live != null) intent.putExtra(
                    "is_live",
                    video.is_live.toString()
                )
                if (video.rating != null) intent.putExtra(
                    "rating",
                    video.rating.toString()
                )
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null
                ) {
                    intent.putExtra("trailer", video.thumbnail)
                }

                if (video.genres != null) {
                    if (video.genres.size > 0) {
                        var genres: String
                        genres = video.genres[0]
                        for (i in 1 until video.genres.size) {
                            genres = genres + "," + video.genres[i]
                        }
                        intent.putExtra("genres", genres)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (video.type == "T") {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra(
                        "description",
                        video.description
                    )
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                }
                if (video.duration_str != null) intent.putExtra(
                    "duration",
                    video.duration_str
                )
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra(
                    "ispaid",
                    video.is_free.toString()
                )
                if (video.language_str != null) intent.putExtra(
                    "language_str",
                    video.language_str
                )
                if (video.is_live != null) intent.putExtra(
                    "is_live",
                    video.is_live.toString()
                )
                if (video.rating != null) intent.putExtra(
                    "rating",
                    video.rating.toString()
                )
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null
                ) {
                    intent.putExtra("trailer", video.trailers[0].media_url)
                }

                if (video.genres != null) {
                    var genres: String
                    genres = video.genres[0]
                    for (i in 1 until video.genres.size) {
                        genres = genres + "," + video.genres[i]
                    }
                    intent.putExtra("genres", genres)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (video.type == "M" && video.is_live.toString()
                    .equals("1", ignoreCase = true)
            ) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                } else {
                    if (video.id != null) intent.putExtra(
                        "video_id",
                        video.id.toString()
                    )
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra(
                        "description",
                        video.description
                    )
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                } else {
                    if (video.release_date != null) {
                        intent.putExtra("release", video.release_date)
                    }
                }
                if (video.duration_str != null) intent.putExtra(
                    "duration",
                    video.duration_str
                )
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra(
                    "ispaid",
                    video.is_free.toString()
                )
                if (video.language_str != null) intent.putExtra(
                    "language_str",
                    video.language_str
                )
                if (video.is_live != null) intent.putExtra(
                    "is_live",
                    video.is_live.toString()
                )
                if (video.rating != null) intent.putExtra(
                    "rating",
                    video.rating.toString()
                )
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null
                ) {
                    intent.putExtra("trailer", video.trailers[0].media_url)
                }

                if (video.genres != null) {
                    var genres: String
                    genres = video.genres[0]
                    for (i in 1 until video.genres.size) {
                        genres = genres + "," + video.genres[i]
                    }
                    intent.putExtra("genres", genres)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
        }


    }

    override fun onItemClick(item: ListRecommend?) {
        val video = arrayOf(item as ListRecommend).get(0)

        val status = DatabaseHelper(activity).activeStatusData.status
        if (video.type == null) {
            video.type = "M"
        }
        run {
            if (video.type.equals("VM", ignoreCase = true)) {
                val intent = Intent(context, ItemCountryActivity::class.java)
                intent.putExtra("id", video.id)
                if (video.genres != null) {
                    if (video.genres.size > 0) {
                        var genres: String
                        genres = video.genres[0]
                        for (i in 1 until video.genres.size) {
                            genres = genres + "," + video.genres[i]
                        }
                        intent.putExtra("title", genres)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (video.type == "M" && video.isLive.toString()
                    .equals("0", ignoreCase = true)
            ) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                } else {
                    if (video.thumbnail != null) intent.putExtra(
                        "thumbImage",
                        video.thumbnail
                    )
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                } else {
                    if (video.id != null) intent.putExtra(
                        "video_id",
                        video.id.toString()
                    )
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra(
                        "description",
                        video.description
                    )
                }
                if (video.releaseDate != null) {
                    intent.putExtra("release", video.releaseDate)
                }
                /*   if (videoContent.getRuntime() != null)
       intent.putExtra("duration", videoContent.getRuntime());*/if (video.durationStr != null) intent.putExtra(
                    "duration",
                    video.durationStr
                )
                if (video.maturityRating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturityRating
                )
                if (video.isFree != null) intent.putExtra(
                    "ispaid",
                    video.isFree.toString()
                )
                if (video.languageStr != null) intent.putExtra(
                    "language_str",
                    video.languageStr
                )
                if (video.isLive != null) intent.putExtra(
                    "is_live",
                    video.isLive.toString()
                )
                if (video.rating != null) intent.putExtra(
                    "rating",
                    video.rating.toString()
                )
                if (video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].mediaUrl != null
                ) {
                    intent.putExtra("trailer", video.thumbnail)
                }

                if (video.genres.size > 0) {
                    var genres: String
                    genres = video.genres[0]
                    for (i in 1 until video.genres.size) {
                        genres = genres + "," + video.genres[i]
                    }
                    intent.putExtra("genres", genres)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (video.type == "T") {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra(
                        "description",
                        video.description
                    )
                }
                if (video.releaseDate != null) {
                    intent.putExtra("release", video.releaseDate)
                }
                if (video.durationStr != null) intent.putExtra(
                    "duration",
                    video.durationStr
                )
                if (video.maturityRating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturityRating
                )
                if (video.isFree != null) intent.putExtra(
                    "ispaid",
                    video.isFree.toString()
                )
                if (video.languageStr != null) intent.putExtra(
                    "language_str",
                    video.languageStr
                )
                if (video.isLive != null) intent.putExtra(
                    "is_live",
                    video.isLive.toString()
                )
                if (video.rating != null) intent.putExtra(
                    "rating",
                    video.rating.toString()
                )
                if (video.trailers.size > 0 && video.trailers[0].mediaUrl != null
                ) {
                    intent.putExtra("trailer", video.trailers[0].mediaUrl)
                }

                var genres: String
                genres = video.genres[0]
                for (i in 1 until video.genres.size) {
                    genres = genres + "," + video.genres[i]
                }
                intent.putExtra("genres", genres)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (video.type == "M" && video.isLive.toString()
                    .equals("1", ignoreCase = true)
            ) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                } else {
                    if (video.id != null) intent.putExtra(
                        "video_id",
                        video.id.toString()
                    )
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra(
                        "description",
                        video.description
                    )
                }
                if (video.releaseDate != null) {
                    intent.putExtra("release", video.releaseDate)
                }
                if (video.durationStr != null) intent.putExtra(
                    "duration",
                    video.durationStr
                )
                if (video.maturityRating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturityRating
                )
                if (video.isFree != null) intent.putExtra(
                    "ispaid",
                    video.isFree.toString()
                )
                if (video.languageStr != null) intent.putExtra(
                    "language_str",
                    video.languageStr
                )
                if (video.isLive != null) intent.putExtra(
                    "is_live",
                    video.isLive.toString()
                )
                if (video.rating != null) intent.putExtra(
                    "rating",
                    video.rating.toString()
                )
                if (video.trailers.size > 0 && video.trailers[0].mediaUrl != null
                ) {
                    intent.putExtra("trailer", video.trailers[0].mediaUrl)
                }

                if (video.genres != null) {
                    var genres: String
                    genres = video.genres[0]
                    for (i in 1 until video.genres.size) {
                        genres = genres + "," + video.genres[i]
                    }
                    intent.putExtra("genres", genres)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
        }
    }

}
