package com.ott.tv.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.snackbar.Snackbar;
import com.ott.tv.R;

/**
 * Created by Pushpendra Kumar on 28/08/21 @ 11:52 am
 * Organization - Team Leader @ Colour Moon Technologies PVT LTD INDIA
 * Contact - pushpendra@thecolourmoon.com ► +91-9719325299
 */
public class CMHelper {
    /**
     * Use for show the messages
     *
     * @param parentView -> View for Snack Bar
     * @param message    -> Message in details
     * @param status     -> 0 for regular, 1 for Success message and 2 for failure
     */
    public static void setSnackBar(View parentView, String message, int status) {
        if (message != null) {
            Snackbar snackBar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG);
            View view = snackBar.getView();
            TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTypeface(ResourcesCompat.getFont(parentView.getContext(), R.font.amazon));
            textView.setMaxLines(5);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextColor(ContextCompat.getColor(parentView.getContext(), android.R.color.white));

            if (status == 1) {
                view.setBackgroundColor(ContextCompat.getColor(parentView.getContext(), android.R.color.holo_green_dark));
                textView.setTextColor(ContextCompat.getColor(parentView.getContext(), android.R.color.white));
            } else if (status == 2) {
                view.setBackgroundColor(ContextCompat.getColor(parentView.getContext(), android.R.color.holo_red_dark));
                textView.setTextColor(ContextCompat.getColor(parentView.getContext(), android.R.color.white));
            }

            snackBar.show();
        }
    }

    /**
     * Use for show the messages
     *
     * @param parentView -> View for Snack Bar
     * @param message    -> Message in details
     * @param status     -> 0 for regular, 1 for Success message and 2 for failure
     * @param length     -> for showing snack bar duration
     */
    public static void setSnackBar(View parentView, String message, int status, int length) {
        if (message != null) {
            Snackbar snackBar = Snackbar.make(parentView, message, length);
            View view = snackBar.getView();
            TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTypeface(ResourcesCompat.getFont(parentView.getContext(), R.font.amazon));
            textView.setMaxLines(5);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextColor(ContextCompat.getColor(parentView.getContext(), android.R.color.white));

            if (status == 1) {
                view.setBackgroundColor(ContextCompat.getColor(parentView.getContext(), android.R.color.holo_green_dark));
                textView.setTextColor(ContextCompat.getColor(parentView.getContext(), android.R.color.white));
            } else if (status == 2) {
                view.setBackgroundColor(ContextCompat.getColor(parentView.getContext(), android.R.color.holo_red_dark));
                textView.setTextColor(ContextCompat.getColor(parentView.getContext(), android.R.color.white));
            }

            snackBar.show();
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
