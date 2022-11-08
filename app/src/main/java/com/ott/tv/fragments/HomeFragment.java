package com.ott.tv.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import com.ott.tv.Config;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.database.homeContent.HomeContentViewModel;
import com.ott.tv.model.Channel;
import com.ott.tv.model.VideoContent;
import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.ott.tv.model.home_content.HomeContent;
import com.ott.tv.model.home_content.LatestTvseries;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;
import com.ott.tv.ui.activity.PlayerActivity;
import com.ott.tv.ui.presenter.CardPresenterBanner;
import com.ott.tv.ui.presenter.SliderCardPresenter;
import com.ott.tv.ui.presenter.TvPresenter;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.HomeApi;
import com.ott.tv.ui.BackgroundHelper;
import com.ott.tv.ui.activity.ErrorActivity;
import com.ott.tv.ui.activity.LeanbackActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.utils.PaidDialog;
import com.ott.tv.video_service.PlaybackModel;
import com.ott.tv.video_service.VideoPlaybackActivity;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class HomeFragment extends RowsSupportFragment {

    private BackgroundHelper bgHelper;
    private ArrayObjectAdapter rowsAdapter;
    private CardPresenter cardPresenter;
    private CardPresenterBanner cardPresenterBanner;
    private SliderCardPresenter sliderCardPresenter;
    private HomeContent homeContent = null;
    private HomeContentViewModel homeContentViewModel;
    private ImageView imageView;
    FrameLayout headerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            bgHelper = new BackgroundHelper(getActivity());
            LeanbackActivity activity = (LeanbackActivity) getActivity();
            activity.showLogo();
            setOnItemViewClickedListener(getDefaultItemViewClickedListener());
            setOnItemViewSelectedListener(getDefaultItemSelectedListener());
            if (new NetworkInst(activity).isNetworkAvailable()) {
                getHomeContentDataFromServer();
            } else {
                Intent intent = new Intent(activity, ErrorActivity.class);
                startActivity(intent);
                activity.finish();
            }
        }
    }


    private void getHomeContentDataFromServer() {
        if (getActivity() != null) {
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
                            //   homeContent.getSlider();
                            //  loadSliderRows(homeContent.getSlider().getSlideArrayList());
                            loadRows(homeContent.getFeaturesGenreAndMovie(), homeContent.getSlider().getSlideArrayList());

                        } else if (response.errorBody() != null) {
                            CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                        } else {
                            CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                        }
                    } else {
                        CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HomeContent> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                }
            });
        }
    }

    private void loadRowsTV(List<LatestTvseries> homeContents) {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenter();
        cardPresenterBanner = new CardPresenterBanner();
        sliderCardPresenter = new SliderCardPresenter();
        TvPresenter tvPresenter = new TvPresenter();
        int i;
        for (i = 0; i < homeContents.size(); i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;
            if (i == 0) {
                // load slider
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, "");
            } else if (i == 1) {
                //load tv layout
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getTitle());
            } else {
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getTitle());
            }
            //for (int j = 0; j < NUM_COLS; j++) {
            for (int j = 0; j < homeContents.size(); j++) {

                LatestTvseries videoContent = homeContents.get(i);
                videoContent.setType("tv");
                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        setAdapter(rowsAdapter);
        setCustomPadding();
    }

    private void loadRows(List<FeaturesGenreAndMovie> homeContents, ArrayList<Video> slideArrayList) {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenter();
        cardPresenterBanner = new CardPresenterBanner();
        SliderCardPresenter sliderCardPresenter = new SliderCardPresenter();
        TvPresenter tvPresenter = new TvPresenter();
        for (int i = 0; i < 1; i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;

            listRowAdapter = new ArrayObjectAdapter(cardPresenterBanner);
            /*listRowAdapter = new ArrayObjectAdapter(cardPresenter);*/
            header = new HeaderItem(i, "");
            for (int j = 0; j < slideArrayList.size(); j++) {
                Video videoContent = slideArrayList.get(j);
                videoContent.setType(slideArrayList.get(j).getActionType());
                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));

        }

        for (int i = 0; i < homeContents.size(); i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;
            if (i == 0) {
                // load slider
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getName());
            } else if (i == 1) {
                //load tv layout
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getName());
            }
         /*   else if (i == 5) {
                //load tv layout
                listRowAdapter = new ArrayObjectAdapter(cardPresenterBanner);
                header = new HeaderItem(i, homeContents.get(i).getName());
            }*/
            else {
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getName());
            }
            //for (int j = 0; j < NUM_COLS; j++) {
            for (int j = 0; j < homeContents.get(i).getVideos().size(); j++) {
                Video videoContent = homeContents.get(i).getVideos().get(j);

                if (homeContents.get(i).getViewType().equalsIgnoreCase("tv")) {
                    videoContent.setType("tv");
                } else if (homeContents.get(i).getViewType().equalsIgnoreCase("movie")) {
                    videoContent.setType("movie");
                } else if (homeContents.get(i).getViewType().equalsIgnoreCase("tvseries")) {
                    videoContent.setType("tvseries");
                } else if (homeContents.get(i).getViewType().equalsIgnoreCase("slider")) {
                    if (videoContent.getIsTvseries().equals("1")) {
                        videoContent.setType("tvseries");
                    } else if (videoContent.getIsTvseries().equals("0")) {
                        videoContent.setType("movie");
                    }
                } else {
                    videoContent.setType("movie");
                }

                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        setAdapter(rowsAdapter);
        setCustomPadding();
    }

    private void setCustomPadding() {
   if(getView()!=null) {
       getView().setPadding(20, 0, 0, -50);
   }
    }

    // click listener

    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {
            if (getActivity() != null && getContext() != null) {
                Video videoContent = (Video) o;

                String status = new DatabaseHelper(getContext()).getActiveStatusData().getStatus();

                if (videoContent.getType().equals("tv")) {
                    if (videoContent.getIsPaid().equals("1")) {
                        if (PreferenceUtils.isValid(getActivity())) {
                            if (status.equals("active")) {
                                PlaybackModel model = new PlaybackModel();
                                model.setId(Long.parseLong(videoContent.getVideosId()));
                                model.setTitle(videoContent.getTitle());
                                model.setDescription(videoContent.getDescription());
                                // model.setVideoType(videoContent.getStreamFrom());
                                model.setCategory("tv");
                                // model.setVideoUrl(videoContent.getStreamUrl());
                                model.setCardImageUrl(videoContent.getPosterUrl());
                                model.setBgImageUrl(videoContent.getThumbnailUrl());
                                model.setIsPaid(videoContent.getIsPaid());

                                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                                intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, model);
                                startActivity(intent);
                            } else {
                                //subscription is not active
                                //new PaidDialog(getActivity()).showPaidContentAlertDialog();
                                if (getContext() != null) {
                                    PaidDialog dialog = new PaidDialog(getContext());
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    dialog.show();
                                }
                            }
                        } else {
                            //saved data is not valid, because it was saved more than 2 hours ago
                            PreferenceUtils.updateSubscriptionStatus(getActivity());

                            PaidDialog dialog = new PaidDialog(getContext());
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.show();
                        }
                    } else {
                        PlaybackModel model = new PlaybackModel();
                        model.setId(Long.parseLong(videoContent.getVideosId()));
                        model.setTitle(videoContent.getTitle());
                        model.setDescription(videoContent.getDescription());
                        model.setCategory("tv");
                        model.setCardImageUrl(videoContent.getPosterUrl());
                        model.setBgImageUrl(videoContent.getThumbnailUrl());
                        model.setIsPaid(videoContent.getIsPaid());
                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, model);
                        startActivity(intent);
                    }
                } else {

                    if (videoContent.getActionType() != null) {
                        if (videoContent.getActionType().equalsIgnoreCase("tvseries")) {
                            Intent intent = new Intent(getActivity(), DetailsActivityTvSeries.class);
                            intent.putExtra("id", videoContent.getVideosId());
                            intent.putExtra("video_id", videoContent.getId());
                            intent.putExtra("actions_type", videoContent.getActionType());
                            intent.putExtra("type", videoContent.getType());
                            intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                            intent.putExtra("poster_url", videoContent.getImageLink());
                            intent.putExtra("title", videoContent.getTitle());
                            intent.putExtra("description", videoContent.getDescription());
                            intent.putExtra("release", videoContent.getRelease());
                            intent.putExtra("video_quality", videoContent.getVideoQuality());
                            intent.putExtra("duration", videoContent.getRuntime());
                            intent.putExtra("ispaid", videoContent.getIsPaid());
                            getContext().startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                        }else{
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra("video_id", videoContent.getId());
                            intent.putExtra("type", videoContent.getActionType());
                            intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                            intent.putExtra("poster_url", videoContent.getImageLink());
                            intent.putExtra("title", videoContent.getTitle());
                            intent.putExtra("description", videoContent.getDescription());
                            intent.putExtra("release", videoContent.getRelease());
                            intent.putExtra("video_quality", videoContent.getVideoQuality());
                            intent.putExtra("duration", videoContent.getRuntime());
                            intent.putExtra("ispaid", videoContent.getIsPaid());
                            getContext().startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                        }
                    }else {
                        if(videoContent.getIsTvseries()!=null){
                            if(videoContent.getIsTvseries().equalsIgnoreCase("1")){
                                Intent intent = new Intent(getActivity(), DetailsActivityTvSeries.class);
                                intent.putExtra("id", videoContent.getVideosId());
                                intent.putExtra("video_id", videoContent.getId());
                                intent.putExtra("actions_type", "tvseries");
                                intent.putExtra("type", videoContent.getType());
                                intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                                intent.putExtra("poster_url", videoContent.getImageLink());
                                intent.putExtra("title", videoContent.getTitle());
                                intent.putExtra("description", videoContent.getDescription());
                                intent.putExtra("release", videoContent.getRelease());
                                intent.putExtra("video_quality", videoContent.getVideoQuality());
                                intent.putExtra("duration", videoContent.getRuntime());
                                intent.putExtra("ispaid", videoContent.getIsPaid());
                                getContext().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                            }else{
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                intent.putExtra("video_id", videoContent.getVideosId());
                                intent.putExtra("type", videoContent.getType());
                                intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                                intent.putExtra("poster_url", videoContent.getPosterUrl());
                                intent.putExtra("title", videoContent.getTitle());
                                intent.putExtra("description", videoContent.getDescription());
                                intent.putExtra("release", videoContent.getRelease());
                                intent.putExtra("video_quality", videoContent.getVideoQuality());
                                intent.putExtra("duration", videoContent.getRuntime());
                                intent.putExtra("ispaid", videoContent.getIsPaid());
                                getContext().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                            }

                        }else{
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra("video_id", videoContent.getVideosId());
                            intent.putExtra("type", videoContent.getType());
                            intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                            intent.putExtra("poster_url", videoContent.getPosterUrl());
                            intent.putExtra("title", videoContent.getTitle());
                            intent.putExtra("description", videoContent.getDescription());
                            intent.putExtra("release", videoContent.getRelease());
                            intent.putExtra("video_quality", videoContent.getVideoQuality());
                            intent.putExtra("duration", videoContent.getRuntime());
                            intent.putExtra("ispaid", videoContent.getIsPaid());
                            getContext().startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                        }


                    }

                }
            }
        };
    }


    //listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return (itemViewHolder, item, rowViewHolder, row) -> {
            if (getActivity() != null) {
                if (item instanceof VideoContent) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((VideoContent) item).getPosterUrl());
                } else if (item instanceof Channel) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((Channel) item).getPosterUrl());

                }

            }
        };
    }

}
