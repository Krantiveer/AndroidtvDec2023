package com.ott.tv.model.phando;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.tvprovider.media.tv.TvContractCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.adapter.ServerAdapter;
import com.ott.tv.adapter.SubtitleListAdapter;
import com.ott.tv.model.Subtitle;
import com.ott.tv.model.Video;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.APIResponse;
import com.ott.tv.network.api.ContinueWatchApi;
import com.ott.tv.ui.activity.PlayerActivity;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;
import com.ott.tv.video_service.MediaSessionHelper;
import com.ott.tv.video_service.MockDatabase;
import com.ott.tv.video_service.PlaybackModel;
import com.ott.tv.video_service.Subscription;
import com.ott.tv.video_service.TvUtil;
import com.ott.tv.video_service.VideoPlaybackActivity;
import com.ott.tv.video_service.WatchNextAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayerActivityNewCode extends Activity {
    private static final String TAG = "PlayerActivityNewCode";
    private static final String CLASS_NAME = "PlayerActivityNewCode";
    private static final String YOUTUBE = "youtube";
    private static final String YOUTUBE_LIVE = "youtube_live";
    private PlayerView exoPlayerView;
    private SimpleExoPlayer player;
    private LinearLayout rootLayout;
    private MediaSource mediaSource;
    private boolean isPlaying;
    private List<Video> videos = new ArrayList<>();
    private Video video = null;
    private String url = "";
    private String videoType = "";
    private String category = "";
    private int visible;
    private ImageButton serverButton, fastForwardButton, subtitleButton, imgVideoQuality;
    private TextView movieTitleTV, movieDescriptionTV;
    private ImageView posterImageView;
    private RelativeLayout seekBarLayout;
    private TextView liveTvTextInController;
    private ProgressBar progressBar;
    private PowerManager.WakeLock wakeLock;
    private MediaSession session;

    private long mChannelId;
    private long mStartingPosition;
    private PlaybackModel model;
    private MediaSessionHelper mediaSessionHelper;
    private DefaultTrackSelector trackSelector;
    private long playerCurrentPosition = 0L;
    private long mediaDuration = 0L;
    HashMap<String, String> resolutionHashMap = null;
    private String categoryType = "", id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

    /*    mChannelId = getIntent().getLongExtra(VideoPlaybackActivity.EXTRA_CHANNEL_ID, -1L);
        mStartingPosition = getIntent().getLongExtra(VideoPlaybackActivity.EXTRA_POSITION, -1L);
*/
        model = (PlaybackModel) getIntent().getSerializableExtra(VideoPlaybackActivity.EXTRA_VIDEO);

        assert model != null;
        url = model.getVideoUrl();
        if (model.getVideoType() != null) {
            videoType = model.getVideoType();
        } else {
            videoType = Config.VideoURLTypeHls;
        }


        category = model.getCategory();
        if (model.getVideo() != null)
            video = model.getVideo();
        if (model.getCategory() != null) {
            categoryType = model.getCategory();
        }
        id = String.valueOf(model.getId());


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
        intiViews();
        initVideoPlayer(url, videoType);

    }

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


    @SuppressLint("WrongViewCast")
    private void intiViews() {
        progressBar = findViewById(R.id.progress_bar);
        exoPlayerView = findViewById(R.id.player_view);
        rootLayout = findViewById(R.id.root_layout);
        movieTitleTV = findViewById(R.id.movie_title);
        movieDescriptionTV = findViewById(R.id.movie_description);
        posterImageView = findViewById(R.id.poster_image_view);
        serverButton = findViewById(R.id.img_server);
        subtitleButton = findViewById(R.id.img_subtitle);
        imgVideoQuality = findViewById(R.id.img_video_quality);
        fastForwardButton = findViewById(R.id.exo_ffwd);
        liveTvTextInController = findViewById(R.id.live_tv);
        seekBarLayout = findViewById(R.id.seekbar_layout);
        if (category.equalsIgnoreCase("t")) {
            serverButton.setVisibility(View.GONE);
            subtitleButton.setVisibility(View.GONE);
            //seekBarLayout.setVisibility(View.GONE);
            fastForwardButton.setVisibility(View.GONE);
            liveTvTextInController.setVisibility(View.VISIBLE);
            seekBarLayout.setVisibility(GONE);
        }
        if (category.equalsIgnoreCase("tvseries")) {
            serverButton.setVisibility(View.GONE);
            //hide subtitle button if there is no subtitle
            if (video != null) {
                if (video.getSubtitle().isEmpty()) {
                    subtitleButton.setVisibility(View.GONE);
                }
            } else {
                subtitleButton.setVisibility(View.GONE);
            }
        }
        if (category.equalsIgnoreCase("movie")) {
            if (model.getVideoList() != null)
                videos.clear();
            videos = model.getVideoList();
            //hide subtitle button if there is no subtitle
            if (video != null) {
                if (video.getSubtitle().isEmpty()) {
                    subtitleButton.setVisibility(View.GONE);
                }
            } else {
                subtitleButton.setVisibility(View.GONE);
            }
            if (videos != null) {
                if (videos.size() < 1)
                    serverButton.setVisibility(View.GONE);
            }
            Log.i(TAG, "intiViews: " + model.getIstrailer());
            if (model.getIstrailer()) {
                imgVideoQuality.setVisibility(GONE);
            } else {
                //       setResolutionHashMaps(model.getVideo());
            }

        }
    }

    /*
        private class PlayerEventListener implements Player.EventListener {
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


            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

            @Override
            public void onLoadingChanged(boolean isLoading) {}

            @Override
            public void onRepeatModeChanged(int repeatMode) { }

            @Override
            public void onPlayerError(ExoPlaybackException error) { }


            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }
        }
    */
    @Override
    protected void onStart() {
        super.onStart();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "My Tag:");

        subtitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open subtitle dialog
                openSubtitleDialog();
            }
        });
        imgVideoQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVideoQuality();
            }
        });

        serverButton.setOnClickListener(v -> {
            //open server dialog
            openServerDialog(videos);
        });


        //set title, description and poster in controller layout
        movieTitleTV.setText(model.getTitle());
        movieDescriptionTV.setText(model.getDescription());
        /*movieDescriptionTV.setVisibility(View.GONE);*/
