package com.ott.tv.ui.activity;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

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
import com.ott.tv.model.MovieSingleDetails;


import com.ott.tv.model.Video;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.DetailsApi;
import com.ott.tv.network.api.FavouriteApi;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;
import com.ott.tv.video_service.PlaybackModel;
import com.ott.tv.video_service.VideoPlaybackActivity;
import com.romainpiel.shimmer.ShimmerTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends FragmentActivity {

    private String videoId;
    private String id;
    private String thumbUrl;
    private String poster_url;
    private ImageView bannerImageView;
    private ImageView thumbnail_image;
    private FrameLayout contentView;


    private TextView tvReleaseDate;
    private TextView tvDurationTime;
    private ImageView tvVideoQualityType;

    private boolean isWatchLater = false;
    private boolean isFav = false;
    private ImageView favIv;
    private AppCompatButton tvWatchNow;
    private AppCompatButton tvWatchTrailer;
    private AppCompatButton imgWatchList;
    private AppCompatButton imgFavList;
    private MovieSingleDetails singleDetails = null;
    private final List<CommonModels> listRelated = new ArrayList<>();
    String userid = null;
    String tvDurationTime_value;
    String tvVideoQuality;
    PlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady = true;
    int current_Window = 0;
    long playbackPosition = 0;
    private ShimmerTextView premiumIconImage;

    final private ArrayList<CustomAddsModel> customAddsModelArrayList = new ArrayList<>();

    private ArrayObjectAdapter mAdapter;
    private RecyclerView rvRelated;
    private HomePageAdapter relatedAdapter;
    private RelativeLayout activity_rv;
    private ProgressBar progress_indicator;


    public DetailsActivity() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String type = "movie";
        videoId = this.getIntent().getStringExtra("video_id");
        id = this.getIntent().getStringExtra("id");
        thumbUrl = this.getIntent().getStringExtra("thumbImage");
        poster_url = this.getIntent().getStringExtra("poster_url");
        String title = this.getIntent().getStringExtra("title");
        String description = this.getIntent().getStringExtra("description");
        tvDurationTime_value = this.getIntent().getStringExtra("duration");
        tvVideoQuality = this.getIntent().getStringExtra("video_quality");
        String tvispaid = this.getIntent().getStringExtra("ispaid");
        contentView = findViewById(R.id.contentView);

        thumbnail_image = findViewById(R.id.thumbnail_image);
        premiumIconImage = findViewById(R.id.premiumIcon);


        if (tvispaid != null) {
            if (tvispaid.equalsIgnoreCase("1"))
                premiumIconImage.setVisibility(VISIBLE);
        }
        updateBackgroundThumnail(thumbUrl);
        bannerImageView = findViewById(R.id.bannerImageView);
        setBannerImage(poster_url);

        TextView movie_title = findViewById(R.id.movie_title);
        movie_title.setText(title);
        TextView movie_description_tv = findViewById(R.id.movie_description_tv);
        movie_description_tv.setText(description);

        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvDurationTime = findViewById(R.id.duration_time);
        tvVideoQualityType = findViewById(R.id.tvVideoQualityType);
        tvWatchNow = findViewById(R.id.tvWatchNow);
        tvWatchTrailer = findViewById(R.id.tvWatchTrailer);
        rvRelated = findViewById(R.id.rv_related);
        playerView = findViewById(R.id.video_view);
        activity_rv = findViewById(R.id.activity_rv);
        imgWatchList = findViewById(R.id.imgWatchList);
        imgFavList = findViewById(R.id.imgFavList);
        progress_indicator = findViewById(R.id.progress_indicator);
        if (type.equals("movie")) {
            if (videoId != null) {
                getData(type, videoId);
            } else {
                getData(type, id);
            }
        }

        imgWatchList.setOnClickListener(v -> {
            if (isWatchLater) {
                removeFromFav(Constants.WishListType.watch_later);
            } else {
                addToFav(Constants.WishListType.watch_later);
            }
        });

        imgFavList.setOnClickListener(v -> {
            if (isFav) {
                removeFromFav(Constants.WishListType.fav);
            } else {
                addToFav(Constants.WishListType.fav);
            }
        });
        getFavStatus(Constants.WishListType.watch_later);
        getFavStatus(Constants.WishListType.fav);
        tvWatchNow.setOnClickListener(v -> payAndWatchTV());
        tvWatchTrailer.setOnClickListener(view -> watchNowClick());
        PreferenceUtils.updateSubscriptionStatus(DetailsActivity.this);
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
                .placeholder(R.color.black_color)
                .error(R.drawable.poster_placeholder_land)
                .into(bannerImageView);
    }

    public void updateBackgroundThumnail(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.color.black_color)
                .error(R.drawable.poster_placeholder_land)
                .into(thumbnail_image);
    }

    private void payAndWatchTV() {
        if (singleDetails != null) {
            List<Video> videoList = new ArrayList<>();
            for (Video video : singleDetails.getVideos()) {
                if (video.getFileType() != null && !video.getFileType().equalsIgnoreCase("embed")) {
                    videoList.add(video);
                }
            }
            if (tvWatchNow.getText() == null || !tvWatchNow.getText().toString().equalsIgnoreCase("Watch Now")) {
                CMHelper.setSnackBar(this.getCurrentFocus(), "Hey, Please upgrade your account from MOBILE APP | WEBSITE - UVTV " , 1, 10000);
                return;
            } else if (videoList.isEmpty()) {
                CMHelper.setSnackBar(this.getCurrentFocus(), "We are sorry, Video not available for your selected content", 2);
                return;
            }/*
            PlaybackModel video = new PlaybackModel();
            ArrayList<Video> videoListForIntent = new ArrayList<>(videoList);
            video.setVideoList(videoListForIntent);
            VideoNew mSelectedVideo = new VideoNew(
                    Long.parseLong(videoId),
                    "movie",
                    singleDetails.getTitle(),
                    singleDetails.getDescription(),
                    videoListForIntent.get(0).getFileUrl(),
                    poster_url,
                    thumbUrl,
                    poster_url);
            Intent intent = new Intent(this, PlaybackActivityNew.class);
            intent.putExtra(VideoDetailsActivity.VIDEO, mSelectedVideo);
            startActivity(intent);*/
            PlaybackModel video = new PlaybackModel();
            ArrayList<Video> videoListForIntent = new ArrayList<>(videoList);
            video.setId(Long.parseLong(videoId));
            video.setTitle(singleDetails.getTitle());
            video.setDescription(singleDetails.getDescription());
            video.setVideoType("mp4");
            video.setCategory("movie");
            video.setVideoUrl(videoListForIntent.get(0).getFileUrl());
            video.setCardImageUrl(poster_url);
            video.setIstrailer(false);
            video.setBgImageUrl(thumbUrl);
            video.setIsPaid("paid");
            video.setVideo(singleDetails.getVideos().get(0));
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, video);
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
        /* Note this player used for both youtube and mp3
           VideoNew mSelectedVideo = new VideoNew(
                    Long.parseLong(videoId),
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
*/
            PlaybackModel model = new PlaybackModel();
            model.setId(Long.parseLong(videoId));
            model.setTitle(singleDetails.getTitle());
            model.setDescription(singleDetails.getDescription());
            model.setVideoType("mp4");
            model.setCategory("movie");
            model.setVideoUrl(trailerURL);
            model.setCardImageUrl(poster_url);
            model.setBgImageUrl(thumbUrl);
            model.setIsPaid("paid");
            model.setIstrailer(true);
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, model);
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
        PreferenceUtils.getInstance().getUsersIdActionOTT(this);
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
/*                    //----related post---------------
                    for (int i = 0; i < singleDetails.getRelatedMovie().size(); i++) {
                        RelatedMovie relatedMovie = singleDetails.getRelatedMovie().get(i);
                        CommonModels models = new CommonModels();
                        models.setTitle(relatedMovie.getTitle());
                        models.setImageUrl(relatedMovie.getThumbnailUrl());
                        models.setId(relatedMovie.getVideosId());
                        models.setVideoType("movie");
                        models.setIsPaid(relatedMovie.getIsPaid());
                        models.setIsPaid(relatedMovie.getIsPaid());
                        models.setVideo_view_type(relatedMovie.getVideo_view_type());
                        listRelated.add(models);
                    }
                    relatedAdapter = new HomePageAdapter(getBaseContext(), listRelated, "");
                    rvRelated.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL,
                            false));
                    rvRelated.setHasFixedSize(false);
                    rvRelated.setAdapter(relatedAdapter);
*/
                    setMovieData();
                } else {
                    CMHelper.setSnackBar(DetailsActivity.this.getCurrentFocus(), "We are sorry, This video content not available, Please try another", 2);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieSingleDetails> call, @NonNull Throwable t) {
                activityIndicator(false);
                CMHelper.setSnackBar(DetailsActivity.this.getCurrentFocus(), "We are sorry, This video content not available, Please try another", 2);
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
        if (singleDetails.video_view_type != null && singleDetails.video_view_type.equalsIgnoreCase("Subscription Based")) {
            if (PreferenceUtils.isActivePlan(DetailsActivity.this)) {
                if (PreferenceUtils.isValid(DetailsActivity.this)) {
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

    private void addToFav(String type) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        Call<FavoriteModel> call = api.addToFavorite(Config.API_KEY, userid, videoId, type);
        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull retrofit2.Response<FavoriteModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        new ToastMsg(DetailsActivity.this).toastIconSuccess(response.body().getMessage());
                        if (type.equals(Constants.WishListType.watch_later)) {
                            isWatchLater = true;
                            //kranti
                            imgWatchList.setText("Remove to Watchlist");
                            //imgWatchList.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorGold), android.graphics.PorterDuff.Mode.SRC_IN);
                        } else {
                            isFav = true;
                            imgFavList.setText("Remove to Favorite");

                       /*     imgAddFav.setImageResource(R.drawable.ic_circle_fav);
                            favIv.setImageResource(R.drawable.ic_circle_fav);*/
                        }
                    } else {
                        new ToastMsg(DetailsActivity.this).toastIconError(response.body().getMessage());
                    }
                } else {
                    new ToastMsg(DetailsActivity.this).toastIconError(getString(R.string.error_toast));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteModel> call, @NonNull Throwable t) {
                new ToastMsg(DetailsActivity.this).toastIconError(getString(R.string.error_toast));

            }
        });

    }


    private void removeFromFav(String type) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);

        Call<FavoriteModel> call = api.removeFromFavorite(Config.API_KEY, userid, videoId, type);
        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull retrofit2.Response<FavoriteModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        new ToastMsg(DetailsActivity.this).toastIconSuccess(response.body().getMessage());
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
                        new ToastMsg(DetailsActivity.this).toastIconError(response.body().getMessage());
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
                new ToastMsg(DetailsActivity.this).toastIconError(getString(R.string.fetch_error));
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
            public void onResponse(@NonNull Call<FavoriteModel> call, @NonNull retrofit2.Response<FavoriteModel> response) {
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
                new ToastMsg(DetailsActivity.this).toastIconError(getString(R.string.fetch_error));
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
