package com.ott.tv.model.phando

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioManager
import android.media.session.MediaSession
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.tvprovider.media.tv.TvContractCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView.ControllerVisibilityListener
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.ott.tv.Config
import com.ott.tv.R
import com.ott.tv.adapter.ServerAdapter
import com.ott.tv.adapter.ServerAdapter.OriginalViewHolder
import com.ott.tv.adapter.SubtitleListAdapter
import com.ott.tv.model.Video
import com.ott.tv.model.home_content.SubtitleDataNew
import com.ott.tv.utils.PreferenceUtils
import com.ott.tv.utils.ToastMsg
import com.ott.tv.video_service.MediaSessionHelper
import com.ott.tv.video_service.MockDatabase
import com.ott.tv.video_service.PlaybackModel
import com.ott.tv.video_service.VideoPlaybackActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern

/*import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;*/ /*
import com.npaw.youbora.lib6.exoplayer2.Exoplayer2Adapter;
import com.npaw.youbora.lib6.plugin.Options;
import com.npaw.youbora.lib6.plugin.Plugin;
*/   class PlayerActivityNewCode : AppCompatActivity(), ControllerVisibilityListener {
    // private PlayerView exoPlayerView;
    private var youTubePlayerView: YouTubePlayerView? = null

    // private SimpleExoPlayer player;
    protected var player: ExoPlayer? = null
    protected var exoPlayerView //playerView;
            : PlayerView? = null

    //    private Options youboraOptions;
    private var rootLayout: LinearLayout? = null
    private val mediaSource: MediaSource? = null
    private val isPlaying = false
    private var videos: MutableList<Video>? = ArrayList()
    private var video: Video? = null
    private var url = ""
    private var videoType = ""
    private var category = ""
    private val visible = 0
    private var exo_pause: ImageButton? = null
    private var selectTracksButton: ImageButton? = null
    private var fastForwardButton: ImageButton? = null
    private var subtitleButton: ImageButton? = null
    private var imgVideoQuality: ImageButton? = null
    private var exo_prev: ImageButton? = null
    private var exo_rew: ImageButton? = null
    private var bt_golive: Button? = null
    private var movieTitleTV: TextView? = null
    private var movieDescriptionTV: TextView? = null
    private var posterImageView: ImageView? = null
    private var watermark: ImageView? = null
    private var watermark_live: ImageView? = null
    private var seekBarLayout: RelativeLayout? = null
    private val playerViewRv: RelativeLayout? = null
    private val webView: WebView? = null
    private var liveTvTextInController: TextView? = null
    private var progressBar: ProgressBar? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private val session: MediaSession? = null
    private var channelId: Long = 0
    private val mStartingPosition: Long = 0
    private var model: PlaybackModel? = null
    private val mediaSessionHelper: MediaSessionHelper? = null
    private val trackSelector: DefaultTrackSelector? = null
    private var playerCurrentPosition = 0L
    private val mediaDuration = 0L
    var resolutionHashMap: HashMap<String, String>? = null
    private var categoryType = ""
    private var Enable_Subtile = ""
    private var subtitleList: ArrayList<SubtitleDataNew>? = null

    private var id = ""
    private val youTubePlayer: YouTubePlayer? = null
    private val startAutoPlay = true
    private var isShowingTrackSelectionDialog = false

    //    private MediaSessionCompat mediaSession;
    /*private MediaSessionConnector mediaSessionConnector;*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)


        /*    mChannelId = getIntent().getLongExtra(VideoPlaybackActivity.EXTRA_CHANNEL_ID, -1L);
        mStartingPosition = getIntent().getLongExtra(VideoPlaybackActivity.EXTRA_POSITION, -1L);
*/   model = intent.getSerializableExtra(VideoPlaybackActivity.EXTRA_VIDEO) as PlaybackModel?
        assert(model != null)

        Enable_Subtile = intent.getStringExtra("Enable_Subtile").toString()
        // subtitle_Data = intent.getParcelableExtra<CCFile>("subtitle")
        // subtitle_Data = intent.getStringArrayListExtra("subtitle")

        subtitleList = intent.getParcelableArrayListExtra<SubtitleDataNew>("subtitle")




        Log.i(TAG, "onCreate: string -->" + Enable_Subtile + subtitleList)

        // The second parameter is the default value if EXTRA_INT is not found

        url = model!!.videoUrl
        videoType = if (model!!.videoType != null) {
            model!!.videoType
        } else {
            Config.VideoURLTypeHls
        }
        category = model!!.category
        if (model!!.video != null) video = model!!.video
        if (model!!.category != null) {
            categoryType = model!!.category
        }
        id = model!!.id.toString()


        /*  if (model.getCategory().equals("movie") && mChannelId > -1L && model.getIsPaid().equals("1")) {
            //Paid Content from Channel
            //check user has subscription or not
            //if not, send user to VideoPlayerActivity
            DatabaseHelper db = new DatabaseHelper(PlayerActivity.this);
            final String status = db.getActiveStatusData().getStatus();
            if (!status.equals("active") || !PreferenceUtils.isValid(PlayerActivity.this)) {
                Intent intent = new Intent(PlayerActivity.this, VideoPlayerActivity.class);
                intent.putExtra("type", model.getCategory());
                intent.putExtra("id", model.getMovieId());
                intent.putExtra("thumbImage", model.getCardImageUrl());
                startActivity(intent, null);
                finish();
            }
        }*/
        intiViews()
        initVideoPlayer(url, videoType)
    }

    /*
    private void updateContinueWatchingDataToServer() {
        if (player != null) {
            playerCurrentPosition = player.getCurrentPosition();
            Log.d("VideoFragment3", "Is prepped, seeking to " + player.getCurrentPosition());
        }
        Log.i(TAG, "onKeyDown: " + "here click" + playerCurrentPosition);
        if ((playerCurrentPosition / 1000) > 5) {
            double fromTime = 0L;
            if (getIntent().hasExtra(Constants.POSITION)) {
                String pos = getIntent().getStringExtra(Constants.POSITION);
                if (pos != null) {
                    fromTime = Double.parseDouble(pos);
                }
            }

            Retrofit retrofit = RetrofitClient.getRetrofitInstanceAuth();
            ContinueWatchApi api = retrofit.create(ContinueWatchApi.class);
            Call<APIResponse<Object>> call = api.saveContinueWatch(Config.API_KEY, PreferenceUtils.getUserId(this), id,
                    String.valueOf((playerCurrentPosition / 1000)),
                    categoryType, String.valueOf(fromTime), String.valueOf((playerCurrentPosition / 1000)), Config.Device_Type);

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
    @SuppressLint("WrongViewCast")
    private fun intiViews() {
        progressBar = findViewById(R.id.progress_bar)
        exoPlayerView = findViewById(R.id.player_view)
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        rootLayout = findViewById(R.id.root_layout)
        movieTitleTV = findViewById(R.id.movie_title)
        movieDescriptionTV = findViewById(R.id.movie_description)
        posterImageView = findViewById(R.id.poster_image_view)
        bt_golive = findViewById(R.id.bt_golive)
        selectTracksButton = findViewById(R.id.select_tracks_button)
        subtitleButton = findViewById(R.id.img_subtitle)
        imgVideoQuality = findViewById(R.id.img_video_quality)
        fastForwardButton = findViewById(R.id.fastForwardButton)
        exo_rew = findViewById(R.id.rewineButton)
        exo_prev = findViewById(R.id.prevButton)
        liveTvTextInController = findViewById(R.id.live_tv)
        seekBarLayout = findViewById(R.id.seekbar_layout)
        watermark = findViewById(R.id.watermark)
        watermark_live = findViewById(R.id.watermark_live)
        exo_pause = findViewById(R.id.exo_pause)
        val imageUrl = PreferenceUtils.getInstance().getWatermarkLogoUrlPref(this)

        // Replace with your image URL
        Log.i(
            TAG,
            "intiViews:playerAcitivity " + imageUrl.equals("1", ignoreCase = true) + imageUrl
        )
        if (!imageUrl.isEmpty() && !imageUrl.equals("1", ignoreCase = true)) {
            Glide.with(this)
                .load(imageUrl)
                .into(watermark_live!!)
            Glide.with(this)
                .load(imageUrl)
                .into(watermark!!)
        }
        if (PreferenceUtils.getInstance().getWatermarkEnablePref(this)
                .equals("0", ignoreCase = true)
        ) {
            watermark_live!!.setVisibility(View.GONE)
            watermark!!.setVisibility(View.GONE)
        }
        exo_pause!!.setOnClickListener(View.OnClickListener {
            if (model!!.islive != null && model!!.islive.equals("1", ignoreCase = true)) {
                if (player!!.isPlaying) {
                    bt_golive!!.setVisibility(View.VISIBLE)
                    liveTvTextInController!!.setVisibility(View.GONE)
                }
            }
            player!!.pause()
        })
        //  webView=findViewById(R.id.webview);
        if (category.equals("t", ignoreCase = true)) {
            bt_golive!!.setVisibility(View.GONE)
            subtitleButton!!.setVisibility(View.GONE)
            fastForwardButton!!.setVisibility(View.GONE)
            liveTvTextInController!!.setVisibility(View.VISIBLE)
            seekBarLayout!!.setVisibility(View.GONE)
        }
        if (category.equals("tvseries", ignoreCase = true)) {
            bt_golive!!.setVisibility(View.GONE)
            //hide subtitle button if there is no subtitle
            if (video != null) {
                if (video!!.subtitle.isEmpty()) {
                    subtitleButton!!.setVisibility(View.GONE)
                }
            } else {
                subtitleButton!!.setVisibility(View.GONE)
            }
        }
        if (category.equals("movie", ignoreCase = true)) {
            if (model!!.videoList != null) videos!!.clear()
            videos = model!!.videoList
            //hide subtitle button if there is no subtitle
            if (video != null) {
                if (video!!.subtitle.isEmpty()) {
                    subtitleButton!!.setVisibility(View.GONE)
                }
            } else {
                subtitleButton!!.setVisibility(View.GONE)
            }
            if (videos != null) {
                if (videos!!.size < 1) bt_golive!!.setVisibility(View.GONE)
                Log.i(TAG, "clickpausebutton: " + "--->" + "gone22")
            }
            Log.i(TAG, "intiViews: " + model!!.istrailer)
            if (model!!.istrailer) {
                imgVideoQuality!!.setVisibility(View.GONE)
            } else {
                //       setResolutionHashMaps(model.getVideo());
            }
        }
        selectTracksButton!!.setOnClickListener(View.OnClickListener { view: View? ->
            isShowingTrackSelectionDialog = true
            val trackSelectionDialog = TrackSelectionDialog.createForPlayer(
                player
            )  /* onDismissListener= */
            { dismissedDialog: DialogInterface? -> isShowingTrackSelectionDialog = false }
            trackSelectionDialog.show(supportFragmentManager,  /* tag= */null)
        })
        fastForwardButton!!.setOnClickListener(View.OnClickListener {
            player!!.seekTo(player!!.currentPosition + 30000) // 10000 = 10 Seconds
        })
        exo_rew!!.setOnClickListener(View.OnClickListener { v: View? ->
            player!!.seekTo(
                player!!.currentPosition - 30000
            ) // 10000 = 10 Seconds
        })
        if (model!!.islive != null && model!!.islive.equals("1", ignoreCase = true)) {
            bt_golive!!.setVisibility(View.GONE)
            subtitleButton!!.setVisibility(View.GONE)
            fastForwardButton!!.setVisibility(View.GONE)
            exo_prev!!.setVisibility(View.GONE)
            exo_rew!!.setVisibility(View.GONE)
            liveTvTextInController!!.setVisibility(View.VISIBLE)
            seekBarLayout!!.setVisibility(View.INVISIBLE)
            watermark!!.setVisibility(View.GONE)
            watermark_live!!.setVisibility(View.VISIBLE)
        } else {
            watermark!!.setVisibility(View.VISIBLE)
            watermark_live!!.setVisibility(View.GONE)
        }
        if (PreferenceUtils.getInstance().getWatermarkEnablePref(this)
                .equals("0", ignoreCase = true)
        ) {
            watermark_live!!.setVisibility(View.GONE)
            watermark!!.setVisibility(View.GONE)
        }

        if (Enable_Subtile.contentEquals("true")) {
     //       subtitleButton!!.setVisibility(View.VISIBLE)
        }
        // PreferenceUtils.getInstance().getWatermarkLogoUrlPref(this);
    }

    /*
    private class PlayerEventListener implements Player.Listener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
                    !playWhenReady) {

                exoPlayerView.setKeepScreenOn(false);
            } else { // STATE_IDLE, STATE_ENDED
                // This prevents the screen from getting dim/lock
                exoPlayerView.setKeepScreenOn(true);
            }
        }


        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
        }


        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }
    }
*/
    override fun onStart() {
        super.onStart()
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ON_AFTER_RELEASE,
            "My Tag:"
        )
        subtitleButton!!.setOnClickListener { v: View? ->
            //open subtitle dialog
            openSubtitleDialog()
        }
        imgVideoQuality!!.setOnClickListener { setVideoQuality() }
        bt_golive!!.setOnClickListener { v: View? ->
            player!!.seekToDefaultPosition()
            player!!.seekToDefaultPosition()
            player!!.play()
            bt_golive!!.visibility = View.GONE
            Log.i(TAG, "clickpausebutton: " + "--->" + "gone2")
            liveTvTextInController!!.visibility = View.VISIBLE
        }


        //set title, description and poster in controller layout
        movieTitleTV!!.text = model!!.title
        movieDescriptionTV!!.text = model!!.description
        /*movieDescriptionTV.setVisibility(View.GONE);*/
        /*        Picasso.get()
                .load(model.getCardImageUrl())
                .placeholder(R.drawable.poster_placeholder_land)
                .centerCrop()
                .resize(120, 200)
                .error(R.drawable.poster_placeholder_land)
                .into(posterImageView);*/Glide.with(this)
            .load(model!!.cardImageUrl)
            .placeholder(R.drawable.poster_placeholder_land)
            .centerCrop()
            .apply(RequestOptions().override(130, 210))
            .error(R.drawable.poster_placeholder_land)
            .into(posterImageView!!)
    }

    override fun onUserLeaveHint() {
        Log.e("RemoteKey", "DPAD_HOME")
        /** Use pressed home button  */
        //time to set media session active
        super.onUserLeaveHint()
    }

    private val internetSpeedHandler = Handler(Looper.getMainLooper())
    private val internetSpeedRunnable: Runnable? = null
    private var currentAutoResolutionPlayed: String? = null
    var doubleBackToExitPressedOnce = false
    @SuppressLint("ResourceAsColor")
    private fun setVideoQuality() {
        @SuppressLint("InflateParams") val builder = AlertDialog.Builder(this@PlayerActivityNewCode)
        val view = LayoutInflater.from(this@PlayerActivityNewCode)
            .inflate(R.layout.videoquality_rv_tv, null)
        view.requestFocus()
        /*     RecyclerView serverRv = view.findViewById(R.id.serverRv);
                SubtitleListAdapter adapter = new SubtitleListAdapter(PlayerActivity.this, video.getSubtitle());
                serverRv.setLayoutManager(new LinearLayoutManager(PlayerActivity.this));
                serverRv.setHasFixedSize(true);
                serverRv.setAdapter(adapter);*/

        /*View view = getLayoutInflater().inflate(R.layout.videoquality_rv_tv, null);*/
        val tvAuto = view.findViewById<Button>(R.id.tvAuto)
        val tvResolutionOne = view.findViewById<Button>(R.id.tvResolutionOne)
        val tvResolutionTwo = view.findViewById<Button>(R.id.tvResolutionTwo)
        val tvResolutionThree = view.findViewById<Button>(R.id.tvResolutionThree)
        val tvResolutionFour = view.findViewById<Button>(R.id.tvResolutionFour)
        val tvResolutionFive = view.findViewById<Button>(R.id.tvResolutionFive)
        val tvResolutionSix = view.findViewById<Button>(R.id.tvResolutionSix)
        val tvResolutionSeven = view.findViewById<Button>(R.id.tvResolutionSeven)
        val tvResolutionEight = view.findViewById<Button>(R.id.tvResolutionEight)
        Log.i(TAG, "setVideoQuality: resolutionhashmap$resolutionHashMap")
        if (resolutionHashMap!!.containsKey(getString(R.string.hint_auto))) {
            tvAuto.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._144p))) {
            tvResolutionOne.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._240p))) {
            tvResolutionTwo.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._360p))) {
            tvResolutionThree.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._480p))) {
            tvResolutionFour.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._720p))) {
            tvResolutionFive.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._1080p))) {
            tvResolutionSix.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._1440p))) {
            tvResolutionSeven.visibility = View.VISIBLE
        }
        if (resolutionHashMap!!.containsKey(getString(R.string._2160p))) {
            tvResolutionEight.visibility = View.VISIBLE
        }
        /*   Dialog dialog = new Dialog(this);
            dialog.setContentView(view);
         //   setupFullHeight(dialog);
            dialog.show();*/builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        Log.i(
            TAG,
            "setVideoQuality: " + PreferenceUtils.getInstance()
                .getVideoQualityPref(this@PlayerActivityNewCode)
        )
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string.hint_auto), ignoreCase = true)
        ) {
            if (resolutionHashMap != null && currentAutoResolutionPlayed != null) {
                for (key in resolutionHashMap!!.keys) {
                    val keyValue = resolutionHashMap!![key]
                    if (keyValue != null) {
                        if (keyValue.equals(currentAutoResolutionPlayed, ignoreCase = true)) {
                            val keyString = getString(R.string.hint_auto) + " ■ " + key
                            tvAuto.text = keyString
                            break
                        }
                    }
                }
            }
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._144p), ignoreCase = true)
        ) {
            tvResolutionOne.setBackgroundResource(R.color.paypalColor)
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._240p), ignoreCase = true)
        ) {
            tvResolutionTwo.requestFocus()
            tvResolutionTwo.setBackgroundResource(R.color.paypalColor)
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._360p), ignoreCase = true)
        ) {
            tvResolutionThree.requestFocus()
            tvResolutionThree.setBackgroundResource(R.color.paypalColor)
            // tvResolutionThree.setBackgroundResource(R.color.paypalColor);
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._480p), ignoreCase = true)
        ) {
            tvResolutionFour.setBackgroundResource(R.color.paypalColor)
            tvResolutionFour.requestFocus()
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._720p), ignoreCase = true)
        ) {
            tvResolutionFive.requestFocus()
            tvResolutionFive.setBackgroundResource(R.color.paypalColor)
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._1080p), ignoreCase = true)
        ) {
            tvResolutionSix.setBackgroundResource(R.color.paypalColor)
            tvResolutionSix.requestFocus()
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._1440p), ignoreCase = true)
        ) {
            tvResolutionSeven.requestFocus()
            tvResolutionSeven.setBackgroundResource(R.color.paypalColor)
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(this@PlayerActivityNewCode)
                .equals(getString(R.string._2160p), ignoreCase = true)
        ) {
            tvResolutionEight.requestFocus()
            tvResolutionEight.setBackgroundResource(R.color.paypalColor)
        }
        tvAuto.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string.hint_auto)
            )
            dialog.dismiss()
        }
        tvResolutionOne.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._144p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._144p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionTwo.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._240p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._240p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionThree.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._360p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._360p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionFour.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._480p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._480p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionFive.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._720p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._720p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionSix.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._1080p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._1080p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionSeven.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._1440p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._1440p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
        tvResolutionEight.setOnClickListener { v: View? ->
            PreferenceUtils.getInstance().setVideoQualityPref(
                applicationContext, getString(R.string._2160p)
            )
            currentAutoResolutionPlayed = resolutionHashMap!![getString(R.string._2160p)]
            playVideoViaResolutionSelection()
            dialog.dismiss()
        }
    }

    private fun playVideoViaResolutionSelection() {
        if (player != null) {
            playerCurrentPosition = player!!.currentPosition
            Log.d("VideoFragment3", "Is prepped, seeking to " + player!!.currentPosition)
        }
        /*releasePlayer();*/
        //  setPlayerFullScreen();
        progressBar!!.visibility = View.VISIBLE
        // swipeRefreshLayout.setVisibility(GONE);
        // lPlay.setVisibility(VISIBLE);
        initVideoPlayer(currentAutoResolutionPlayed, Config.VideoURLTypeHls)
    }

    fun setResolutionHashMaps(model: Video) {
        resolutionHashMap = HashMap()
        if (model.resolution_1 != null && !model.resolution_1.isEmpty()) {
            resolutionHashMap!![getString(R.string._144p)] = model.resolution_1
        }
        if (model.resolution_2 != null
            && !model.resolution_2.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._240p)] = model.resolution_2
        }
        if (model.resolution_3 != null
            && !model.resolution_3.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._360p)] = model.resolution_3
        }
        if (model.resolution_4 != null
            && !model.resolution_4.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._480p)] = model.resolution_4
        }
        if (model.resolution_5 != null
            && !model.resolution_5.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._720p)] = model.resolution_5
        }
        if (model.resolution_6 != null
            && !model.resolution_6.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._1080p)] = model.resolution_6
        }
        if (model.resolution_7 != null
            && !model.resolution_7.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._1440p)] = model.resolution_7
        }
        if (model.resolution_8 != null
            && !model.resolution_8.isEmpty()
        ) {
            resolutionHashMap!![getString(R.string._2160p)] = model.resolution_8
        }
        if (resolutionHashMap!!.size > 1) {
            resolutionHashMap!![getString(R.string.hint_auto)] = ""
            /*    if (model.getStremURL() != null && !model.getStremURL().isEmpty()) {
                resolutionHashMap.put(getString(R.string.hint_default), model.getStremURL());
            }*/imgVideoQuality!!.visibility = View.VISIBLE
        } else {
            imgVideoQuality!!.visibility = View.GONE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_MOVE_HOME -> {}
            KeyEvent.KEYCODE_DPAD_UP -> if (!exoPlayerView!!.isControllerVisible) {
                exoPlayerView!!.showController()
            }

            KeyEvent.KEYCODE_DPAD_DOWN -> {
                Log.e("RemoteKey", "DPAD_DOWN")
                if (!exoPlayerView!!.isControllerVisible) {
                    exoPlayerView!!.showController()
                }
            }

            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                Log.e("RemoteKey", "DPAD_RIGHT")
                if (!exoPlayerView!!.isControllerVisible) {
                    exoPlayerView!!.showController()
                }
            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                Log.e("RemoteKey", "DPAD_LEFT")
                if (!exoPlayerView!!.isControllerVisible) {
                    exoPlayerView!!.showController()
                }
            }

            KeyEvent.KEYCODE_DPAD_CENTER -> {
                Log.e("RemoteKey", "DPAD_CENTER")
                if (!exoPlayerView!!.isControllerVisible) {
                    exoPlayerView!!.showController()
                }
            }

            KeyEvent.KEYCODE_BACK -> {
                Log.e("RemoteKey", "DPAD_BACK")
                if (exoPlayerView!!.isControllerVisible) {
                    exoPlayerView!!.hideController()
                } else {
                    Log.i(TAG, "onKeyDown: " + "here click")
                    //   updateContinueWatchingDataToServer();
                    releasePlayer()
                    //  mediaSessionHelper.stopMediaSession();
                    finish()
                    onBackPressed()
                    /* if (doubleBackToExitPressedOnce) {

                    } else {
                        handleBackPress();
                    }*/
                }
            }

            KeyEvent.KEYCODE_ESCAPE -> Log.e("RemoteKey", "DPAD_ESCAPE")
        }
        return false
    }

    private fun handleBackPress() {
        doubleBackToExitPressedOnce = true
        //Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
        ToastMsg(this@PlayerActivityNewCode).toastIconSuccess("Please click BACK again to exit.")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun openServerDialog(videos: List<Video>?) {
        if (videos != null) {
            val videoList: MutableList<Video> = ArrayList()
            videoList.clear()
            for (video in videos) {
                if (!video.fileType.equals("embed", ignoreCase = true)) {
                    videoList.add(video)
                }
            }
            val builder = AlertDialog.Builder(this@PlayerActivityNewCode)
            val view = LayoutInflater.from(this@PlayerActivityNewCode)
                .inflate(R.layout.layout_server_tv, null)
            val serverRv = view.findViewById<RecyclerView>(R.id.serverRv)
            val serverAdapter = ServerAdapter(this@PlayerActivityNewCode, videoList, "movie")
            serverRv.layoutManager = LinearLayoutManager(this@PlayerActivityNewCode)
            serverRv.setHasFixedSize(true)
            serverRv.adapter = serverAdapter
            val closeBt = view.findViewById<Button>(R.id.close_bt)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            closeBt.setOnClickListener { v: View? -> dialog.dismiss() }
            val viewHolder = arrayOf<OriginalViewHolder?>(null)
            serverAdapter.setOnItemClickListener { view1: View?, obj: Video, position: Int, holder: OriginalViewHolder? ->
                val playerIntent =
                    Intent(this@PlayerActivityNewCode, PlayerActivityNewCode::class.java)
                val video = PlaybackModel()
                video.id = model!!.id
                video.title = model!!.title
                video.description = model!!.description
                video.category = "movie"
                video.video = obj
                video.videoList = model!!.videoList
                video.videoUrl = obj.fileUrl
                video.videoType = obj.fileType
                video.bgImageUrl = model!!.bgImageUrl
                video.cardImageUrl = model!!.cardImageUrl
                video.isPaid = model!!.isPaid
                playerIntent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video)
                startActivity(playerIntent)
                dialog.dismiss()
                finish()
            }
        } else {
            ToastMsg(this).toastIconError(getString(R.string.no_other_server_found))
        }
    }

    private fun openSubtitleDialog() {
        if (subtitleList != null) {

            if (subtitleList!!.size > 0) {
                val builder = AlertDialog.Builder(this@PlayerActivityNewCode)
                val view = LayoutInflater.from(this@PlayerActivityNewCode)
                    .inflate(R.layout.layout_subtitle_dialog, null)
                val serverRv = view.findViewById<RecyclerView>(R.id.serverRv)
                val adapter = SubtitleListAdapter(this@PlayerActivityNewCode, subtitleList)
                serverRv.layoutManager = LinearLayoutManager(this@PlayerActivityNewCode)
                serverRv.setHasFixedSize(true)
                serverRv.adapter = adapter
                val closeBt = view.findViewById<Button>(R.id.close_bt)
                builder.setView(view)
                val dialog = builder.create()
                dialog.show()
                closeBt.setOnClickListener { dialog.dismiss() }
                //click event
                adapter.setListener { view, subtitle, position, holder -> //    setSelectedSubtitle(mediaSource, subtitle.getUrl());
                    dialog.dismiss()
                }
            } else {
                ToastMsg(this).toastIconError(resources.getString(R.string.no_subtitle_found))
            }
        } else {
            ToastMsg(this).toastIconError(resources.getString(R.string.no_subtitle_found))
        }
    }

    /*
    private void setSelectedSubtitle(MediaSource mediaSource, String url) {
        MergingMediaSource mergedSource;
        if (url != null) {
            Uri subtitleUri = Uri.parse(url);

            Format subtitleFormat = Format.createTextSampleFormat(
                    null, // An identifier for the track. May be null.
                    MimeTypes.TEXT_VTT, // The mime type. Must be set correctly.
                    Format.NO_VALUE, // Selection flags for the track.
                    "en"); // The subtitle language. May be null.

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(PlayerActivityNewCode.this,
                    Util.getUserAgent(PlayerActivityNewCode.this, CLASS_NAME), new DefaultBandwidthMeter());


            MediaSource subtitleSource = new SingleSampleMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(subtitleUri, subtitleFormat, C.TIME_UNSET);


            mergedSource = new MergingMediaSource(mediaSource, subtitleSource);
            player.prepare(mergedSource, false, false);
            player.setPlayWhenReady(true);
            //resumePlayer();

        } else {
            Toast.makeText(PlayerActivityNewCode.this, "there is no subtitle", Toast.LENGTH_SHORT).show();
        }
    }
*/     /*
    private void setSelectedSubtitle(MediaSource mediaSource, String url) {
        MergingMediaSource mergedSource;
        if (url != null) {
            Uri subtitleUri = Uri.parse(url);

            Format subtitleFormat = Format.createTextSampleFormat(
                    null, // An identifier for the track. May be null.
                    MimeTypes.TEXT_VTT, // The mime type. Must be set correctly.
                    Format.NO_VALUE, // Selection flags for the track.
                    "en"); // The subtitle language. May be null.

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(PlayerActivityNewCode.this,
                    Util.getUserAgent(PlayerActivityNewCode.this, CLASS_NAME), new DefaultBandwidthMeter());


            MediaSource subtitleSource = new SingleSampleMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(subtitleUri, subtitleFormat, C.TIME_UNSET);


            mergedSource = new MergingMediaSource(mediaSource, subtitleSource);
            player.prepare(mergedSource, false, false);
            player.setPlayWhenReady(true);
            //resumePlayer();

        } else {
            Toast.makeText(PlayerActivityNewCode.this, "there is no subtitle", Toast.LENGTH_SHORT).show();
        }
    }
*/
/*
    open fun initVideoPlayer(url: String?, type: String) {
        Log.i(TAG, "initVideoPlayer: $type")
        if (player != null) {
            player!!.stop()
            player!!.release()
        }

        */
