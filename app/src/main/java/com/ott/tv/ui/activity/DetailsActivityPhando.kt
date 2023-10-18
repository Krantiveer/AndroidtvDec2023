package com.ott.tv.ui.activity

/*
import com.singular.sdk.Attributes
import com.singular.sdk.Singular
import com.singular.sdk.SingularConfig
*/
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.ott.tv.Config
import com.ott.tv.Constants
import com.ott.tv.R
import com.ott.tv.adapter.HomePageAdapter
import com.ott.tv.database.DatabaseHelper
import com.ott.tv.fragments.DetailsFragment
import com.ott.tv.model.*
import com.ott.tv.model.home_content.SubtitleDataNew
import com.ott.tv.model.phando.*
import com.ott.tv.network.RetrofitClient
import com.ott.tv.network.api.Dashboard
import com.ott.tv.network.api.FavouriteApi
import com.ott.tv.utils.CMHelper
import com.ott.tv.utils.PreferenceUtils
import com.ott.tv.utils.ToastMsg
import com.ott.tv.video_service.PlaybackModel
import com.ott.tv.video_service.VideoPlaybackActivity
import com.squareup.picasso.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext


class DetailsActivityPhando : FragmentActivity() {
    private var videoId: String? = null
    private var id: String? = null
    private var thumbUrl: String? = null
    private var poster_url: String? = null
    private var trailer_url: String? = null
    private var bannerImageView: ImageView? = null
    private var thumbnail_image: ImageView? = null
    private var contentView: FrameLayout? = null
    private var isWatchLater = false
    private var isFav = false
    private val favIv: ImageView? = null
    private var tvWatchNow: AppCompatButton? = null
    private var tvWatchTrailer: AppCompatButton? = null
    private var imgWatchList: AppCompatButton? = null
    private var btn_seasonAndEpisode: AppCompatButton? = null
    private var imgFavList: AppCompatButton? = null
    private var singleDetails: MediaplaybackData? = null


    private var singleDetailsTVseries: LatestMoviesTVSeriesList? = null
    private var rv_SeasonList: RecyclerView? = null

    private var episode_rv: LinearLayout? = null
    private var details_fragment_root: LinearLayoutCompat? = null
    private var webView: WebView? = null
    private var episode_url = ""
    private val listRelated: MutableList<CommonModels> = ArrayList()
    var userid: String? = null
    var tvDurationTime_value: String? = null
    var tvVideoQuality: String? = null
    var playerView: PlayerView? = null
    var player: SimpleExoPlayer? = null
    var playWhenReady = true
    var current_Window = 0
    var playbackPosition: Long = 0

    //    private ShimmerTextVifew premiumIconImage;
    private var premiumIconImage: ImageView? = null

    private val customAddsModelArrayList = ArrayList<CustomAddsModel>()
    private val mAdapter: ArrayObjectAdapter? = null

    //    private var rvRelated: RecyclerView? = null
    private var relatedAdapter: HomePageAdapter? = null
    private var activity_rv: RelativeLayout? = null
    private var progress_indicator: ProgressBar? = null
    var releaseDate: String? = null
    var description: String? = null
    var title: String? = null
    var tvispaid: String? = null
    var maturity_rating: String? = null
    var ratingData: String? = null
    var language: String? = null
    var genres: String? = null
    var is_live: String? = null
    var is_youtube: String? = null
    private var content_rating_image: ImageView? = null
    private var content_duration_image: ImageView? = null
    private var releasedate_image: ImageView? = null
    private var language_image: ImageView? = null
    private var maturity_rating_image: ImageView? = null
    private var genres_image: ImageView? = null
    private var content_rating_text: TextView? = null
    private var duration_time: TextView? = null
    private var tvReleaseDate: TextView? = null

    private var language_tv: TextView? = null
    private var maturity_rating_tv: TextView? = null
    private var genres_tv: TextView? = null
    private var progDailog: ProgressDialog? = null

    //   private var tv_related: TextView? = null
    var type: String? = null
    val seasonList: MutableList<String> = ArrayList()
    var epList: MutableList<EpiModel> = ArrayList()
    private var movie_title: TextView? = null
    private var movie_titleTwo: TextView? = null
    //  private var deeplinkData: Bundle? = null

    private var detailsFragment: DetailsFragment? = null

    /* private var videodetail: PlaybackModel? = null

     private lateinit var mediaSession: MediaSessionCompat
     private lateinit var mediaSessionConnector: MediaSessionConnector
     private val MEDIA_SESSION_TAG = "ReferenceAppKotlin"
     private val TAG = "SearchFragment"
 */
    val TAG: String = "DetailsAcitivityPhando"

    companion object {
        var contentList: ArrayList<ShowWatchlist>? = null
        var mediaplabackApiResponse: MediaplabackApiResponse? = null
        var detailsData: DetailsData? = null
        var singleDetailsRelated: MediaplaybackData? = null
        var purchaseOption: ArrayList<PurchaseOption>? = null
        lateinit var ivLogo: ImageView
        lateinit var tvTitle: TextView
        lateinit var tvDescription: TextView
        lateinit var textView: TextView
        lateinit var tvWatchNow: TextView
        lateinit var tvResume: TextView
        lateinit var tvBuy: TextView
        lateinit var tvRent: TextView
        lateinit var ratingBar: RatingBar
        var indexOfRow: Int = 0
        var indexOfItem: Int = 0
        const val AMAZON_FEATURE_FIRE_TV = "amazon.hardware.fire_tv"
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_phando)
        type = this.intent.getStringExtra("type")
        thumbUrl = this.intent.getStringExtra("thumbImage")
        videoId = this.intent.getStringExtra("video_id")
        title = this.intent.getStringExtra("title")
        description = this.intent.getStringExtra("description")
        releaseDate = this.intent.getStringExtra("release")
        tvDurationTime_value = this.intent.getStringExtra("duration")
        maturity_rating = this.intent.getStringExtra("maturity_rating")
        tvispaid = this.intent.getStringExtra("ispaid")
        ratingData = this.intent.getStringExtra("rating")
        language = this.intent.getStringExtra("language_str")
        is_live = if (this.intent.getStringExtra("is_live") != null) {
            this.intent.getStringExtra("is_live").toString()
        } else {
            "0"
        }
        genres = this.intent.getStringExtra("genres")

        //  id = this.getIntent().getStringExtra("id");
        poster_url = this.intent.getStringExtra("thumbImage")
        trailer_url = this.intent.getStringExtra("trailer")
        //  tvVideoQuality = this.getIntent().getStringExtra("video_quality");
        contentView = findViewById(R.id.contentView)
        thumbnail_image = findViewById(R.id.thumbnail_image)
        premiumIconImage = findViewById(R.id.premiumIcon)
        tvWatchTrailer = findViewById(R.id.tvWatchTrailer)
        // tv_related = findViewById(R.id.tv_related)

        detailsFragment =
            supportFragmentManager.findFragmentById(R.id.detailsFragment) as DetailsFragment


