package com.ott.tv.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ott.tv.R;
import com.ott.tv.model.home_content.Video;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class SliderCardPresenter extends Presenter {
    private static final int CARD_WIDTH = 1000;
    private static final int CARD_HEIGHT = 431;
    private CardPresenter.OnSelectionListener onSelectionListener;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private static Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d("onCreateViewHolder", "creating viewholder");
        mContext = parent.getContext();
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.transparent);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.bcn_selected_color);
     /*   ImageCardView cardView = new ImageCardView(mContext);*/

       ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                if(onSelectionListener != null ) {
                    onSelectionListener.onItemSelected(this, selected);
                }
            /*    if (!selected) {
                    ((ImageCardView)this).decreaseSize(this);
                }else{
                    ((ImageCardView)this).increase(this);
                }*/
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }

        };
       // cardView.setFocusable(true);

       // cardView.setInfoAreaBackgroundColor(0xFFDD1A);
       // cardView.setFocusableInTouchMode(true);
        //cardView.requestLayout();
        //((TextView)cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Video video;
        video = (Video) item;
        ((ViewHolder) viewHolder).mCardView.setTitleText(video.getTitle());
        ((ViewHolder) viewHolder).mCardView.setContentText(video.getDescription());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        ((ViewHolder) viewHolder).mCardView.setInfoVisibility(View.GONE);
        ((ViewHolder) viewHolder).updateCardViewImage(video.getPosterUrl());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    private static void updateCardBackgroundColor(View view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        view.setBackgroundColor(color);
        //here we can change background colour of cardPresenter
        // view.findViewById(R.id.info_field).setBackgroundColor(color);
    }
    static class ViewHolder extends Presenter.ViewHolder {

        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        @SuppressLint("UseCompatLoadingForDrawables")
        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext
                    .getResources()
                    .getDrawable(R.drawable.logo);
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        protected void updateCardViewImage(String url) {
         /*   Picasso.get()
                    .load(url)
                    .resize(Double.valueOf(CARD_WIDTH * 2.5).intValue(), CARD_HEIGHT * 5)
                   *//* .centerCrop()*//*
                    .centerInside()
                    .placeholder(R.drawable.poster_placeholder_land)
                    .error(mDefaultCardImage)
                    .into(mImageCardViewTarget);*/

            Glide.with(mContext).load(url)
                    .apply(new RequestOptions().override(2500, 800))
                    .error(mDefaultCardImage)
                    .fitCenter()
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(mCardView.getMainImageView());
            /*Glide.with(this).load(url).into(mm);*/

        }
    }


    static class PicassoImageCardViewTarget implements Target {


        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView mImageCardView) {
            this.mImageCardView = mImageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            mImageCardView.setMainImage(errorDrawable);
        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
