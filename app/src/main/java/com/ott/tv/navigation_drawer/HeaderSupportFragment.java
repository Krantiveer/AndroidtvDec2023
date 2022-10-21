package com.ott.tv.navigation_drawer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;


import com.ott.tv.R;
import com.ott.tv.fragments.MenuFocusChangeListener;
import com.ott.tv.ui.IconHeaderItem;

import java.util.List;


public class HeaderSupportFragment extends RowsSupportFragment {

    private int prevPos = 0;
    private int focussedPosition;
    public static boolean firstTime = true;

    /**
     * Interface definition for a callback to be invoked when a header item is clicked.
     */
    public interface OnHeaderClickedListener {
        /**
         * Called when a header item has been clicked.
         *
         */
       // void onHeaderClicked(CustomRowHeaderPresenter.ViewHolder viewHolder, Row row);
        void onHeaderChangeRequested();
    }

    /**
     * Interface definition for a callback to be invoked when a header item is selected.
     */
    public interface OnHeaderViewSelectedListener {
        /**
         * Called when a header item has been selected.
         *
         * @param viewHolder Row ViewHolder object corresponding to the selected Header.
         * @param row Row object corresponding to the selected Header.
         */
      //  void onHeaderSelected(CustomRowHeaderPresenter.ViewHolder viewHolder, Row row,int position);
    }

    private OnHeaderViewSelectedListener mOnHeaderViewSelectedListener;
    OnHeaderClickedListener mOnHeaderClickedListener;
    MenuFocusChangeListener mMenuFocusChangeListener;
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
   // CustomRowHeaderPresenter headerPresenter;
    ArrayObjectAdapter rowsAdapter;
  //  private List<CarouselInfoData> mListCarouselInfo;


    private static PresenterSelector sHeaderPresenter;
/*    public HeaderSupportFragment() {
        headerPresenter = new CustomRowHeaderPresenter();
        sHeaderPresenter  = new ClassPresenterSelector()
                .addClassPresenter(Row.class, headerPresenter);
        setPresenterSelector(sHeaderPresenter);
        CustomFocusHighLightHelper.setupHeaderItemFocusHighlight(getBridgeAdapter());
        //setBackgroundColor(R.color.redColor);
    }*/
/*
    public void setCarousalData(List<CarouselInfoData> mListCarouselInfo) {
        this.mListCarouselInfo = mListCarouselInfo;
        loadRows();
    }*/
    public void setMenuFocusChangedListener (MenuFocusChangeListener focusChangedListener){
        mMenuFocusChangeListener = focusChangedListener;
    }

    public void setOnHeaderClickedListener(OnHeaderClickedListener listener) {
        mOnHeaderClickedListener = listener;
    }

    public void setOnHeaderViewSelectedListener(OnHeaderViewSelectedListener listener) {
        mOnHeaderViewSelectedListener = listener;
    }

   /* @Override
    public VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view.findViewById(R.id.browse_headers);
    }*/



