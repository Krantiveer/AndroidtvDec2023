package com.ott.tv.adapter;

import static com.ott.tv.video_service.VideoPlaybackActivity.TAG;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ott.tv.R;
import com.ott.tv.model.home_content.FeaturesGenreAndMovie;
import com.ott.tv.model.home_content.Video;

import java.util.List;

public class HomeBannerSecAdapter extends RecyclerView.Adapter<HomeBannerSecAdapter.ViewHolder> {
    private List<FeaturesGenreAndMovie> listdata;
    private Context context;

    public HomeBannerSecAdapter(List<FeaturesGenreAndMovie> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    public interface SendInterfaceClick {
        void sendclick();
    }
    public HomeBannerSecAdapter.SendInterfaceClick sendInterfaceclick;

    public void setSendInterfaceClick(HomeBannerSecAdapter.SendInterfaceClick sendInterfaceclick) {
        this.sendInterfaceclick = sendInterfaceclick;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.banner_home_list_item_movies, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    public interface SendInterfacedata {
        void sendDescription(Video description);
    }

    public HomeBannerAdapter.SendInterfacedata sendInterfacedata;

    public void setSendInterfacedata(HomeBannerAdapter.SendInterfacedata sendInterfacedata) {
        this.sendInterfacedata = sendInterfacedata;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listdata != null) {

            holder.title_name.setText(listdata.get(position).getName());
            HomeBannerSecAdapterbottom homeBannerSecAdapterbottom = new HomeBannerSecAdapterbottom(listdata.get(position), context.getApplicationContext());
            holder.rVBannerBottom.setAdapter(homeBannerSecAdapterbottom);
            // Here we have assigned the layout
            // as LinearLayout with vertical orientation
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(
                    holder.rVBannerBottom
                            .getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false);

            HomeBannerSecAdapterbottom adapter = new HomeBannerSecAdapterbottom(listdata.get(position), context.getApplicationContext());
            Log.i(TAG, "onBindViewHolder: "+listdata.get(position).getName());
            adapter.setSendInterfacedata(new HomeBannerSecAdapterbottom.SendInterfaceDataBottom() {
                @Override
                public void sendDescriptionBottom(Video description) {
                    Log.i(TAG, "sendDescriptionBottom: " + description.getTitle() + description.getDescription());
                    if (sendInterfacedata != null) {
                        sendInterfacedata.sendDescription(description);
                    }
                }
            });
            adapter.setSendInterfaceClick(new HomeBannerSecAdapterbottom.SendInterfaceClickSec() {
                @Override
                public void sendclickSec() {
                    if(sendInterfaceclick!=null){
                        sendInterfaceclick.sendclick();
                    }
                }
            });
            holder.rVBannerBottom.setAdapter(adapter);

           /* holder.relativeLayout.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "click on item: " + position, Toast.LENGTH_LONG).show();

            });*/

        }
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + listdata.size());
        {
            return listdata.size();
        }

    }

    protected void updateCardViewImage(String url) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rVBannerBottom;
        public ImageView main_image;
        public TextView primary_text, title_name;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.main_image = (ImageView) itemView.findViewById(R.id.main_image);
            this.primary_text = (TextView) itemView.findViewById(R.id.primary_text);
            this.title_name = itemView.findViewById(R.id.title_name);
            rVBannerBottom = itemView.findViewById(R.id.rVBannerBottom);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.banner_LinearLayout);
        }
    }


}