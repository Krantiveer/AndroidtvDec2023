package com.ott.tv.ui.presenter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ott.tv.video_service.VideoPlaybackActivity.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.ui.views.ImageCardView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class HorizontalCardPresenterNew extends Presenter {
    private final String type;
    private static Context mContext;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;

    public HorizontalCardPresenterNew(String type) {
        this.type = type;
    }

    private int itemViewType=0;
    private CardPresenter.OnSelectionListener onSelectionListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d("krantiveer", "data" + type);
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.transparent);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.bcn_selected_color);

        mContext = parent.getContext();
        ImageCardView cardView = new ImageCardView(parent.getContext(), itemViewType) {
            @Override
            public void setSelected(boolean selected) {
                if (onSelectionListener != null) {
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
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setInfoVisibility(View.GONE);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {

        LatestMovieList movie = (LatestMovieList) item;
        //  ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        ((ViewHolder) viewHolder).updateCardViewImage(movie.getThumbnail());

        ((ViewHolder) viewHolder).mCardView.getTextPrimeView();
        if(movie.getIs_free()!=null){
        if (movie.getIs_free().toString().equalsIgnoreCase("0")) {
            ((ViewHolder) viewHolder).mCardView.getPrimeImageView().setVisibility(VISIBLE);
        } else {
            ((ViewHolder) viewHolder).mCardView.getPrimeImageView().setVisibility(GONE);
        }}else{
            ((ViewHolder) viewHolder).mCardView.getPrimeImageView().setVisibility(GONE);

        }
        if(movie.getTitle()!=null){
            ((ViewHolder) viewHolder).mCardView.getTextTitle().setText(movie.getTitle());
        }

        if (movie.getViewallTitle().equalsIgnoreCase("Continue Watching")) {
            Log.i(TAG, "onBindViewHolder: lovemost"+movie.getViewallTitle());

            ((ViewHolder) viewHolder).mCardView.getProgressBar().setVisibility(VISIBLE);


            int total_runtime;
            int percentageProgressbar;
            int watch_runtime;
            if (movie != null) {
                if (movie.getDuration() != null) {
                    total_runtime = movie.getDuration();
                    total_runtime=total_runtime/60;
                } else {
                    total_runtime = 0;
                }

                if (movie.getLast_watch_time() != null ) {
                    if (movie.getLast_watch_time() !=null) {
                        watch_runtime = Integer.parseInt(movie.getLast_watch_time().toString()) / 60;
                    } else {
                        watch_runtime = 100;

                    }
                    Log.i("continuewatching", watch_runtime + "--totaltime=" + total_runtime);


                    if (total_runtime == 0) {
                        percentageProgressbar = 100;
                    } else {
                        //    percentageProgressbar = (Integer) ((watch_runtime / total_runtime) * 100);
                        percentageProgressbar = (int) ((watch_runtime * 100.0) / total_runtime);

                    }
                    Log.i("continuewatching2", "" + percentageProgressbar);
                    ((ViewHolder) viewHolder).mCardView.getProgressBar().setProgress(percentageProgressbar);
                    ((ViewHolder) viewHolder).mCardView.getProgressBar().getProgressDrawable().setColorFilter(
                                    Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                   // holder.progressBarMovie.setProgress(percentageProgressbar);
        /*  holder.progressBarMovie.getProgressDrawable().setColorFilter(
                            Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
       */
         }
            }
        }else{
            ((ViewHolder) viewHolder).mCardView.getProgressBar().setVisibility(GONE);

        }




 /*       if (movie.getIs_free().toString().equalsIgnoreCase("1")) {
            ((ViewHolder) viewHolder).mCardView.getTextPrimeView().setVisibility(GONE);
        } else {
            ((ViewHolder) viewHolder).mCardView.getTextPrimeView().setVisibility(VISIBLE);
        }
        if (type.equalsIgnoreCase("Pay And Watch")) {
            ((ViewHolder) viewHolder).mCardView.getTextPrimeView().setText("Pay And Watch");
            *//*        Note change text backgroud color
             *//*        //    ((ViewHolder) viewHolder).mCardView.getTextPrimeView().setBackgroundResource(R.drawable.premium_paywatchbg);

        }*/
       /* if (type.equals(TvSeriesFragment.TV_SERIES)) {
            Movie movie = (Movie) item;
           // ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            ((ViewHolder) viewHolder).updateCardViewImage(movie.getThumbnailUrl());
        } else if (type.equals(MoviesFragment.MOVIE)) {

        } else if (type.equals(FavouriteFragment.FAVORITE)) {
            Movie movie = (Movie) item;
            //((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            ((ViewHolder) viewHolder).updateCardViewImage(movie.getThumbnailUrl());
        } else if (type.equals(GenreFragment.GENRE)) {
            Genre genre = (Genre) item;
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(200, 200);
            ((ViewHolder) viewHolder).updateCardViewImage(genre.getImageUrl());
        } else if (type.equals(CountryFragment.COUNTRY)) {
            CountryModel countryModel = (CountryModel) item;
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            ((ViewHolder) viewHolder).updateCardViewImage(countryModel.getImageUrl());
        }*/
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

