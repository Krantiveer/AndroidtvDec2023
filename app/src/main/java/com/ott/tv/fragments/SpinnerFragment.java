package com.ott.tv.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ott.tv.BuildConfig;
import com.ott.tv.R;

import pl.droidsonroids.gif.GifImageView;

public class SpinnerFragment extends Fragment {


    private static final String TAG = SpinnerFragment.class.getSimpleName();

    private static final int SPINNER_WIDTH = 100;
    private static final int SPINNER_HEIGHT = 100;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ProgressBar progressBar;
    GifImageView uvtv_loading_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressBar = container.findViewById(R.id.progress_circular);

        if (BuildConfig.FLAVOR.equalsIgnoreCase("uvtv")) {
            uvtv_loading_icon = container.findViewById(R.id.uvtv_loading_icon);

            //    uvtv_loading_icon.setVisibility(View.VISIBLE);
        } else {
            if (container != null) {
                ProgressBar progressBar = new ProgressBar(container.getContext(), null, android.R.attr.progressBarStyle);

                if (container instanceof FrameLayout) {
                    progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(container.getContext(), R.color.colorGold), android.graphics.PorterDuff.Mode.MULTIPLY);
                    FrameLayout.LayoutParams layoutParams =
                            new FrameLayout.LayoutParams(SPINNER_WIDTH, SPINNER_HEIGHT, Gravity.CENTER);
                    progressBar.setLayoutParams(layoutParams);
                }
                return progressBar;
            }
            return null;
        }

//            progressBar.setVisibility(View.VISIBLE);


        // Inflate the layout for this fragment


/*
        Glide.with(this)
                .load(R.raw.uvtv_loading_icon)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
*/
        return inflater.inflate(R.layout.progressbar_layout, container, false);


    }
}
