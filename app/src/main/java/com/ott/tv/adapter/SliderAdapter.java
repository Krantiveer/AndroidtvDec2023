package com.ott.tv.adapter;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ott.tv.R;
import com.ott.tv.model.SliderHome.SliderData;
import com.ott.tv.ui.activity.DetailsActivityPhando;
import com.ott.tv.ui.activity.ItemCountryActivity;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<SliderData> mSliderItems;
    private OnSliderClickListener mSliderClickListener;

    //   private OnSliderClickListener mSliderClickListener; // Click listener


    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList,OnSliderClickListener clickListener) {
        this.mSliderItems = sliderDataArrayList;
        this.mSliderClickListener = clickListener;

    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .placeholder(R.drawable.poster_placeholder_land)
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.title_name.setText(sliderItem.getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSliderClickListener != null) {
                    mSliderClickListener.onSliderClick(sliderItem);
                }

                Log.i("data-->", "onClick: "+sliderItem.getTitle());


             /*   if (sliderItem.getVideoContent().getType().equals("M") && sliderItem.getVideoContent().getIs_live().toString().equalsIgnoreCase("0")) {
                    Intent intent = new Intent(getContext(), DetailsActivityPhando.class);
                    if (sliderItem.getVideoContent().getType() != null)
                        intent.putExtra("type", sliderItem.getVideoContent().getType());
                    if (sliderItem.getVideoContent().getThumbnail() != null)
                        intent.putExtra("thumbImage", sliderItem.getVideoContent().getThumbnail());
                    if (sliderItem.getVideoContent().getId() != null)
                        intent.putExtra("video_id", sliderItem.getVideoContent().getId().toString());
                    if (sliderItem.getVideoContent().getTitle() != null)
                        intent.putExtra("title", sliderItem.getVideoContent().getTitle());
                    if (sliderItem.getVideoContent().getDetail() != null)
                        intent.putExtra("description", sliderItem.getVideoContent().getDetail());
                    if (sliderItem.getVideoContent().getRelease_date() != null)
                        intent.putExtra("release", sliderItem.getVideoContent().getRelease_date());
                    if (sliderItem.getVideoContent().getDuration_str() != null)
                        intent.putExtra("duration", sliderItem.getVideoContent().getDuration_str());
                    if (sliderItem.getVideoContent().getMaturity_rating() != null)
                        intent.putExtra("maturity_rating", sliderItem.getVideoContent().getMaturity_rating());
                    if (sliderItem.getVideoContent().getIs_free() != null)
                        intent.putExtra("ispaid", sliderItem.getVideoContent().getIs_free().toString());
                    if (sliderItem.getVideoContent().getLanguage_str() != null)
                        intent.putExtra("language_str", sliderItem.getVideoContent().getLanguage_str());
                    if (sliderItem.getVideoContent().getIs_live() != null)
                        intent.putExtra("is_live", sliderItem.getVideoContent().getIs_live().toString());
                    if (sliderItem.getVideoContent().getRating() != null)
                        intent.putExtra("rating", sliderItem.getVideoContent().getRating().toString());
                    if (sliderItem.getVideoContent().getTrailers() != null && sliderItem.getVideoContent().getTrailers().size() > 0 && sliderItem.getVideoContent().getTrailers().get(0) != null && sliderItem.getVideoContent().getTrailers().get(0).getMedia_url() != null) {
                        intent.putExtra("trailer", sliderItem.getVideoContent().getTrailers().get(0).getMedia_url());
                    }

//kranti
                    if (sliderItem.getVideoContent().getGenres() != null) {
                        if (sliderItem.getVideoContent().getGenres().size() > 0) {
                            String genres;
                            genres = sliderItem.getVideoContent().getGenres().get(0);
                            for (int i = 1; i < sliderItem.getVideoContent().getGenres().size(); i++) {
                                genres = genres.concat("," + sliderItem.getVideoContent().getGenres().get(i));
                            }
                            intent.putExtra("genres", genres);
                        }
                    }
                    getContext().startActivity(intent);
                    //getContext().overridePendingTransition(R.anim.enter, R.anim.exit);

                }
                if (sliderItem.getVideoContent().getType().equals("T")) {
                    Intent intent = new Intent(getContext(), DetailsActivityPhando.class);
                    if (sliderItem.getVideoContent().getType() != null)
                        intent.putExtra("type", sliderItem.getVideoContent().getType());
                    if (sliderItem.getVideoContent().getThumbnail() != null)
                        intent.putExtra("thumbImage", sliderItem.getVideoContent().getThumbnail());
                    if (sliderItem.getVideoContent().getId() != null)
                        intent.putExtra("video_id", sliderItem.getVideoContent().getId().toString());
                    if (sliderItem.getVideoContent().getTitle() != null)
                        intent.putExtra("title", sliderItem.getVideoContent().getTitle());
                    if (sliderItem.getVideoContent().getDetail() != null)
                        intent.putExtra("description", sliderItem.getVideoContent().getDetail());
                    if (sliderItem.getVideoContent().getRelease_date() != null)
                        intent.putExtra("release", sliderItem.getVideoContent().getRelease_date());
                    if (sliderItem.getVideoContent().getDuration_str() != null)
                        intent.putExtra("duration", sliderItem.getVideoContent().getDuration_str());
                    if (sliderItem.getVideoContent().getMaturity_rating() != null)
                        intent.putExtra("maturity_rating", sliderItem.getVideoContent().getMaturity_rating());
                    if (sliderItem.getVideoContent().getIs_free() != null)
                        intent.putExtra("ispaid", sliderItem.getVideoContent().getIs_free().toString());
                    if (sliderItem.getVideoContent().getLanguage_str() != null)
                        intent.putExtra("language_str", sliderItem.getVideoContent().getLanguage_str());
                    if (sliderItem.getVideoContent().getIs_live() != null)
                        intent.putExtra("is_live", sliderItem.getVideoContent().getIs_live().toString());
                    if (sliderItem.getVideoContent().getRating() != null)
                        intent.putExtra("rating", sliderItem.getVideoContent().getRating().toString());
                    if (sliderItem.getVideoContent().getTrailers() != null && sliderItem.getVideoContent().getTrailers().size() > 0 && sliderItem.getVideoContent().getTrailers().get(0) != null && sliderItem.getVideoContent().getTrailers().get(0).getMedia_url() != null) {
                        intent.putExtra("trailer", sliderItem.getVideoContent().getTrailers().get(0).getMedia_url());
                    }


                    if (sliderItem.getVideoContent().getGenres() != null) {
                        String genres;
                        genres = sliderItem.getVideoContent().getGenres().get(0);
                        for (int i = 1; i < sliderItem.getVideoContent().getGenres().size(); i++) {
                            genres = genres.concat("," + sliderItem.getVideoContent().getGenres().get(i));
                        }
                        intent.putExtra("genres", genres);
                    }
                    getContext().startActivity(intent);

                }
                if (sliderItem.getVideoContent().getType().equals("M") && sliderItem.getVideoContent().getIs_live().toString().equalsIgnoreCase("1")) {
                    Intent intent = new Intent(getContext(), DetailsActivityPhando.class);
                    if (sliderItem.getVideoContent().getType() != null)
                        intent.putExtra("type", sliderItem.getVideoContent().getType());
                    if (sliderItem.getVideoContent().getThumbnail() != null)
                        intent.putExtra("thumbImage", sliderItem.getVideoContent().getThumbnail());
                    if (sliderItem.getVideoContent().getId() != null)
                        intent.putExtra("video_id", sliderItem.getVideoContent().getId().toString());
                    if (sliderItem.getVideoContent().getTitle() != null)
                        intent.putExtra("title", sliderItem.getVideoContent().getTitle());
                    if (sliderItem.getVideoContent().getDetail() != null)
                        intent.putExtra("description", sliderItem.getVideoContent().getDetail());
                    if (sliderItem.getVideoContent().getRelease_date() != null)
                        intent.putExtra("release", sliderItem.getVideoContent().getRelease_date());
                    if (sliderItem.getVideoContent().getDuration_str() != null)
                        intent.putExtra("duration", sliderItem.getVideoContent().getDuration_str());
                    if (sliderItem.getVideoContent().getMaturity_rating() != null)
                        intent.putExtra("maturity_rating", sliderItem.getVideoContent().getMaturity_rating());
                    if (sliderItem.getVideoContent().getIs_free() != null)
                        intent.putExtra("ispaid", sliderItem.getVideoContent().getIs_free().toString());
                    if (sliderItem.getVideoContent().getLanguage_str() != null)
                        intent.putExtra("language_str", sliderItem.getVideoContent().getLanguage_str());
                    if (sliderItem.getVideoContent().getIs_live() != null)
                        intent.putExtra("is_live", sliderItem.getVideoContent().getIs_live().toString());
                    if (sliderItem.getVideoContent().getRating() != null)
                        intent.putExtra("rating", sliderItem.getVideoContent().getRating().toString());
                    if (sliderItem.getVideoContent().getTrailers() != null && sliderItem.getVideoContent().getTrailers().size() > 0 && sliderItem.getVideoContent().getTrailers().get(0) != null && sliderItem.getVideoContent().getTrailers().get(0).getMedia_url() != null) {
                        intent.putExtra("trailer", sliderItem.getVideoContent().getTrailers().get(0).getMedia_url());
                    }

//kranti
                    if (sliderItem.getVideoContent().getGenres() != null) {
                        String genres;
                        genres = sliderItem.getVideoContent().getGenres().get(0);
                        for (int i = 1; i < sliderItem.getVideoContent().getGenres().size(); i++) {
                            genres = genres.concat("," + sliderItem.getVideoContent().getGenres().get(i));
                        }
                        intent.putExtra("genres", genres);
                    }
                    getContext().startActivity(intent);

                }
                if (sliderItem.getVideoContent().getType().equalsIgnoreCase("VM")) {
                    Intent intent = new Intent(getContext(), ItemCountryActivity.class);
                    intent.putExtra("id", sliderItem.getVideoContent().getId().toString());
                    intent.putExtra("title", sliderItem.getVideoContent().getViewallTitle());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }
                if (sliderItem.getVideoContent().getType().equalsIgnoreCase("OTT")) {
                    //   Intent intent = new Intent(context, ItemCountryActivity.class);

                    Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(
                            sliderItem.getVideoContent().getAndroid_link().substring(sliderItem.getVideoContent().getAndroid_link().lastIndexOf("=") + 1)
                    );

                    if (intent == null) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(sliderItem.getVideoContent().getAndroid_link()));
                    }

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);


                }*/

            }
        });
        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
             //   Log.i("@@sliderAdapterdata-->", "onClick: "+sliderItem.getTitle()+position);



            }
        });
        viewHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
              //  Log.i("@@sliderAdapterdata-->", "onClick: "+sliderItem.getTitle()+position+"---"+keyCode);
              /*  if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ALT_RIGHT) {
                    Log.e("@@keyevent", keyCode.toString())
                    true}*/
             /*   if (event.getAction() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    if (position == 2 && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        return true; // Consume the key event, preventing the left button action
                    }
                }*/
                return false; // Allow the key event to be handled normally
            }
        });
/*
        viewHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("@@sliderAdapterdata-->", "onClick: "+event+keyCode);

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            // Check if the left slider is focused
                            if (isLeftSliderFocused(v)) {
                                // Handle left slider focused case
                                // For example: Don't perform any action
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            // Handle center button click (D-pad center, Enter)
                            // Your existing logic to handle the click
                            return true; // Return true to indicate you've handled the event
                    }
                }
                return false; // Return false to allow normal event handling
            }
        });
*/


    }

    private Context getContext() {
        return null;
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }


    static class SliderAdapterViewHolder extends ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;
        TextView title_name;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            title_name=itemView.findViewById(R.id.title_name);
            this.itemView = itemView;
        }

    }
    public interface OnSliderClickListener {
        void onSliderClick(SliderData sliderItem);
    }
}
