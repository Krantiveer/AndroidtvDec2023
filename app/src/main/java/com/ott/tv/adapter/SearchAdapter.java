package com.ott.tv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.network.api.ListRecommend;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public interface OnItemClickListenerSearch {
        void onItemClick(ShowWatchlist item);
    }

    private final List<ShowWatchlist> items;
    private final OnItemClickListenerSearch listener;
    private final Context context ;

    public SearchAdapter(List<ShowWatchlist> items, Context context, OnItemClickListenerSearch listener ) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommedded, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override public int getItemCount() {
        return items.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title_name);
            image = (ImageView) itemView.findViewById(R.id.main_image);
        }

        public void bind(final ShowWatchlist item, final OnItemClickListenerSearch listener) {
            name.setText(item.getTitle());

            Glide.with(context)
                    .load(item.getThumbnail())
                    .placeholder(R.drawable.poster_placeholder_land)
                    .error(R.drawable.poster_placeholder_land)
                    .into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}