    void onRowSelected(RecyclerView parent, RecyclerView.ViewHolder viewHolder,
                       int position, int subposition) {
        focussedPosition = position;
/*
        if(AndroidTVApplication.isFirstTime) {
            if (mOnHeaderViewSelectedListener != null) {
                if (viewHolder != null && position >= 0) {
                    CustomItemBridgeAdapter.ViewHolder vh = (CustomItemBridgeAdapter.ViewHolder) viewHolder;
                    mOnHeaderViewSelectedListener.onHeaderSelected(
                            (CustomRowHeaderPresenter.ViewHolder) vh.getViewHolder(), (Row) vh.getItem(), position);
                    */
/*HeaderRow headerRow = (HeaderRow) vh.getItem();
                    if (headerRow.getHeaderItem().getName().contains(APIConstants.SEARCH_ITEM)) {
                        return;
                    } else if (headerRow.getHeaderItem().getName().contains(APIConstants.LANGUAGES_ITEM)) {
                        return;
                    } else if (headerRow.getHeaderItem().getName().contains(APIConstants.WATCHLIST_ITEM)) {
                        return;
                    }*//*

                    if (mListCarouselInfo != null && mListCarouselInfo.size() > position) {
                        if (firstTime || prevPos != position) {
                            firstTime = false;
                            prevPos = position;
                            CarouselInfoData carouselInfoData = mListCarouselInfo.get(position);
                            showCarouselMenuData(carouselInfoData);
                        }
                    }

                } else {
                    mOnHeaderViewSelectedListener.onHeaderSelected(null, null, position);
                }
            }
        }
*/
    }
/*

    private void showCarouselMenuData(CarouselInfoData carouselInfoData) {
        ContentBrowseFragment contentBrowseFragment = (ContentBrowseFragment) getFragmentManager().findFragmentById(R.id.content_browse_fragment);
        contentBrowseFragment.setCarouselMenuData(carouselInfoData);
    }



    public int getImageViewHeader(){
        if(headerPresenter != null){
           return headerPresenter.getTheWidthOfImageView();
        }
        return 0;
    }

    public int getRootViewWidth(){
        if(headerPresenter != null){
            return headerPresenter.getTheWidthORootView();
        }
        return 0;
    }

    public void expandOrCollapseTextView(boolean shouldExpand){
        if(headerPresenter != null){
            if (!shouldExpand) {
                headerPresenter.collapseTextView();
                headerPresenter.setSelectedPosition(prevPos);
                headerPresenter.setDidSelectedPosChange(true);
                Log.d("CheckHeaderPosition", "position: " + prevPos);
                refreshAdapter();
            } else {
                headerPresenter.expandTextView();
                refreshAdapter();
            }
        }
    }



    private final CustomItemBridgeAdapter.AdapterListener mAdapterListener =
            new CustomItemBridgeAdapter.AdapterListener() {
                @Override
                public void onCreate(final CustomItemBridgeAdapter.ViewHolder viewHolder) {
                    View headerView = viewHolder.getViewHolder().view;
                    headerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            */
/*if (mOnHeaderClickedListener != null) {
                                mOnHeaderClickedListener.onHeaderClicked(
                                        (CustomRowHeaderPresenter.ViewHolder) viewHolder.getViewHolder(),
                                        (Row) viewHolder.getItem());

                            }*//*


                            if (mOnHeaderViewSelectedListener != null) {
                                if (viewHolder != null && focussedPosition >= 0) {
                                    CustomItemBridgeAdapter.ViewHolder vh = (CustomItemBridgeAdapter.ViewHolder) viewHolder;
                                    mOnHeaderViewSelectedListener.onHeaderSelected(
                                            (CustomRowHeaderPresenter.ViewHolder) vh.getViewHolder(), (Row) vh.getItem(), focussedPosition);
                                    */
/*HeaderRow headerRow = (HeaderRow) vh.getItem();
                                    if (headerRow.getHeaderItem().getName().contains(APIConstants.SEARCH_ITEM)) {
                                        return;
                                    } else if (headerRow.getHeaderItem().getName().contains(APIConstants.LANGUAGES_ITEM)) {
                                        return;
                                    } else if (headerRow.getHeaderItem().getName().contains(APIConstants.WATCHLIST_ITEM)) {
                                        return;
                                    }*//*

                                    if (mListCarouselInfo != null && mListCarouselInfo.size() > focussedPosition) {
                                        if (firstTime || prevPos != focussedPosition) {
                                            firstTime = false;
                                            prevPos = focussedPosition;
                                            headerPresenter.setSelectedPosition(prevPos);
                                            headerPresenter.setDidSelectedPosChange(true);
                                            CarouselInfoData carouselInfoData = mListCarouselInfo.get(focussedPosition);
                                            showCarouselMenuData(carouselInfoData);
                                        }
                                    }

                                } else {
                                    mOnHeaderViewSelectedListener.onHeaderSelected(null, null, focussedPosition);
                                }
                            }

                        }
                    });
                        viewHolder.itemView.addOnLayoutChangeListener(sLayoutChangeListener);
                        viewHolder.itemView.setOnFocusChangeListener(mOnFocusChangeListener);
                        headerView.addOnLayoutChangeListener(sLayoutChangeListener);
                }

            };
*/


    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            mMenuFocusChangeListener.onFocusChanged(v, hasFocus);
        }
    };

    private View.OnLayoutChangeListener sLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
            v.setPivotY(v.getMeasuredHeight());
            v.setPivotY(v.getMeasuredHeight());
        }
    };


    int getLayoutResourceId() {
        return R.layout.headers_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final VerticalGridView listView = getVerticalGridView();
        if (listView == null) {
            return;
        }
        if (mBackgroundColorSet) {
            listView.setBackgroundColor(mBackgroundColor);
            updateFadingEdgeToBrandColor(mBackgroundColor);
        } else {
            Drawable d = listView.getBackground();
            if (d instanceof ColorDrawable) {
                updateFadingEdgeToBrandColor(((ColorDrawable) d).getColor());
            }
        }

    }


