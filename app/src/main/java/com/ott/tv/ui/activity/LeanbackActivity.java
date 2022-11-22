package com.ott.tv.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.VerticalGridView;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.fragments.CountryMovieFragment;
import com.ott.tv.fragments.CountryFragment;
import com.ott.tv.fragments.CustomHeadersFragment;
import com.ott.tv.fragments.CustomRowsFragment;
import com.ott.tv.fragments.FavouriteFragment;
import com.ott.tv.fragments.GenreFragment;
import com.ott.tv.fragments.GenreMovieFragment;
import com.ott.tv.fragments.HomeFragment;
import com.ott.tv.fragments.HomeFragmentNewUI;
import com.ott.tv.fragments.MainFragment;
import com.ott.tv.fragments.MoviesFragment;
import com.ott.tv.fragments.MyAccountFragment;
import com.ott.tv.fragments.ShowWatchlistFragment;
import com.ott.tv.fragments.TvSeriesFragment;
import com.ott.tv.model.BrowseData;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.ui.CustomFrameLayout;
import com.ott.tv.ui.Utils;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LeanbackActivity extends FragmentActivity {
    private static final String TAG = "LeanbackMainActivity";
    private long mLastKeyDownTime;
    private CustomHeadersFragment headersFragment;
    private Fragment rowsFragment;
    private ImageView logoIv, logoIvNew;
    private LinkedHashMap<Integer, Fragment> fragments;
    private boolean navigationDrawerOpen;
    private static final float NAVIGATION_DRAWER_SCALE_FACTOR = 0.8f;
    private SearchOrbView orbView;
    private CustomFrameLayout customFrameLayout;
    private boolean rowsContainerFocused;
    int height;
    int width;
    int focus = 2;
    int deltaValue = 450;
    int headerValue = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leanback);
        orbView = findViewById(R.id.custom_search_orb);
        orbView.bringToFront();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        if (height < 1080) {
            Log.i(TAG, "onCreate hight: less then 1080");
            deltaValue = 250;
            headerValue = 0;
            /*PreferenceUtils.getInstance().setWindowHeightPref(getApplicationContext(), "false");*/
        } else {
            deltaValue = 450;
            headerValue = 50;
            /*PreferenceUtils.getInstance().setWindowHeightPref(getApplicationContext(), "true");*/
        }
        Log.i(TAG, "onCreate: " + height + width);
        orbView.setOnOrbClickedListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });
        logoIv = findViewById(R.id.logo);
        /*logoIvNew = findViewById(R.id.logonew);*/
        PreferenceUtils.updateSubscriptionStatus(this);

        fragments = new LinkedHashMap<>();


        int CATEGORIES_NUMBER = 10;
        for (int i = 0; i < CATEGORIES_NUMBER; i++) {
            if (i == 0) {
                HomeFragmentNewUI fragment = new HomeFragmentNewUI();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);
            } else if (i == 1) {

                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                bundle.putString("type", "movies");
                fragment.setArguments(bundle);
                fragments.put(i, fragment);

/*
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                bundle.putString("type", "Pay And Watch");
                MoviesFragment fragment = new MoviesFragment();
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/
            } else if (i == 2) {
                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "live");
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);
            } else if (i == 3) {

                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);
                /*
                FavouriteFragment fragment = new FavouriteFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);

                bundle.putString("type", "Watch Later");
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/
            } else if (i == 4) {
                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                bundle.putString("type", "live");
                fragment.setArguments(bundle);
                fragments.put(i, fragment);
                /*
                GenreMovieFragment fragment = new GenreMovieFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/

            } else if (i == 5) {
                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "27");
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);
                /*
                FavouriteFragment fragment = new FavouriteFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                bundle.putString("type", "movie");
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/
            } else if (i == 6) {

                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                bundle.putString("type", "Watchlist");
                ShowWatchlistFragment fragment = new ShowWatchlistFragment();
                fragment.setArguments(bundle);
                fragments.put(i, fragment);

/*                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "live");
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/
               /* MyAccountFragment fragment = new MyAccountFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/
                /*
                CountryMovieFragment fragment = new CountryMovieFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/

            } else if (i == 7) {
                MyAccountFragment fragment = new MyAccountFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);

            } else {/*
                MyAccountFragment fragment = new MyAccountFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("menu", i);
                fragment.setArguments(bundle);
                fragments.put(i, fragment);*/
            }
        }

        headersFragment = new CustomHeadersFragment();
        rowsFragment = fragments.get(0);
        customFrameLayout = (CustomFrameLayout) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        setupCustomFrameLayout();

        if (new NetworkInst(this).isNetworkAvailable()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.header_container, headersFragment, "CustomHeadersFragment")
                    .add(R.id.rows_container, rowsFragment, "CustomRowsFragment");
            transaction.commit();
        } else {
            // show no internet page
            Intent intent = new Intent(this, ErrorActivity.class);
            startActivity(intent);
            finish();
        }


    }
