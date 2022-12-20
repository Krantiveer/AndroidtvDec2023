package com.ott.tv.ui.activity;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.ott.tv.utils.PreferenceUtils.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;

import com.ott.tv.model.CommonModels;
import com.ott.tv.model.CustomAddsModel;
import com.ott.tv.model.EpiModel;
import com.ott.tv.model.Episode;
import com.ott.tv.model.FavoriteModel;
import com.ott.tv.model.MovieSingleDetails;
import com.ott.tv.model.Season;
import com.ott.tv.model.Video;
import com.ott.tv.model.VideoNew;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.DetailsApi;
import com.ott.tv.network.api.FavouriteApi;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;
import com.ott.tv.video_service.PlaybackModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivityTvSeries extends FragmentActivity {

    private String id;
    private String videoid;
    private String actions_type;
    private String thumbUrl;
    private String poster_url;
    private ImageView bannerImageView;
    private ImageView thumbnail_image;
    private FrameLayout contentView;
    private ImageView indicatorImageView;
    private TextView movie_description_tv;
    private TextView movie_title;
    private LinearLayout ll_timeDate;
    private TextView tvReleaseDate;
    private TextView tvDurationTime;
    private ImageView tvVideoQualityType;
    private AppCompatButton imgWatchList;
    private boolean isWatchLater = false;
    private boolean isFav = false;
    private ImageView imgAddFav;
    private ImageView favIv;
    private TextView tvWatchNow;
    private AppCompatButton tvWatchTrailer;
    private MovieSingleDetails singleDetails = null;
    /*private SingleDetails singleDetails;*/
    String userid = null;
    String tvDurationTime_value;
    String tvVideoQuality;
    PlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady = true;
    int current_Window = 0;
    long playbackPosition = 0;
    private ImageView premiumIconImage;

    final private ArrayList<CustomAddsModel> customAddsModelArrayList = new ArrayList<>();
    private final List<CommonModels> listServer = new ArrayList<>();
    //----episode------
    List<EpiModel> epList = new ArrayList<>();
    private AppCompatButton btn_seasonAndEpisode;
    private AppCompatButton add_watchlist;
    private RecyclerView rv_SeasonList;
    private LinearLayout episode_rv;

    public DetailsActivityTvSeries() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tvseries);
        String type = "tvseries";
        id = this.getIntent().getStringExtra("id");
        actions_type = this.getIntent().getStringExtra("actions_type");
        videoid = this.getIntent().getStringExtra("video_id");
        thumbUrl = this.getIntent().getStringExtra("thumbImage");
        poster_url = this.getIntent().getStringExtra("poster_url");
        String title = this.getIntent().getStringExtra("title");
        String description = this.getIntent().getStringExtra("description");
        tvDurationTime_value = this.getIntent().getStringExtra("duration");
        tvVideoQuality = this.getIntent().getStringExtra("video_quality");
        String tvispaid = this.getIntent().getStringExtra("ispaid");
        contentView = findViewById(R.id.contentView);
        indicatorImageView = findViewById(R.id.indicatorImageView);
        thumbnail_image = findViewById(R.id.thumbnail_image);
        premiumIconImage = findViewById(R.id.premiumIcon);
        Glide.with(this).load(R.raw.logo_anim).into(indicatorImageView);
        btn_seasonAndEpisode = findViewById(R.id.btn_seasonAndEpisode);
        add_watchlist = findViewById(R.id.add_watchlist);
        if (tvispaid != null) {
            if (tvispaid.equalsIgnoreCase("1"))
                premiumIconImage.setVisibility(VISIBLE);
        }
        updateBackgroundThumnail(thumbUrl);
        bannerImageView = findViewById(R.id.bannerImageView);
        setBannerImage(poster_url);

        movie_title = findViewById(R.id.movie_title);
        movie_title.setText(title);
        movie_description_tv = findViewById(R.id.movie_description_tv);
        ll_timeDate = findViewById(R.id.ll_timeDate);
        movie_description_tv.setText(description);

        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvDurationTime = findViewById(R.id.duration_time);
        tvVideoQualityType = findViewById(R.id.tvVideoQualityType);
        tvWatchNow = findViewById(R.id.tvWatchNow);
        tvWatchTrailer = findViewById(R.id.tvWatchTrailer);
        imgWatchList = findViewById(R.id.imgWatchList);
        playerView = findViewById(R.id.video_view);
        episode_rv = findViewById(R.id.episode_rv);
        /*if (type.equals("movie")) {
            if (id != null) {
                getData(type, id);
            }
        }*/
