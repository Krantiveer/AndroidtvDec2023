package com.ott.tv.ui.activity;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.adapter.HomePageAdapter;
import com.ott.tv.model.CommonModels;
import com.ott.tv.model.CustomAddsModel;
import com.ott.tv.model.FavoriteModel;
import com.ott.tv.model.Video;
import com.ott.tv.model.phando.MediaplaybackData;
import com.ott.tv.model.phando.PlayerActivityNewCode;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.model.phando.Wishlist;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.network.api.FavouriteApi;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;
import com.ott.tv.video_service.PlaybackModel;
import com.ott.tv.video_service.VideoPlaybackActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivityPhando extends FragmentActivity {

    private String videoId;
    private String id;
    private String thumbUrl;
    private String poster_url, trailer_url;
    private ImageView bannerImageView;
    private ImageView thumbnail_image;
    private FrameLayout contentView;


    private ImageView tvVideoQualityType;

    private boolean isWatchLater = false;
    private boolean isFav = false;
    private ImageView favIv;
    private AppCompatButton tvWatchNow;
    private AppCompatButton tvWatchTrailer;
    private AppCompatButton imgWatchList;
    private AppCompatButton imgFavList;
    private MediaplaybackData singleDetails = null;
    private final List<CommonModels> listRelated = new ArrayList<>();
    String userid = null;
    String tvDurationTime_value;
    String tvVideoQuality;
    PlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady = true;
    int current_Window = 0;
    long playbackPosition = 0;
    //    private ShimmerTextView premiumIconImage;
    private ImageView premiumIconImage;

    final private ArrayList<CustomAddsModel> customAddsModelArrayList = new ArrayList<>();

    private ArrayObjectAdapter mAdapter;
    private RecyclerView rvRelated;
    private HomePageAdapter relatedAdapter;
    private RelativeLayout activity_rv;
    private ProgressBar progress_indicator;
    String releaseDate, description, title, tvispaid, maturity_rating, ratingData, language, genres, is_live;
    private ImageView content_rating_image, content_duration_image, releasedate_image, language_image, maturity_rating_image, genres_image;
    private TextView content_rating_text, duration_time, tvReleaseDate, language_tv, maturity_rating_tv, genres_tv, tv_related;
    String type;

    public DetailsActivityPhando() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_phando);
        type = this.getIntent().getStringExtra("type");
        thumbUrl = this.getIntent().getStringExtra("thumbImage");

        videoId = this.getIntent().getStringExtra("video_id");
        title = this.getIntent().getStringExtra("title");
        description = this.getIntent().getStringExtra("description");
        releaseDate = this.getIntent().getStringExtra("release");

        tvDurationTime_value = this.getIntent().getStringExtra("duration");
        maturity_rating = this.getIntent().getStringExtra("maturity_rating");
        tvispaid = this.getIntent().getStringExtra("ispaid");
        ratingData = this.getIntent().getStringExtra("rating");
        language = this.getIntent().getStringExtra("language_str");
        if(this.getIntent().getStringExtra("is_live")!=null) {
            is_live = this.getIntent().getStringExtra("is_live").toString();
        }else{
            is_live="0";
        }
        genres = this.getIntent().getStringExtra("genres");

        //  id = this.getIntent().getStringExtra("id");
        poster_url = this.getIntent().getStringExtra("thumbImage");
        trailer_url = this.getIntent().getStringExtra("trailer");
        //  tvVideoQuality = this.getIntent().getStringExtra("video_quality");
        contentView = findViewById(R.id.contentView);

        thumbnail_image = findViewById(R.id.thumbnail_image);
        premiumIconImage = findViewById(R.id.premiumIcon);
        tvWatchTrailer = findViewById(R.id.tvWatchTrailer);
        tv_related = findViewById(R.id.tv_related);
        contentFromPreviousScreen();

        if (tvispaid != null) {
            if (tvispaid.equalsIgnoreCase("0") || tvispaid.equalsIgnoreCase("2")) {
                premiumIconImage.setVisibility(VISIBLE);
            } else {
                premiumIconImage.setVisibility(GONE);
            }
        }

        updateBackgroundThumnail(thumbUrl);
        bannerImageView = findViewById(R.id.bannerImageView);

        setBannerImage(poster_url);

        TextView movie_title = findViewById(R.id.movie_title);
        movie_title.setText(title);
        TextView movie_description_tv = findViewById(R.id.movie_description_tv);
        movie_description_tv.setText(description);


        tvVideoQualityType = findViewById(R.id.tvVideoQualityType);
        tvWatchNow = findViewById(R.id.tvWatchNow);
        imgWatchList = findViewById(R.id.imgWatchList);
        if (is_live != null) {
            if (is_live.equalsIgnoreCase("1")) {
                tvWatchTrailer.setVisibility(GONE);
                imgWatchList.setVisibility(GONE);
            }
        }

        rvRelated = findViewById(R.id.rv_related);
        playerView = findViewById(R.id.video_view);
        activity_rv = findViewById(R.id.activity_rv);

        imgFavList = findViewById(R.id.imgFavList);
        progress_indicator = findViewById(R.id.progress_indicator);

        if (type == null) {
            type = "M";
        }
        if (type.equals("M")) {
            if (videoId != null) {
                getData(type, videoId);
            } else {
                getData(type, id);
            }
        }

        imgWatchList.setOnClickListener(v -> {

            if (isWatchLater) {
                addToFav("0");

            } else {
                addToFav("1");
            }
        });

       /* imgFavList.setOnClickListener(v -> {
            if (isFav) {
                removeFromFav(Constants.WishListType.fav);
            } else {
                addToFav(Constants.WishListType.fav);
            }
        });*/
        //  getFavStatus(Constants.WishListType.watch_later);
        //  getFavStatus(Constants.WishListType.fav);
        tvWatchNow.setOnClickListener(v -> payAndWatchTV());
        tvWatchTrailer.setOnClickListener(view -> trailerClick());
        PreferenceUtils.updateSubscriptionStatus(DetailsActivityPhando.this);
    }

    private void contentFromPreviousScreen() {
        content_rating_text = findViewById(R.id.content_rating_text);
        content_rating_image = findViewById(R.id.content_rating_image);
        if (ratingData != null) {
            content_rating_text.setText(ratingData);
        } else {
            content_rating_image.setVisibility(GONE);
            content_rating_text.setVisibility(GONE);
        }

        content_duration_image = findViewById(R.id.content_duration_image);
        duration_time = findViewById(R.id.duration_time);

        if (tvDurationTime_value != null && !tvDurationTime_value.isEmpty()) {
            duration_time.setText(tvDurationTime_value);
        } else {
            content_duration_image.setVisibility(GONE);
            duration_time.setVisibility(GONE);
        }

        releasedate_image = findViewById(R.id.releasedate_image);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);

        if (releaseDate != null) {
            tvReleaseDate.setText(releaseDate);
        } else {
            releasedate_image.setVisibility(GONE);
            tvReleaseDate.setVisibility(GONE);
        }

        language_image = findViewById(R.id.language_image);
        language_tv = findViewById(R.id.language_tv);

        if (language != null && !language.isEmpty()) {
            language_tv.setText(language);
        } else {
            language_image.setVisibility(GONE);
            language_tv.setVisibility(GONE);
        }

        maturity_rating_image = findViewById(R.id.maturity_rating_image);
        maturity_rating_tv = findViewById(R.id.maturity_rating);

        if (maturity_rating != null) {
            maturity_rating_tv.setText(maturity_rating);
        } else {
            maturity_rating_image.setVisibility(GONE);
            maturity_rating_tv.setVisibility(GONE);
        }

        genres_image = findViewById(R.id.genres_image);
        genres_tv = findViewById(R.id.genres_tv);

        if (genres != null) {
            genres_tv.setText(genres);
        } else {
            genres_image.setVisibility(GONE);
            genres_tv.setVisibility(GONE);
        }
        if (trailer_url != null) {
            tvWatchTrailer.setVisibility(VISIBLE);
        } else {
            tvWatchTrailer.setVisibility(GONE);
        }
    }


    @Override
    protected void onStop() {
        if (Util.SDK_INT >= 24) releasePlayer();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (Util.SDK_INT >= 24) releasePlayer();
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
        if (Util.SDK_INT < 24) releasePlayer();
        super.onPause();
    }

    public void setBannerImage(String url) {
        Glide.with(this).load(url).placeholder(R.color.black_color).error(R.drawable.poster_placeholder_land).into(bannerImageView);
    }

    public void updateBackgroundThumnail(String url) {
        Glide.with(this).load(url).placeholder(R.color.black_color).error(R.drawable.poster_placeholder_land).into(thumbnail_image);
    }

    private void payAndWatchTV() {
        if (singleDetails != null) {
            List<Video> videoList = new ArrayList<>();
   /*         for (Video video : singleDetails.getVideos()) {
                if (video.getFileType() != null && !video.getFileType().equalsIgnoreCase("embed")) {
                    videoList.add(video);
                }
            }*/
            if (tvWatchNow.getText() == null || !tvWatchNow.getText().toString().equalsIgnoreCase("Watch Now")) {
                CMHelper.setSnackBar(this.getCurrentFocus(), "Hey, Please upgrade your account from MOBILE APP | WEBSITE - UVTV ", 1, 10000);
                return;
            } else if (singleDetails.getList().getMedia_url().isEmpty()) {
                CMHelper.setSnackBar(this.getCurrentFocus(), "We are sorry, Video not available for your selected content", 2);
                return;
            }
/*            PlaybackModel video = new PlaybackModel();
            ArrayList<Video> videoListForIntent = new ArrayList<>(videoList);
            video.setVideoList(videoListForIntent);
            VideoNew mSelectedVideo = new VideoNew(
                    Long.parseLong(videoId),
                    "movie",title,
                    description,
                    singleDetails.getList().getMedia_url(),
                    poster_url,
                    thumbUrl,
                    poster_url);
            Intent intent = new Intent(this, PlaybackActivityNew.class);
            intent.putExtra(VideoDetailsActivity.VIDEO, mSelectedVideo);
            startActivity(intent);*/
            PlaybackModel video = new PlaybackModel();
            ArrayList<Video> videoListForIntent = new ArrayList<>(videoList);
            video.setId(Long.parseLong(videoId));
            video.setTitle(title);
            video.setDescription(description);
            video.setVideoType(Config.VideoURLTypeHls);
            video.setCategory(type);
            video.setVideoUrl(singleDetails.getList().getMedia_url());
            video.setCardImageUrl(poster_url);
            video.setIstrailer(false);

            video.setBgImageUrl(thumbUrl);
            video.setIsPaid("free");

            //  video.setVideo(singleDetails.getVideos().get(0));

            Intent intent = new Intent(this, PlayerActivityNewCode.class);
            intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video);
            startActivity(intent);
        }
    }

    public void trailerClick() {
        List<Video> videoList = new ArrayList<>();
        PlaybackModel video = new PlaybackModel();
        ArrayList<Video> videoListForIntent = new ArrayList<>(videoList);
        video.setId(Long.parseLong(videoId));
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoType(Config.VideoURLTypeHls);
        video.setCategory(type);
        video.setVideoUrl(trailer_url);
        // video.setCardImageUrl(trailer_url);
        video.setIstrailer(false);
        video.setBgImageUrl(thumbUrl);
        video.setIsPaid("free");
        //  video.setVideo(singleDetails.getVideos().get(0));
        Intent intent = new Intent(this, PlayerActivityNewCode.class);
        intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video);
        startActivity(intent);

    }


    private void getData(String videoType, final String videoId) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        if (PreferenceUtils.getUserId(getApplicationContext()) != null) {
            userid = PreferenceUtils.getUserId(getApplicationContext());
        } else {
            userid = " ";
        }
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this);
        //  PreferenceUtils.getInstance().getUsersIdActionOTT(this);
        Dashboard api = retrofit.create(Dashboard.class);
        Call<MediaplaybackData> call = api.getSingleDetailAPI(accessToken, videoId, videoType, "1");
        activityIndicator(true);
        call.enqueue(new Callback<MediaplaybackData>() {
            @Override
            public void onResponse(@NonNull Call<MediaplaybackData> call, @NonNull Response<MediaplaybackData> response) {
                activityIndicator(false);
                if (response.code() == 200 && response.body() != null) {
                    singleDetails = response.body();
                    if (singleDetails.getList() != null) {
                        if (singleDetails.getList().getIs_wishlist().equalsIgnoreCase("1")) {
                            isWatchLater = true;
                            imgWatchList.setText("Remove to Watchlist");

                        } else {
                            imgWatchList.setText("Add to Watchlist");
                            isWatchLater = false;
                        }
                        if (singleDetails.getList().getMedia_url() == null) {
                            tvWatchNow.setVisibility(GONE);
                        }
                        //  if(singleDetails.getMediaCode())
                        //  singleDetails.setType("M");

                        if (singleDetails.getList().getRelated().size() > 0) {
                            //----related post---------------
                            for (int i = 0; i < singleDetails.getList().getRelated().size(); i++) {
                                ShowWatchlist relatedMovie = singleDetails.getList().getRelated().get(i);
                                CommonModels models = new CommonModels();
                                models.setTitle(relatedMovie.getTitle());
                                models.setImageUrl(relatedMovie.getThumbnail());
                                models.setId(relatedMovie.getId().toString());
                                models.setVideoType(relatedMovie.getType());
                                models.setIsPaid(relatedMovie.getIs_free().toString());
                                /*   models.setIsLive(relatedMovie.getIsPaid());*/
                                /*  models.setVideo_view_type(relatedMovie.getVideo_view_type());*/
                                listRelated.add(models);
                            }
                            relatedAdapter = new HomePageAdapter(getBaseContext(), listRelated, "");
                            rvRelated.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL,
                                    false));
                            rvRelated.setHasFixedSize(false);
                            rvRelated.setAdapter(relatedAdapter);
                        } else {
                            tv_related.setVisibility(GONE);
                            rvRelated.setVisibility(GONE);
                        }
                        setMovieData();
                    }
                } else {
                    CMHelper.setSnackBar(DetailsActivityPhando.this.getCurrentFocus(), "We are sorry, This video content not available, Please try another", 2);

                }
            }

            @Override
            public void onFailure(@NonNull Call<MediaplaybackData> call, @NonNull Throwable t) {
                activityIndicator(false);
                Log.i("DetailISSUE_kranti", "onResponse: " + t);
                CMHelper.setSnackBar(DetailsActivityPhando.this.getCurrentFocus(), "We are sorry, This video content not available, Please try another" + t, 2);
            }
        });

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
    private void setMovieData() {

        if (singleDetails.getList().getMedia_url() != null) {
            tvWatchNow.setText("Watch Now");
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 22) {
            if (tvWatchNow.hasFocus()) {
                tvWatchTrailer.requestFocus();
                tvWatchTrailer.setFocusable(true);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addToFav(String value) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        Dashboard api = retrofit.create(Dashboard.class);
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this);

        Call<Wishlist> call = api.updateWatchList(accessToken, videoId, type, Integer.valueOf(value));
        call.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(@NonNull Call<Wishlist> call, @NonNull Response<Wishlist> response) {
                if (response.code() == 200 && response.body() != null) {


                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        new ToastMsg(DetailsActivityPhando.this).toastIconSuccess(response.body().getMessage());
                        if (response.body().getStatus_code() != null) {
                            if (response.body().getStatus_code().equalsIgnoreCase("1")) {
                                isWatchLater = true;

                                imgWatchList.setText("Remove to Watchlist");
                                //imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else {
                                isWatchLater = false;

                                imgWatchList.setText("Add to Watchlist");


                       /*    imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                            }
                        }
                    } else {
                        new ToastMsg(DetailsActivityPhando.this).toastIconError(response.body().getMessage());
                    }
                } else {
                    new ToastMsg(DetailsActivityPhando.this).toastIconError(getString(R.string.error_toast));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Wishlist> call, @NonNull Throwable t) {
                new ToastMsg(DetailsActivityPhando.this).toastIconError(getString(R.string.error_toast));
                Log.e("DetailsActivityPhando", "onFailure: " + t);

            }
        });

    }


    private void removeFromFav(String type) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);

        Call<FavoriteModel> call = api.removeFromFavorite(Config.API_KEY, userid, videoId, type);
        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull Response<FavoriteModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        new ToastMsg(DetailsActivityPhando.this).toastIconSuccess(response.body().getMessage());
                        if (type.equals(Constants.WishListType.fav)) {
                            isFav = false;
                            imgFavList.setText("Add to Favorite");
                      /*      imgAddFav.setImageResource(R.drawable.ic_bottom_fav);
                            favIv.setImageResource(R.drawable.ic_bottom_fav);*/
                        } else {
                            isWatchLater = false;
                            //kranti
                            imgWatchList.setText("Add to Watchlist");
                            // imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorHint), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    } else {
                        new ToastMsg(DetailsActivityPhando.this).toastIconError(response.body().getMessage());
                        if (type.equals(Constants.WishListType.fav)) {
                            isFav = true;
                            imgFavList.setText("Remove to Favorite");
                    /*        imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        } else {
                            isWatchLater = true;
                            //kranti
                            imgWatchList.setText("Remove to Watchlist");
                            //     imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteModel> call, @NonNull Throwable t) {
                new ToastMsg(DetailsActivityPhando.this).toastIconError(getString(R.string.fetch_error));
            }
        });
    }

    private void getFavStatus(String type) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        Call<FavoriteModel> call = api.verifyFavoriteList(Config.API_KEY, userid, videoId, type);
        activityIndicator(true);
        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull Response<FavoriteModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    activityIndicator(false);
                    if (type.equals(Constants.WishListType.fav)) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            isFav = true;
                            imgFavList.setText("Remove to Favourite");
                   /*         imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        } else {
                            isFav = false;
                            imgFavList.setText("Add to Favourite");
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
                activityIndicator(true);
                new ToastMsg(DetailsActivityPhando.this).toastIconError(getString(R.string.fetch_error));
            }
        });

    }

    private void activityIndicator(boolean show) {
        if (show) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            progress_indicator.setVisibility(View.VISIBLE);
        } else {
            progress_indicator.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }


}
