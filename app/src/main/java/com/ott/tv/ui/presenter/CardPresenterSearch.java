package com.ott.tv.ui.presenter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.leanback.widget.BaseCardView;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.phando.ShowWatchlist;


/**
 * Created by Vikas Kumar Singh on 19/04/20.
 */
public class CardPresenterSearch extends Presenter {

    public AppImageCardView cardView;
    private static int sDefaultBackgroundColor;
    private static int sSelectedBackgroundColor;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.transparent);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.bcn_selected_color);
        cardView = new AppImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };
        cardView.setMainImageScaleType(ImageView.ScaleType.FIT_XY);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setCardType(BaseCardView.CARD_TYPE_MAIN_ONLY);
        cardView.setElevation(0);
        cardView.setBackgroundColor(parent.getResources().getColor(android.R.color.transparent));
        cardView.getBackground().setAlpha(0);
        return new ViewHolder(cardView);
    }

    private void updateCardBackgroundColor(AppImageCardView imageCardView, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        imageCardView.setBackgroundColor(color);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ShowWatchlist contentList = (ShowWatchlist) item;
        AppImageCardView cardView = (AppImageCardView) viewHolder.view;
        cardView.setTitleText(contentList.getTitle());
        cardView.setContentText(String.valueOf(contentList.getIs_free()));
        Glide.with(viewHolder.view.getContext())
                .load(contentList.getThumbnail())
                .into(cardView.getMainImageView());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

}