        contentFromPreviousScreen()
        if (tvispaid != null) {
            if (tvispaid.equals("0", ignoreCase = true) || tvispaid.equals(
                    "2", ignoreCase = true
                )
            ) {
                premiumIconImage!!.setVisibility(View.VISIBLE)
            } else {
                premiumIconImage!!.setVisibility(View.INVISIBLE)
            }
        }
        updateBackgroundThumnail(thumbUrl)
        bannerImageView = findViewById(R.id.bannerImageView)
        setBannerImage(poster_url)
        movie_title = findViewById(R.id.movie_title)
        movie_titleTwo = findViewById(R.id.movie_title_two)
        movie_titleTwo!!.setText(title)
        movie_title!!.setText(title)
        val movie_description_tv = findViewById<TextView>(R.id.movie_description_tv)
        movie_description_tv.text = description
        val tvVideoQualityType = findViewById<ImageView>(R.id.tvVideoQualityType)
        tvWatchNow = findViewById(R.id.tvWatchNow)
        imgWatchList = findViewById(R.id.imgWatchList)
        btn_seasonAndEpisode = findViewById(R.id.img_series)
        if (is_live != null) {
            if (is_live.equals("1", ignoreCase = true)) {
                imgWatchList!!.setVisibility(View.VISIBLE)
            } else {
                imgWatchList!!.setVisibility(View.VISIBLE)
            }
        }
        /*
                rvRelated = findViewById(R.id.rv_related)
        */
        playerView = findViewById(R.id.video_view)
        activity_rv = findViewById(R.id.activity_rv)
        imgFavList = findViewById(R.id.imgFavList)
        progress_indicator = findViewById(R.id.progress_indicator)
        episode_rv = findViewById(R.id.episode_rv_ll)
        details_fragment_root = findViewById(R.id.details_fragment_root)
        webView = findViewById(R.id.webViewScreen)


        if (type == null) {
            type = "M"
        }
        if (type == "M") {
            if (videoId != null) {
                getData(type!!, videoId)
            } else {
                getData(type!!, id)
            }
        } else if (type == "E") {
            if (videoId != null) {
                getData(type!!, videoId)
            } else {
                getData(type!!, id)
            }
        } else if (type == "T") {
            getDataTvseries(type!!, videoId)
        }
        imgWatchList!!.setOnClickListener(View.OnClickListener { v: View? ->
            if (isWatchLater) {
                addToFav("0")
                setResult(0)
            } else {
                addToFav("1")

            }
        })


