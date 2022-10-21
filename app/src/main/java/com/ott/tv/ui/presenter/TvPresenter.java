package com.ott.tv.ui.presenter;

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

public class TvPresenter extends Presenter {
    private static int CARD_WIDTH = 300;
    private static int CARD_HEIGHT = 200;
    private static Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d("onCreateViewHolder", "creating viewholder");
        mContext = parent.getContext();
        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.requestLayout();
        //((TextView)cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Video video;
        video = (Video) item;
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(ViewGroup.MarginLayoutParams.MATCH_PARENT, CARD_HEIGHT);

        ((ViewHolder) viewHolder).mCardView.setInfoVisibility(View.GONE);
        ((ViewHolder) viewHolder).updateCardViewImage(video.getPosterUrl());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }


    class ViewHolder extends Presenter.ViewHolder {

        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

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

      /*      Picasso.get()
                    .load(url)
                    .resize(Double.valueOf(CARD_WIDTH * 2.5).intValue(), CARD_HEIGHT * 2)
                    .centerCrop()
                    .error(mDefaultCardImage)
                     .placeholder(R.drawable.poster_placeholder_land)
                    .into(mImageCardViewTarget);
     */       Glide.with(mContext).load(url)
                   /*.override(100,300)*/
                    .apply(new RequestOptions().override(750, 350))
                    .error(mDefaultCardImage)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(mCardView.getMainImageView());

        }

/*
        protected void updateCardViewImage(String url) {



            Glide.with(onCreateViewHolder(getCardView()).view.getContext())
                    .load(url)
                    */
/*.override(100,300)*//*

                    .error(mDefaultCardImage)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(mImageCardViewTarget);


        }
*/


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
