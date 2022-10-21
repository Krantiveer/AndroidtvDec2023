package com.ott.tv.adapter;

import static com.ott.tv.video_service.VideoPlaybackActivity.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;

import java.util.ArrayList;

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.ViewHolder> {
    private ArrayList<Video> listdata;
    private Context context;

    public HomeBannerAdapter(ArrayList<Video> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    public interface SendInterfaceClick {
        void sendclick();
    }
    public SendInterfaceClick sendInterfaceclick;

    public void setSendInterfaceClick(SendInterfaceClick sendInterfaceclick) {
        this.sendInterfaceclick = sendInterfaceclick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.banner_home_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public interface SendInterfacedata {
        void sendDescription(Video description);
    }
    public SendInterfacedata sendInterfacedata;

    public void setSendInterfacedata(SendInterfacedata sendInterfacedata) {
        this.sendInterfacedata = sendInterfacedata;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: " + listdata.get(position).getTitle() + "---->" + position);

     {
         {
                holder.primary_text.setText(listdata.get(position).getTitle());
                if (!TextUtils.isEmpty(listdata.get(position).getImageLink()))
                    Glide.with(context)
                            .load(listdata.get(position).getImageLink()).
                            placeholder(R.drawable.poster_placeholder_land)
                            .error(R.drawable.poster_placeholder_land)
                            .into(holder.main_image);
            }
            holder.relativeLayout.setOnClickListener(view -> {
                detailActivity(listdata.get(position));
                if(sendInterfaceclick!=null){
                    sendInterfaceclick.sendclick();
                }
            });

            holder.relativeLayout.setOnFocusChangeListener((view, b) -> {
                Log.i(TAG, "onFocusChange: " + listdata.get(position).getTitle() +position);

                if (listdata.get(position).getTitle() != null) {
                    if (listdata.get(position).getRelease() == null) {
                        listdata.get(position).setRelease("");
                    }
                    if (sendInterfacedata != null) {
                        sendInterfacedata.sendDescription(listdata.get(position));
                    }
                }
            });
        }
    }

    private void detailActivity(Video video) {
        Video videoContent = video;
        if (videoContent.getActionType() != null) {
            if (videoContent.getActionType().equalsIgnoreCase("tvseries")) {
                Intent intent = new Intent(context, DetailsActivityTvSeries.class);
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
                context.startActivity(intent);

            }else{
                Intent intent = new Intent(context, DetailsActivity.class);
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
                context.startActivity(intent);

            }
        }else {
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
                    context.startActivity(intent);

                } else {
                    Intent intent = new Intent(context, DetailsActivity.class);
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
                    context.startActivity(intent);

                }

            } else {
                Intent intent = new Intent(context, DetailsActivity.class);
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
                context.startActivity(intent);
            }
        }

    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + listdata.size());
  /*      if (listdata.size() > 6) {
            return 6;
        } else */{
            return listdata.size();
        }

    }

    protected void updateCardViewImage(String url) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView main_image;
        public TextView primary_text;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.main_image = (ImageView) itemView.findViewById(R.id.main_image);
            this.primary_text = (TextView) itemView.findViewById(R.id.primary_text);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}