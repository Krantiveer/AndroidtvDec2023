package com.ott.tv.ui.activity

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.StateSet
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.ott.tv.BuildConfig
import com.ott.tv.Config
import com.ott.tv.MapFragmentUVTV
import com.ott.tv.R
import com.ott.tv.database.DatabaseHelper
import com.ott.tv.databinding.ActivityNewMainBinding
import com.ott.tv.databinding.LayoutMenuBinding
import com.ott.tv.fragments.*
import com.ott.tv.model.BrowseData
import com.ott.tv.model.phando.LatestMovieList
import com.ott.tv.model.phando.PlayerActivityNewCodeDirectPlay
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.AppInfo
import com.ott.tv.network.api.Dashboard
import com.ott.tv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController


class NewMainActivity : FragmentActivity() {
    private lateinit var binding: ActivityNewMainBinding
    private var itemBinding: LayoutMenuBinding? = null
    private var defaultType: String = "home";
    private val TAG = NewMainActivity::class.java.simpleName
    lateinit var listFragment: com.ott.tv.fragments.ListFragment
    private var movieListContent: List<BrowseData>? = null
    private var firstTime: String = "firstTime";
    protected var player: ExoPlayer? = null
    private var exoPlayerView: PlayerView? = null
    private val startAutoPlay = true


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Creating an extended library configuration.
        /*  // Creating an extended library configuration.
          val config = YandexMetricaConfig.newConfigBuilder("45c548e2-21f1-4386-934b-3059b7d28b56").build()
          // Initializing the AppMetrica SDK.
          // Initializing the AppMetrica SDK.
          YandexMetrica.activate(applicationContext, config)
          // Automatic tracking of user activity.
          // Automatic tracking of user activity.
          YandexMetrica.enableActivityAutoTracking(application)
  */

        binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            applicationContext
        )
        exoPlayerView = findViewById(R.id.player_view_home)

        val call: Call<AppInfo> = api.getAppInfo(Config.Device_Type, accessToken)
        call.enqueue(object : Callback<AppInfo?> {
            override fun onResponse(call: Call<AppInfo?>, response: Response<AppInfo?>) {
                if (response.code() == 200) {

                    onGetAppInfoSuccess(response.body()!!)
                    Log.i(
                        "appinfo",
                        "onResponse: " + response.body()!!.player_logo_enable + "--" + response.body()!!.playerLogo
                    )
                    PreferenceUtils.getInstance().setWatermarkLogoUrlPref(
                        applicationContext,
                        response.body()!!.playerLogo
                    )
                    PreferenceUtils.getInstance().setWatermarkEnablePref(
                        applicationContext,
                        response.body()!!.player_logo_enable
                    )


                    //  homeContent.setHomeContentId(1);
                    //   homeContent.getSlider();
                    //  loadSliderRows(homeContent.getSlider().getSlideArrayList());

                    //   loadRows();
                } else if (response.code() == 401) {

                    // signOut();
                } else if (response.errorBody() != null) {
                    if (AccessController.getContext() != null) {
                        Toast.makeText(
                            applicationContext,
                            "sorry! Something went wrong. Please try again after some time" + response.errorBody(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //  CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                } else {
                    if (AccessController.getContext() != null) {
                        Toast.makeText(
                            applicationContext,
                            "sorry! Something went wrong. Please try again after some time",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<AppInfo?>, t: Throwable) {
                //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                if (AccessController.getContext() != null) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        })

        supportFragmentManager.beginTransaction().replace(binding.menuSection.id, MenuFragment())
            .commit()

    /*    supportFragmentManager.beginTransaction()
            .replace(binding.browserSection.id, HomeFragmentNewUI())
            .commit()*/
        belowFragment()
        /*binding.homepage.visibility = View.GONE
        binding.browserSection.visibility = View.VISIBLE
*/

    }
    fun belowFragment(){
        listFragment = com.ott.tv.fragments.ListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            applicationContext
        )


        val calldetail = api.getBrowseDataList(accessToken, "", "", "", "", 10, 1)
        calldetail.enqueue(object : Callback<List<BrowseData>?> {
            override fun onResponse(
                call: Call<List<BrowseData>?>,
                response: Response<List<BrowseData>?>
            ) {
                if (response.code() == 200) {
                    movieListContent = response.body()
//todo: Here we need to create vertical thumnail for according to orientation of ui
                    movieListContent?.let { listFragment.bindData(it) }

                    if (movieListContent?.get(0)?.title.equals("Home Slider")) {

                        val movieList = movieListContent?.get(0)?.list

                        if (movieList != null) {
                            for (i in 0 until movieList.size) {
                                //val movie = movieList?.get(i)
                                //    println(movie.title)
                                Log.i(TAG, "onResponse:veersa " + movieList.get(i).title)

                                val videoContent = movieListContent!![0].list[i]
                                // videoContent.setViewallTitle(movieListContent.get(i).getTitle());


                            }


                        }


                    }
                }
            }

            override fun onFailure(call: Call<List<BrowseData>?>, t: Throwable) {
                Log.i("NewMainActivity", "onResponse: no size ---Reject")
                Log.e("Genre Item", "code: " + t.localizedMessage)
            }
        })

        listFragment.setOnContentSelectedListener {

            updateBanner(it)
        }
        listFragment.setOnItemClickListener {
            playContain(it)
        }

    }
    fun belowFragmentReplace() {
        listFragment = com.ott.tv.fragments.ListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(
            applicationContext
        )


        val calldetail = api.getBrowseDataList(accessToken, "", "", "", "", 10, 1)
        calldetail.enqueue(object : Callback<List<BrowseData>?> {
            override fun onResponse(
                call: Call<List<BrowseData>?>,
                response: Response<List<BrowseData>?>
            ) {
                if (response.code() == 200) {
                    movieListContent = response.body()
                    if (movieListContent!!.isEmpty()) {


                    } else {
//todo: Here we need to create vertical thumnail for according to orientation of ui
                        movieListContent?.let { listFragment.bindData(it) }

                        if (movieListContent?.get(0)?.title.equals("Home Slider")) {

                            val movieList = movieListContent?.get(0)?.list

                            if (movieList != null) {
                                for (i in 0 until movieList.size) {
                                    //val movie = movieList?.get(i)
                                    //    println(movie.title)
                                    Log.i(TAG, "onResponse:veersa " + movieList.get(i).title)

                                    val videoContent = movieListContent!![0].list[i]
                                    // videoContent.setViewallTitle(movieListContent.get(i).getTitle());


                                }


                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<BrowseData>?>, t: Throwable) {
                Log.i("NewMainActivity", "onResponse: no size ---Reject")
                Log.e("Genre Item", "code: " + t.localizedMessage)
            }
        })

        listFragment.setOnContentSelectedListener {

            updateBanner(it)
        }
        listFragment.setOnItemClickListener {
            playContain(it)
        }

    }


    fun playContain(dataList: LatestMovieList) {
        /*    binding.title.text = dataList.title
            binding.description.text = dataList.detail
     */
        Log.i(TAG, "playContain: " + dataList.title)
        //  Glide.with(this).load(dataList.thumbnail).into(binding.imgBanner)


        if (applicationContext != null) {
            val videoContent = dataList
            val status = DatabaseHelper(applicationContext).activeStatusData.status
            if (videoContent.type == "M" && videoContent.is_live.toString()
                    .equals("0", ignoreCase = true)
            ) {
                if (Config.DirectVideoPlayEnable) {
                    val i = Intent(applicationContext, PlayerActivityNewCodeDirectPlay::class.java)
                    if (videoContent.type != null) i.putExtra("type", videoContent.type)
                    if (videoContent.id != null) {
                        i.putExtra("video_id", videoContent.id.toString())
                    } else {
                        if (videoContent.videosId != null) i.putExtra(
                            "video_id",
                            videoContent.videosId.toString()
                        )
                    }
                    if (videoContent.title != null) intent.putExtra("title", videoContent.title)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(i)
                } else {
                    val intent = Intent(applicationContext, DetailsActivityPhando::class.java)
                    if (videoContent.type != null) intent.putExtra("type", videoContent.type)
                    if (videoContent.thumbnail != null) {
                        intent.putExtra("thumbImage", videoContent.thumbnail)
                    } else {
                        if (videoContent.thumbnailUrl != null) intent.putExtra(
                            "thumbImage",
                            videoContent.thumbnailUrl
                        )
                    }
                    if (videoContent.id != null) {
                        intent.putExtra("video_id", videoContent.id.toString())
                    } else {
                        if (videoContent.videosId != null) intent.putExtra(
                            "video_id",
                            videoContent.videosId.toString()
                        )
                    }
                    if (videoContent.title != null) intent.putExtra("title", videoContent.title)
                    if (videoContent.detail != null) {
                        intent.putExtra("description", videoContent.detail)
                    } else {
                        if (videoContent.description != null) intent.putExtra(
                            "description",
                            videoContent.description
                        )
                    }
                    if (videoContent.release_date != null) {
                        intent.putExtra("release", videoContent.release_date)
                    } else {
                        if (videoContent.release != null) {
                            intent.putExtra("release", videoContent.release)
                        }
                    }
                    /*   if (videoContent.getRuntime() != null)
                    intent.putExtra("duration", videoContent.getRuntime());*/if (videoContent.duration_str != null) intent.putExtra(
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
                        intent.putExtra("trailer", videoContent.thumbnailUrl)
                    }
                    if (videoContent.trailer_aws_source != null) {
                        intent.putExtra("trailer", videoContent.trailer_aws_source)
                    }
                    if (videoContent.genres != null) {
                        if (videoContent.genres.size > 0) {
                            var genres: String
                            genres = videoContent.genres[0]
                            for (i in 1 until videoContent.genres.size) {
                                genres = genres + "," + videoContent.genres[i]
                            }
                            intent.putExtra("genres", genres)
                        }
                    } else {
                        if (videoContent.genre != null) {
                            intent.putExtra("genres", videoContent.genre)
                        }
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                }
            }
            if (videoContent.type == "T") {
                val intent = Intent(applicationContext, DetailsActivityPhando::class.java)
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
                    if (videoContent.genres.size > 0) {
                        var genres: String
                        genres = videoContent.genres[0]
                        for (i in 1 until videoContent.genres.size) {
                            genres = genres + "," + videoContent.genres[i]
                        }
                        intent.putExtra("genres", genres)
                    }
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                applicationContext.startActivity(intent)
            }
            if (videoContent.type == "M" && videoContent.is_live.toString()
                    .equals("1", ignoreCase = true)
            ) {
                if (Config.DirectVideoPlayEnable) {
                    val i = Intent(applicationContext, PlayerActivityNewCodeDirectPlay::class.java)
                    if (videoContent.type != null) i.putExtra("type", videoContent.type)
                    if (videoContent.id != null) {
                        i.putExtra("video_id", videoContent.id.toString())
                    } else {
                        if (videoContent.videosId != null) i.putExtra(
                            "video_id",
                            videoContent.videosId.toString()
                        )
                    }
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(i)
                } else {
                    val intent = Intent(applicationContext, DetailsActivityPhando::class.java)
                    if (videoContent.type != null) intent.putExtra("type", videoContent.type)
                    if (videoContent.thumbnail != null) intent.putExtra(
                        "thumbImage",
                        videoContent.thumbnail
                    )
                    if (videoContent.id != null) intent.putExtra(
                        "video_id",
                        videoContent.id.toString()
                    )
                    if (videoContent.title != null) intent.putExtra("title", videoContent.title)
                    if (videoContent.detail != null) intent.putExtra(
                        "description",
                        videoContent.detail
                    )
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
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                }
            }

            if (videoContent.type.equals("VM", ignoreCase = true)||videoContent.type.equals("GENRE")) {
                val intent = Intent(applicationContext, ItemCountryActivity::class.java)
                intent.putExtra("id", videoContent.id.toString())
                if(videoContent.type.equals("GENRE")){
                    intent.putExtra("title", "   "+videoContent.title)
                }else{
                    intent.putExtra("title", "   "+videoContent.viewallTitle)

                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(intent)
            }
            if (videoContent.type.equals("OTT", ignoreCase = true)) {
                //   Intent intent = new Intent(context, ItemCountryActivity.class);
                var intent: Intent? =
                    applicationContext.getPackageManager().getLaunchIntentForPackage(
                        videoContent.android_link.substring(videoContent.android_link.lastIndexOf("=") + 1)
                    )
                if (intent == null) {
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(videoContent.android_link)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
            }
            if (videoContent.getType() == "E" && videoContent.getIs_live().toString()
                    .equals("0", ignoreCase = true)
            ) {
                val intent = Intent(applicationContext, DetailsActivityPhando::class.java)
                if (videoContent.getType() != null) intent.putExtra("type", videoContent.getType())
                if (videoContent.getThumbnail() != null) {
                    intent.putExtra("thumbImage", videoContent.getThumbnail())
                } else {
                    if (videoContent.getThumbnailUrl() != null) intent.putExtra(
                        "thumbImage",
                        videoContent.getThumbnailUrl()
                    )
                }
                if (videoContent.getId() != null) {
                    intent.putExtra("video_id", videoContent.getId().toString())
                } else {
                    if (videoContent.getId() != null) intent.putExtra("video_id", videoContent.getId().toString())
                }
                if (videoContent.getTitle() != null) intent.putExtra("title", videoContent.getTitle())
                if (videoContent.getDetail() != null) {
                    intent.putExtra("description", videoContent.getDetail())
                } else {
                    if (videoContent.getDescription() != null) intent.putExtra(
                        "description",
                        videoContent.getDescription()
                    )
                }
                if (videoContent.getRelease_date() != null) {
                    intent.putExtra("release", videoContent.getRelease_date())
                } else {
                    if (videoContent.getRelease() != null) {
                        intent.putExtra("release", videoContent.getRelease())
                    }
                }
                /*   if (videoContent.getRuntime() != null)
                    intent.putExtra("duration", videoContent.getRuntime());*/if (videoContent.getDuration_str() != null) intent.putExtra(
                    "duration",
                    videoContent.getDuration_str()
                )
                if (videoContent.getMaturity_rating() != null) intent.putExtra(
                    "maturity_rating",
                    videoContent.getMaturity_rating()
                )
                if (videoContent.getIs_free() != null) intent.putExtra(
                    "ispaid",
                    videoContent.getIs_free().toString()
                )
                if (videoContent.getLanguage_str() != null) intent.putExtra(
                    "language_str",
                    videoContent.getLanguage_str()
                )
                if (videoContent.getIs_live() != null) intent.putExtra(
                    "is_live",
                    videoContent.getIs_live().toString()
                )
                if (videoContent.getRating() != null) intent.putExtra(
                    "rating",
                    videoContent.getRating().toString()
                )
                if (videoContent.trailers != null && videoContent.trailers.size > 0 && videoContent.trailers[0] != null && videoContent.trailers[0].media_url != null) {
                    intent.putExtra("trailer", videoContent.trailers[0].media_url)
                }
                if (videoContent.getTrailer_aws_source() != null) {
                    intent.putExtra("trailer", videoContent.getTrailer_aws_source())
                }
                if (videoContent.genres != null) {
                    if (videoContent.genres.size > 0) {
                        var genres: String
                        genres = videoContent.genres[0]
                        for (i in 1 until videoContent.genres.size) {
                            genres = genres + "," + videoContent.genres[i]
                        }
                        intent.putExtra("genres", genres)
                    }
                } else {
                    if (videoContent.genre != null) {
                        intent.putExtra("genres", videoContent.genre)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
            }

        }

    }

    fun updateBanner(dataList: LatestMovieList) {
        Log.i(TAG, "updateBanner: "+dataList.viewallTitle+dataList)
        if (dataList.type != null) {

            if (!dataList.type.equals("VM", ignoreCase = true)) {
                binding.title.text = dataList.title
                binding.description.text = dataList.detail

                Glide.with(this).
                load(dataList.thumbnail)
                    .error(R.drawable.poster_placeholder_land)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(binding.imgBanner)

            }

        }



  /*      if(firstTime.equals("firstTime")){
            firstTime="secondTime"
            return
        }*/
        releasePlayer()



        binding.imgBanner.visibility=View.VISIBLE
        binding.guidelineCenter.visibility=View.VISIBLE
        binding.description.visibility=View.VISIBLE
        binding.guidelineTop.visibility=View.VISIBLE
        binding.guidelineStart.visibility=View.VISIBLE
        binding.imgBanner.visibility=View.VISIBLE
        binding.gradientHorizontal.visibility=View.VISIBLE
        binding.title.visibility=View.VISIBLE

        val params = binding.listFragment.layoutParams as ConstraintLayout.LayoutParams
        params.width = 0
        params.height = 0
        binding.listFragment.layoutParams = params
        binding.listFragment.visibility = View.VISIBLE

        if (dataList.trailer_aws_source != null && !dataList.trailer_aws_source.isEmpty()) {
            val url = dataList.trailer_aws_source
            //  String url = "https://action-ott-live.s3.ap-south-1.amazonaws.com/Sultan+Trailer/sultan+(1).mp4";
            initVideoPlayer(url, "movie")
        }else{
            binding.playerViewHome.visibility=View.INVISIBLE
        }


    }


    private fun onGetAppInfoSuccess(appInfo: AppInfo) {
        val storeVersion = appInfo.currentVersion
        val forceUpdate = appInfo.isForceUpdate
        PreferenceUtils.getInstance().setNpawEnablePref(this, appInfo.isnpawEnable)
        PreferenceUtils.getInstance().setNpawAccountKeyPref(this, appInfo.npawAccountKey)

        var currentVersion = 0
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            currentVersion = pInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        Log.e("Store app version: $storeVersion", "")
        Log.e("Current app version: $currentVersion", "")

        if (Config.Device_Type.contentEquals("androidtv")) {
            if (currentVersion < storeVersion) {
                // Need to update application
                val dialog =
                    androidx.appcompat.app.AlertDialog.Builder(this, R.style.MaterialDialogSheet)
                dialog.setTitle("Update Available")
                dialog.setMessage("A new version of " + getString(R.string.app_name) + " is available on App Store. Do you want to update?")
                dialog.setCancelable(false)

                dialog.setPositiveButton("Yes, update") { dialog, which ->
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (ex: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }

                    this@NewMainActivity.finish()
                }

                if (!forceUpdate) {
                    dialog.setNegativeButton("No, leave it!") { dialog, which -> }
                }
                dialog.setIcon(android.R.drawable.ic_dialog_alert)
                dialog.show()
            }
        }
    }

    fun onMenuFocus(onFocus: Boolean) {
        if (onFocus) {
            //        Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()
            binding.slidingPaneLayout.openPane()
        } else {
            //      Toast.makeText(this, "closeactivity", Toast.LENGTH_SHORT).show()
            binding.slidingPaneLayout.closePane()
            // Toast.makeText(this, "close", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        if (!binding.slidingPaneLayout.closePane()) {
            binding.slidingPaneLayout.openPane();

        } else {
            // binding.slidingPaneLayout.openPane();
            val dialog: Dialog
            dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_dialog_exit)
            dialog.setCancelable(true)
            val button_no = dialog.findViewById<View>(R.id.button_no) as Button
            button_no.background = getSelectorDrawable()
            button_no.setOnClickListener { dialog.dismiss() }

            val button_yes = dialog.findViewById<View>(R.id.button_yes) as Button
            button_yes.background = getSelectorDrawable()
            button_yes.setOnClickListener {
                dialog.dismiss()
                this.finishAffinity()
            }
            dialog.show()
        }
    }

    private fun getSelectorDrawable(): StateListDrawable? {
        val out = StateListDrawable()
        out.addState(
            intArrayOf(android.R.attr.state_focused), createFocusedDrawable(
                Color.parseColor("#EF3C23")
            )
        )
        out.addState(
            StateSet.WILD_CARD,
            createNormalDrawable(Color.parseColor("#80858B"))
        )
        return out
    }

    private fun createFocusedDrawable(color: Int): GradientDrawable? {
        val out = GradientDrawable()
        out.setColor(color)
        return out
    }

    private fun createNormalDrawable(color: Int): GradientDrawable? {
        val out = GradientDrawable()
        out.setColor(color)
        return out
    }


    fun onMenuSelection(type: String, type_id: String, title: String, gener_id: String) {
        Log.i(
            TAG,
            "onMenuSelection: -->" + type + "-typeid-" + type_id + "title" + title + "--" + defaultType
        )

        /*  if (type_id == defaultType) {
              return;
          } else {
              defaultType = type_id;
          }*/
        releasePlayer()
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

            } else {
                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*       val intent = Intent(this, SearchActivity_Phando::class.java)
                   startActivity(intent)
                   return*/
        }
        binding.tvTitle.text = title + "    "
        if (title.isEmpty()) {
            binding.tvTitle.isVisible = false
        } else if (type.equals("home") || type.equals("profile")) {
            // binding.tvTitle.isVisible = true
            binding.tvTitle.isVisible = false
        } else {
            binding.tvTitle.isVisible = true
        }
        val bundle = bundleOf(

            "menu" to 1,
            "gener_id" to gener_id,
            "type" to type,
            "type_id" to type_id

        )
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

                val newFragment = SearchPhandoUVTVFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
            } else {
                PreferenceUtils.getInstance().setWatchListPref(this, 0)
                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*


            */
        } else if (type.equals("viewall")) {
            binding.homepage.visibility = View.GONE
            binding.browserSection.visibility = View.VISIBLE

            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()

        } else if (type.equals("genre")) {
           binding.homepage.visibility = View.GONE
            binding.browserSection.visibility = View.VISIBLE

            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("watchlist") || type.equals("Watchlist")) {
           binding.homepage.visibility = View.GONE
            binding.browserSection.visibility = View.VISIBLE
            if (PreferenceUtils.getInstance().getLOGIN_DISABLEPref(this).contentEquals("1")) {
                val newFragment = MyAccountWithoutLoginFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
                return
            } else {

                val newFragment = ShowWatchlistFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
            }
        } else if (type.equals("profile")) {
                    binding.homepage.visibility = View.GONE
            binding.browserSection.visibility = View.VISIBLE

            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            if (PreferenceUtils.getInstance().getLOGIN_DISABLEPref(this).contentEquals("1")) {
                val newFragment = MyAccountWithoutLoginFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
                return
            } else {
                val newFragment = MyAccountFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
            }
        } else if (type.equals("uvtv-bharat")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = MapFragmentUVTV()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("home")) {
            binding.homepage.visibility = View.VISIBLE
            binding.browserSection.visibility = View.GONE
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragmentNewUI()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else {
            binding.homepage.visibility = View.GONE
            binding.browserSection.visibility = View.VISIBLE

            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }
    }

    fun onMenuSelection(type: String, title: String, gener_id: String) {
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

            } else {

                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*       val intent = Intent(this, SearchActivity_Phando::class.java)
                   startActivity(intent)
                   return*/
        }
        binding.tvTitle.text = title
        if (title.isEmpty()) {
            binding.tvTitle.isVisible = false
        } else if (type.equals("home") || type.equals("profile")) {
            // binding.tvTitle.isVisible = true
            binding.tvTitle.isVisible = false
        } else {
            binding.tvTitle.isVisible = true
        }
        val bundle = bundleOf(

            "menu" to 1,
            "gener_id" to gener_id,
            "type" to type

        )
        if (type.equals("search") || type.equals("Search")) {

            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {

                val newFragment = SearchPhandoUVTVFragment()
                newFragment.setArguments(bundle)
                supportFragmentManager.beginTransaction()
                    .replace(binding.browserSection.id, newFragment)
                    .commit()
            } else {
                PreferenceUtils.getInstance().setWatchListPref(this, 0)
                val intent = Intent(this, SearchActivity_Phando::class.java)
                startActivity(intent)
                return
            }
            /*


            */
        } else if (type.equals("viewall")) {
            val newFragment = GenreMovieFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()

            /*    } else if (type.equals("genre")) {
                    val newFragment = GenreMovieFragment()
                    newFragment.setArguments(bundle)
                    supportFragmentManager.beginTransaction()
                        .replace(binding.browserSection.id, newFragment)
                        .commit()
        */
        } else if (type.equals("watchlist") || type.equals("Watchlist")) {
            val newFragment = ShowWatchlistFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("profile")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)

            val newFragment = MyAccountFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("uvtv-bharat")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = MapFragmentUVTV()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else if (type.equals("home")) {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragmentNewUI()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        } else {
            PreferenceUtils.getInstance().setWatchListPref(this, 0)
            val newFragment = HomeFragment()
            newFragment.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(binding.browserSection.id, newFragment)
                .commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("NewMainActivity", "***** keyCode =" + keyCode + "event :" + event)
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                onBackPressed()
                return true
            }

            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_DPAD_UP_LEFT, KeyEvent.KEYCODE_DPAD_UP_RIGHT, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_DOWN_LEFT, KeyEvent.KEYCODE_DPAD_DOWN_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_UP -> {
                Log.e("NewMainActivity", "movieIndex : ")
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    private fun releasePlayer() {
        if (player != null) {
            player!!.playWhenReady = false
            player!!.stop()
            player!!.release()
            player = null
            exoPlayerView!!.player = null
        }
    }
    fun initVideoPlayer(url: String?, type: String?) {
        if (this != null) {
            if (url != null && !url.isEmpty()) {
                if (player != null) {
                    player!!.stop()
                    player!!.release()
                }
                val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
                // Create a HLS media source pointing to a playlist uri.
                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(url))
                // Create a player instance.
                player = ExoPlayer.Builder(this).build()
                player!!.setMediaSource(hlsMediaSource)
                player!!.prepare()
                player!!.playWhenReady = startAutoPlay
                exoPlayerView!!.player = player
                player!!.playWhenReady = true
                player!!.addListener(object : Player.Listener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        when (playbackState) {
                            1 -> {
                                Log.d("krantiv", "Ideal state")
                                exoPlayerView!!.visibility = View.INVISIBLE
                            }

                            2 ->                                 // exoPlayerView.setVisibility(View.INVISIBLE);
                                Log.d("krantiv", "STATE_BUFFERING state")

                            3 -> {
                                Log.d("krantiv", "STATE_READY state")
                                if (exoPlayerView!!.visibility == View.INVISIBLE) exoPlayerView!!.visibility =
                                    View.VISIBLE
                            }

                            4 -> {
                                exoPlayerView!!.visibility = View.INVISIBLE
                                Log.d("krantiv", "STATE_ENDED state")
                            }
                        }
                    }
                })
            } else {
                //  if (!player.isPlaying()) {
                run {
                    releasePlayer()
                    exoPlayerView!!.visibility = View.INVISIBLE
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


}