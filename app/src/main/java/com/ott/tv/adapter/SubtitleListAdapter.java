package com.ott.tv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ott.tv.R;
import com.ott.tv.model.Subtitle;
import com.ott.tv.model.home_content.SubtitleDataNew;

import java.util.ArrayList;
import java.util.List;

public class SubtitleListAdapter extends RecyclerView.Adapter<SubtitleListAdapter.SubtitleViewHolder>{
    private Context context;
    private ArrayList<SubtitleDataNew> subtitleList;


    private OnSubtitleItemClickListener listener;
    private SubtitleViewHolder viewHolder;
//    private var subtitleList: ArrayList<SubtitleDataNew>? = null


    public SubtitleListAdapter(Context context, ArrayList<SubtitleDataNew> subtitleList) {
        this.context = context;
        this.subtitleList = subtitleList;
    }

    @NonNull
    @Override
    public SubtitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_server_tv_item, parent, false);
        return new SubtitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtitleViewHolder holder, int position) {
        SubtitleDataNew subtitle = subtitleList.get(position);
        holder.subtitleNameTv.setText(subtitle.getLanguage());
        holder.subtitleNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onSubtitleItemClick(v, subtitle, position, holder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subtitleList.size();
    }

    public static class SubtitleViewHolder extends RecyclerView.ViewHolder{
        Button subtitleNameTv;

        SubtitleViewHolder(@NonNull View itemView) {
            super(itemView);
            subtitleNameTv = itemView.findViewById(R.id.s_name_tv);
        }
    }

    public interface OnSubtitleItemClickListener {
        void onSubtitleItemClick(View view, SubtitleDataNew subtitle, int position, SubtitleListAdapter.SubtitleViewHolder holder);
    }

    public void setListener(OnSubtitleItemClickListener listener) {
        this.listener = listener;
    }
}
