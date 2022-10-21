package com.ott.tv.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.Movie;
import com.ott.tv.ui.views.ImageCardView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CardPresenterMovie extends Presenter {

    private static int CARD_WIDTH = 185;
    private static int CARD_HEIGHT = 278;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private int itemViewType;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private OnSelectionListener onSelectionListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d("onCreateViewHolder", "creating viewholder");
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.transparent);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.bcn_selected_color);

        mContext = parent.getContext();
         ImageCardView cardView = new ImageCardView(parent.getContext(), itemViewType) {
            @Override
            public void setSelected(boolean selected) {
                if(onSelectionListener != null ) {
                    onSelectionListener.onItemSelected(this, selected);
                }
              /*  if (!selected) {
                    ((ImageCardView)this).decreaseSize(this);
                }else{
                    ((ImageCardView)this).increase(this);
                }*/
              //  updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }

        };

/*        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);*/
    /*    updateCardBackgroundColor(cardView, false);*/
        return new ViewHolder(cardView);
        }




       /* cardView.setFocusable(true);

        cardView.setFocusableInTouchMode(true);
        cardView.requestLayout();
        cardView.setInfoVisibility(View.GONE);*/
      //  return new ViewHolder(cardView);


    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Movie video = (Movie) item;
        ((ViewHolder) viewHolder).updateCardViewImage(video.getThumbnailUrl());

    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }


    class ViewHolder extends Presenter.ViewHolder {

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
                        .placeholder(R.drawable.poster_placeholder_land)
                    .error(mDefaultCardImage)
                    .into(mCardView.getImageView());

*/

            Glide.with(onCreateViewHolder(getCardView()).view.getContext())
                    .load(url)
                    /*.override(100,300)*/
                    .error(mDefaultCardImage)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(mCardView.getImageView());


        }
    }

    private static void updateCardBackgroundColor(View view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        view.setBackgroundColor(color);
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
    public interface OnSelectionListener {
        void onItemSelected(View view, boolean selected);
    }

}