/*
    private void loadRows() {
        if(mListCarouselInfo != null && mListCarouselInfo.size()>0) {
            rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            for (int i = 0; i < mListCarouselInfo.size(); i++) {
                Log.d("TAG","mListCarouselInfo"+mListCarouselInfo.get(i).title);
                Log.d("TAG","mListCarouselInfo"+mListCarouselInfo.get(i).actionUrl);
                Log.d("TAG","mListCarouselInfo"+mListCarouselInfo.get(i).getIconUrl(true));
                IconHeaderItem header =
                        null;
                if (AndroidTVApplication.enableNavigationImageHighlight) {
                    header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, MovieList.MOVIE_CATEGORY_IMAGES[i]);
                }else{

                    switch (mListCarouselInfo.get(i).title){
                        case "Search":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_search);break;
                        case "Home":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_home);break;
                        case "Programs":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_programs);break;
                        case "Movies":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_movies);break;
                        case "News":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_news);break;
                        case "Settings":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_settings);break;
                        case "Premium":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_premium);break;
                        case "Playlist":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_watchlist);break;
                        case "Watch History":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_watch_history);break;
                        case "Liked Videos":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_favourites);break;
                        case "Music":header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, R.drawable.mmtv_music);break;
                    }
//                    header = new IconHeaderItem(i, mListCarouselInfo.get(i).title, MovieList.PLAIN_IMAGE_PLACE_HOLDER_IMAGES[i]);
                }
                rowsAdapter.add(new HeaderRow(header));
            }
            setAdapter(rowsAdapter);
        }
        setSelectedPosition(1);
    }
*/
    static class NoOverlappingFrameLayout extends FrameLayout {

        public NoOverlappingFrameLayout(Context context) {
            super(context);
        }

        /**
         * Avoid creating hardware layer for header dock.
         */
        @Override
        public boolean hasOverlappingRendering() {
            return false;
        }
    }

    // Wrapper needed because of conflict between RecyclerView's use of alpha
    // for ADD animations, and RowHeaderPresenter's use of alpha for selected level.
   /* final CustomItemBridgeAdapter.Wrapper mWrapper = new CustomItemBridgeAdapter.Wrapper() {
        @Override
        public void wrap(View wrapper, View wrapped) {
            ((FrameLayout) wrapper).addView(wrapped);
        }

        @Override
        public View createWrapper(View root) {
            return new NoOverlappingFrameLayout(root.getContext());
        }
    };
    @Override
    void updateAdapter() {
        super.updateAdapter();
        CustomItemBridgeAdapter adapter = getBridgeAdapter();
        adapter.setAdapterListener(mAdapterListener);
       // adapter.setWrapper(mWrapper);

    }
*/
    private void refreshAdapter(){
        if (getVerticalGridView() != null
                && getVerticalGridView().getAdapter() != null
                && !getVerticalGridView().isComputingLayout()) {
            getVerticalGridView().setAnimateChildLayout(false);
            getVerticalGridView().getAdapter().notifyDataSetChanged();
        }
    }

    void setBackgroundColor(int color) {
        mBackgroundColor = color;
        mBackgroundColorSet = true;

        if (getVerticalGridView() != null) {
            getVerticalGridView().setBackgroundColor(mBackgroundColor);
            updateFadingEdgeToBrandColor(mBackgroundColor);
        }
    }

    private void updateFadingEdgeToBrandColor(int backgroundColor) {
        View fadingView = getView().findViewById(R.id.browse_headers_root);
        Drawable background = fadingView.getBackground();
        if (background instanceof GradientDrawable) {
            background.mutate();
            ((GradientDrawable) background).setColors(
                    new int[] {Color.TRANSPARENT, backgroundColor});
        }
    }

    @Override
    public void onTransitionStart() {
        super.onTransitionStart();

    }

    @Override
    public void onTransitionEnd() {
        super.onTransitionEnd();
    }



    public boolean isScrolling() {
        return getVerticalGridView().getScrollState()
                != HorizontalGridView.SCROLL_STATE_IDLE;
    }
}
