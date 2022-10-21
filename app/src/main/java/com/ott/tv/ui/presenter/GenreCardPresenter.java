package com.ott.tv.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.ott.tv.R;
import com.ott.tv.model.Genre;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class GenreCardPresenter extends Presenter {

    private static int CARD_WIDTH = 200;
    private static int CARD_HEIGHT = 200;

    private static Context mContext;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.requestLayout();

        ((TextView) cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);
        return new GenreCardPresenter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Genre genre = (Genre) item;
        ((ViewHolder) viewHolder).mCardView.setTitleText(genre.getName());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        ((ViewHolder) viewHolder).updateCardViewImage(genre.getImageUrl());
        ((ViewHolder) viewHolder).mCardView.setBackgroundResource(getColor());

    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }


    class ViewHolder extends Presenter.ViewHolder {

        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private GenreCardPresenter.PicassoImageCardViewTarget mImageCardViewTarget;

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

            Picasso.get()
                    .load(R.drawable.moviessidenav)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .resize(Double.valueOf(CARD_WIDTH * 5).intValue(), CARD_HEIGHT * 5)
                    .error(mDefaultCardImage)
                    .into(mCardView.getMainImageView());

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
    int count;
    private int getColor() {

        //int colorList[] = {R.color.colorPrimary, R.color.blue_400, R.color.indigo_400, R.color.orange_400, R.color.light_green_400, R.color.blue_grey_400};
        int[] colorList2 = {R.drawable.gradient_1, R.drawable.gradient_2, R.drawable.gradient_3, R.drawable.gradient_4, R.drawable.gradient_5, R.drawable.gradient_6};

        if (count >= 6) {
            count = 0;
        }

        int color = colorList2[count];
        count++;

        return color;

    }

}
