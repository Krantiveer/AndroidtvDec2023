package com.ott.tv.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ott.tv.R;

public class SpinnerFragment extends Fragment {

    private static final String TAG = SpinnerFragment.class.getSimpleName();

    private static final int SPINNER_WIDTH = 100;
    private static final int SPINNER_HEIGHT = 100;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

}
