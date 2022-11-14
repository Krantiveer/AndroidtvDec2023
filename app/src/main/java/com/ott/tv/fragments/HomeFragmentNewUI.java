package com.ott.tv.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.adapter.HomeBannerAdapter;
import com.ott.tv.adapter.HomeBannerSecAdapter;

import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.ott.tv.model.home_content.HomeContent;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.HomeApi;
import com.ott.tv.utils.CMHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class HomeFragmentNewUI extends Fragment {
    private RecyclerView recyclerViewBannerTop;
    private RecyclerView recyclerViewBannerBottom;
    private ImageView imageViewBGBanner;
    private TextView textViewBanner;
    private TextView textViewBannerDescription;
    private TextView textViewBannerReleaseYear;
    private TextView textViewBannerLanguage;
    private TextView textViewBannerGenre;
    private TextView textViewDuration;

    private HomeContent homeContent = null;
    private PlayerView exoPlayerView;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private MediaSource mediaSource;
    int maxVolume = 50;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getHomeContentDataFromServer();
        View view = inflater.inflate(R.layout.fragment_home_new_ui, container, false);
        initViews(view);
        return view;
    }

    private void setTextViewBanner(Video video) {
        releasePlayer();
        //  String url = "https://action-ott-live.s3.ap-south-1.amazonaws.com/Sultan+Trailer/sultan+(1).mp4";
        if (video.getTrailer_aws_source() != null) {
            String url = "https://phando010.s.llnwi.net/633d7daf012dd/633d7daf012dd_sec.mp4";
            initVideoPlayer(url, "movie");
        } else {
            if (video.getTrailler_youtube_source() != null) {
                String url = video.getTrailler_youtube_source();
                initVideoPlayer(url, "movie");
            }
        }
        textViewBanner.setText(video.getTitle());
        /*Glide.with(getContext()).load(video.getImageLink()).into(imageViewBGBanner);*/
        if (video.getImageLink() == null) {
            video.setImageLink(video.getPosterUrl());
        }
        if (getActivity() == null) {
            return;
        }
        Glide.with(HomeFragmentNewUI.this)
                .load(video.getImageLink())
                .placeholder(R.drawable.poster_placeholder_land)
                .error(R.drawable.poster_placeholder_land)
                .centerCrop()
                .into(imageViewBGBanner);

        if (video.getLanguage() != null) {
            textViewBannerLanguage.setText(video.getLanguage());
        } else {
            textViewBannerLanguage.setVisibility(View.GONE);
        }
        Log.i("release", "setTextViewBanner: " + video.getRelease() + video.getId());
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
        if (video.getDescription() != null) {
            textViewBannerDescription.setText(video.getDescription());
        } else {
            textViewBannerDescription.setVisibility(View.GONE);
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
        if (player != null) {
            player.stop();
            player.release();
        }
        if (getContext() != null) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new
                    AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new
                    DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            player.setVolume(.05f);
            exoPlayerView.setPlayer(player);
            // below 2 lines will make screen size to fit
            exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

            player.setPlayWhenReady(true);


            player.addListener(new Player.EventListener() {
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
            Uri uri = Uri.parse(url);
            type = "mp4";
            switch (type) {
            /*case "hls":
                mediaSource = hlsMediaSource(uri, getContext());
                break;
            case "youtube":
                extractYoutubeUrl(url, getContext(), 18);
                break;
            case "youtube-live":
                extractYoutubeUrl(url, getContext(), 133);
                break;
            case "rtmp":
                mediaSource = rtmpMediaSource(uri);
                break;*/
                case "mp4":
                    mediaSource = mediaSource(uri, HomeFragmentNewUI.this);
                    break;
                default:
                    mediaSource = mediaSource(uri, HomeFragmentNewUI.this);
                    break;
            }
            if (!type.contains("youtube")) {
                player.prepare(mediaSource, true, false);
                exoPlayerView.setPlayer(player);
                player.setPlayWhenReady(true);
            }

      /*  seekTocurrentPosition();
        seekToStartPosition();
*/

        }
    }

    private MediaSource mediaSource(Uri uri, HomeFragmentNewUI homeFragmentNewUI) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }


    private void getHomeContentDataFromServer() {
        if (getActivity() != null) {
            final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
            final FragmentManager fm = getFragmentManager();
            assert fm != null;
            fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();

            String userId = new DatabaseHelper(requireContext()).getUserData().getUserId();
            Retrofit retrofit = RetrofitClient.getRetrofitInstanceWithV1();
            HomeApi api = retrofit.create(HomeApi.class);
            Call<HomeContent> call = api.getHomeContent(Config.API_KEY, userId);
            call.enqueue(new Callback<HomeContent>() {
                @Override
                public void onResponse(@NotNull Call<HomeContent> call, @NotNull retrofit2.Response<HomeContent> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            homeContent = response.body();
                            homeContent.setHomeContentId(1);
                            Constants.IS_FROM_HOME=true;
                            setTextViewBanner(homeContent.getSlider().getSlideArrayList().get(0));
                            loadRows(homeContent.getFeaturesGenreAndMovie(), homeContent.getSlider().getSlideArrayList());
                            ArrayList<Video> slideArrayList = homeContent.getSlider().getSlideArrayList();

                        } else if (response.errorBody() != null) {
                            CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                        } else {
                            CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                        }

                    } else {
                        CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                    }
                    /*recyclerViewBannerTop.scrollToPosition(1);*/
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();


                }

                @Override
                public void onFailure(@NonNull Call<HomeContent> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
                }
            });
        }
    }

    private void loadRows(List<FeaturesGenreAndMovie> homeContents, ArrayList<Video> slideArrayList) {

        HomeBannerAdapter adapter = new HomeBannerAdapter(slideArrayList, getContext());
        adapter.setSendInterfacedata(description -> setTextViewBanner(description));
        adapter.setSendInterfaceClick(() -> releasePlayer());
        recyclerViewBannerTop.setAdapter(adapter);

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
}