/*
        if (type.equals("tvseries"))*/ {
            if (id != null) {
                getSeriesData(type, id);
            } else {
                getSeriesData(type, videoid);
            }
        }
        if (id == null) {
            id = videoid;
        }

        imgWatchList.setOnClickListener(v -> {
            if (isWatchLater) {
              //  removeFromFav(Constants.WishListType.watch_later);
            } else {
                addToFav(Constants.WishListType.watch_later);
            }
        });
        getFavStatus(Constants.WishListType.watch_later);
        tvWatchNow.setOnClickListener(v -> payAndWatchTV());
        btn_seasonAndEpisode.setOnClickListener(v -> {
            EpisodeAndSeason();
        });
        tvWatchTrailer.setOnClickListener(view -> watchNowClick());
        PreferenceUtils.updateSubscriptionStatus(DetailsActivityTvSeries.this);


    }

    private void getFavStatus(String type) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        Call<FavoriteModel> call = api.verifyFavoriteList(Config.API_KEY, userid, id, type);
        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull retrofit2.Response<FavoriteModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (type.equals(Constants.WishListType.fav)) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            isFav = true;
                   /*         imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        } else {
                            isFav = false;
                     /*       imgAddFav.setImageResource(R.drawable.ic_bottom_fav);
                            favIv.setImageResource(R.drawable.ic_bottom_fav);
                    */
                        }
                  /*      imgAddFav.setVisibility(VISIBLE);
                        favIv.setVisibility(VISIBLE);*/
                    } else {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            isWatchLater = true;
                            imgWatchList.setText("Remove to Watchlist");
                            //   imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);

                        } else {
                            isWatchLater = false;
                            imgWatchList.setText("Add to Watchlist");
                            //     imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorHint), android.graphics.PorterDuff.Mode.SRC_IN);

                        }
                     /*   imgAddFav.setVisibility(VISIBLE);
                        favIv.setVisibility(VISIBLE);*/

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FavoriteModel> call, @NotNull Throwable t) {

            }
        });

    }

    private void addToFav(String type) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        Call<FavoriteModel> call = api.addToFavorite(Config.API_KEY, userid, id, type);
        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull retrofit2.Response<FavoriteModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        new ToastMsg(DetailsActivityTvSeries.this).toastIconSuccess(response.body().getMessage());
                        if (type.equals(Constants.WishListType.watch_later)) {
                            isWatchLater = true;
                            //kranti
                            imgWatchList.setText("Remove to Watchlist");
                            //imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivityTvSeries.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                        } else {
                            isFav = true;
                       /*     imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        }
                    } else {
                        new ToastMsg(DetailsActivityTvSeries.this).toastIconError(response.body().getMessage());
                    }
                } else {
                    new ToastMsg(DetailsActivityTvSeries.this).toastIconError(getString(R.string.error_toast));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteModel> call, @NonNull Throwable t) {
                new ToastMsg(DetailsActivityTvSeries.this).toastIconError(getString(R.string.error_toast));

            }
        });

    }


    public void SeasonListClick(boolean isShow) {
        if (singleDetails != null) {
            if (isShow) {
                tvWatchTrailer.setVisibility(VISIBLE);
                movie_description_tv.setVisibility(VISIBLE);
                tvReleaseDate.setVisibility(VISIBLE);
                ll_timeDate.setVisibility(VISIBLE);
                imgWatchList.setVisibility(VISIBLE);
                movie_title.setVisibility(VISIBLE);
                btn_seasonAndEpisode.setVisibility(VISIBLE);
                episode_rv.setVisibility(GONE);

            } else {
                tvWatchTrailer.setVisibility(GONE);
                movie_description_tv.setVisibility(GONE);
                tvReleaseDate.setVisibility(GONE);
                ll_timeDate.setVisibility(GONE);

                imgWatchList.setVisibility(GONE);
                movie_title.setVisibility(GONE);
                btn_seasonAndEpisode.setVisibility(GONE);
                episode_rv.setVisibility(VISIBLE);

            }
        }
    }

    private void EpisodeAndSeason() {
        if (singleDetails != null) {
            String categoryType;
            String trailerURL;
            String youtubeSource = singleDetails.getTrailler_youtube_source();
            String trailerAwsSource = singleDetails.trailer_aws_source;
            if (trailerAwsSource != null && !trailerAwsSource.isEmpty()) {
                categoryType = "movie";
                trailerURL = trailerAwsSource;
            } else if (youtubeSource != null && !youtubeSource.isEmpty()) {
                categoryType = "youtube";
                trailerURL = youtubeSource;
            } else {
                CMHelper.setSnackBar(this.getCurrentFocus(), "We are sorry, Trailer is not available for this video", 2);
                return;
            }
            if (id == null) {
                id = videoid;
            }
            SeasonListClick(false);
            List<Season> season = singleDetails.getSeason();
            rv_SeasonList = (RecyclerView) findViewById(R.id.rv_SeasonList);
            SeasonAdapter seasonAdapter = new SeasonAdapter(season);
            rv_SeasonList.setHasFixedSize(true);
            rv_SeasonList.setLayoutManager(new LinearLayoutManager(this));
            rv_SeasonList.setAdapter(seasonAdapter);

            List<Episode> episodes = singleDetails.getSeason().get(0).getEpisodes();
            RecyclerView rv_EpisodeList = (RecyclerView) findViewById(R.id.rv_EpisodeList);
            EpisodeAdapter episodeAdapter = new EpisodeAdapter(episodes);
            rv_EpisodeList.setHasFixedSize(true);
            rv_EpisodeList.setLayoutManager(new LinearLayoutManager(this));
            rv_EpisodeList.setAdapter(episodeAdapter);
        }

    }

    private class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
        private List<Episode> listdata;

        public EpisodeAdapter(List<Episode> listdata) {
            this.listdata = listdata;
        }

        @Override
        public EpisodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.layout_episode, parent, false);
            EpisodeAdapter.ViewHolder viewHolder = new EpisodeAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @SuppressLint({"SetTextI18n", "RestrictedApi"})
        @Override
        public void onBindViewHolder(EpisodeAdapter.ViewHolder holder, int position) {
            holder.episode_title.setText(listdata.get(position).getEpisodesName());
            Glide.with(getApplicationContext()).load(listdata.get(position).getImageUrl())
                    .error(R.drawable.poster_placeholder)
                    .fitCenter()
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(holder.episode_image);


            holder.episode_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String categoryType;
                    int pos = holder.getAbsoluteAdapterPosition();

                    String fileType = getFileTypeFromURL(listdata.get(pos).getFileUrl());
                    if (fileType.equals("movie")) {
                        categoryType = "movie";
                        // Do your task here
                    } else {
                        categoryType = "youtube";
                    }
                    if (listdata.get(pos).getFileUrl().isEmpty()) {
                        CMHelper.setSnackBar(getParent().getCurrentFocus(), "Video Not Available.", 2);
                    }
//todo:we need to add description in below

                    Log.i(TAG, "onClick: " + categoryType + listdata.get(pos).getEpisodesName());
                    VideoNew mSelectedVideo = new VideoNew(
                            Long.parseLong(listdata.get(holder.getAbsoluteAdapterPosition()).getEpisodesId()),
                            categoryType,
                            listdata.get(pos).getEpisodesName(),
                            "",
                            listdata.get(pos).getFileUrl(),
                            listdata.get(pos).getImageUrl(),
                            listdata.get(pos).getImageUrl(),
                            listdata.get(pos).getImageUrl());

                    Intent intent = new Intent(getApplicationContext(), PlaybackActivityNew.class);
                    intent.putExtra(VideoDetailsActivity.VIDEO, mSelectedVideo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);


                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView episode_title;
            public TextView episodeTv;
            public LinearLayout episode_ll;
            public ImageView episode_image;

            public ViewHolder(View itemView) {
                super(itemView);
                this.episode_image = itemView.findViewById(R.id.episode_image);
                this.episode_title = (TextView) itemView.findViewById(R.id.episode_title);
                // this.episodeTv = (TextView) itemView.findViewById(R.id.episode_tv);
                episode_ll = (LinearLayout) itemView.findViewById(R.id.episode_ll);
            }


        }

    }

    private class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
        private List<Season> listdata;

        // RecyclerView recyclerView;
        public SeasonAdapter(List<Season> listdata) {
            this.listdata = listdata;
        }

        @Override
        public SeasonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.layout_season, parent, false);
            SeasonAdapter.ViewHolder viewHolder = new SeasonAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(SeasonAdapter.ViewHolder holder, int position) {
            holder.seasonsTv.setText(listdata.get(position).getSeasonsName());
            if (listdata.get(position).getEpisodes() != null) {
                if (!listdata.get(position).getEpisodes().isEmpty()) {
                    holder.episodeTv.setText("    " + listdata.get(position).getEpisodes().size() + " Episodes");
                }
            }

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.episodeTv.getText().toString().isEmpty()) {
                        CMHelper.setSnackBar(view, "Episode not Available", 1);

                    } else {
                        List<Episode> episodes = singleDetails.getSeason().get(holder.getAbsoluteAdapterPosition()).getEpisodes();
                        RecyclerView rv_EpisodeList = (RecyclerView) findViewById(R.id.rv_EpisodeList);
                        EpisodeAdapter episodeAdapter = new EpisodeAdapter(episodes);
                        rv_EpisodeList.setHasFixedSize(true);
                        rv_EpisodeList.setLayoutManager(new LinearLayoutManager(DetailsActivityTvSeries.this));
                        rv_EpisodeList.setAdapter(episodeAdapter);
                    }


                    //      CMHelper.setSnackBar(view, "Episode not Available" + holder.getAbsoluteAdapterPosition(), 1);

                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView seasonsTv;
            public TextView episodeTv;
            public RelativeLayout relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                this.seasonsTv = (TextView) itemView.findViewById(R.id.seasons);
                this.episodeTv = (TextView) itemView.findViewById(R.id.episode_tv);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            }


        }

    }

    private void getSeriesData(String vtype, String vId) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        final List<String> seasonList = new ArrayList<>();

        if (PreferenceUtils.getUserId(getApplicationContext()) != null) {
            userid = PreferenceUtils.getUserId(getApplicationContext());
        } else {
            userid = " ";
        }
       // PreferenceUtils.getInstance().getUsersIdActionOTT(this);
        DetailsApi api = retrofit.create(DetailsApi.class);
        Call<MovieSingleDetails> call = api.getSingleDetail(Config.API_KEY, vtype, vId, userid);

        /*activityIndicator(true);*/
        call.enqueue(new Callback<MovieSingleDetails>() {
            @Override
            public void onResponse(@NotNull Call<MovieSingleDetails> call, @NotNull Response<MovieSingleDetails> response) {
                if (response.code() == 200) {
                    singleDetails = response.body();
                    singleDetails.setType("tvseries");
                    setSeriesData();
                    singleDetails = response.body();
                    String isPaid = singleDetails.getIsPaid();
                    if (singleDetails.getSeason().isEmpty()) {
                        tvWatchNow.setVisibility(GONE);
                    }
                    //----season and episode download------------
                    for (int i = 0; i < singleDetails.getSeason().size(); i++) {
                        Season season = singleDetails.getSeason().get(i);
                        CommonModels models = new CommonModels();
                        String season_name = season.getSeasonsName();
                        models.setTitle(season.getSeasonsName());
                        seasonList.add("Season: " + season.getSeasonsName());
                        if (singleDetails.getSeason().get(0).getSeasonsName() != null) {
                            tvWatchTrailer.setText(singleDetails.getSeason().get(0).getSeasonsName());
                        } else {
                            tvWatchTrailer.setText("Play Season ");
                        }
                        // singleDetails.season.get(0).seasonsName
                        //----episode------
                        // List<EpiModel> epList = new ArrayList<>();
                        for (int j = 0; j < singleDetails.getSeason().get(i).getEpisodes().size(); j++) {
                            Episode episode = singleDetails.getSeason().get(i).getEpisodes().get(j);

                            EpiModel model = new EpiModel();
                            model.setSeson(season_name);
                            model.setEpi(episode.getEpisodesName());
                            model.setStreamURL(episode.getFileUrl());
                            model.setServerType(episode.getFileType());
                            model.setImageUrl(episode.getImageUrl());
                            // model.setSubtitleList(episode.getSubtitle());
                            epList.add(model);
                        }

                        // models.setListEpi(epList);
                        listServer.add(models);
                    }

                    if (!listServer.isEmpty() && !listServer.get(0).getListEpi().isEmpty()) {
                        /*watchNowBt.setText(R.string.play_episode);
                        watchNowBt.setVisibility(VISIBLE);
                        */
                    }


                    //validateForContinueWatching();
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieSingleDetails> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String getFileTypeFromURL(String url) {
        String[] splitedArray = url.split("\\.");
        String lastValueOfArray = splitedArray[splitedArray.length - 1];
        if (lastValueOfArray.equals("mp4") || lastValueOfArray.equals("flv") || lastValueOfArray.equals("m4a") || lastValueOfArray.equals("3gp") || lastValueOfArray.equals("mkv")) {
            return "movie";
        } else if (lastValueOfArray.equals("mp3") || lastValueOfArray.equals("ogg")) {
            return "movie";
        } else if (lastValueOfArray.equals("jpg") || lastValueOfArray.equals("png") || lastValueOfArray.equals("gif")) {
            return "photo";
        } else {
            return "";
        }
    }

    private void setSeriesData() {

        if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Subscription Based")) {
            if (PreferenceUtils.isActivePlan(DetailsActivityTvSeries.this)) {
                if (PreferenceUtils.isValid(DetailsActivityTvSeries.this)) {
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
                tvWatchNow.setText(String.format("Pre Booking ৳%s", singleDetails.price));
            }
        } else if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Pay and Watch")) {
            if (singleDetails.is_subscribed_previously != null && singleDetails.is_subscribed_previously.equalsIgnoreCase("1")) {
                tvWatchNow.setText(String.format("Pay Again and watch for ৳%s", singleDetails.price));
                if (singleDetails.is_rent_expired != null && singleDetails.is_rent_expired.equalsIgnoreCase("0")) {
                    tvWatchNow.setText("Watch Now");
                }
            } else {
                tvWatchNow.setText(String.format("Pay and watch for ৳ %s", singleDetails.price));
            }
        } else {
            tvWatchNow.setText("Watch Now");
        }

        String releaseDate = "Release on - " + singleDetails.getRelease();
        /*arrayList=singleDetails.getWriter();*/

        tvReleaseDate.setText(releaseDate);
        //     String releaseDate = "Release on - " + singleDetails.getRelease();
        tvDurationTime.setText(tvDurationTime_value);
        if (tvVideoQuality != null && tvVideoQuality.equalsIgnoreCase("hd"))
            tvVideoQualityType.setVisibility(VISIBLE);
        else
            tvVideoQualityType.setVisibility(GONE);
    /*
            tvDurationTime.setText("tvDurationTime_value.toString()");
    */
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
      /*  if (singleDetails.getPosterUrl() == null || singleDetails.getPosterUrl().equalsIgnoreCase("")) {
            tvWatchTrailer.setVisibility(View.INVISIBLE);

        } else {
            tvWatchTrailer.setVisibility(VISIBLE);
        }*/
    }


    @Override
    protected void onStop() {
        if (Util.SDK_INT >= 24)
            releasePlayer();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (Util.SDK_INT >= 24)
            releasePlayer();
        super.onDestroy();

    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            current_Window = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onPause() {
        if (Util.SDK_INT < 24)
            releasePlayer();
        super.onPause();
    }


    public void setBannerImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.poster_placeholder_land)
                .error(R.drawable.poster_placeholder_land)
                .into(bannerImageView);
    }

    public void updateBackgroundThumnail(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.poster_placeholder_land)
                .error(R.drawable.poster_placeholder_land)
                .into(thumbnail_image);
    }

    private void payAndWatchTV() {
        if (singleDetails != null) {
            List<Video> videoList = new ArrayList<>();
            epList.size();
            if (epList.isEmpty() || epList.get(0).getStreamURL().isEmpty()) {
                CMHelper.setSnackBar(this.getCurrentFocus(), "We are sorry, Video not available for your selected content", 2);
                return;
            }
            PlaybackModel video = new PlaybackModel();
            ArrayList<Video> videoListForIntent = new ArrayList<>(videoList);
            video.setVideoList(videoListForIntent);
            VideoNew mSelectedVideo = new VideoNew(
                    Long.parseLong(singleDetails.getVideosId()),
                    "tvseries",
                    singleDetails.getTitle(),
                    singleDetails.getDescription(),
                    epList.get(0).getStreamURL(),
                    poster_url,
                    thumbUrl,
                    poster_url);

            Intent intent = new Intent(this, PlaybackActivityNew.class);
            intent.putExtra(VideoDetailsActivity.VIDEO, mSelectedVideo);
            startActivity(intent);
        }
    }


    public void watchNowClick() {
        if (singleDetails != null) {
            String categoryType;
            String trailerURL;
            String youtubeSource = singleDetails.getTrailler_youtube_source();
            String trailerAwsSource = singleDetails.trailer_aws_source;


            if (trailerAwsSource != null && !trailerAwsSource.isEmpty()) {
                categoryType = "movie";
                trailerURL = trailerAwsSource;
            } else if (youtubeSource != null && !youtubeSource.isEmpty()) {
                categoryType = "youtube";
                trailerURL = youtubeSource;
            } else {
                CMHelper.setSnackBar(this.getCurrentFocus(), "We are sorry, Trailer is not available for this video", 2);
                return;
            }
            if (id == null) {
                id = videoid;
            }
            VideoNew mSelectedVideo = new VideoNew(
                    Long.parseLong(id),
                    categoryType,
                    singleDetails.getTitle(),
                    singleDetails.getDescription(),
                    trailerURL,
                    poster_url,
                    thumbUrl,
                    poster_url);
            Intent intent = new Intent(this, PlaybackActivityNew.class);
            intent.putExtra(VideoDetailsActivity.VIDEO, mSelectedVideo);
            startActivity(intent);

        }
    }


    private void getData(String videoType, final String videoId) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();


        if (PreferenceUtils.getUserId(getApplicationContext()) != null) {
            userid = PreferenceUtils.getUserId(getApplicationContext());
        } else {
            userid = " ";
        }
    //    PreferenceUtils.getInstance().getUsersIdActionOTT(this);
        DetailsApi api = retrofit.create(DetailsApi.class);
        Call<MovieSingleDetails> call = api.getSingleDetail(Config.API_KEY, videoType, videoId, userid);

        activityIndicator(true);

        call.enqueue(new Callback<MovieSingleDetails>() {
            @Override
            public void onResponse(@NonNull Call<MovieSingleDetails> call, @NonNull Response<MovieSingleDetails> response) {
                activityIndicator(false);
                if (response.code() == 200 && response.body() != null) {
                    singleDetails = response.body();
                    singleDetails.setType("movie");
                    setMovieData();
                } else {
                    CMHelper.setSnackBar(DetailsActivityTvSeries.this.getCurrentFocus(), "We are sorry, This video content not available, Please try another", 2);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieSingleDetails> call, @NonNull Throwable t) {
                activityIndicator(false);
                CMHelper.setSnackBar(DetailsActivityTvSeries.this.getCurrentFocus(), "We are sorry, This video content not available, Please try another", 2);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setMovieData() {
        if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Subscription Based")) {
            if (PreferenceUtils.isActivePlan(DetailsActivityTvSeries.this)) {
                if (PreferenceUtils.isValid(DetailsActivityTvSeries.this)) {
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
                tvWatchNow.setText(String.format("Pre Booking ৳%s", singleDetails.price));
            }
        } else if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Pay and Watch")) {
            if (singleDetails.is_subscribed_previously != null && singleDetails.is_subscribed_previously.equalsIgnoreCase("1")) {
                tvWatchNow.setText(String.format("Pay Again and watch for ৳%s", singleDetails.price));
                if (singleDetails.is_rent_expired != null && singleDetails.is_rent_expired.equalsIgnoreCase("0")) {
                    tvWatchNow.setText("Watch Now");
                }
            } else {
                tvWatchNow.setText(String.format("Pay and watch for ৳%s", singleDetails.price));
            }
        } else {
            tvWatchNow.setText("Watch Now");
        }

        String releaseDate = "Release on - " + singleDetails.getRelease();
        /*arrayList=singleDetails.getWriter();*/

        tvReleaseDate.setText(releaseDate);
        //     String releaseDate = "Release on - " + singleDetails.getRelease();
        tvDurationTime.setText(tvDurationTime_value);
        if (tvVideoQuality != null && tvVideoQuality.equalsIgnoreCase("hd"))
            tvVideoQualityType.setVisibility(VISIBLE);
        else
            tvVideoQualityType.setVisibility(GONE);
    /*
            tvDurationTime.setText("tvDurationTime_value.toString()");
    */


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
      /*  if (singleDetails.getPosterUrl() == null || singleDetails.getPosterUrl().equalsIgnoreCase("")) {
            tvWatchTrailer.setVisibility(View.INVISIBLE);

        } else {
            tvWatchTrailer.setVisibility(VISIBLE);
        }*/

    }

    public void activityIndicator(boolean show) {
        if (show) {
            indicatorImageView.setVisibility(View.VISIBLE);
            contentView.animate().alpha(0.2f);
        } else {
            indicatorImageView.setVisibility(View.GONE);
            contentView.animate().alpha(1f);
        }
    }

    @Override
    public void onBackPressed() {

        if (movie_title.getVisibility() == GONE) {
            SeasonListClick(true);
            return;
        }
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 22) {
            if (tvWatchNow.hasFocus()) {
                tvWatchTrailer.requestFocus();
                tvWatchTrailer.setFocusable(true);

            }
        }

            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK: return false;
                case KeyEvent.KEYCODE_DPAD_CENTER: return  false;
                case KeyEvent.KEYCODE_DPAD_LEFT: return  false;
                case KeyEvent.KEYCODE_DPAD_RIGHT: return  false;
                case KeyEvent.KEYCODE_DPAD_UP:
                    Log.e("SPLASH ACTIVITY", "movieIndex : " );

                    return  false;

                case KeyEvent.KEYCODE_DPAD_UP_LEFT: return  false;
                case KeyEvent.KEYCODE_DPAD_UP_RIGHT: return  false;
                case KeyEvent.KEYCODE_DPAD_DOWN: return  false;
                case KeyEvent.KEYCODE_DPAD_DOWN_LEFT: return  false;
                case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT: return  false;
            }
            return super.onKeyDown(keyCode, event);
        }
    }