/*
        val streamUrl = "rtsp://192.168.1.11:554/user=admin&password=&channel=1&stream=0.sdp"
        val mediaSource = RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(streamUrl))

        val player = SimpleExoPlayer.Builder(context).build()
        player.repeatMode = Player.REPEAT_MODE_OFF
        player.setVideoTextureView(ipcamVideoView)
        player.setMediaSource(mediaSource)
        player.prepare()
        player.play()
                *//*
if (type.equals("youtube", ignoreCase = true)) {
            Log.i(TAG, "initVideoPlayer: $type")
            initYoutubeVideo(url, this@PlayerActivityNewCode, 18)
        } else {
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            // Create a HLS media source pointing to a playlist uri.
            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url!!))
            // Create a player instance.
            player = ExoPlayer.Builder(this).build()
            */
/*
            if (PreferenceUtils.getInstance().getNpawEnablePref(this)) {
                youboraOptions = new Options();
                youboraOptions.setAccountCode(PreferenceUtils.getInstance().getNpawAccountKeyPref(this));
                youboraOptions.setContentTitle(model.getTitle());
                youboraOptions.setContentId(model.getMovieId());
                youboraOptions.setDeviceType(Config.Device_Type);
                youboraOptions.setContentType(type);
             //   youboraOptions.setAppName();
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);


                Plugin youboraPlugin = new Plugin(youboraOptions, this);
                youboraPlugin.setActivity(this);
                Exoplayer2Adapter adapter = new Exoplayer2Adapter(player);
                youboraPlugin.setAdapter(adapter);
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);
            }
*//*
player!!.setMediaSource(hlsMediaSource)
            player!!.trackSelector
            player!!.prepare()
            player!!.playWhenReady = startAutoPlay
            exoPlayerView!!.player = player
            player!!.prepare()
            exoPlayerView!!.player = player
        }


        //   player.setPlayWhenReady(startAutoPlay);


        */