        /* imgFavList.setOnClickListener(v -> {
            if (isFav) {
                removeFromFav(Constants.WishListType.fav);
            } else {
                addToFav(Constants.WishListType.fav);
            }
        });*/
        //  getFavStatus(Constants.WishListType.watch_later);
        //  getFavStatus(Constants.WishListType.fav);
        tvWatchNow!!.setOnClickListener(View.OnClickListener { v: View? -> payAndWatchTV() })
        tvWatchTrailer!!.setOnClickListener(View.OnClickListener { view: View? -> trailerClick() })
        btn_seasonAndEpisode!!.setOnClickListener(View.OnClickListener { v: View? -> EpisodeAndSeason() })
        PreferenceUtils.getInstance().setFocusFromWatchNowPref(this, false)
        //  initSingularSDK()
        //    PreferenceUtils.updateSubscriptionStatus(DetailsActivityPhando.this);

    }

    /*private  fun initSingularSDK() {
        val config = SingularConfig("phando_corp_5231bd68", "2caece12456ddeba0c28241d9cae9130").withSingularLink(
            intent
        ) { singularLinkParams ->
            deeplinkData = Bundle()
            deeplinkData!!.putString("deeplink", singularLinkParams.deeplink)
            deeplinkData!!.putString("passthrough", singularLinkParams.passthrough)
            deeplinkData!!.putBoolean("isDeferred", singularLinkParams.isDeferred)


        }
        Singular.init(this, config)
        Log.d("@@Singular: ", "Singular Initialized")
    }
    */private fun EpisodeAndSeason() {
        if (singleDetailsTVseries != null) {
            if (id == null) {
                id = videoId
            }
            SeasonListClick(false)
            val season = singleDetailsTVseries!!.seasons
            rv_SeasonList = findViewById<View>(R.id.rv_SeasonList) as RecyclerView
            val seasonAdapter: SeasonAdapter = SeasonAdapter(season)
            rv_SeasonList!!.setHasFixedSize(true)
            rv_SeasonList!!.layoutManager = LinearLayoutManager(this)
            rv_SeasonList!!.adapter = seasonAdapter
            val episodes = singleDetailsTVseries!!.seasons[0].episodes
            val rv_EpisodeList = findViewById<View>(R.id.rv_EpisodeList) as RecyclerView
            val episodeAdapter: EpisodeAdapter = EpisodeAdapter(episodes)
            rv_EpisodeList.setHasFixedSize(true)
            rv_EpisodeList.layoutManager = LinearLayoutManager(this)
            rv_EpisodeList.adapter = episodeAdapter
        }
    }

    private inner class EpisodeAdapter(private val listdata: List<EpisodeList?>?) :
        RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem = layoutInflater.inflate(R.layout.layout_episode, parent, false)
            return ViewHolder(listItem)
        }

        @SuppressLint("SetTextI18n", "RestrictedApi")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (listdata != null) {
                if (listdata[position] != null) {

                    holder.episode_title.text = listdata[position]!!.title
                    holder.episode_description.text = listdata[position]!!.detail
                    holder.episode_time.text = listdata[position]!!.duration_str
                    if(listdata[position]!!.is_free.toString().equals("1",true)){
                        holder.premiumIconImage_TVSeries.visibility=View.GONE

                    }else{
                        holder.premiumIconImage_TVSeries.visibility=View.VISIBLE

                    }

                            Glide.with(applicationContext).load(listdata[position]!!.thumbnail)
                        .error(R.drawable.poster_placeholder_land).fitCenter()
                        .placeholder(R.drawable.poster_placeholder_land).into(holder.episode_image)
                    holder.episode_ll.setOnClickListener { view: View? ->
                        val categoryType: String
                        val pos = holder.absoluteAdapterPosition

                        /* String fileType = "movie";
                if (fileType.equals("movie")) {
                    categoryType = "movie";
                    // Do your task here
                } else {
                    categoryType = "youtube";
                }*/categoryType = "E"

//todo:we need to add description in below
                        getDataEpisode(
                            categoryType,
                            listdata[holder.absoluteAdapterPosition]!!.id.toString(),
                            listdata[holder.absoluteAdapterPosition]
                        )
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return listdata!!.size
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var episode_title: TextView
            var episode_description: TextView
            var episode_time: TextView
            var episodeTv: TextView? = null
            var episode_ll: LinearLayout
            var episode_image: ImageView
            var premiumIconImage_TVSeries: ImageView

            init {
                episode_image = itemView.findViewById(R.id.episode_image)
                premiumIconImage_TVSeries = itemView.findViewById(R.id.premiumIconImage_TVSeries)
                episode_title = itemView.findViewById<View>(R.id.episode_title) as TextView
                episode_description =
                    itemView.findViewById<View>(R.id.episode_description) as TextView
                episode_time = itemView.findViewById<View>(R.id.episode_time) as TextView
                // this.episodeTv = (TextView) itemView.findViewById(R.id.episode_tv);
                episode_ll = itemView.findViewById<View>(R.id.episode_ll) as LinearLayout
            }
        }
    }

    private fun getDataEpisode(videoType: String, videoId: String, dataEpisode: EpisodeList?) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this)
        //  PreferenceUtils.getInstance().getUsersIdActionOTT(this);
        val api = retrofit.create(Dashboard::class.java)
        val call = api.getSingleDetailAPI(accessToken, videoId, videoType, "1")
        activityIndicator(true)
        call.enqueue(object : Callback<MediaplaybackData?> {
            override fun onResponse(
                call: Call<MediaplaybackData?>, response: Response<MediaplaybackData?>
            ) {
                activityIndicator(false)
                if (response.code() == 200 && response.body() != null) {
                    singleDetails = response.body()
                    if (singleDetails!!.list != null) {
                        episode_url = singleDetails!!.list.media_url

                        val videoList: List<Video> = ArrayList()

                            if (singleDetails!!.mediaCode.contentEquals("buyed") ||singleDetails!!.mediaCode.contentEquals("free") || singleDetails!!.mediaCode.contentEquals("package_purchased")
                                || singleDetails!!.mediaCode.contentEquals("package_purchased") || singleDetails!!.mediaCode.contentEquals(
                                    "rented_and_can_buy"
                                )
                            ) {
                               // tvWatchNow!!.text = "Watch Now"

                            }else{
                                if(com.ott.tv.BuildConfig.FLAVOR.contentEquals("vyasott")){
                                    CMHelper.setSnackBar(
                                        contentView,
                                        "Unlock Premium Content, Stream Unlimited Content – Subscribe, Rent, and Enjoy on our Mobile App ",
                                        1,
                                        10000
                                    )
                                }else{
                                    CMHelper.setSnackBar(
                                        contentView,
                                        "Unlock Premium Content, Stream Unlimited Content – Subscribe, Rent, and Enjoy on our Mobile App | WEBSITE -" + Config.DOMAIN,
                                        1,
                                        10000
                                    )
                                }

                                return
                            }

                        if (tvWatchNow!!.text == null || !tvWatchNow!!.text.toString()
                                .equals("Watch Now", ignoreCase = true)
                        ) {
                            if(com.ott.tv.BuildConfig.FLAVOR.contentEquals("vyasott")){
                                Toast.makeText(
                                    applicationContext,
                                    "Unlock Premium Content, Stream Unlimited Content – Subscribe, Rent, and Enjoy on our Mobile App  -",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                Toast.makeText(
                                    applicationContext,
                                    "Unlock Premium Content, Stream Unlimited Content – Subscribe, Rent, and Enjoy on our Mobile App | WEBSITE -" + Config.DOMAIN,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            return
                        } else if (singleDetails!!.list.media_url.isEmpty()) {
                            /*CMHelper.setSnackBar(this.getCurrentFocus(), "We are sorry, Video not available for your selected content", 2);*/
                            Toast.makeText(
                                applicationContext,
                                "We are sorry, Video not available for your selected content",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                        val video = PlaybackModel()
                        val videoListForIntent = ArrayList(videoList)
                        video.id = videoId.toLong()
                        video.title = dataEpisode!!.title
                        video.description = dataEpisode.detail
                        video.videoType = Config.VideoURLTypeHls
                        video.category = "movie"
                        video.videoUrl = singleDetails!!.list.media_url
                        video.cardImageUrl = dataEpisode.thumbnail

                        /*
                       if (!com.ott.tv.BuildConfig.FLAVOR.equals("vyasott", ignoreCase = true)) {

                                video.cardImageUrl = dataEpisode.thumbnail

                        }else {
                            video.cardImageUrl = dataEpisode.poster
                        }*/
                        video.istrailer = false

                        //  video.setBgImageUrl(thumbUrl);
                        video.isPaid = "free"

                        //  video.setVideo(singleDetails.getVideos().get(0));
                        val intent = Intent(applicationContext, PlayerActivityNewCode::class.java)
                        intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video)
                        if(singleDetails!!.list.media_type=="audio"){
                            intent.putExtra("media_type",singleDetails!!.list.media_type )
                            Log.i(TAG, "onResponse: media_type2"+singleDetails!!.list.media_type)

                        }
                        if(singleDetails!!.list.nextMedia!=null){

                        intent.putExtra("next_media_id",singleDetails!!.list!!.nextMedia.id.toString())
                        intent.putExtra("next_media_type",singleDetails!!.list!!.nextMedia.type.toString())

                            Log.i(TAG, "onResponse: media_type"+singleDetails!!.list!!.nextMedia.id.toString() +singleDetails!!.list!!.nextMedia.type)

                        }

                        startActivity(intent)
                    } else {
                        episode_url = ""
                    }
                } else if (response.code() == 401) {
                    signOut()
                } else {
                    CMHelper.setSnackBar(
                        this@DetailsActivityPhando.currentFocus,
                        "We are sorry, This video content not available, Please try another",
                        2
                    )
                }
            }

            override fun onFailure(call: Call<MediaplaybackData?>, t: Throwable) {
                activityIndicator(false)
                Log.i("DetailISSUE_kranti", "onResponse: $t")
                CMHelper.setSnackBar(
                    this@DetailsActivityPhando.currentFocus,
                    "We are sorry, This video content not available, Please try another$t",
                    2
                )
            }
        })
    }

    private inner class SeasonAdapter     // RecyclerView recyclerView;
        (private val listdata: List<SeasonList>) :
        RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem = layoutInflater.inflate(R.layout.layout_season, parent, false)
            return ViewHolder(listItem)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.seasonsTv.text = listdata[position].title
            if (listdata[position].episodes != null) {
                /* if (!listdata.get(position).getEpisodes().isEmpty()) {
                    holder.episodeTv.setText("    " + listdata.get(position).getEpisodes().size() + " Episodes");
                }*/
            }
            holder.relativeLayout.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    /*  if (holder.episodeTv.getText().toString().isEmpty()) {
                        CMHelper.setSnackBar(view, "Episode not Available", 1);

                    } else */
                    run {
                        val episodes =
                            singleDetailsTVseries!!.seasons[holder.absoluteAdapterPosition].episodes
                        val rv_EpisodeList = findViewById<View>(R.id.rv_EpisodeList) as RecyclerView
                        val episodeAdapter: EpisodeAdapter = EpisodeAdapter(episodes)
                        rv_EpisodeList.setHasFixedSize(true)
                        rv_EpisodeList.layoutManager =
                            LinearLayoutManager(this@DetailsActivityPhando)
                        rv_EpisodeList.adapter = episodeAdapter
                    }


                    //      CMHelper.setSnackBar(view, "Episode not Available" + holder.getAbsoluteAdapterPosition(), 1);
                }
            })
        }

        override fun getItemCount(): Int {
            return listdata.size
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var seasonsTv: TextView
            var episodeTv: TextView
            var relativeLayout: RelativeLayout

            init {
                seasonsTv = itemView.findViewById<View>(R.id.seasons) as TextView
                episodeTv = itemView.findViewById<View>(R.id.episode_tv) as TextView
                relativeLayout = itemView.findViewById<View>(R.id.relativeLayout) as RelativeLayout
            }
        }
    }

    fun SeasonListClick(isShow: Boolean) {
        if (singleDetailsTVseries != null) {
            if (isShow) {
                details_fragment_root!!.visibility = View.VISIBLE
                btn_seasonAndEpisode!!.requestFocus()
                episode_rv!!.visibility = View.GONE

            } else {
                /*       episode_rv!!.visibility = View.VISIBLE
                       tvWatchTrailer!!.visibility = View.GONE
                       movie_title!!.visibility = View.GONE*/
                details_fragment_root!!.visibility = View.GONE
                episode_rv!!.visibility = View.VISIBLE

            }
        }
    }

    private fun contentFromPreviousScreen() {
        content_rating_text = findViewById(R.id.content_rating_text)
        content_rating_image = findViewById(R.id.content_rating_image)
        if (ratingData != null) {
            if (!ratingData.equals("0", ignoreCase = true)) {
                content_rating_text!!.setText(ratingData)
            } else {
                content_rating_image!!.setVisibility(View.GONE)
                content_rating_text!!.setVisibility(View.GONE)
            }
        } else {
            content_rating_image!!.setVisibility(View.GONE)
            content_rating_text!!.setVisibility(View.GONE)
        }
        content_duration_image = findViewById(R.id.content_duration_image)
        duration_time = findViewById(R.id.duration_time)
        if (tvDurationTime_value != null && !tvDurationTime_value!!.isEmpty()) {
            duration_time!!.setText(tvDurationTime_value)
        } else {
            content_duration_image!!.setVisibility(View.GONE)
            duration_time!!.setVisibility(View.GONE)
        }
        releasedate_image = findViewById(R.id.releasedate_image)
        tvReleaseDate = findViewById(R.id.tvReleaseDate)
        if (releaseDate != null) {
            if (!releaseDate.equals("0", ignoreCase = true)) {
                tvReleaseDate!!.setText(releaseDate)
            } else {
                releasedate_image!!.setVisibility(View.GONE)
                tvReleaseDate!!.setVisibility(View.GONE)
            }
        } else {
            releasedate_image!!.setVisibility(View.GONE)
            tvReleaseDate!!.setVisibility(View.GONE)
        }
        language_image = findViewById(R.id.language_image)
        language_tv = findViewById(R.id.language_tv)
        if (language != null && !language!!.isEmpty()) {
            language_tv!!.setText(language)
        } else {
            language_image!!.setVisibility(View.GONE)
            language_tv!!.setVisibility(View.GONE)
        }
        maturity_rating_image = findViewById(R.id.maturity_rating_image)
        maturity_rating_tv = findViewById(R.id.maturity_rating)
        if (maturity_rating != null) {
            maturity_rating_tv!!.setText(maturity_rating)
        } else {
            maturity_rating_image!!.setVisibility(View.GONE)
            maturity_rating_tv!!.setVisibility(View.GONE)
        }
        genres_image = findViewById(R.id.genres_image)
        genres_tv = findViewById(R.id.genres_tv)
        if (genres != null) {
            genres_tv!!.setText(genres)
        } else {
            genres_image!!.setVisibility(View.GONE)
            genres_tv!!.setVisibility(View.GONE)
        }
        if (trailer_url != null && !trailer_url!!.isEmpty()) {

            if (BuildConfig.FLAVOR.equals("solidtv") || BuildConfig.FLAVOR.equals("kaafaltv")) {
                tvWatchTrailer!!.visibility = View.GONE

            } else {
                tvWatchTrailer!!.visibility = View.VISIBLE
            }
        } else {
            tvWatchTrailer!!.visibility = View.GONE
        }
    }

    override fun onStop() {
        if (Util.SDK_INT >= 24) releasePlayer()
        super.onStop()
        //destroyPlayer()
    }

    public override fun onDestroy() {

        // Releasing the mediaSession due to inactive playback and setting token for cast to null.
        /*        if (mediaSession != null) {
                    mediaSession.release()
                    CastHelper.setMediaSessionTokenForCast(
                        *//* mediaSession =*//* null,
                CastReceiverContext.getInstance().mediaManager
            )
        }*/

        if (Util.SDK_INT >= 24) releasePlayer()
        super.onDestroy()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            current_Window = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    /*
        private fun createMediaSession() {
            mediaSession = MediaSessionCompat(this, MEDIA_SESSION_TAG)
            Log.i(TAG, "createMediaSession: " + videodetail)
            if (videodetail != null) {
                mediaSessionConnector = MediaSessionConnector(mediaSession).apply {

                    setQueueNavigator(SingleVideoQueueNavigator(videodetail!!, mediaSession))
                    setControlDispatcher(object : DefaultControlDispatcher() {
                        override fun dispatchStop(player: Player, reset: Boolean): Boolean {
                            // Treat stop commands as pause, this keeps ExoPlayer, MediaSession, etc.
                            // in memory to allow for quickly resuming. This also maintains the playback
                            // position so that the user will resume from the current position when backing
                            // out and returning to this video
                            // Timber.v("Playback stopped at ${player.currentPosition}")
                            Log.i(TAG, "Playback stopped at ${player.currentPosition} ")
                            // This both prevents playback from starting automatically and pauses it if
                            // it's already playing
                            player.playWhenReady = false
                            return true
                        }
                    })
                }
            }

            CastHelper.setMediaSessionTokenForCast(
                mediaSession,
                CastReceiverContext.getInstance().mediaManager
            )
        }
    */
    override fun onPause() {
        Log.i(TAG, "onPause: ")
        if (Util.SDK_INT <= 24)
            releasePlayer()
        super.onPause()
    }

    fun setBannerImage(url: String?) {
        Glide.with(this).load(url).placeholder(R.drawable.poster_placeholder_land)
            .error(R.drawable.poster_placeholder_land).into(
                bannerImageView!!
            )
    }

    fun updateBackgroundThumnail(url: String?) {
        Glide.with(this).load(url).placeholder(R.color.black_color)
            .error(R.drawable.poster_placeholder_land).into(
                thumbnail_image!!
            )
    }

    private fun payAndWatchTV() {
        val subtitleList = ArrayList<SubtitleDataNew>()

        if (singleDetails != null) {
            if (singleDetails!!.list != null) {
                /*
                            Singular.event("Click watch movie",
                                Attributes.sngAttrContent.toString(),title,
                                Attributes.sngAttrContentId.toString(),videoId,
                                Attributes.sngAttrContentType.toString(), "ugccontent",
                                Attributes.sngAttrSuccess.toString(), "SUCCESS"
                            )
                         */               //    Constants.IS_FROM_WATCH_NOW=true
                PreferenceUtils.getInstance().setFocusFromWatchNowPref(this, true);
                val videoList: List<Video> = ArrayList()
                /*         for (Video video : singleDetails.getVideos()) {
                    if (video.getFileType() != null && !video.getFileType().equalsIgnoreCase("embed")) {
                        videoList.add(video);
                    }
                }*/if (tvWatchNow!!.text == null || !tvWatchNow!!.text.toString()
                        .equals("Watch Now", ignoreCase = true)
                ) {

                    if(com.ott.tv.BuildConfig.FLAVOR.contentEquals("vyasott")){
                        CMHelper.setSnackBar(
                            contentView,
                            "Unlock Premium Content, Stream Unlimited Content – Subscribe, Rent, and Enjoy on our Mobile App ",
                            1,
                            10000
                        )
                    }else{
                        CMHelper.setSnackBar(
                            contentView,
                            "Unlock Premium Content, Stream Unlimited Content – Subscribe, Rent, and Enjoy on our Mobile App | WEBSITE -" + Config.DOMAIN,
                            1,
                            10000
                        )
                    }

                    return
                } else if (singleDetails!!.list.media_url.isEmpty() && singleDetails!!.list.youtube_url.isEmpty()) {
                    CMHelper.setSnackBar(
                        this.currentFocus,
                        "We are sorry, Video not available for your selected content",
                        2
                    )
                    return
                }
                val video = PlaybackModel()
                val videoListForIntent = ArrayList(videoList)
                video.id = videoId!!.toLong()
                video.title = title
                video.description = description
                video.videoType = Config.VideoURLTypeHls
                // video.subtitle = singleDetails?.subtitle

                if (type == "M") {
                    video.category = "movie"
                } else if (type == "E") {
                    video.category = "movie"
                    video.videoType = "hls"
                    video.videoUrl = singleDetails!!.list.media_url
                } else {
                    video.category = "movie"
                }


                //  video.setCategory(type);
                //  singleDetails!!.list.is_youtube=1
                if (singleDetails!!.list.is_youtube != null) {
                    if (singleDetails!!.list.is_youtube.toString().equals("1", ignoreCase = true)) {
                        video.videoType = "youtube"
                        video.videoUrl = singleDetails!!.list.youtube_url
                        /* val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=3vlKXvuhAGw"))
                         startActivity(intent)
                         return*/


                        val youtubeVideoURL = video.videoUrl
                        //val youtubeVideoURL = "https://youtu.be/3vlKXvuhAGw?si=y3mWse_2IgEq9t9c"
                        // https://youtu.be/a7iIuRJtPU0?si=0_eYEsO3Ja2MUchd
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeVideoURL))

                        // Verify if there's an app to handle this Intent
                        if (intent.resolveActivity(packageManager) != null) {
                            // Start the activity to open YouTube
                            startActivity(intent)
                        }
                        return

                        /*val intentyoutube = Intent(this, PlaybackActivityYoutube::class.java)
                        intent.putExtra("youtube", video.videoUrl)
                        startActivity(intentyoutube)
                        return*/


                    } else {

                        video.videoType = "hls"
                        video.videoUrl = singleDetails!!.list.media_url
                    }
                }
                video.cardImageUrl = poster_url
                video.istrailer = false
                video.bgImageUrl = thumbUrl
                video.isPaid = "free"
                if (is_live.equals("1", ignoreCase = true)) {
                    video.islive = is_live;
                } else {
                    video.islive = "0"
                }
                if (singleDetails!!.list.is_youtube.toString().equals("3")) {

                    progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
                    progDailog!!.setCancelable(false);

                    webView!!.visibility = View.VISIBLE

                    var url = singleDetails!!.list.external_url
                    Log.i(TAG, "payAndWatchTV: --->" + url)
                    webView!!.getSettings().setJavaScriptEnabled(true);
                    webView!!.getSettings().setLoadWithOverviewMode(true);
                    webView!!.getSettings().setUseWideViewPort(true);
                    webView!!.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            progDailog!!.show()
                            view.loadUrl(url)
                            return true
                        }

                        override fun onPageFinished(view: WebView, url: String) {
                            progDailog!!.dismiss()
                        }
                    }
                    webView!!.loadUrl(url)

                    //      webView!!.webViewClient = WebViewClient()
                    //  webView!!.loadUrl("https://www.mxplayer.in/movie/watch-chennai-central-hindi-dubbed-movie-online-0dcdb2dad744506671b7ca7ef024c9ec?watch=true");
                    return
                }


                /*   if (singleDetails!!.list.is_youtube.toString().equals("0", ignoreCase = true)) {
                       findViewById<WebView>(R.id.Webview).visibility = View.VISIBLE
                       findViewById<FrameLayout>(R.id.contentView).visibility = View.GONE
                       val mywebview = findViewById<View>(R.id.Webview) as WebView

                           val urlIntent = Intent(
                               Intent.ACTION_VIEW,
                               Uri.parse("https://www.uvtv.in/watch/tvshow/episode/962")
                           )
                           startActivity(urlIntent)
                     //  mywebview.loadUrl("https://www.uvtv.in/watch/tvshow/episode/962")

                   } else {*/
                //  video.setVideo(singleDetails.getVideos().get(0));
                var enable_subtitle = "false"
                if (singleDetails!!.list.ccFiles != null) {
                    if (singleDetails!!.list.ccFiles.size > 0) {
                        enable_subtitle = "true"
                    }
                }
                Log.i(TAG, "payAndWatchTV: " + singleDetails!!.list.ccFiles.size)
                for (i in 0 until singleDetails!!.list.ccFiles.size) {
                    // Access each element of the array

                    /*      }

                          for (i in 1..2) {*/
                    val language = singleDetails!!.list.ccFiles.get(i).language
                    val url = singleDetails!!.list.ccFiles.get(i).url
                    subtitleList.add(SubtitleDataNew(language, url))
                    Log.i(TAG, "payAndWatchTV22: " + language)

                }


                val intent = Intent(this, PlayerActivityNewCode::class.java)
                intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video)
                intent.putExtra("Enable_Subtile", enable_subtitle)
                if(singleDetails!!.list.media_type=="audio"){
                    intent.putExtra("media_type",singleDetails!!.list.media_type )
                    Log.i(TAG, "onResponse: media_type3"+singleDetails!!.list.media_type)

                }
                if (enable_subtitle.contentEquals("true")) {
                    //  intent.putExtra("subtitle", singleDetails!!.list.ccFiles)
                    intent.putParcelableArrayListExtra("subtitle", subtitleList)
                    // intent.putParcelableArrayListExtra("subtitle", singleDetails!!.list.ccFiles)
                    //     intent.putExtra("CC_FILES_DATA", singleDetails!!.list.ccFiles);
                    //    intent.putParcelableArrayListExtra("cc_files_list", new ArrayList<>(singleDetails!!.list.ccFiles))
                }
                startActivity(intent)

            }
        }
    }


    fun trailerClick() {
        val videoList: List<Video> = ArrayList()
        val video = PlaybackModel()
        val videoListForIntent = ArrayList(videoList)
        video.id = videoId!!.toLong()
        video.title = title
        video.description = description
        video.videoType = Config.VideoURLTypeHls
        video.category = type
        video.videoUrl = trailer_url
        // video.setCardImageUrl(trailer_url);
        video.istrailer = false
        video.bgImageUrl = thumbUrl
        video.isPaid = "free"
        //  video.setVideo(singleDetails.getVideos().get(0));
        val intent = Intent(this, PlayerActivityNewCode::class.java)
        intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video)
        startActivity(intent)
    }

    private fun getData(videoType: String, videoId: String?) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        userid = if (PreferenceUtils.getUserId(applicationContext) != null) {
            PreferenceUtils.getUserId(applicationContext)
        } else {
            " "
        }
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this)
        //  PreferenceUtils.getInstance().getUsersIdActionOTT(this);
        val api = retrofit.create(Dashboard::class.java)
        val call = api.getSingleDetailAPI(accessToken, videoId, videoType, "1")
        activityIndicator(true)
        call.enqueue(object : Callback<MediaplaybackData?> {
            override fun onResponse(
                call: Call<MediaplaybackData?>, response: Response<MediaplaybackData?>
            ) {
                activityIndicator(false)
                if (response.code() == 200 && response.body() != null) {
                    singleDetails = response.body()
                    singleDetailsRelated = singleDetails
                    //     detailsData=singleDetails.list
                    if (singleDetails!!.list != null) {
                        if (singleDetails!!.list.is_wishlist.equals("1", ignoreCase = true)) {
                            isWatchLater = true
                            imgWatchList!!.text = "Remove to Watchlist"
                            imgWatchList!!.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_right_tick,
                                0,
                                0,
                                0
                            );
                        } else {
                            imgWatchList!!.text = "Add to Watchlist"
                            imgWatchList!!.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_baseline_add_24,
                                0,
                                0,
                                0
                            );
                            isWatchLater = false
                        }
                        if (singleDetails!!.list.media_url == null) {
                            tvWatchNow!!.visibility = View.GONE
                        }
                        if (singleDetails!!.list.is_youtube != null) {
                            is_live = singleDetails!!.list.is_live.toString()
                        }
                        if (singleDetails!!.list.is_live != null) {
                            is_youtube = singleDetails!!.list.is_live.toString()
                        }

                        //  if(singleDetails.getMediaCode())
                        //  singleDetails.setType("M");

                        if (singleDetails!!.list.related.size > 0) {
                            //        detailsData=singleDetails
                            //----related post---------------

                            detailsFragment!!.requireView().visibility == View.VISIBLE
                            detailsFragment!!.setData()


                        } else {
                            detailsFragment!!.requireView().visibility == View.GONE
                            //      tv_related!!.visibility = View.GONE
                            // rvRelated!!.visibility = View.GONE
                        }
                        setMovieData()


                    }
                } else if (response.code() == 401) {
                    signOut()
                } else {
                    CMHelper.setSnackBar(
                        this@DetailsActivityPhando.currentFocus,
                        "We are sorry, This video content not available, Please try another",
                        2
                    )
                }
            }

            override fun onFailure(call: Call<MediaplaybackData?>, t: Throwable) {
                activityIndicator(false)
                CMHelper.setSnackBar(
                    this@DetailsActivityPhando.currentFocus,
                    "We are sorry, This video content not available, Please try another$t",
                    2
                )
            }
        })
    }

    private fun signOut() {
        if (getContext() != null && this != null) {
            val databaseHelper = DatabaseHelper(this)
            if (PreferenceUtils.getInstance().getAccessTokenPref(this) !== "") {
                val editor: SharedPreferences.Editor =
                    getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE)
                        .edit()
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false)
                editor.apply()
                databaseHelper.deleteUserData()
                PreferenceUtils.clearSubscriptionSavedData(this)
                PreferenceUtils.getInstance().setAccessTokenNPref(this, "")
                Toast.makeText(
                    this,
                    "You've been logged out because we have detected another login from your ID on a different device. You are not allowed to login on more than one device at a time.",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, LoginChooserActivity::class.java))
                finish()
            }
        }
    }

    private fun getDataTvseries(videoType: String, videoId: String?) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        userid = if (PreferenceUtils.getUserId(applicationContext) != null) {
            PreferenceUtils.getUserId(applicationContext)
        } else {
            " "
        }
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this)
        //  PreferenceUtils.getInstance().getUsersIdActionOTT(this);
        val api = retrofit.create(Dashboard::class.java)
        val call = api.getSingleDetailAPITvSeries(accessToken, videoId)
        activityIndicator(true)
        call.enqueue(object : Callback<LatestMoviesTVSeriesList?> {
            override fun onResponse(
                call: Call<LatestMoviesTVSeriesList?>, response: Response<LatestMoviesTVSeriesList?>
            ) {
                activityIndicator(false)
                if (response.code() == 200 && response.body() != null) {
                    singleDetailsTVseries = response.body()
                    if (singleDetailsTVseries!!.list != null) {
                        imgWatchList!!.visibility = View.GONE
                        tvWatchNow!!.visibility = View.GONE
                        setMovieDataTVSeries()
                        btn_seasonAndEpisode!!.visibility = View.VISIBLE
                    }
                } else {
                    CMHelper.setSnackBar(
                        this@DetailsActivityPhando.currentFocus,
                        "We are sorry, This video content not available, Please try another",
                        2
                    )
                }
            }

            override fun onFailure(call: Call<LatestMoviesTVSeriesList?>, t: Throwable) {
                activityIndicator(false)
                Log.i("DetailISSUE_kranti", "onResponse: $t")
                CMHelper.setSnackBar(
                    this@DetailsActivityPhando.currentFocus,
                    "We are sorry, This video content not available, Please try another$t",
                    2
                )
            }
        })
    }

    private fun setMovieDataTVSeries() {

        //----season and episode download------------
        for (i in singleDetailsTVseries!!.seasons.indices) {
            val season = singleDetailsTVseries!!.seasons[i]
            val models = CommonModels()
            val season_name = season.title
            models.setTitle(season.title)
            seasonList.add("Season: " + season.title)
            if (singleDetailsTVseries!!.seasons[0].title != null) {
                tvWatchTrailer!!.text = singleDetailsTVseries!!.seasons[0].title
            } else {
                tvWatchTrailer!!.text = "Play Season "
            }
            // singleDetails.season.get(0).seasonsName
            //----episode------
            // List<EpiModel> epList = new ArrayList<>();
            for (j in singleDetailsTVseries!!.seasons[i].episodes.indices) {
                val episode = singleDetailsTVseries!!.seasons[i].episodes[j]
                val model = EpiModel()
                model.seson = season_name
                model.epi = episode.title
                //kingsa
                //  model.setStreamURL(episode.getFileUrl());
                model.serverType = episode.type
                model.imageUrl = episode.thumbnail
                // model.setSubtitleList(episode.getSubtitle());
                epList.add(model)
            }

            // models.setListEpi(epList);
            //   listServer.add(models);
        }
    }

    /*
        private void updateContinueWatchingDataToServer() {
            if ((playerCurrentPosition / 1000) > 5) {
                long fromTime=0L;
                if (getIntent().hasExtra(POSITION)){
                    String pos = getIntent().getStringExtra(POSITION);
                    if (pos != null) {
                        fromTime = Long.parseLong(pos) ;
                    }
                }

                Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                ContinueWatchApi api = retrofit.create(ContinueWatchApi.class);
                Call<APIResponse<Object>> call = api.saveContinueWatch(Config.API_KEY, userId, id,
                        String.valueOf((playerCurrentPosition / 1000)),
                        categoryType, String.valueOf(fromTime),String.valueOf((playerCurrentPosition / 1000)), AppConfig.Device_Type);

                call.enqueue(new Callback<APIResponse<Object>>() {
                    @Override
                    public void onResponse(@NonNull Call<APIResponse<Object>> call, @NonNull Response<APIResponse<Object>> response) {
                        try {
                            Log.d(TAG, "onResponse: " + response.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<APIResponse<Object>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        }
    */
    @SuppressLint("SetTextI18n")
    private fun setMovieData() {
        if (singleDetails!!.list.media_url != null) {
            tvWatchNow!!.text = "Watch Now"
        }
        /*
                 if (singleDetails!!.list.duration_str.isNullOrEmpty()) {
                     duration_time!!.text = singleDetails!!.list.duration_str
                     duration_time!!.setVisibility(View.VISIBLE)
                     content_duration_image!!.setVisibility(View.VISIBLE)
                 } else {
                     duration_time!!.setVisibility(View.GONE)
                     content_duration_image!!.setVisibility(View.GONE)
                 }
        */
        if (singleDetails!!.list.is_free == 0) {
            tvWatchNow!!.setText("Please Subscribe")
        }
        if (singleDetails!!.list.is_free == 2) {
            tvWatchNow!!.setText("Pay and Watch ")
        }
        if (singleDetails!!.mediaCode.contentEquals("buyed") || singleDetails!!.mediaCode.contentEquals(
                "package_purchased"
            )
            || singleDetails!!.mediaCode.contentEquals("package_purchased") || singleDetails!!.mediaCode.contentEquals(
                "rented_and_can_buy"
            )
        ) {
            tvWatchNow!!.text = "Watch Now"

        }

        /*
        if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Subscription Based")) {
            if (PreferenceUtils.isActivePlan(DetailsActivityPhando.this)) {
                if (PreferenceUtils.isValid(DetailsActivityPhando.this)) {
                    tvWatchNow.setText("Watch Now");
                } else {
                    tvWatchNow.setText("Please Subscribe");
                }
            } else {
                tvWatchNow.setText("Please Subscribe");
            }
        } else if (singleDetails.pre_booking_enabled != null && singleDetails.pre_booking_enabled.equalsIgnoreCase("true")) {
            if (singleDetails.is_video_pre_booked_subscription_started != null && singleDetails.is_video_pre_booked_subscription_started.equals("true")) {
                tvWatchNow.setText("Watch Now");
            } else if (singleDetails.is_video_pre_booked != null && singleDetails.is_video_pre_booked.equals("true")) {
                tvWatchNow.setText("Already Booked");
            } else {
                tvWatchNow.setText(String.format("Pre Booking ₹%s", singleDetails.price));
            }
        } else if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Pay and Watch")) {
            if (singleDetails.is_subscribed_previously != null && singleDetails.is_subscribed_previously.equalsIgnoreCase("1")) {
                tvWatchNow.setText(String.format("Pay Again and watch for ₹%s", singleDetails.price));
                if (singleDetails.is_rent_expired != null && singleDetails.is_rent_expired.equalsIgnoreCase("0")) {
                    tvWatchNow.setText("Watch Now");
                }
            } else {
                tvWatchNow.setText(String.format("Pay and watch for ₹%s", singleDetails.price));
            }
        } else {
            tvWatchNow.setText("Watch Now");
        }


        arrayList=singleDetails.getWriter();


        //     String releaseDate = "Release on - " + singleDetails.getRelease();

        if (tvVideoQuality != null && tvVideoQuality.equalsIgnoreCase("hd"))
            tvVideoQualityType.setVisibility(VISIBLE);
        else
            tvVideoQualityType.setVisibility(GONE);


        if (singleDetails.tv_banner_image != null && !singleDetails.tv_banner_image.isEmpty()) {
            setBannerImage(singleDetails.tv_banner_image);
        }
        if (singleDetails != null) {
            String youtubeSource = singleDetails.getTrailler_youtube_source();
            String trailerAwsSource = singleDetails.trailer_aws_source;
            if (trailerAwsSource != null && !trailerAwsSource.isEmpty()) {
                tvWatchTrailer.setVisibility(VISIBLE);
                tvWatchTrailer.requestFocus();
            } else if (youtubeSource != null && !youtubeSource.isEmpty()) {
                tvWatchTrailer.setVisibility(VISIBLE);
                tvWatchTrailer.requestFocus();
            } else {
                tvWatchTrailer.setVisibility(GONE);
            }
        }
        if (singleDetails.getPosterUrl() == null || singleDetails.getPosterUrl().equalsIgnoreCase("")) {
            tvWatchTrailer.setVisibility(View.INVISIBLE);

        } else {
            tvWatchTrailer.setVisibility(VISIBLE);
        }
*/
    }


    override fun onResume() {
        Log.i("onresume call", "onResume: " + Constants.IS_FROM_HOME)
        if (webView!!.isVisible) {
            webView!!.requestFocus()
        } else {
            if (episode_rv!!.isVisible) {
                episode_rv!!.requestFocus()
            }
            if (PreferenceUtils.getInstance().getFocusFromWatchNowPref(this).equals(true)) {
                tvWatchNow?.requestFocus()
                PreferenceUtils.getInstance().setFocusFromWatchNowPref(this, false)


            }
        }
        /*
                if (Constants.IS_FROM_WATCH_NOW) {
                    tvWatchNow?.requestFocus()
                    Constants.IS_FROM_WATCH_NOW=false

                }else{
                    Constants.IS_FROM_WATCH_NOW=false
                }
        */
        Log.i("onresume call", "onResume: " + Constants.IS_FROM_HOME)

        // tvWatchNow!!.requestFocus()
        super.onResume()
    }

    override fun onBackPressed() {
        if (webView!!.isVisible) {
            webView!!.visibility = View.GONE
            return
        }
        if (episode_rv!!.isVisible) {
            Log.i("Backbutton", "onBackPressed:2 " + episode_rv!!.isVisible)
            SeasonListClick(true)

        } else {
            super.onBackPressed()
            Log.i("Backbutton", "onBackPressed:3 " + episode_rv!!.isVisible)
        }

        Log.i("Backbutton", "onBackPressed:1 " + episode_rv!!.isVisible)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        /* if (keyCode == 22) {
             if (tvWatchNow!!.hasFocus()&&tvWatchNow) {
              *//*   tvWatchTrailer!!.requestFocus()
                tvWatchTrailer!!.isFocusable = true*//*
            }
        }*/
        if (KeyEvent.KEYCODE_DPAD_UP == keyCode) {

            if (webView!!.isVisible) {
                webView!!.requestFocus()
            } else {
                if (episode_rv!!.isVisible) {
                } else {
                    tvWatchNow?.requestFocus()
                }
            }
            /*if(detailsFragment?.isVisible==visible){
}
          }*/
        }
        Log.e("DetailActivityPhando", "movieIndex : " + keyCode + episode_rv?.isVisible)
        when (keyCode) {

            /*    KeyEvent.KEYCODE_BACK -> {
                    return false
                    Log.i("Backbutton", "onBackPressed:4 " + episode_rv!!.isVisible)
                }*/
            KeyEvent.KEYCODE_DPAD_CENTER -> return false
            KeyEvent.KEYCODE_DPAD_LEFT -> return false
            KeyEvent.KEYCODE_DPAD_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_UP -> {
                Log.e("DetailActivityPhando", "movieIndex : ")
                return false
            }

            KeyEvent.KEYCODE_DPAD_UP_LEFT -> return false
            KeyEvent.KEYCODE_DPAD_UP_RIGHT -> return false
            KeyEvent.KEYCODE_DPAD_DOWN -> return false
            KeyEvent.KEYCODE_DPAD_DOWN_LEFT -> return false
            KeyEvent.KEYCODE_DPAD_DOWN_RIGHT -> return false
        }
        return super.onKeyDown(keyCode, event)


    }

    private fun addToFav(value: String) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(Dashboard::class.java)
        val accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this)
        val call = api.updateWatchList(accessToken, videoId, type, Integer.valueOf(value))
        call.enqueue(object : Callback<Wishlist?> {
            override fun onResponse(call: Call<Wishlist?>, response: Response<Wishlist?>) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body()!!.status.equals("success", ignoreCase = true)) {
                        ToastMsg(this@DetailsActivityPhando).toastIconSuccess(response.body()!!.message)
                        if (response.body()!!.status_code != null) {
                            if (response.body()!!.status_code.equals("1", ignoreCase = true)) {
                                isWatchLater = true
                                imgWatchList!!.text = "Added to Watchlist"
                                imgWatchList!!.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.ic_right_tick,
                                    0,
                                    0,
                                    0
                                );
                                //imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else {
                                imgWatchList!!.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.ic_baseline_add_24,
                                    0,
                                    0,
                                    0
                                );
                                isWatchLater = false
                                imgWatchList!!.text = "Add to Watchlist"

                                /*    imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                            }
                        }
                    } else {
                        ToastMsg(this@DetailsActivityPhando).toastIconError(response.body()!!.message)
                    }
                } else if (response.code() == 401) {
                    signOut()
                } else {
                    ToastMsg(this@DetailsActivityPhando).toastIconError(getString(R.string.error_toast))
                }
            }

            override fun onFailure(call: Call<Wishlist?>, t: Throwable) {
                ToastMsg(this@DetailsActivityPhando).toastIconError(getString(R.string.error_toast))
                Log.e("DetailsActivityPhando", "onFailure: $t")
            }
        })
    }

    private fun removeFromFav(type: String) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(FavouriteApi::class.java)
        val call = api.removeFromFavorite(Config.API_KEY, userid, videoId, type)
        call.enqueue(object : Callback<FavoriteModel?> {
            override fun onResponse(
                call: Call<FavoriteModel?>, response: Response<FavoriteModel?>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body()!!.status.equals("success", ignoreCase = true)) {
                        ToastMsg(this@DetailsActivityPhando).toastIconSuccess(response.body()!!.message)
                        if (type == Constants.WishListType.fav) {
                            isFav = false
                            imgFavList!!.text = "Add to Favorite"
                            /*      imgAddFav.setImageResource(R.drawable.ic_bottom_fav);
                            favIv.setImageResource(R.drawable.ic_bottom_fav);*/
                        } else {
                            isWatchLater = false
                            //kranti
                            imgWatchList!!.text = "Add to Watchlist"
                            // imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorHint), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    } else {
                        ToastMsg(this@DetailsActivityPhando).toastIconError(response.body()!!.message)
                        if (type == Constants.WishListType.fav) {
                            isFav = true
                            imgFavList!!.text = "Remove to Favorite"
                            /*        imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        } else {
                            isWatchLater = true
                            //kranti
                            imgWatchList!!.text = "Remove to Watchlist"
                            //     imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FavoriteModel?>, t: Throwable) {
                ToastMsg(this@DetailsActivityPhando).toastIconError(getString(R.string.fetch_error))
            }
        })
    }

    private fun getFavStatus(type: String) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(FavouriteApi::class.java)
        val call = api.verifyFavoriteList(Config.API_KEY, userid, videoId, type)
        activityIndicator(true)
        call.enqueue(object : Callback<FavoriteModel?> {
            override fun onResponse(
                call: Call<FavoriteModel?>, response: Response<FavoriteModel?>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    activityIndicator(false)
                    if (type == Constants.WishListType.fav) {
                        if (response.body()!!.status.equals("success", ignoreCase = true)) {
                            isFav = true
                            imgFavList!!.text = "Remove to Favourite"
                            /*         imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        } else {
                            isFav = false
                            imgFavList!!.text = "Add to Favourite"
                            /*       imgAddFav.setImageResource(R.drawable.ic_bottom_fav);
                            favIv.setImageResource(R.drawable.ic_bottom_fav);
                    */
                        }
                        /*      imgAddFav.setVisibility(VISIBLE);
                        favIv.setVisibility(VISIBLE);*/
                    } else {
                        if (response.body()!!.status.equals("success", ignoreCase = true)) {
                            isWatchLater = true
                            imgWatchList!!.text = "Remove to Watchlist"
                            //   imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                        } else {
                            isWatchLater = false
                            imgWatchList!!.text = "Add to Watchlist"
                            //     imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorHint), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                        /*   imgAddFav.setVisibility(VISIBLE);
                        favIv.setVisibility(VISIBLE);*/
                    }
                }
            }

            override fun onFailure(call: Call<FavoriteModel?>, t: Throwable) {
                activityIndicator(true)
                ToastMsg(this@DetailsActivityPhando).toastIconError(getString(R.string.fetch_error))
            }
        })
    }

    private fun activityIndicator(show: Boolean) {
        if (show) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
            progress_indicator!!.visibility = View.VISIBLE
        } else {
            progress_indicator!!.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }


}