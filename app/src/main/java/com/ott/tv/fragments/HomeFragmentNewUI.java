package com.ott.tv.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;

import com.ott.tv.BuildConfig;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.adapter.HomeBannerAdapter;
import com.ott.tv.adapter.HomeBannerSecAdapter;

import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.ott.tv.model.home_content.HomeContent;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.model.phando.PlayerActivityNewCode;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.network.api.HomeApi;
import com.ott.tv.ui.activity.LoginChooserActivity;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class HomeFragmentNewUI extends Fragment {
    private RecyclerView recyclerViewBannerTop;
    private RecyclerView recyclerViewBannerBottom;
    private ImageView imageViewBGBanner, content_premiumIconImage;
    private TextView textViewBanner;
    private TextView textViewBannerDescription;
    private TextView textViewBannerReleaseYear;
    private TextView textViewBannerLanguage;
    private TextView textViewBannerGenre;
    private TextView textViewDuration;

    private HomeContent homeContent = null;
    private BrowseData dataContent = null;
    private PlayerView exoPlayerView;
    protected @Nullable
    ExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private MediaSource mediaSource;
    int maxVolume = 50;
    private boolean startAutoPlay = true;
    private static final String TAG = "HomeFragmentNewUI";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getHomeContentDataFromServer();
        View view = inflater.inflate(R.layout.fragment_home_new_ui, container, false);
        initViews(view);
        return view;
    }

    private void setTextViewBanner(LatestMovieList video) {
        if (video.getType() != null) {
            if (!video.getType().equalsIgnoreCase("VM") ) {
                if(video.getType().equalsIgnoreCase("OTT") && video.getTitle().equalsIgnoreCase("View All")){
                    return;
                }
                //  releasePlayer();
                //  String url = "https://action-ott-live.s3.ap-south-1.amazonaws.com/Sultan+Trailer/sultan+(1).mp4";
                if (video.getTrailer_aws_source() != null) {
                    String url = video.getTrailer_aws_source();
                    initVideoPlayer(url, "movie");
                } /*else {
            if (video.getTrailler_youtube_source() != null) {
                String url = video.getTrailler_youtube_source();
                initVideoPlayer(url, "movie");
            }
        }*/

                textViewBanner.setText(video.getTitle());

                if (video.getIs_free() != null) {
                    if (video.getIs_free().toString().equalsIgnoreCase("1")) {
                        content_premiumIconImage.setVisibility(View.INVISIBLE);
                    } else {
                        content_premiumIconImage.setVisibility(View.VISIBLE);

                    }
                }


                /*Glide.with(getContext()).load(video.getImageLink()).into(imageViewBGBanner);*/
                if (video.getImageLink() == null) {
               if(video.getThumbnailUrl()!=null){
                   video.setImageLink(video.getThumbnailUrl());
               }else if(video.getThumbnail()!=null){
                   video.setImageLink(video.getThumbnail());

               }
                }

                if (getActivity() == null) {
                    return;
                }
                Glide.with(HomeFragmentNewUI.this)
                        .load(video.getImageLink())
                        .error(R.drawable.poster_placeholder_land)
                        .placeholder(R.drawable.poster_placeholder_land)
                        .into(imageViewBGBanner);

                if (video.getLanguage() != null) {
                    textViewBannerLanguage.setText(video.getLanguage());
                } else {
                    textViewBannerLanguage.setVisibility(View.GONE);
                }
                textViewBannerReleaseYear.setText(video.getRelease());
                if (video.getGenre() != null) {
                    textViewBannerGenre.setText(video.getGenre());
                } else {
                    textViewBannerGenre.setVisibility(View.GONE);
                }
                if (video.getRuntime() != null) {
                    textViewDuration.setText(video.getRuntime());
                } else {
                    textViewDuration.setVisibility(View.GONE);
                }
                if (video.getDetail() != null) {
                    textViewBannerDescription.setText(video.getDetail());
                } else {
                    textViewBannerDescription.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initViews(View view) {
        recyclerViewBannerTop = view.findViewById(R.id.recyclerViewBanner);
        recyclerViewBannerBottom = view.findViewById(R.id.recyclerViewBannerBottom);
        imageViewBGBanner = view.findViewById(R.id.bg_imageview);
        textViewBanner = view.findViewById(R.id.content_detail_title);
        textViewBannerDescription = view.findViewById(R.id.content_detail_description);
        textViewBannerReleaseYear = view.findViewById(R.id.content_releaseyear);
        textViewBannerLanguage = view.findViewById(R.id.content_language);
        textViewBannerGenre = view.findViewById(R.id.content_genre);
        textViewDuration = view.findViewById(R.id.content_duration);
        exoPlayerView = view.findViewById(R.id.player_view_home);
        content_premiumIconImage = view.findViewById(R.id.content_premiumIconImage);
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

    public void initVideoPlayer(String url, String type) {
        Log.i(TAG, "setTextViewBanner:1 " + url);
        if (getContext() != null) {
            if (url != null && !url.isEmpty()) {
                if (player != null) {
                    player.stop();
                    player.release();
                }

                DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
// Create a HLS media source pointing to a playlist uri.
                HlsMediaSource hlsMediaSource =
                        new HlsMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(MediaItem.fromUri(url));
// Create a player instance.
                player = new ExoPlayer.Builder(getContext()).build();
                player.setMediaSource(hlsMediaSource);

                player.prepare();
                player.setPlayWhenReady(startAutoPlay);

                exoPlayerView.setPlayer(player);

                player.setPlayWhenReady(true);
                player.addListener(new Player.Listener() {
                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        switch (playbackState) {
                            case 1:
                                Log.d("krantiv", "Ideal state");
                                exoPlayerView.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                // exoPlayerView.setVisibility(View.INVISIBLE);
                                Log.d("krantiv", "STATE_BUFFERING state");

                                break;
                            case 3:
                                Log.d("krantiv", "STATE_READY state");
                                if (exoPlayerView.getVisibility() == View.INVISIBLE)
                                    exoPlayerView.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                exoPlayerView.setVisibility(View.INVISIBLE);
                                Log.d("krantiv", "STATE_ENDED state");
                                break;
                        }
                    }
                });

            }else {
                //  if (!player.isPlaying()) {
                {
                    releasePlayer();
                    exoPlayerView.setVisibility(View.INVISIBLE);

                }

            }
        }
    }


/*
    private MediaSource mediaSource(Uri uri, HomeFragmentNewUI homeFragmentNewUI) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }*/

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

    private void getHomeContentDataFromServer() {
        if (getActivity() != null) {
            final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
            final FragmentManager fm = getFragmentManager();

            assert fm != null;
            if (!BuildConfig.FLAVOR.equalsIgnoreCase("uvtv")) {
                fm.beginTransaction().add(R.id.browserSection, mSpinnerFragment).commit();
            }
            String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(requireContext());
            Retrofit retrofit = RetrofitClient.getRetrofitInstanceWithV1();
            Dashboard api = retrofit.create(Dashboard.class);
         //   Call<HomeContent> call = api.getHomeContent(accessToken);
            Call<List<BrowseData>> call = api.getBrowseDataList(accessToken, "", "", "", "", 10, 1);

            call.enqueue(new Callback<List<BrowseData>>() {
                @Override
                public void onResponse(@NotNull Call<List<BrowseData>> call, @NotNull retrofit2.Response<List<BrowseData>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            //homeContent = response.body();
                           // dataContent=response.body();
                          //  homeContent.setHomeContentId(1);
                            Constants.IS_FROM_HOME = true;
                            if (response.body() != null && !response.body().isEmpty()) {
                                setTextViewBanner(response.body().get(0).getList().get(0));
                          //      setTextViewBanner(response.body().get(0).getVideos().get(0));
                                loadRows(response.body());
                            }
                         //   ArrayList<Video> slideArrayList = homeContent.getSlider().getSlideArrayList();

                        } else if (response.code() == 401) {
                            signOut();
                        } else if (response.errorBody() != null) {
                            Log.e("@@", response.errorBody().toString());
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();

                    }
                    /*recyclerViewBannerTop.scrollToPosition(1);*/
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();


                }

                @Override
                public void onFailure(@NonNull Call<List<BrowseData>> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Log.e("@@error", t.getMessage()+ t.getLocalizedMessage()+t.toString());
                    CMHelper.setSnackBar(requireView(), t.getMessage().toString(), 2);
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
                }
            });
        }
    }

    private void signOut() {
        if (getContext() != null && getActivity() != null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        /*    String userId = databaseHelper.getUserData().getUserId();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
            }*/
            if (PreferenceUtils.getInstance().getAccessTokenPref(getContext()) != "") {
                SharedPreferences.Editor editor = getContext().getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false);
                editor.apply();
                databaseHelper.deleteUserData();
                PreferenceUtils.clearSubscriptionSavedData(getContext());
                PreferenceUtils.getInstance().setAccessTokenNPref(getContext(), "");
                startActivity(new Intent(getContext(), LoginChooserActivity.class));
                Toast.makeText(getContext(), "You've been logged out because we have detected another login from your ID on a different device. You are not allowed to login on more than one device at a time.", Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        }
    }
/*
    private void loadRows(List<FeaturesGenreAndMovie> homeContents, ArrayList<Video> slideArrayList) {

        HomeBannerAdapter adapter = new HomeBannerAdapter(slideArrayList, getContext());
        adapter.setSendInterfacedata(description -> setTextViewBanner(description));
        adapter.setSendInterfaceClick(() -> releasePlayer());
        recyclerViewBannerTop.setAdapter(adapter);

        HomeBannerSecAdapter homeBannerSecAdapter = new HomeBannerSecAdapter(homeContents, getContext());

        homeBannerSecAdapter.setSendInterfacedata(description -> setTextViewBanner(description));
        homeBannerSecAdapter.setSendInterfaceClick(() -> releasePlayer());

        recyclerViewBannerBottom.setAdapter(homeBannerSecAdapter);
    }*/

/*
    private void loadRowsnew(List<FeaturesGenreAndMovie> homeContents, ArrayList<Video> slideArrayList) {

        HomeBannerAdapter adapter = new HomeBannerAdapter(slideArrayList, getContext());
        adapter.setSendInterfacedata(description -> setTextViewBanner(description));
        adapter.setSendInterfaceClick(() -> releasePlayer());
        recyclerViewBannerTop.setAdapter(adapter);
*/
/*
        HomeBannerSecAdapter homeBannerSecAdapter = new HomeBannerSecAdapter(homeContents, getContext());

        homeBannerSecAdapter.setSendInterfacedata(description -> setTextViewBanner(description));
        homeBannerSecAdapter.setSendInterfaceClick(() -> releasePlayer());

        recyclerViewBannerBottom.setAdapter(homeBannerSecAdapter);*//*

    }
*/

    private void loadRows(List<BrowseData> homeContents) {

        HomeBannerSecAdapter homeBannerSecAdapter = new HomeBannerSecAdapter(homeContents, getContext());

        homeBannerSecAdapter.setSendInterfacedata(description -> setTextViewBanner(description));
        homeBannerSecAdapter.setSendInterfaceClick(() -> releasePlayer());

        recyclerViewBannerBottom.setAdapter(homeBannerSecAdapter);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        releasePlayer();
        super.onDetach();
    }

    @Override
    public void onPause() {
        releasePlayer();
        super.onPause();
    }
}


