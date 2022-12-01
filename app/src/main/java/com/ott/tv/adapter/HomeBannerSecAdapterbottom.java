package com.ott.tv.adapter;

import static com.ott.tv.video_service.VideoPlaybackActivity.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityPhando;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;

import java.util.ArrayList;
import java.util.List;

public class HomeBannerSecAdapterbottom extends RecyclerView.Adapter<HomeBannerSecAdapterbottom.ViewHolder> {
    private FeaturesGenreAndMovie listdata;
    private Context context;

    public HomeBannerSecAdapterbottom(FeaturesGenreAndMovie listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    public interface SendInterfaceClickSec {
        void sendclickSec();
    }

    public HomeBannerSecAdapterbottom.SendInterfaceClickSec sendInterfaceClickSec;

    public void setSendInterfaceClick(HomeBannerSecAdapterbottom.SendInterfaceClickSec sendInterfaceClick) {
        this.sendInterfaceClickSec = sendInterfaceClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.banner_home_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public interface SendInterfaceDataBottom {
        void sendDescriptionBottom(Video description);
    }

    public SendInterfaceDataBottom SendInterfaceDataBottom;

    public void setSendInterfacedata(SendInterfaceDataBottom SendInterfaceDataBottom) {
        this.SendInterfaceDataBottom = SendInterfaceDataBottom;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listdata.getName().equalsIgnoreCase("Continue watching")) {
            holder.progressBarMovie.setVisibility(View.VISIBLE);
            int total_runtime;
            int percentageProgressbar;
            int watch_runtime;
            if (listdata.getVideos().get(position) != null) {
                if (listdata.getVideos().get(position).getRuntime_in_minutes() != null) {
                    total_runtime = Integer.parseInt(listdata.getVideos().get(position).getRuntime_in_minutes());
                } else {
                    total_runtime = 0;
                }
                if (listdata.getVideos().get(position).continue_watch_minutes.getLast_watched_at() != null) {
                    watch_runtime = Integer.parseInt(listdata.getVideos().get(position).continue_watch_minutes.getLast_watched_at()) / 60;
                } else {
                    watch_runtime = 100;

                }

                Log.i("continuewatching", watch_runtime + "--totaltime=" + total_runtime);

                if (total_runtime == 0) {
                    percentageProgressbar = 100;
                } else {
                    percentageProgressbar = (Integer) ((watch_runtime / total_runtime) * 100);
                }
                Log.i("continuewatching2", "" + percentageProgressbar);
                holder.progressBarMovie.setProgress(percentageProgressbar);
                holder.progressBarMovie.getProgressDrawable().setColorFilter(
                        Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);


            } else {
                holder.progressBarMovie.setVisibility(View.GONE);
            }
        } else {

        }
        if (true) {
            /*if (position == 5) {
                holder.primary_text.setText(listdata.getVideos().get(position).getTitle());
                if (!TextUtils.isEmpty(listdata.getVideos().get(position).getPosterUrl()))
                    Glide.with(context)
                            .load(R.drawable.movie_1).
                            placeholder(R.drawable.poster_placeholder_land)
                            .error(R.drawable.poster_placeholder_land)
                            .into(holder.main_image);
            } else*/
            if (listdata.getVideos().get(position) != null) {
                holder.title_name.setText(listdata.getVideos().get(position).getTitle());
            }
            if (listdata.getVideos().get(position).getIsPaid().equalsIgnoreCase("1")) {
                holder.premiumIconImage.setVisibility(View.GONE);
            } else {
                holder.premiumIconImage.setVisibility(View.VISIBLE);
            }

            {
                holder.primary_text.setText(listdata.getVideos().get(position).getTitle());
                if (!TextUtils.isEmpty(listdata.getVideos().get(position).getThumbnailUrl())) {
                    Glide.with(context)
                            .load(listdata.getVideos().get(position).getThumbnailUrl()).
                            placeholder(R.drawable.poster_placeholder_land)
                            .error(R.drawable.poster_placeholder_land)
                            .into(holder.main_image);
                } else {
                    Glide.with(context)
                            .load(listdata.getVideos().get(position).getPosterUrl()).
                            placeholder(R.drawable.poster_placeholder_land)
                            .error(R.drawable.poster_placeholder_land)
                            .into(holder.main_image);
                }
            }
            holder.relativeLayout_parent.setOnClickListener(view -> {
                //   Toast.makeText(view.getContext(), "click on item: " + position, Toast.LENGTH_LONG).show();
                detailActivity(listdata.getVideos().get(position));

                if (sendInterfaceClickSec != null) {
                    sendInterfaceClickSec.sendclickSec();
                }
            });
            holder.relativeLayout_parent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (listdata.getVideos().get(position).getTitle() != null) {
                        //setTextViewBanner(myListData.getDescription());
                        Log.i(TAG, "onFocusChange: " + listdata.getVideos().get(position).getTitle());
                        if (SendInterfaceDataBottom != null) {
                            SendInterfaceDataBottom.sendDescriptionBottom(listdata.getVideos().get(position));
                        }
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount2: " + listdata);
       /* if (listdata.getVideos().size() > 6) {
            return 6;
        } else*/
        {
            return listdata.getVideos().size();
        }
    }

    protected void updateCardViewImage(String url) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView main_image, premiumIconImage;
        public TextView primary_text;
        public TextView title_name;
        public ProgressBar progressBarMovie;
        public RelativeLayout relativeLayout_parent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.main_image = (ImageView) itemView.findViewById(R.id.main_image);
            this.premiumIconImage = (ImageView) itemView.findViewById(R.id.premiumIconImage);
            this.primary_text = (TextView) itemView.findViewById(R.id.primary_text);
            this.title_name = (TextView) itemView.findViewById(R.id.title_name);
            this.progressBarMovie = (ProgressBar) itemView.findViewById(R.id.progress_bar_movie);
            relativeLayout_parent = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

    private void detailActivity(Video video) {
        Video videoContent = video;

        {
            if (videoContent.getIsTvseries() != null) {
                if (videoContent.getIsTvseries().equalsIgnoreCase("1")) {
                    Intent intent = new Intent(context, DetailsActivityTvSeries.class);
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, DetailsActivityPhando.class);

                    if (videoContent.getType() != null)
                        intent.putExtra("type", videoContent.getType());
                    if (videoContent.getThumbnailUrl() != null)
                        intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                    if (videoContent.getId() != null)
                        intent.putExtra("video_id", videoContent.getVideosId());
                    if (videoContent.getId() != null)
                        intent.putExtra("id", videoContent.getId().toString());
                    if (videoContent.getVideosId() != null)
                        intent.putExtra("video_id", videoContent.getVideosId().toString());
                    if (videoContent.getTitle() != null)
                        intent.putExtra("title", videoContent.getTitle());
                    if (videoContent.getDescription() != null)
                        intent.putExtra("description", videoContent.getDescription());
                    if (videoContent.getRelease() != null)
                        intent.putExtra("release", videoContent.getRelease());
                    if (videoContent.getRuntime() != null)
                        intent.putExtra("duration", videoContent.getRuntime());
                    if (videoContent.getMaturity_rating() != null)
                        intent.putExtra("maturity_rating", videoContent.getMaturity_rating());
                    if (videoContent.getIs_free() != null)
                        intent.putExtra("ispaid", videoContent.getIs_free().toString());
                    if (videoContent.getLanguage() != null)
                        intent.putExtra("language_str", videoContent.getLanguage());
                    if (videoContent.getIs_live() != null)
                        intent.putExtra("is_live", videoContent.getIs_live().toString());
                    if (videoContent.getRating() != null)
                        intent.putExtra("rating", videoContent.getRating().toString());
                    if (videoContent.getTrailer_aws_source() != null) {
                        intent.putExtra("trailer", videoContent.getTrailer_aws_source());
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } else {
                Intent intent = new Intent(context, DetailsActivityPhando.class);

                if (videoContent.getType() != null)
                    intent.putExtra("type", videoContent.getType());
                if (videoContent.getThumbnailUrl() != null)
                    intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                if (videoContent.getId() != null)
                    intent.putExtra("video_id", videoContent.getId().toString());
                if (videoContent.getTitle() != null)
                    intent.putExtra("title", videoContent.getTitle());
                if (videoContent.getDetail() != null)
                    intent.putExtra("description", videoContent.getDetail());
                if (videoContent.getRelease_date() != null)
                    intent.putExtra("release", videoContent.getRelease_date());
                if (videoContent.getRuntime() != null)
                    intent.putExtra("duration", videoContent.getRuntime());
                if (videoContent.getMaturity_rating() != null)
                    intent.putExtra("maturity_rating", videoContent.getMaturity_rating());
                if (videoContent.getIs_free() != null)
                    intent.putExtra("ispaid", videoContent.getIs_free().toString());
                if (videoContent.getGenre() != null)
                    intent.putExtra("language_str", videoContent.getGenre());
                if (videoContent.getIs_live() != null)
                    intent.putExtra("is_live", videoContent.getIs_live().toString());
                if (videoContent.getRating() != null)
                    intent.putExtra("rating", videoContent.getRating().toString());
                if (videoContent.getTrailer_aws_source() != null) {
                    intent.putExtra("trailer", videoContent.getTrailer_aws_source());
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);




         /*       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/

            }
        }

    }

}
