package com.ott.tv.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.leanback.widget.VerticalGridPresenter
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.model.Movie
import com.ott.tv.model.phando.ShowWatchlist
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.ui.activity.ItemCountryActivity
import com.ott.tv.ui.presenter.HorizontalCardPresenter
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemCountryFragment : VerticalGridSupportFragment() {
    private var movies: List<ShowWatchlist> = ArrayList()
    private var mAdapter: ArrayObjectAdapter? = null
    private var pageCount = 1
    private var dataAvailable = true
    private var mContext: Context? = null
    private var title: String? = null
    private var id: String? = ""
    private var activity: ItemCountryActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = context
        title = requireActivity().intent.getStringExtra("title")
        id = requireActivity().intent.getStringExtra("id")
        activity = getActivity() as ItemCountryActivity?
        setTitle("\t\t                                     $title")
        //bgHelper = new BackgroundHelper(getActivity());
        onItemViewClickedListener = defaultItemViewClickedListener
        setOnItemViewSelectedListener(defaultItemSelectedListener)
        setupFragment()
    }


    private fun setupFragment() {
        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = NUM_COLUMNS
        setGridPresenter(gridPresenter)
        // mAdapter = new ArrayObjectAdapter(new CardPresenter());
        // mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(MOVIE));
        mAdapter = ArrayObjectAdapter(HorizontalCardPresenter(MOVIE))
        adapter = mAdapter
        //   fetchMovieData(id, String.valueOf(pageCount));
        fetchMovieData()
    }

    private fun fetchMovieData() {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        Constants.IS_FROM_HOME = false
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            context
        )
        Log.i(
            TAG, id + "Access Token --->" + PreferenceUtils.getInstance().getAccessTokenPref(
                context
            )
        )
        val call = api.getViewAllListAPI(accessToken, id, "0,400", "")
        call.enqueue(object : Callback<List<ShowWatchlist?>?> {
            override fun onResponse(
                call: Call<List<ShowWatchlist?>?>,
                response: Response<List<ShowWatchlist?>?>
            ) {
                if (response.code() == 200) {
                    val movieList = response.body()!!
                    if (movieList.size <= 0) {
                        dataAvailable = false
                        if (context != null) {
                            Toast.makeText(
                                context,
                                resources.getString(R.string.no_data_found),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    mAdapter!!.clear()
                    for (movie in movieList) {
                        mAdapter!!.add(movie)
                    }
                    mAdapter!!.notifyArrayItemRangeChanged(
                        movieList.size - 1,
                        movieList.size + movies.size
                    )
                    //   movies.addAll(movieList);
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.something_went_text),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ShowWatchlist?>?>, t: Throwable) {
                Log.e("Genre Item", "code: " + t.localizedMessage)
            }
        })
    }

    private val defaultItemViewClickedListener: OnItemViewClickedListener
        /*
    private void fetchMovieData(String id, String pageCount) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();


        Dashboard api = retrofit.create(Dashboard.class);
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(mContext);
        Log.i(TAG, "Access Token --->" + PreferenceUtils.getInstance().getAccessTokenPref(mContext));
        Call<List<ShowWatchlist>> call = api.getViewAllListAPI(accessToken, "43","100","");
        call.enqueue(new Callback<List<ShowWatchlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShowWatchlist>> call, @NonNull Response<List<ShowWatchlist>> response) {
                if (response.code() == 200) {
                    List<ShowWatchlist> movieList = response.body();
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        //Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }


                    for (ShowWatchlist movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                    movies.addAll(movieList);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShowWatchlist>> call, @NonNull Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });
    }
*/
    private get() = OnItemViewClickedListener { viewHolder: Presenter.ViewHolder?, o: Any, viewHolder2: RowPresenter.ViewHolder?, row: Row? ->
            val videoContent = o as ShowWatchlist
            if (videoContent.type == null) {
                videoContent.type = "M"
            }
            if (videoContent.type.equals(
                    "VM",
                    ignoreCase = true
                ) || videoContent.type.equals("GENRE", ignoreCase = true)
            ) {
                val intent = Intent(context, ItemCountryActivity::class.java)
                intent.putExtra("id", videoContent.id.toString())
                if (videoContent.genres != null) {
                    if (videoContent.genres.size > 0) {
                        var genres: String
                        genres = videoContent.genres[0]
                        for (i in 1 until videoContent.genres.size) {
                            genres = genres + "," + videoContent.genres[i]
                        }
                        intent.putExtra("title", genres)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            if (videoContent.type == "M" && videoContent.is_live.toString()
                    .equals("0", ignoreCase = true)
            ) {
                val intent = Intent(getActivity(), DetailsActivityPhando::class.java)
                if (videoContent.type != null) intent.putExtra("type", videoContent.type)
                if (videoContent.thumbnail != null) intent.putExtra(
                    "thumbImage",
                    videoContent.thumbnail
                )
                if (videoContent.id != null) intent.putExtra("video_id", videoContent.id.toString())
                if (videoContent.title != null) intent.putExtra("title", videoContent.title)
                if (videoContent.detail != null) intent.putExtra("description", videoContent.detail)
                if (videoContent.release_date != null) intent.putExtra(
                    "release",
                    videoContent.release_date
                )
                if (videoContent.duration_str != null) intent.putExtra(
                    "duration",
                    videoContent.duration_str
                )
                if (videoContent.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    videoContent.maturity_rating
                )
                if (videoContent.is_free != null) intent.putExtra(
                    "ispaid",
                    videoContent.is_free.toString()
                )
                if (videoContent.language_str != null) intent.putExtra(
                    "language_str",
                    videoContent.language_str
                )
                if (videoContent.is_live != null) intent.putExtra(
                    "is_live",
                    videoContent.is_live.toString()
                )
                if (videoContent.rating != null) intent.putExtra(
                    "rating",
                    videoContent.rating.toString()
                )
                if (videoContent.trailers != null && videoContent.trailers.size > 0 && videoContent.trailers[0] != null && videoContent.trailers[0].media_url != null) {
                    intent.putExtra("trailer", videoContent.trailers[0].media_url)
                }

//kranti
                if (videoContent.genres != null) {
                    if (videoContent.genres.size > 0) {
                        var genres: String
                        genres = videoContent.genres[0]
                        for (i in 1 until videoContent.genres.size) {
                            genres = genres + "," + videoContent.genres[i]
                        }
                        intent.putExtra("genres", genres)
                    }
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
            }
            if (videoContent.type == "T") {
                val intent = Intent(getActivity(), DetailsActivityPhando::class.java)
                if (videoContent.type != null) intent.putExtra("type", videoContent.type)
                if (videoContent.thumbnail != null) intent.putExtra(
                    "thumbImage",
                    videoContent.thumbnail
                )
                if (videoContent.id != null) intent.putExtra("video_id", videoContent.id.toString())
                if (videoContent.title != null) intent.putExtra("title", videoContent.title)
                if (videoContent.detail != null) intent.putExtra("description", videoContent.detail)
                if (videoContent.release_date != null) intent.putExtra(
                    "release",
                    videoContent.release_date
                )
                if (videoContent.duration_str != null) intent.putExtra(
                    "duration",
                    videoContent.duration_str
                )
                if (videoContent.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    videoContent.maturity_rating
                )
                if (videoContent.is_free != null) intent.putExtra(
                    "ispaid",
                    videoContent.is_free.toString()
                )
                if (videoContent.language_str != null) intent.putExtra(
                    "language_str",
                    videoContent.language_str
                )
                if (videoContent.is_live != null) intent.putExtra(
                    "is_live",
                    videoContent.is_live.toString()
                )
                if (videoContent.rating != null) intent.putExtra(
                    "rating",
                    videoContent.rating.toString()
                )
                if (videoContent.trailers != null && videoContent.trailers.size > 0 && videoContent.trailers[0] != null && videoContent.trailers[0].media_url != null) {
                    intent.putExtra("trailer", videoContent.trailers[0].media_url)
                }
                if (videoContent.genres != null) {
                    var genres: String
                    genres = videoContent.genres[0]
                    for (i in 1 until videoContent.genres.size) {
                        genres = genres + "," + videoContent.genres[i]
                    }
                    intent.putExtra("genres", genres)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
            }
            if (videoContent.type == "M" && videoContent.is_live.toString()
                    .equals("1", ignoreCase = true)
            ) {
                val intent = Intent(getActivity(), DetailsActivityPhando::class.java)
                if (videoContent.type != null) intent.putExtra("type", videoContent.type)
                if (videoContent.thumbnail != null) intent.putExtra(
                    "thumbImage",
                    videoContent.thumbnail
                )
                if (videoContent.id != null) intent.putExtra("video_id", videoContent.id.toString())
                if (videoContent.title != null) intent.putExtra("title", videoContent.title)
                if (videoContent.detail != null) intent.putExtra("description", videoContent.detail)
                if (videoContent.release_date != null) intent.putExtra(
                    "release",
                    videoContent.release_date
                )
                if (videoContent.duration_str != null) intent.putExtra(
                    "duration",
                    videoContent.duration_str
                )
                if (videoContent.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    videoContent.maturity_rating
                )
                if (videoContent.is_free != null) intent.putExtra(
                    "ispaid",
                    videoContent.is_free.toString()
                )
                if (videoContent.language_str != null) intent.putExtra(
                    "language_str",
                    videoContent.language_str
                )
                if (videoContent.is_live != null) intent.putExtra(
                    "is_live",
                    videoContent.is_live.toString()
                )
                if (videoContent.rating != null) intent.putExtra(
                    "rating",
                    videoContent.rating.toString()
                )
                if (videoContent.trailers != null && videoContent.trailers.size > 0 && videoContent.trailers[0] != null && videoContent.trailers[0].media_url != null) {
                    intent.putExtra("trailer", videoContent.trailers[0].media_url)
                }

//kranti
                if (videoContent.genres != null) {
                    var genres: String
                    genres = videoContent.genres[0]
                    for (i in 1 until videoContent.genres.size) {
                        genres = genres + "," + videoContent.genres[i]
                    }
                    intent.putExtra("genres", genres)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
            }
            if (videoContent.type.equals("OTT", ignoreCase = true)) {
                //   Intent intent = new Intent(context, ItemCountryActivity.class);
                var intent = requireActivity().packageManager.getLaunchIntentForPackage(
                    videoContent.android_link.substring(videoContent.android_link.lastIndexOf("=") + 1)
                )
                if (intent == null) {
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(videoContent.android_link)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                requireActivity().startActivity(intent)
            }
        }
    protected val defaultItemSelectedListener: OnItemViewSelectedListener
        // selected listener for setting blur background each time when the item will select.
        protected get() = OnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row -> // pagination
            if (dataAvailable) {
                val itemPos = mAdapter!!.indexOf(item)
                if (itemPos == movies.size - 1) {
                    pageCount++
                    //   fetchMovieData(id, String.valueOf(pageCount));
                }
            }

            //Log.d("iamge url: ------------------------------", itemPos+" : "+ movies.size());
            // change the background color when the item will select
            if (item is Movie) {
                /*bgHelper = new BackgroundHelper(getActivity());
                bgHelper.prepareBackgroundManager();
                bgHelper.startBackgroundTimer(((Movie) item).getThumbnailUrl());*/
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        movies = ArrayList()
        pageCount = 1
        dataAvailable = true
    }

    companion object {
        private val TAG = ItemCountryFragment::class.java.simpleName
        private const val NUM_COLUMNS = 4

        //private BackgroundHelper bgHelper;
        const val MOVIE = "movie"
    }
}