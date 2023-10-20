package com.ott.tv.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.ott.tv.R;
import com.ott.tv.network.ThumbnailResizer;


import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by Krantiveer on 26-8-2021.
 */

public class UiUtil {


    public static String toCamelCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    @SuppressWarnings("deprecation")
    public static int getColor(Context context, int id) {
        if (context == null) {
            return Color.parseColor("#00000000");
        }
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static void translateY(View view, float yPosition, int duration) {
        view.clearAnimation();
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", yPosition);
        animation.setDuration(duration);
        animation.start();
    }



    public static void translateY(View view, float yPosition, int duration, Animator.AnimatorListener animationListener) {
        view.clearAnimation();
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", yPosition);
        animation.setDuration(duration);
        animation.addListener(animationListener);
        animation.start();
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(
//                ObjectAnimator.ofFloat(view, "alpha", 0, 1),
//                ObjectAnimator.ofFloat(view, "translationY", yPosition, 0)
//        );

    }


    public static void translateX(View view, float xPosition, int duration) {
        view.clearAnimation();
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", xPosition);
        animation.setDuration(duration);
        animation.start();
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(
//                ObjectAnimator.ofFloat(view, "alpha", 0, 1),
//                ObjectAnimator.ofFloat(view, "translationX", xPosition, 0)
//        );
    }

    /**
     * Loads an image using Glide from a URL into an image view and crossfades it with the image
     * view's current image.
     * @param context          The activity.
     * @param imageView         The image view to load the image into to.
     * @param url               The URL that points to the image to load.
     */
    public static void loadImageWithCrossFadeTransition(Context context, ImageView imageView,
                                                        String url) {
        /*
         * With the Glide image managing framework, cross fade animations only take place if the
         * image is not already downloaded in cache. In order to have the cross fade animation
         * when the image is in cache, we need to make the following two calls.
         */
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565)
//                .listener(new LoggingListener<>())
                .error(R.drawable.banner_placeholder)
                .placeholder(imageView.getDrawable())
                .transition(new DrawableTransitionOptions()
                        .crossFade(new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
                .into(imageView);

        // Adding this second Glide call enables cross-fade transition even if the image is cached.
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565)
                .error(R.drawable.banner_placeholder)
                .placeholder(imageView.getDrawable())
                // Here we override the onResourceReady of the RequestListener to force
                // the cross fade animation.
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        Log.d("GLIDE", String.format(Locale.ROOT,
                                "onException(%s, %s, %s, %s)", e, model,
                                target, isFirstResource), e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        ImageViewTarget<Drawable> imageTarget
                                = (ImageViewTarget<Drawable>) target;
                        Drawable current = imageTarget.getCurrentDrawable();
                        if (current != null) {
                            TransitionDrawable transitionDrawable
                                    = new TransitionDrawable(new Drawable[]{current, resource});
                            transitionDrawable.setCrossFadeEnabled(true);
                            transitionDrawable.startTransition(300);
                            imageTarget.setDrawable(transitionDrawable);
                            return true;
                        }
                        else
                            return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }


    /**
     * A debug helper class to listen for errors when loading image resources via Glide.
     *
     * @param <T> The type of the input source.
     * @param <R> The type of the resource that will be transcoded from the loaded resource.
     */
   /* private static class LoggingListener<T, R> implements RequestListener<T, R> {

        @Override
        public boolean onException(Exception e, Object model, Target target, boolean
                isFirstResource) {

            Log.e("GLIDE", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target,
                    isFirstResource), e);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean
                isFromMemoryCache, boolean isFirstResource) {

            return false;
        }
    }*/

    /**
     * This method converts the dp to pixels.
     *
     * @param ctx The application context.
     * @param dp  The pixel value to convert to dp.
     * @return The rounded pixel result.
     */
    public static int convertDpToPixel(Context ctx, int dp) {

        float density = 0;
        try {
            density = ctx.getResources().getDisplayMetrics().density;
        }
        catch (Resources.NotFoundException exception) {
         //   SDKLogger.debugLog("Resources not found" + exception);
        }
        return Math.round((float) dp * density);
    }

    /**
     * Converts a pixel value to dp value.
     *
     * @param ctx The application context.
     * @param px  The pixel value to convert to dp.
     * @return The rounded dp result.
     */
    public static int convertPixelToDp(Context ctx, int px) {

        float density = 0;
        try {
            density = ctx.getResources().getDisplayMetrics().density;
        }
        catch (Resources.NotFoundException exception) {
            exception.printStackTrace();
           // SDKLogger.debugLog("Resources not found" + exception.getMessage());
        }
        return Math.round(px * density);
    }

    /**
     * This method updates the opacity of the bitmap.
     *
     * @param bitmap  The bitmap.
     * @param opacity The value of alpha.
     * @return The bitmap after adjusting the opacity.
     */
    public static Bitmap adjustOpacity(Bitmap bitmap, int opacity) {

        Bitmap mutableBitmap = bitmap.isMutable() ? bitmap : bitmap.copy(Bitmap.Config.ARGB_8888,
                true);
        Canvas canvas = new Canvas(mutableBitmap);
        int color = (opacity & 0xFF) << 24;
        canvas.drawColor(color, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }

    /**
     * Rounds the corners of an image.
     *
     * @param activity The activity.
     * @param raw      The raw bitmap image to round.
     * @param round    The radius for the round corners.
     * @return The rounded image.
     */
    public static Bitmap roundCornerImage(Activity activity, Bitmap raw, float round) {

        int width = raw.getWidth();
        int height = raw.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(activity, android.R.color.black));

        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(raw, rect, rect, paint);

        return result;
    }
    public static void displayImage(final Context activity, final String url, final int placeHolder, final int errorPlaceholder, final ImageView imageView) {

        // double imageWidth = thumbnailResizer.getWidth() * 1.0;
        double imageWidth = 640 * 1.0;
        String imageUrl = "https://imstool.phando.com/?image_url=" + url + "&width=" + imageWidth + "&service=resize&aspect_ratio=true";
        Log.i("displayImage:@@imageUrl\"", imageUrl);

        if (activity != null) {
            Glide.with(activity)
                    .load(imageUrl)
                    .error(R.drawable.poster_placeholder_land)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .into(imageView);
        }
    }

}