/*   Uri uri = Uri.parse(url);

        switch (type) {
            case "hls":
             //   mediaSource = hlsMediaSource(uri, PlayerActivityNewCode.this);
                break;
            case "youtube":
                initYoutubeVideo(url, PlayerActivityNewCode.this, 18);
                break;
            case "youtube-live":
           //     extractYoutubeUrl(url, PlayerActivityNewCode.this, 133);
                break;
            case "rtmp":
               // mediaSource = rtmpMediaSource(uri);
                break;
            case "mp4":
                mediaSource = mediaSource(uri, PlayerActivityNewCode.this);
                break;
            default:
                mediaSource = mediaSource(uri, PlayerActivityNewCode.this);
                break;
        }*//*

        */
/*if (!type.contains("youtube")) {
            player.prepare(mediaSource, true, false);
            exoPlayerView.setPlayer(player);
            player.setPlayWhenReady(true);
        }*//*

        // seekTocurrentPosition();
        //  seekToStartPosition();


        */
/*    exoPlayerView.setControllerVisibilityListener(visibility -> visible = visibility);

        exoPlayerView.setControllerShowTimeoutMs(5 * 1000);*//*

    }
*/

    fun initVideoPlayer(url: String?, type: String) {
        Log.i(TAG, "initVideoPlayer: $type")
        if (player != null) {
            player!!.stop()
            player!!.release()
        }

        /*
        val streamUrl = "rtsp://192.168.1.11:554/user=admin&password=&channel=1&stream=0.sdp"
        val mediaSource = RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(streamUrl))

        val player = SimpleExoPlayer.Builder(context).build()
        player.repeatMode = Player.REPEAT_MODE_OFF
        player.setVideoTextureView(ipcamVideoView)
        player.setMediaSource(mediaSource)
        player.prepare()
        player.play()
             {

            /*val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            // Create a HLS media source pointing to a playlist uri.
            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url!!))
            // Create a player instance.
            player = ExoPlayer.Builder(this).build()

            val trackSelector: TrackSelector = DefaultTrackSelector(this)
            val loadControl = DefaultLoadControl()
            val renderersFactory = DefaultRenderersFactory(this)
            player = ExoPlayer.Builder(this, renderersFactory)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()
            exoPlayerView!!.player = player
            */playWithCaption()
            /*
            if (PreferenceUtils.getInstance().getNpawEnablePref(this)) {
                youboraOptions = new Options();
                youboraOptions.setAccountCode(PreferenceUtils.getInstance().getNpawAccountKeyPref(this));
                youboraOptions.setContentTitle(model.getTitle());
                youboraOptions.setContentId(model.getMovieId());
                youboraOptions.setDeviceType(Config.Device_Type);
                youboraOptions.setContentType(type);
             //   youboraOptions.setAppName();
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);


                Plugin youboraPlugin = new Plugin(youboraOptions, this);
                youboraPlugin.setActivity(this);
                Exoplayer2Adapter adapter = new Exoplayer2Adapter(player);
                youboraPlugin.setAdapter(adapter);
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);
            }
*/{

            /*val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            // Create a HLS media source pointing to a playlist uri.
            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url!!))
            // Create a player instance.
            player = ExoPlayer.Builder(this).build()

            val trackSelector: TrackSelector = DefaultTrackSelector(this)
            val loadControl = DefaultLoadControl()
            val renderersFactory = DefaultRenderersFactory(this)
            player = ExoPlayer.Builder(this, renderersFactory)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()
            exoPlayerView!!.player = player
            */playWithCaption()
            /*
            if (PreferenceUtils.getInstance().getNpawEnablePref(this)) {
                youboraOptions = new Options();
                youboraOptions.setAccountCode(PreferenceUtils.getInstance().getNpawAccountKeyPref(this));
                youboraOptions.setContentTitle(model.getTitle());
                youboraOptions.setContentId(model.getMovieId());
                youboraOptions.setDeviceType(Config.Device_Type);
                youboraOptions.setContentType(type);
             //   youboraOptions.setAppName();
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);


                Plugin youboraPlugin = new Plugin(youboraOptions, this);
                youboraPlugin.setActivity(this);
                Exoplayer2Adapter adapter = new Exoplayer2Adapter(player);
                youboraPlugin.setAdapter(adapter);
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);
            }
*/{

            /*val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            // Create a HLS media source pointing to a playlist uri.
            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url!!))
            // Create a player instance.
            player = ExoPlayer.Builder(this).build()

            val trackSelector: TrackSelector = DefaultTrackSelector(this)
            val loadControl = DefaultLoadControl()
            val renderersFactory = DefaultRenderersFactory(this)
            player = ExoPlayer.Builder(this, renderersFactory)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()
            exoPlayerView!!.player = player
            */playWithCaption()
            /*
            if (PreferenceUtils.getInstance().getNpawEnablePref(this)) {
                youboraOptions = new Options();
                youboraOptions.setAccountCode(PreferenceUtils.getInstance().getNpawAccountKeyPref(this));
                youboraOptions.setContentTitle(model.getTitle());
                youboraOptions.setContentId(model.getMovieId());
                youboraOptions.setDeviceType(Config.Device_Type);
                youboraOptions.setContentType(type);
             //   youboraOptions.setAppName();
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);


                Plugin youboraPlugin = new Plugin(youboraOptions, this);
                youboraPlugin.setActivity(this);
                Exoplayer2Adapter adapter = new Exoplayer2Adapter(player);
                youboraPlugin.setAdapter(adapter);
                Log.i(TAG, "initVideoPlayer: "+youboraOptions);
            }
*/
            // player.setMediaSource(mergedSource);
           // player!!.setMediaSource(hlsMediaSource)
            player!!.trackSelector
            player!!.prepare()
            player!!.playWhenReady = startAutoPlay
            exoPlayerView!!.player = player
            player!!.prepare()
            exoPlayerView!!.player = player
        }
            // player.setMediaSource(mergedSource);
           // player!!.setMediaSource(hlsMediaSource)
            player!!.trackSelector
            player!!.prepare()
            player!!.playWhenReady = startAutoPlay
            exoPlayerView!!.player = player
            player!!.prepare()
            exoPlayerView!!.player = player
        }
            // player.setMediaSource(mergedSource);
           // player!!.setMediaSource(hlsMediaSource)
            player!!.trackSelector
            player!!.prepare()
            player!!.playWhenReady = startAutoPlay
            exoPlayerView!!.player = player
            player!!.prepare()
            exoPlayerView!!.player = player
        }   */if (type.equals("youtube", ignoreCase = true)) {
            Log.i(TAG, "initVideoPlayer: $type")
            initYoutubeVideo(url, this@PlayerActivityNewCode, 18)
        } else {

            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            // Create a HLS media source pointing to a playlist uri.
           /* val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url!!))
           */
            val subtitleConfigurations = ArrayList<MediaItem.SubtitleConfiguration>()
            if(subtitleList!=null) {
                for (subtitleData in subtitleList!!) {
                    val subtitle =
                        MediaItem.SubtitleConfiguration.Builder(Uri.parse(subtitleData.url))
                            .setMimeType(MimeTypes.TEXT_VTT) // The correct MIME type (required).
                            .setLanguage(subtitleData.language) // The subtitle language (optional).
                            .setSelectionFlags(C.SELECTION_FLAG_DEFAULT) // Selection flags for the track (optional).
                            .build()
                    subtitleConfigurations.add(subtitle)
                }
            }


           /* val subtitle = MediaItem.SubtitleConfiguration.Builder(Uri.parse(subtitleList!!.get(0).url))
                .setMimeType(MimeTypes.TEXT_VTT) // The correct MIME type (required).
                .setLanguage("en") // MUST, The subtitle language (optional).
                .setSelectionFlags(C.SELECTION_FLAG_DEFAULT) // MUST, Selection flags for the track (optional).
                .build()*/
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse((url)))
                .setSubtitleConfigurations(subtitleConfigurations)
                .build()

         /*   player!!.setMediaItem(mediaItem)

            player!!.prepare()

            player!!.playWhenReady = true*/

            player = ExoPlayer.Builder(this).build()
            player!!.setMediaItem(mediaItem)
            player!!.trackSelector
            player!!.prepare()
            player!!.playWhenReady = startAutoPlay
            exoPlayerView!!.player = player

        }


    }


    private fun playWithCaption() {
        val subtitle = MediaItem.SubtitleConfiguration.Builder(Uri.parse("https://ik.imagekit.io/phando/ims-saas-w/images/28/content/subtitles/subtitles_0_1689933293.vtt"))
            .setMimeType(MimeTypes.TEXT_VTT) // The correct MIME type (required).
            .setLanguage("en") // MUST, The subtitle language (optional).
            .setSelectionFlags(C.SELECTION_FLAG_DEFAULT) // MUST, Selection flags for the track (optional).
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(Uri.parse(("https://ottsaas-cdn.b-cdn.net/64957f3f86515/playlist.m3u8")))
            .setSubtitleConfigurations(listOf(subtitle))
            .build()

        player!!.setMediaItem(mediaItem)

        player!!.prepare()

        player!!.playWhenReady = true
    }

    private fun initYoutubeVideo(url: String?, context: Context, tag: Int) {
        exoPlayerView!!.visibility = View.GONE
        youTubePlayerView!!.visibility = View.VISIBLE
        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
 /*       youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(getYouTubeId(url), 0f)
            }
        })*/



        /* youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer1) {
                super.onReady(youTubePlayer1);
                String videoId = url;
                youTubePlayer.loadVideo(getYouTubeId(videoId), 0f);
            }
        });*/
    }

    private fun getYouTubeId(youTubeUrl: String?): String {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            "error"
        }
    }

    private fun seekToStartPosition() {
        Log.d("VideoFragment2", "Is prepped, seeking to $mStartingPosition")
        // Skip ahead if given a starting position.
        if (mStartingPosition > -1L) {
            if (player!!.playWhenReady) {
                Log.d("VideoFragment", "Is prepped, seeking to $mStartingPosition")
                player!!.seekTo(mStartingPosition)
            }
        }
    }

    private fun seekTocurrentPosition() {
        if (playerCurrentPosition > -1L) {
            if (player!!.playWhenReady) {
                Log.d("VideoFragment", "Is prepped, seeking to $mStartingPosition")
                player!!.seekTo(playerCurrentPosition)
            }
        }
    }

    /*
    private MediaSource mp3MediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "ExoplayerDemo");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Handler mainHandler = new Handler();
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, mainHandler, null);
    }
*/
    /*private MediaSource mediaSource(Uri uri, Context context) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }*/
    /*
    private MediaSource rtmpMediaSource(Uri uri) {
        MediaSource videoSource = null;

        RtmpDataSourceFactory dataSourceFactory = new RtmpDataSourceFactory();
        videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        return videoSource;
    }
*/
    /*  @SuppressLint("StaticFieldLeak")
    private void extractYoutubeUrl(String url, final Context context, final int tag) {

        new YouTubeExtractor(context) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    String dashUrl = ytFiles.get(tag).getUrl();

                    try {
                        MediaSource source = mediaSource(Uri.parse(dashUrl), context);
                        player.prepare(source, true, false);
                        //player.setPlayWhenReady(false);
                        exoPlayerView.setPlayer(player);
                        player.setPlayWhenReady(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.extract(url, true, true);

    }
*/
    /*
    private MediaSource hlsMediaSource(Uri uri, Context context) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "phando"), bandwidthMeter);
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        return videoSource;
    }
*/
    override fun onBackPressed() {
        if (visible == View.GONE) {
            exoPlayerView!!.showController()
        } else {
            releasePlayer()
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        if (wakeLock != null) wakeLock!!.release()
    }

    override fun onResume() {
        Log.i(TAG, "playing ----onResume: ")
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
        wakeLock!!.acquire(10 * 60 * 1000L /*10 minutes*/)
    }

    override fun onStop() {
        Log.i(TAG, "playing ----onstop: ")
        releasePlayer()
        super.onStop()
        youTubePlayerView!!.release()
        finish()
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

    var channelExists = false
    private fun createChannel() {
        val movieSubscription = MockDatabase.getVideoSubscription(this)
        channelExists = movieSubscription.channelId > 0L
     //   AddChannelTask(this).execute(movieSubscription)
    }

    override fun onVisibilityChanged(visibility: Int) {}

    @SuppressLint("StaticFieldLeak")
/*
    private inner class AddChannelTask(private val context: Context) :
        AsyncTask<Subscription?, Void?, Long>() {
        protected override fun doInBackground(vararg varArgs: Subscription): Long {
            val subscriptions = Arrays.asList(*varArgs)
            if (subscriptions.size != 1) {
                return -1L
            }
            val subscription = subscriptions[0]
            val channelId = TvUtil.createChannel(context, subscription)
            subscription.channelId = channelId
            MockDatabase.saveSubscription(context, subscription)
            TvUtil.scheduleSyncingProgramsForChannel(applicationContext, channelId)
            .channelId = channelId
            Log.e("1234", "AsyncTask: " + this.channelId)
            return channelId
        }

        override fun onPostExecute(channelId: Long) {
            super.onPostExecute(channelId)
            if (!channelExists) promptUserToDisplayChannel(channelId)
        }
    }
*/

    private fun promptUserToDisplayChannel(channelId: Long) {
        // TODO: step 17 prompt user.
        TvContractCompat.requestChannelBrowsable(this, channelId)
        /*Intent intent = new Intent(TvContractCompat.ACTION_REQUEST_CHANNEL_BROWSABLE);
        intent.putExtra(TvContractCompat.EXTRA_CHANNEL_ID, channelId);
        try {
            this.startActivityForResult(intent, MAKE_BROWSABLE_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Could not start activity: " + intent.getAction(), e);
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO step 18 handle response
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Channel Added", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Channel not added", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "PlayerActivity"
        private const val CLASS_NAME = "PlayerActivity"
        private const val YOUTUBE = "youtube"
        private const val YOUTUBE_LIVE = "youtube_live"
        private const val MAKE_BROWSABLE_REQUEST_CODE = 9001
    }
}