/*
    private void fetchCategory() {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            Dashboard api = retrofit.create(Dashboard.class);

            Constants.IS_FROM_HOME = false;
            Call<List<BrowseData>> call = api.getBrowseDataList(Config.API_KEY, type, "", "", "", 10, offset);
            call.enqueue(new Callback<List<BrowseData>>() {
                @Override
                public void onResponse(@NonNull Call<List<BrowseData>> call, @NonNull Response<List<BrowseData>> response) {
                    if (response.code() == 200) {
                        movieListContent = response.body();

                        loadRows();

                    } else if (response.errorBody() != null) {
                        CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                    } else {
                        CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                    }


                }

                @Override
                public void onFailure(@NonNull Call<List<BrowseData>> call, @NonNull Throwable t) {
                    CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                }
            });

    }
*/

    public LinkedHashMap<Integer, Fragment> getFragments() {
        return fragments;
    }

    private void setupCustomFrameLayout() {
        customFrameLayout.setOnChildFocusListener(new CustomFrameLayout.OnChildFocusListener() {
            @Override
            public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {

                Log.d(TAG, "keyCode backbutton headersFragment" + direction);
                if (headersFragment.getView() != null && headersFragment.getView().requestFocus(direction, previouslyFocusedRect)) {
                    return true;
                }
                return rowsFragment.getView() != null && rowsFragment.getView().requestFocus(direction, previouslyFocusedRect);
            }

            @Override
            public void onRequestChildFocus(View child, View focused) {
                int childId = child.getId();
                if (childId == R.id.rows_container) {
                    Log.d(TAG, "keyCode backbutton focus row" + rowsContainerFocused + focus);
//here we are focuse in tv row  contenter only not header
                    logoIv.setVisibility(View.VISIBLE);
                    /*logoIvNew.setVisibility(View.VISIBLE);*/
                    if (focus == 0) {
                        if (!rowsContainerFocused) {
                            toggleHeadersFragment(false);
                            rowsContainerFocused = true;
                        }
                        Log.d(TAG, "keyCode backbutton focus row2" + rowsContainerFocused + focus);
                    } else {
                        if (headersFragment.getView() != null)
                            headersFragment.requireView().requestFocus();
                    }
                } else if (childId == R.id.header_container) {
                    Log.d(TAG, "keyCode backbutton focus header" + childId + "focus" + focus);
                    logoIv.setVisibility(View.VISIBLE);
                    focus = 0;
                    /*logoIvNew.setVisibility(View.INVISIBLE);*/
                    toggleHeadersFragment(true);
                    rowsContainerFocused = false;
                }
            }
        });

        customFrameLayout.setOnFocusSearchListener((focused, direction) -> {
            if (direction == View.FOCUS_LEFT) {
                if (isVerticalScrolling() || navigationDrawerOpen) {
                    return focused;
                }
                return getVerticalGridView(headersFragment);
            } else if (direction == View.FOCUS_RIGHT) {
                if (isVerticalScrolling() || !navigationDrawerOpen) {
                    return focused;
                }
                return getVerticalGridView(rowsFragment);
            } else if (focused == orbView && direction == View.FOCUS_DOWN) {
                return navigationDrawerOpen ? getVerticalGridView(headersFragment) : getVerticalGridView(rowsFragment);
            } else if (focused != orbView && orbView.getVisibility() == View.VISIBLE && direction == View.FOCUS_UP) {
                return orbView;
            } else {
                return null;
            }
        });
    }

    public VerticalGridView getVerticalGridView(Fragment fragment) {

        try {
            if (fragment instanceof TvSeriesFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                if (baseRowFragmentClass != null) {
                    Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                    getVerticalGridViewMethod.setAccessible(true);
                    return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);
                }
            } else if (fragment instanceof MoviesFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            }else if (fragment instanceof ShowWatchlistFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof GenreMovieFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof CountryMovieFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof MainFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof FavouriteFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof GenreFragment) {

                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof CountryFragment) {
                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof MyAccountFragment) {
                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else if (fragment instanceof HomeFragmentNewUI) {
                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);
                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);

            } else {
                Class<?> baseRowFragmentClass = getClassLoader().loadClass("androidx/leanback/app/BaseRowSupportFragment");
                Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView");
                getVerticalGridViewMethod.setAccessible(true);

                return (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, (Object[]) null);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public synchronized void toggleHeadersFragment(boolean doOpen) {
        boolean condition = (doOpen != isNavigationDrawerOpen());
        headersFragment.setOnHeaderViewSelectedListener(
                (viewHolder, row) -> {
                    Toast.makeText(getBaseContext(), row.getHeaderItem().getName(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "toggleHeadersFragment: " + row.getHeaderItem().getName());
                }
        );
        if (condition && headersFragment.getView() != null && rowsFragment.getView() != null) {
            final View headersContainer = (View) headersFragment.getView().getParent();
            final View rowsContainer = (View) rowsFragment.getView().getParent();
            if (headersContainer != null && rowsContainer != null) {
                //final float delta = headersContainer.getWidth() * NAVIGATION_DRAWER_SCALE_FACTOR - 300;
                /*if (PreferenceUtils.getInstance().getWindowHeightPref(getApplicationContext()).contentEquals("true")) {
                    deltaValue = 450;
                    headerValue=50;
                }else{
                    deltaValue = 50;
                    headerValue=0;
                }*/
                final float delta = headersContainer.getWidth() * NAVIGATION_DRAWER_SCALE_FACTOR - deltaValue;

                final int currentHeadersMargin = (((ViewGroup.MarginLayoutParams) headersContainer.getLayoutParams()).leftMargin);
                final int currentRowsMargin = (((ViewGroup.MarginLayoutParams) rowsContainer.getLayoutParams()).leftMargin);

                // final int headersDestination = (doOpen ? 0 : (int) (0 - delta));

                final int headersDestination = (doOpen ? 0 : (int) (0 - delta - headerValue)); //kranti -50 not ther
                final int rowsDestination = (doOpen ? (Utils.dpToPx(200, this)) : (int) (Utils.dpToPx(0, this) - delta));


                final int headersDelta = headersDestination - currentHeadersMargin + 15;
                final int rowsDelta = rowsDestination - currentRowsMargin + 115;
                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        ViewGroup.MarginLayoutParams headersParams = (ViewGroup.MarginLayoutParams) headersContainer.getLayoutParams();
                        headersParams.leftMargin = (int) (currentHeadersMargin + headersDelta * interpolatedTime);
                        headersContainer.setLayoutParams(headersParams);

                        ViewGroup.MarginLayoutParams rowsParams = (ViewGroup.MarginLayoutParams) rowsContainer.getLayoutParams();
                        rowsParams.leftMargin = (int) (currentRowsMargin + rowsDelta * interpolatedTime);
                        rowsContainer.setLayoutParams(rowsParams);
                    }
                };

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        navigationDrawerOpen = doOpen;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (!doOpen) {
                            if (rowsFragment instanceof CustomRowsFragment) {
                                ((CustomRowsFragment) rowsFragment).refresh();
                            }
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                });

                animation.setDuration(100);
                ((View) rowsContainer.getParent()).startAnimation(animation);
            }
        }
    }

    private boolean isVerticalScrolling() {
        try {
            if (rowsFragment != null && getVerticalGridView(rowsFragment) != null) {
                return getVerticalGridView(headersFragment).getScrollState()
                        != HorizontalGridView.SCROLL_STATE_IDLE
                        || getVerticalGridView(rowsFragment).getScrollState()
                        != HorizontalGridView.SCROLL_STATE_IDLE;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public synchronized boolean isNavigationDrawerOpen() {
        return navigationDrawerOpen;
    }

    public void updateCurrentRowsFragment(Fragment fragment) {
        rowsFragment = fragment;
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "keyCode backbutton " + rowsContainerFocused + Constants.IS_FROM_HOME);
        if (rowsContainerFocused) {
            toggleHeadersFragment(true);
            rowsContainerFocused = false;
            if (Constants.IS_FROM_HOME) {
                focus = 1;
            }
            headersFragment.getView().requestFocus();
            Log.d(TAG, "toggleHeadersFragment: " + rowsFragment.getView().hasFocus() + headersFragment.getView().hasFocus());

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();



        }
    }

    public void hideLogo() {
        if (logoIv != null)
            logoIv.setVisibility(View.GONE);
    }

    public void showLogo() {
        // logoIv.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Log.d(TAG, "keyCode List" + keyCode);
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_UP_LEFT:
            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:
            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
                Log.d(TAG, "keyCode List" + keyCode);
                return false;

        }
        long current = System.currentTimeMillis();
        boolean res;
        if (current - mLastKeyDownTime < 300) {
            res = true;
        } else {
            res = super.onKeyDown(keyCode, event);
            mLastKeyDownTime = current;
        }
        return res;

        // return super.onKeyDown(keyCode, event);


    }
/*    private void collapseMenu() {
        if (isMenuItemChanged) {
            isMenuItemChanged = false;
            showProgressBar();
        }
        if (headersFragment != null) {
            headersFragment.expandOrCollapseTextView(false);
            headersFragment.setSelectedPosition(carouselPosition);
        }
        if (headersFragment != null) {
         *//*   Log.e("Image View Width", String.valueOf(headerSupportFragment.getImageViewHeader()));
            Log.e("Image View Translation", String.valueOf(Utils.convertDpToPixel(this, 52)));*//*
            width = headerSupportFragment.getImageViewHeader() + Utils.convertDpToPixel(this, 22);
//          Log.e("Image View Width total", String.valueOf(width));
            if (headersFragment.getImageViewHeader() < 20)
                return;
           *//* if (headerSupportFragment.getView() != null) {
                headerSupportFragment.getView().setBackground(getResources()
                        .getDrawable(R.drawable.header_background_with_dash));
            }*//*
        }
        if (navExpandedBackground != null) {
            navExpandedBackground.setVisibility(GONE);
        }
        if (isToShowBanners && bannerGradient != null) {
            showBannerDots();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
        params.width = width;
        isNavMenuExpanded = false;
        TransitionManager.beginDelayedTransition(mTransitionGroup, autoTransition);
        mFrameLayout.setLayoutParams(params);

    }*/
}