/*        Picasso.get()
                .load(model.getCardImageUrl())
                .placeholder(R.drawable.poster_placeholder_land)
                .centerCrop()
                .resize(120, 200)
                .error(R.drawable.poster_placeholder_land)
                .into(posterImageView);*/
        Glide.with(this)
                .load(model.getCardImageUrl())
                .placeholder(R.drawable.poster_placeholder_land)
                .centerCrop()
                .apply(new RequestOptions().override(130, 210))
                .error(R.drawable.poster_placeholder_land)
                .into(posterImageView);
    }

    @Override
    protected void onUserLeaveHint() {
        Log.e("RemoteKey", "DPAD_HOME");
        /** Use pressed home button **/
        //time to set media session active
        super.onUserLeaveHint();
    }

    private final Handler internetSpeedHandler = new Handler(Looper.getMainLooper());
    private Runnable internetSpeedRunnable = null;
    private String currentAutoResolutionPlayed = null;
    boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("ResourceAsColor")
    private void setVideoQuality() {
        @SuppressLint("InflateParams")

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivityNewCode.this);
        View view = LayoutInflater.from(PlayerActivityNewCode.this).inflate(R.layout.videoquality_rv_tv, null);
        view.requestFocus();
           /*     RecyclerView serverRv = view.findViewById(R.id.serverRv);
                SubtitleListAdapter adapter = new SubtitleListAdapter(PlayerActivity.this, video.getSubtitle());
                serverRv.setLayoutManager(new LinearLayoutManager(PlayerActivity.this));
                serverRv.setHasFixedSize(true);
                serverRv.setAdapter(adapter);*/

        /*View view = getLayoutInflater().inflate(R.layout.videoquality_rv_tv, null);*/

        Button tvAuto = view.findViewById(R.id.tvAuto);
        Button tvResolutionOne = view.findViewById(R.id.tvResolutionOne);
        Button tvResolutionTwo = view.findViewById(R.id.tvResolutionTwo);
        Button tvResolutionThree = view.findViewById(R.id.tvResolutionThree);
        Button tvResolutionFour = view.findViewById(R.id.tvResolutionFour);
        Button tvResolutionFive = view.findViewById(R.id.tvResolutionFive);
        Button tvResolutionSix = view.findViewById(R.id.tvResolutionSix);
        Button tvResolutionSeven = view.findViewById(R.id.tvResolutionSeven);
        Button tvResolutionEight = view.findViewById(R.id.tvResolutionEight);


        Log.i(TAG, "setVideoQuality: resolutionhashmap" + resolutionHashMap);

        if (resolutionHashMap.containsKey(getString(R.string.hint_auto))) {
            tvAuto.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._144p))) {
            tvResolutionOne.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._240p))) {
            tvResolutionTwo.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._360p))) {
            tvResolutionThree.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._480p))) {
            tvResolutionFour.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._720p))) {
            tvResolutionFive.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._1080p))) {
            tvResolutionSix.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._1440p))) {
            tvResolutionSeven.setVisibility(VISIBLE);
        }
        if (resolutionHashMap.containsKey(getString(R.string._2160p))) {
            tvResolutionEight.setVisibility(VISIBLE);
        }
         /*   Dialog dialog = new Dialog(this);
            dialog.setContentView(view);
         //   setupFullHeight(dialog);
            dialog.show();*/

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Log.i(TAG, "setVideoQuality: " + PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this));

        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string.hint_auto))) {

            if (resolutionHashMap != null && currentAutoResolutionPlayed != null) {
                for (String key : resolutionHashMap.keySet()) {
                    String keyValue = resolutionHashMap.get(key);
                    if (keyValue != null) {
                        if (keyValue.equalsIgnoreCase(currentAutoResolutionPlayed)) {
                            String keyString = getString(R.string.hint_auto) + " ■ " + key;
                            tvAuto.setText(keyString);
                            break;
                        }
                    }
                }
            }
        }

        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._144p))) {
            tvResolutionOne.setBackgroundResource(R.color.paypalColor);
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._240p))) {
            tvResolutionTwo.requestFocus();
            tvResolutionTwo.setBackgroundResource(R.color.paypalColor);
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._360p))) {
            tvResolutionThree.requestFocus();
            tvResolutionThree.setBackgroundResource(R.color.paypalColor);
            // tvResolutionThree.setBackgroundResource(R.color.paypalColor);
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._480p))) {
            tvResolutionFour.setBackgroundResource(R.color.paypalColor);
            tvResolutionFour.requestFocus();
        }

        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._720p))) {
            tvResolutionFive.requestFocus();
            tvResolutionFive.setBackgroundResource(R.color.paypalColor);
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._1080p))) {
            tvResolutionSix.setBackgroundResource(R.color.paypalColor);
            tvResolutionSix.requestFocus();
        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._1440p))) {
            tvResolutionSeven.requestFocus();
            tvResolutionSeven.setBackgroundResource(R.color.paypalColor);

        }
        if (PreferenceUtils.getInstance().getVideoQualityPref(PlayerActivityNewCode.this).equalsIgnoreCase(getString(R.string._2160p))) {

            tvResolutionEight.requestFocus();
            tvResolutionEight.setBackgroundResource(R.color.paypalColor);
        }
        tvAuto.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string.hint_auto));
            dialog.dismiss();
        });


        tvResolutionOne.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._144p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._144p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionTwo.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._240p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._240p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionThree.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._360p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._360p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionFour.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._480p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._480p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionFive.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._720p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._720p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionSix.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._1080p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._1080p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionSeven.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._1440p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._1440p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
        tvResolutionEight.setOnClickListener(v -> {
            PreferenceUtils.getInstance().setVideoQualityPref(getApplicationContext(), getString(R.string._2160p));
            currentAutoResolutionPlayed = resolutionHashMap.get(getString(R.string._2160p));
            playVideoViaResolutionSelection();
            dialog.dismiss();
        });
    }


    private void playVideoViaResolutionSelection() {
        if (player != null) {
            playerCurrentPosition = player.getCurrentPosition();
            Log.d("VideoFragment3", "Is prepped, seeking to " + player.getCurrentPosition());
        }
        /*releasePlayer();*/
        //  setPlayerFullScreen();
        progressBar.setVisibility(VISIBLE);
        // swipeRefreshLayout.setVisibility(GONE);
        // lPlay.setVisibility(VISIBLE);
        initVideoPlayer(currentAutoResolutionPlayed, Config.VideoURLTypeHls);
    }

    public void setResolutionHashMaps(Video model) {

        resolutionHashMap = new HashMap<>();
        if (model.resolution_1 != null && !model.resolution_1.isEmpty()) {
            resolutionHashMap.put(getString(R.string._144p), model.resolution_1);
        }
        if (model.resolution_2 != null
                && !model.resolution_2.isEmpty()) {
            resolutionHashMap.put(getString(R.string._240p), model.resolution_2);
        }
        if (model.resolution_3 != null
                && !model.resolution_3.isEmpty()) {
            resolutionHashMap.put(getString(R.string._360p), model.resolution_3);
        }
        if (model.resolution_4 != null
                && !model.resolution_4.isEmpty()) {
            resolutionHashMap.put(getString(R.string._480p), model.resolution_4);
        }
        if (model.resolution_5 != null
                && !model.resolution_5.isEmpty()) {
            resolutionHashMap.put(getString(R.string._720p), model.resolution_5);

        }
        if (model.resolution_6 != null
                && !model.resolution_6.isEmpty()) {
            resolutionHashMap.put(getString(R.string._1080p), model.resolution_6);
        }
        if (model.resolution_7 != null
                && !model.resolution_7.isEmpty()) {
            resolutionHashMap.put(getString(R.string._1440p), model.resolution_7);
        }
        if (model.resolution_8 != null
                && !model.resolution_8.isEmpty()) {
            resolutionHashMap.put(getString(R.string._2160p), model.resolution_8);
        }
        if (resolutionHashMap.size() > 1) {
            resolutionHashMap.put(getString(R.string.hint_auto), "");
        /*    if (model.getStremURL() != null && !model.getStremURL().isEmpty()) {
                resolutionHashMap.put(getString(R.string.hint_default), model.getStremURL());
            }*/
            imgVideoQuality.setVisibility(VISIBLE);
        } else {
            imgVideoQuality.setVisibility(GONE);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_MOVE_HOME:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!exoPlayerView.isControllerVisible()) {
                    exoPlayerView.showController();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.e("RemoteKey", "DPAD_DOWN");
                if (!exoPlayerView.isControllerVisible()) {
                    exoPlayerView.showController();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Log.e("RemoteKey", "DPAD_RIGHT");
                if (!exoPlayerView.isControllerVisible()) {
                    exoPlayerView.showController();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.e("RemoteKey", "DPAD_LEFT");
                if (!exoPlayerView.isControllerVisible()) {
                    exoPlayerView.showController();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Log.e("RemoteKey", "DPAD_CENTER");
                if (!exoPlayerView.isControllerVisible()) {
                    exoPlayerView.showController();
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                Log.e("RemoteKey", "DPAD_BACK");
                if (exoPlayerView.isControllerVisible()) {
                    exoPlayerView.hideController();
                } else {
                    Log.i(TAG, "onKeyDown: " + "here click");
                    updateContinueWatchingDataToServer();
                    releasePlayer();
                 //    mediaSessionHelper.stopMediaSession();
                    finish();
                    onBackPressed();
                   /* if (doubleBackToExitPressedOnce) {

                    } else {
                        handleBackPress();
                    }*/
                }

                break;
            case KeyEvent.KEYCODE_ESCAPE:
                Log.e("RemoteKey", "DPAD_ESCAPE");
               /* if (!exoPlayerView.isControllerVisible()){
                    exoPlayerView.showController();
                }else {
                    releasePlayer();
                    finish();
                }*/
                break;
        }
        return false;
    }

    private void handleBackPress() {
        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
        new ToastMsg(PlayerActivityNewCode.this).toastIconSuccess("Please click BACK again to exit.");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    private void openServerDialog(List<Video> videos) {
        if (videos != null) {
            List<Video> videoList = new ArrayList<>();
            videoList.clear();

            for (Video video : videos) {
                if (!video.getFileType().equalsIgnoreCase("embed")) {
                    videoList.add(video);
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivityNewCode.this);
            View view = LayoutInflater.from(PlayerActivityNewCode.this).inflate(R.layout.layout_server_tv, null);
            RecyclerView serverRv = view.findViewById(R.id.serverRv);
            ServerAdapter serverAdapter = new ServerAdapter(PlayerActivityNewCode.this, videoList, "movie");
            serverRv.setLayoutManager(new LinearLayoutManager(PlayerActivityNewCode.this));
            serverRv.setHasFixedSize(true);
            serverRv.setAdapter(serverAdapter);

            Button closeBt = view.findViewById(R.id.close_bt);

            builder.setView(view);

            final AlertDialog dialog = builder.create();
            dialog.show();

            closeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            final ServerAdapter.OriginalViewHolder[] viewHolder = {null};
            serverAdapter.setOnItemClickListener(new ServerAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, Video obj, int position, ServerAdapter.OriginalViewHolder holder) {
                    Intent playerIntent = new Intent(PlayerActivityNewCode.this, PlayerActivityNewCode.class);
                    PlaybackModel video = new PlaybackModel();
                    video.setId(model.getId());
                    video.setTitle(model.getTitle());
                    video.setDescription(model.getDescription());
                    video.setCategory("movie");
                    video.setVideo(obj);
                    video.setVideoList(model.getVideoList());
                    video.setVideoUrl(obj.getFileUrl());
                    video.setVideoType(obj.getFileType());
                    video.setBgImageUrl(model.getBgImageUrl());
                    video.setCardImageUrl(model.getCardImageUrl());
                    video.setIsPaid(model.getIsPaid());

                    playerIntent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video);

                    startActivity(playerIntent);
                    dialog.dismiss();
                    finish();
                }
            });
        } else {
            new ToastMsg(this).toastIconError(getString(R.string.no_other_server_found));
        }
    }

    private void openSubtitleDialog() {
        if (video != null) {
            if (!video.getSubtitle().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivityNewCode.this);
                View view = LayoutInflater.from(PlayerActivityNewCode.this).inflate(R.layout.layout_subtitle_dialog, null);
                RecyclerView serverRv = view.findViewById(R.id.serverRv);
                SubtitleListAdapter adapter = new SubtitleListAdapter(PlayerActivityNewCode.this, video.getSubtitle());
                serverRv.setLayoutManager(new LinearLayoutManager(PlayerActivityNewCode.this));
                serverRv.setHasFixedSize(true);
                serverRv.setAdapter(adapter);
                Button closeBt = view.findViewById(R.id.close_bt);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();
                closeBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //click event
                adapter.setListener(new SubtitleListAdapter.OnSubtitleItemClickListener() {
                    @Override
                    public void onSubtitleItemClick(View view, Subtitle subtitle, int position, SubtitleListAdapter.SubtitleViewHolder holder) {
                        setSelectedSubtitle(mediaSource, subtitle.getUrl());
                        dialog.dismiss();
                    }
                });

            } else {
                new ToastMsg(this).toastIconError(getResources().getString(R.string.no_subtitle_found));
            }
        } else {
            new ToastMsg(this).toastIconError(getResources().getString(R.string.no_subtitle_found));
        }
    }

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

    public void initVideoPlayer(String url, String type) {
        if (player != null) {
            player.stop();
            player.release();
        }
        progressBar.setVisibility(VISIBLE);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new
                AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new
                DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance((Context) PlayerActivityNewCode.this, trackSelector);
        exoPlayerView.setPlayer(player);
        // below 2 lines will make screen size to fit
        exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

        player.setPlayWhenReady(true);

        Uri uri = Uri.parse(url);

        switch (type) {
            case "hls":
                mediaSource = hlsMediaSource(uri, PlayerActivityNewCode.this);
                break;
            case "youtube":
                extractYoutubeUrl(url, PlayerActivityNewCode.this, 18);
                break;
            case "youtube-live":
                extractYoutubeUrl(url, PlayerActivityNewCode.this, 133);
                break;
            case "rtmp":
                mediaSource = rtmpMediaSource(uri);
                break;
            case "mp4":
                mediaSource = mediaSource(uri, PlayerActivityNewCode.this);
                break;
            default:
                mediaSource = mediaSource(uri, PlayerActivityNewCode.this);
                break;
        }
        if (!type.contains("youtube")) {
            player.prepare(mediaSource, true, false);
            exoPlayerView.setPlayer(player);
            player.setPlayWhenReady(true);
        }
        seekTocurrentPosition();
        seekToStartPosition();

/*
        player.addListener(new Player.DefaultEventListener() {
            WatchNextAdapter watchNextAdapter = new WatchNextAdapter();

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    isPlaying = true;
                    progressBar.setVisibility(View.GONE);
                    createChannel();
                    //create media session
                    mediaSessionHelper = new MediaSessionHelper(player, PlayerActivity.this, model, isPlaying);
                    mediaSessionHelper.updateMetadata();
                    mediaSessionHelper.updatePlaybackState();

                } else if (playbackState == Player.STATE_READY) {
                    isPlaying = false;
                    progressBar.setVisibility(View.GONE);
                    //add watch next card
                    long position = player.getCurrentPosition();
                    long duration = player.getDuration();
                    mediaSessionHelper.updateMetadata();
                    mediaSessionHelper.updatePlaybackState();

                    Log.e("123456", "id: " + model.getId());
                    watchNextAdapter.updateProgress(PlayerActivity.this, 119, model, position, duration);

                } else if (playbackState == Player.STATE_BUFFERING) {
                    isPlaying = false;
                    progressBar.setVisibility(VISIBLE);
                    player.setPlayWhenReady(true);

                } else if (playbackState == Player.STATE_ENDED) {
                    //remove now playing card
                    mediaSessionHelper.stopMediaSession();
                    //mediaSessionHelper.stopMediaSession();
                    watchNextAdapter.removeFromWatchNext(PlayerActivity.this, 119, model.getId());
                } else {
                    // player paused in any state
                    isPlaying = false;
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
*/

    /*    exoPlayerView.setControllerVisibilityListener(visibility -> visible = visibility);

        exoPlayerView.setControllerShowTimeoutMs(5 * 1000);*/
    }

    private void seekToStartPosition() {
        Log.d("VideoFragment2", "Is prepped, seeking to " + mStartingPosition);
        // Skip ahead if given a starting position.
        if (mStartingPosition > -1L) {
            if (player.getPlayWhenReady()) {
                Log.d("VideoFragment", "Is prepped, seeking to " + mStartingPosition);
                player.seekTo(mStartingPosition);
            }
        }
    }

    private void seekTocurrentPosition() {
        if (playerCurrentPosition > -1L) {
            if (player.getPlayWhenReady()) {
                Log.d("VideoFragment", "Is prepped, seeking to " + mStartingPosition);
                player.seekTo(playerCurrentPosition);
            }
        }
    }

    private MediaSource mp3MediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "ExoplayerDemo");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Handler mainHandler = new Handler();
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, mainHandler, null);
    }

    private MediaSource mediaSource(Uri uri, Context context) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }

    private MediaSource rtmpMediaSource(Uri uri) {
        MediaSource videoSource = null;

        RtmpDataSourceFactory dataSourceFactory = new RtmpDataSourceFactory();
        videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        return videoSource;
    }

    @SuppressLint("StaticFieldLeak")
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

    private MediaSource hlsMediaSource(Uri uri, Context context) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "phando"), bandwidthMeter);
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        return videoSource;
    }

    @Override
    public void onBackPressed() {
        if (visible == View.GONE) {
            exoPlayerView.showController();
        } else {
            releasePlayer();
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (wakeLock != null)
            wakeLock.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
    }

    private void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
            player = null;
            exoPlayerView.setPlayer(null);
        }
    }

    private long getChannelId() {
        return mChannelId;
    }

    boolean channelExists;
    private static final int MAKE_BROWSABLE_REQUEST_CODE = 9001;

    private void createChannel() {
        Subscription movieSubscription =
                MockDatabase.getVideoSubscription(this);
        channelExists = movieSubscription.getChannelId() > 0L;
        new AddChannelTask(this).execute(movieSubscription);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddChannelTask extends AsyncTask<Subscription, Void, Long> {
        private final Context context;

        public AddChannelTask(Context context) {
            this.context = context;
        }

        @Override
        protected Long doInBackground(Subscription... varArgs) {
            List<Subscription> subscriptions = Arrays.asList(varArgs);
            if (subscriptions.size() != 1) {
                return -1L;
            }
            Subscription subscription = subscriptions.get(0);
            long channelId = TvUtil.createChannel(context, subscription);
            subscription.setChannelId(channelId);
            MockDatabase.saveSubscription(context, subscription);

            TvUtil.scheduleSyncingProgramsForChannel(getApplicationContext(), channelId);
            mChannelId = channelId;
            Log.e("1234", "AsyncTask: " + mChannelId);
            return channelId;
        }

        @Override
        protected void onPostExecute(Long channelId) {
            super.onPostExecute(channelId);
            if (!channelExists)
                promptUserToDisplayChannel(channelId);
        }
    }

    private void promptUserToDisplayChannel(long channelId) {
        // TODO: step 17 prompt user.
        TvContractCompat.requestChannelBrowsable(this, channelId);
        /*Intent intent = new Intent(TvContractCompat.ACTION_REQUEST_CHANNEL_BROWSABLE);
        intent.putExtra(TvContractCompat.EXTRA_CHANNEL_ID, channelId);
        try {
            this.startActivityForResult(intent, MAKE_BROWSABLE_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Could not start activity: " + intent.getAction(), e);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO step 18 handle response
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Channel Added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Channel not added", Toast.LENGTH_LONG).show();
        }
    }
}


