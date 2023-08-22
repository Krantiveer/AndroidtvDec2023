package com.ott.tv.adapter;

import static com.ott.tv.video_service.VideoPlaybackActivity.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.ott.tv.Config;
import com.ott.tv.R;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.CountryModel;
import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityPhando;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;
import com.ott.tv.ui.activity.ItemCountryActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeBannerSecAdapterbottom extends RecyclerView.Adapter<HomeBannerSecAdapterbottom.ViewHolder> {
    private BrowseData listdata;
    private Context context;

    public HomeBannerSecAdapterbottom(BrowseData listdata, Context context) {
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
        void sendDescriptionBottom(LatestMovieList description);
    }

    public SendInterfaceDataBottom SendInterfaceDataBottom;

    public void setSendInterfacedata(SendInterfaceDataBottom SendInterfaceDataBottom) {
        this.SendInterfaceDataBottom = SendInterfaceDataBottom;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (listdata.getTitle().equalsIgnoreCase("Continue watching")) {
            holder.progressBarMovie.setVisibility(View.VISIBLE);
            int total_runtime;
            int percentageProgressbar;
            int watch_runtime;
            if (listdata.getList().get(position) != null) {
                if (listdata.getList().get(position).getRuntime_in_minutes() != null) {
                    total_runtime = Integer.parseInt(listdata.getList().get(position).getRuntime_in_minutes());
                } else {
                    total_runtime = 0;
                }
                if (listdata.getList().get(position).continue_watch_minutes.getLast_watched_at() != null) {
                    watch_runtime = Integer.parseInt(listdata.getList().get(position).continue_watch_minutes.getLast_watched_at()) / 60;
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
            if (listdata.getList().get(position) != null) {
                holder.title_name.setText(listdata.getList().get(position).getTitle());
            }

         /*   if (listdata.getList().get(position).getIsPaid()!=null) {
            if (listdata.getList().get(position).getIsPaid().equalsIgnoreCase("0")) {
                holder.premiumIconImage.setVisibility(View.GONE);
            } else {
                holder.premiumIconImage.setVisibility(View.VISIBLE);
            }}*/
            if (listdata.getList().get(position).getIs_free() != null) {
                if (listdata.getList().get(position).getIs_free().toString().equalsIgnoreCase("1")) {
                    holder.premiumIconImage.setVisibility(View.GONE);
                } else {
                    holder.premiumIconImage.setVisibility(View.VISIBLE);

                }
            }

            holder.primary_text.setText(listdata.getList().get(position).getTitle());
            if (!TextUtils.isEmpty(listdata.getList().get(position).getThumbnail())) {
                Glide.with(context)
                        .load(listdata.getList().get(position).getThumbnail()).
                        placeholder(R.drawable.poster_placeholder_land)
                        .error(R.drawable.poster_placeholder_land)
                        .into(holder.main_image);
            } else {
                Glide.with(context)
                        .load(listdata.getList().get(position).getPosterUrl()).
                        placeholder(R.drawable.poster_placeholder_land)
                        .error(R.drawable.poster_placeholder_land)
                        .into(holder.main_image);
            }

            holder.relativeLayout_parent.setOnClickListener(view -> {
                //   Toast.makeText(view.getContext(), "click on item: " + position, Toast.LENGTH_LONG).show();
                listdata.getList().get(position).setViewallName(listdata.getTitle());
                detailActivity(listdata.getList().get(position));
                if (sendInterfaceClickSec != null) {
                    sendInterfaceClickSec.sendclickSec();
                }
            });
            holder.relativeLayout_parent.setOnFocusChangeListener((view, b) -> {
                if (listdata.getList().get(position).getTitle() != null) {
                    //setTextViewBanner(myListData.getDescription());
                    Log.i(TAG, "onFocusChange: " + listdata.getList().get(position).getTitle());
                    if (SendInterfaceDataBottom != null) {
                        SendInterfaceDataBottom.sendDescriptionBottom(listdata.getList().get(position));
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount2: " + listdata);
       /* if (listdata.getList().size() > 6) {
            return 6;
        } else*/
        {
            return listdata.getList().size();
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

    private void detailActivity(LatestMovieList video) {
        LatestMovieList videoContent = video;

        if (videoContent.getType() == null) {
            videoContent.setType("M");
        }
        {
            if (videoContent.getType().equalsIgnoreCase("GENRE") || videoContent.getType().equalsIgnoreCase("VM")) {
                Intent intent = new Intent(context, ItemCountryActivity.class);
                intent.putExtra("id", video.getId().toString());

                intent.putExtra("title", videoContent.getViewallName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
            if (videoContent.getType().equalsIgnoreCase("OTT")) {
                if (video.getIs_subscribed() != null) {
                    if (video.getIs_subscribed().toString().equalsIgnoreCase("0")) {
                        //  CMHelper.setSnackBar(context, "Enjoy Premium Content Watch anything without ads Watch  Please Subscribe Or Rent  from MOBILE APP | WEBSITE -" + Config.WebsiteURL, 1, 10000);
                        // CMHelper.setSnackBar(,  "Enjoy Premium Content Watch anything without ads Watch  Please Subscribe Or Rent  from MOBILE APP | WEBSITE -" + Config.WebsiteURL, 2);
                        Toast.makeText(
                                context,
                                "Unlock Exclusive Content By Subscribing Today from MOBILE APP | WEBSITE -" + Config.WebsiteURL,
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }
                }

                //   Intent intent = new Intent(context, ItemCountryActivity.class);
                if (video.getAndroid_tv_link() != null) {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                            video.getAndroid_tv_link().substring(video.getAndroid_tv_link().lastIndexOf("=") + 1)
                    );

                    if (intent == null) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(video.getAndroid_tv_link()));
                    }

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                            video.getAndroid_link().substring(video.getAndroid_link().lastIndexOf("=") + 1)
                    );

                    if (intent == null) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(video.getAndroid_link()));
                    }

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }

            if (videoContent.getType().equals("M") && videoContent.getIs_live().toString().equalsIgnoreCase("0")) {
                Intent intent = new Intent(context, DetailsActivityPhando.class);
                if (videoContent.getType() != null)
                    intent.putExtra("type", videoContent.getType());
                if (videoContent.getThumbnail() != null) {
                    intent.putExtra("thumbImage", videoContent.getThumbnail());
                } else {
                    if (videoContent.getThumbnailUrl() != null)
                        intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                }
                if (videoContent.getId() != null) {
                    intent.putExtra("video_id", videoContent.getId().toString());
                } else {
                    if (videoContent.getId() != null)
                        intent.putExtra("video_id", videoContent.getId().toString());
                }
                if (videoContent.getTitle() != null)
                    intent.putExtra("title", videoContent.getTitle());
                if (videoContent.getDetail() != null) {
                    intent.putExtra("description", videoContent.getDetail());
                } else {
                    if (videoContent.getDescription() != null)
                        intent.putExtra("description", videoContent.getDescription());
                }
                if (videoContent.getRelease_date() != null) {
                    intent.putExtra("release", videoContent.getRelease_date());
                } else {
                    if (videoContent.getRelease() != null) {
                        intent.putExtra("release", videoContent.getRelease());
                    }
                }
             /*   if (videoContent.getRuntime() != null)
                    intent.putExtra("duration", videoContent.getRuntime());*/

                if (videoContent.getDuration_str() != null)
                    intent.putExtra("duration", videoContent.getDuration_str());

                if (videoContent.getMaturity_rating() != null)
                    intent.putExtra("maturity_rating", videoContent.getMaturity_rating());
                if (videoContent.getIs_free() != null)
                    intent.putExtra("ispaid", videoContent.getIs_free().toString());
                if (videoContent.getLanguage_str() != null)
                    intent.putExtra("language_str", videoContent.getLanguage_str());
                if (videoContent.getIs_live() != null)
                    intent.putExtra("is_live", videoContent.getIs_live().toString());
                if (videoContent.getRating() != null)
                    intent.putExtra("rating", videoContent.getRating().toString());
                if (videoContent.getTrailers() != null && videoContent.getTrailers().size() > 0 && videoContent.getTrailers().get(0) != null && videoContent.getTrailers().get(0).getMedia_url() != null) {
                    intent.putExtra("trailer", videoContent.getThumbnailUrl());
                }
                if (videoContent.getTrailer_aws_source() != null) {
                    intent.putExtra("trailer", videoContent.getTrailer_aws_source());
                }


                if (videoContent.getGenres() != null) {
                    if (videoContent.getGenres().size() != 0) {
                        if (videoContent.getGenres().size() > 0) {
                            String genres;
                            genres = videoContent.getGenres().get(0);
                            for (int i = 1; i < videoContent.getGenres().size(); i++) {
                                genres = genres.concat("," + videoContent.getGenres().get(i));
                            }
                            intent.putExtra("genres", genres);
                        }
                    }
                } else {
                    if (videoContent.getGenre() != null) {
                        intent.putExtra("genres", videoContent.getGenre());
                    }
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
            if (videoContent.getType().equals("T")) {
                Intent intent = new Intent(context, DetailsActivityPhando.class);
                if (videoContent.getType() != null)
                    intent.putExtra("type", videoContent.getType());
                if (videoContent.getThumbnail() != null) {
                    intent.putExtra("thumbImage", videoContent.getThumbnail());
                } else {
                    if (videoContent.getThumbnailUrl() != null) {
                        intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                    }
                }

                if (videoContent.getId() != null) {
                    intent.putExtra("video_id", videoContent.getId().toString());
                }
                if (videoContent.getTitle() != null)
                    intent.putExtra("title", videoContent.getTitle());
                if (videoContent.getDetail() != null) {
                    intent.putExtra("description", videoContent.getDetail());
                } else {
                    if (videoContent.getDescription() != null)
                        intent.putExtra("description", videoContent.getDescription());
                }
                if (videoContent.getRelease_date() != null) {
                    intent.putExtra("release", videoContent.getRelease_date());
                } else {
                    if (videoContent.getRelease() != null) {
                        intent.putExtra("release", videoContent.getRelease());
                    }
                }
                if (videoContent.getDuration_str() != null)
                    intent.putExtra("duration", videoContent.getDuration_str());
                if (videoContent.getMaturity_rating() != null)
                    intent.putExtra("maturity_rating", videoContent.getMaturity_rating());
                if (videoContent.getIs_free() != null)
                    intent.putExtra("ispaid", videoContent.getIs_free().toString());
                if (videoContent.getLanguage_str() != null)
                    intent.putExtra("language_str", videoContent.getLanguage_str());
                if (videoContent.getIs_live() != null)
                    intent.putExtra("is_live", videoContent.getIs_live().toString());
                if (videoContent.getRating() != null)
                    intent.putExtra("rating", videoContent.getRating().toString());
                if (videoContent.getTrailers() != null && videoContent.getTrailers().size() > 0 && videoContent.getTrailers().get(0) != null && videoContent.getTrailers().get(0).getMedia_url() != null) {
                    intent.putExtra("trailer", videoContent.getTrailers().get(0).getMedia_url());
                }
                if (videoContent.getTrailer_aws_source() != null) {
                    intent.putExtra("trailer", videoContent.getTrailer_aws_source());
                }


                if (videoContent.getGenres() != null) {
                    if (videoContent.getGenres().size() > 0) {
                        String genres;
                        genres = videoContent.getGenres().get(0);
                        for (int i = 1; i < videoContent.getGenres().size(); i++) {
                            genres = genres.concat("," + videoContent.getGenres().get(i));
                        }
                        intent.putExtra("genres", genres);
                    }
                } else {
                    if (videoContent.getGenre() != null) {
                        intent.putExtra("genres", videoContent.getGenre());
                    }
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);


            }
            if (videoContent.getType().equals("M") && videoContent.getIs_live().toString().equalsIgnoreCase("1")) {
                Intent intent = new Intent(context, DetailsActivityPhando.class);
                if (videoContent.getType() != null)
                    intent.putExtra("type", videoContent.getType());
                if (videoContent.getThumbnail() != null) {
                    intent.putExtra("thumbImage", videoContent.getThumbnail());
                } else {
                    if (videoContent.getThumbnailUrl() != null) {
                        intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                    }
                }
                if (videoContent.getId() != null) {
                    intent.putExtra("video_id", videoContent.getId().toString());
                }
                if (videoContent.getTitle() != null)
                    intent.putExtra("title", videoContent.getTitle());
                if (videoContent.getDetail() != null) {
                    intent.putExtra("description", videoContent.getDetail());
                } else {
                    if (videoContent.getDescription() != null)
                        intent.putExtra("description", videoContent.getDescription());
                }
                if (videoContent.getRelease_date() != null) {
                    intent.putExtra("release", videoContent.getRelease_date());
                } else {
                    if (videoContent.getRelease() != null) {
                        intent.putExtra("release", videoContent.getRelease());
                    }
                }
                if (videoContent.getDuration_str() != null)
                    intent.putExtra("duration", videoContent.getDuration_str());
                if (videoContent.getMaturity_rating() != null)
                    intent.putExtra("maturity_rating", videoContent.getMaturity_rating());
                if (videoContent.getIs_free() != null)
                    intent.putExtra("ispaid", videoContent.getIs_free().toString());
                if (videoContent.getLanguage_str() != null)
                    intent.putExtra("language_str", videoContent.getLanguage_str());
                if (videoContent.getIs_live() != null)
                    intent.putExtra("is_live", videoContent.getIs_live().toString());
                if (videoContent.getRating() != null)
                    intent.putExtra("rating", videoContent.getRating().toString());
                if (videoContent.getTrailers() != null && videoContent.getTrailers().size() > 0 && videoContent.getTrailers().get(0) != null && videoContent.getTrailers().get(0).getMedia_url() != null) {
                    intent.putExtra("trailer", videoContent.getTrailers().get(0).getMedia_url());
                }
                if (videoContent.getTrailer_aws_source() != null) {
                    intent.putExtra("trailer", videoContent.getTrailer_aws_source());
                }

                if (videoContent.getGenres() != null) {
                    if (videoContent.getGenres().size() > 0) {
                        String genres;
                        genres = videoContent.getGenres().get(0);
                        for (int i = 1; i < videoContent.getGenres().size(); i++) {
                            genres = genres.concat("," + videoContent.getGenres().get(i));
                        }
                        intent.putExtra("genres", genres);
                    }
                } else {
                    if (videoContent.getGenre() != null) {
                        intent.putExtra("genres", videoContent.getGenre());
                    }
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }


        }
    }

}

