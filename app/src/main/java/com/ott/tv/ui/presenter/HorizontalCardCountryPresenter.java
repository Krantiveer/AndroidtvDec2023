package com.ott.tv.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.ott.tv.R;
import com.ott.tv.model.CountryModel;
import com.ott.tv.model.Genre;
import com.ott.tv.ui.views.ImageCardView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Random;

public class HorizontalCardCountryPresenter extends Presenter {
    private String type;
    private static Context mContext;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    public HorizontalCardCountryPresenter(String type) {
        this.type = type;
    }
    private int itemViewType;
    private CardPresenter.OnSelectionListener onSelectionListener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d("krantiveer","data"+type);
        Log.d("onCreateViewHolder", "creating viewholder");
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.transparent);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.bcn_selected_color);

        mContext = parent.getContext();
        ImageCardView cardView = new ImageCardView(parent.getContext(), 3) {
            @Override
            public void setSelected(boolean selected) {
                if (onSelectionListener != null) {
                    onSelectionListener.onItemSelected(this, selected);
                }
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }

        };
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setInfoVisibility(View.GONE);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {

        CountryModel movie = (CountryModel) item;

      //  ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
       // ((ViewHolder) viewHolder).updateCardViewImage(movie.getPosterUrl());
        int[] colorList2 = {R.drawable.movie_1, R.drawable.movie2, R.drawable.movie_3, R.drawable.movie_4, R.drawable.movie_5, R.drawable.movie2};
        Random r = new Random();
        int index = r.nextInt(colorList2.length -1);
        ((ViewHolder) viewHolder).mCardView.getImageView().setImageResource(colorList2[index]);
        ((ViewHolder) viewHolder).mCardView.getTextPrimeView().setText(movie.getName());


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


    }

    private static void updateCardBackgroundColor(View view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        view.setBackgroundColor(color);
        //here we can change background colour of cardPresenter
        // view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    static class PicassoImageCardViewTarget implements Target {


        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView mImageCardView) {
            this.mImageCardView = mImageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            //  mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            // mImageCardView.setMainImage(errorDrawable);
        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

}

