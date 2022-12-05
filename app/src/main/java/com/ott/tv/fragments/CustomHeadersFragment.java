package com.ott.tv.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.app.HeadersSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnChildSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.VerticalGridView;

import com.ott.tv.BuildConfig;
import com.ott.tv.R;
import com.ott.tv.ui.IconHeaderItem;
import com.ott.tv.ui.Utils;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.LeanbackActivity;
import com.ott.tv.ui.activity.LoginActivity;
import com.ott.tv.ui.activity.SearchActivity;
import com.ott.tv.ui.activity.SignUpActivity;
import com.ott.tv.ui.activity.VideoDetailsActivity;
import com.ott.tv.ui.presenter.IconHeaderItemPresenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class CustomHeadersFragment extends HeadersSupportFragment {

    OnHeaderClickedListener mOnHeaderClickedListener;
    private IconHeaderItem header;

    public CustomHeadersFragment() {
        super();

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setOnHeaderViewSelectedListener((viewHolder, row) -> {
            if (((ListRow) row).getAdapter().size() >= 1) {
                Object obj = ((ListRow) row).getAdapter().get(0);
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rows_container, (Fragment) obj).commitAllowingStateLoss();
                }
            }
        });

        setHeaderAdapter();


        customSetBackground(R.color.colorPrimary);
        setCustomPadding();
        if (getActivity() != null) {
            VerticalGridView gridView = ((LeanbackActivity) getActivity()).getVerticalGridView(this);
            if (gridView != null) {
                gridView.setOnChildSelectedListener((viewGroup, myView, i, l) -> {
                    if (getActivity() != null) {
                        Object obj = ((ListRow) getAdapter().get(i)).getAdapter().get(0);

                        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rows_container, (Fragment) obj).commit();

                        Log.d(TAG, "onViewCreated: " + i);

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.rows_container, (Fragment) obj)
                                .commitAllowingStateLoss();
                        ((LeanbackActivity) getActivity()).updateCurrentRowsFragment((Fragment) obj);

                    }

                 /*   if(i==2)
                    {
                        Intent intent=new Intent(requireActivity(),LoginActivity.class);
                        startActivity(intent);
                    }
                    else if(i==1){
                        Intent intent=new Intent(requireActivity(), SearchActivity.class);
                        startActivity(intent);
                    }*/
                });
            }


        }

    }


    private void setHeaderAdapter() {
        String[] headerItem;
        int[] headerIcon;
        if (BuildConfig.FLAVOR.equalsIgnoreCase("solidtv")||BuildConfig.FLAVOR.equalsIgnoreCase("kaafaltv")) {
            headerItem = new String[]{"Home", "Movie", "Series", "Live Channels"/*, "Coming Soon"*/, "Watchlist", "Profile"};

            headerIcon = new int[]{R.drawable.homeslidernavnew, R.drawable.moviessidenav,
                    R.drawable.seriessidenav,

                    R.drawable.live_sidenav,
                /*    R.drawable.commingsoon_sidenav,*/
                    R.drawable.ic_baseline_flag_24,
                    R.drawable.ic_baseline_exit_to_app_24};
        } else {
            headerItem = new String[]{"Home", "Movie", "Series", "UVTV Bharat", "Live Channels", "Watchlist", "Profile"};
            headerIcon = new int[]{R.drawable.homeslidernavnew, R.drawable.moviessidenav,
                    R.drawable.seriessidenav,
                    R.drawable.map_side_nav,
                    R.drawable.live_sidenav,
/*
                    R.drawable.commingsoon_sidenav,
*/
                    R.drawable.ic_baseline_flag_24,
                    R.drawable.ic_baseline_exit_to_app_24};
        }


        ArrayObjectAdapter adapter = new ArrayObjectAdapter();
        if (getActivity() != null) {
            LinkedHashMap<Integer, Fragment> fragments = ((LeanbackActivity) getActivity()).getFragments();
            ArrayObjectAdapter innerAdapter;
            int id = 0;
            for (int i = 0; i < fragments.size(); i++) {
                IconHeaderItem header = new IconHeaderItem(id, headerItem[i], headerIcon[i]);
                innerAdapter = new ArrayObjectAdapter();
                innerAdapter.add(fragments.get(i));
                adapter.add(id, new ListRow(header, innerAdapter));
                id++;
            }

            setAdapter(adapter);
        }

        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return new IconHeaderItemPresenter();
            }
        });

        setOnHeaderClickedListener((viewHolder, row) -> {
            if (((ListRow) row).getAdapter().size() >= 1) {
                Object obj = ((ListRow) row).getAdapter().get(0);
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rows_container, (Fragment) obj).commit();
                }
            }
        });

    }

    private void setCustomPadding() {
        if (getActivity() != null && getVerticalGridView() != null) {
            getVerticalGridView().setPadding(50, Utils.dpToPx(128, getActivity()), 0, 0);
        }
    }


    public void setOnHeaderClickedListener(OnHeaderClickedListener listener) {
        mOnHeaderClickedListener = listener;
    }

    public void setOnHeaderViewSelectedListener(OnHeaderViewSelectedListener listener) {
    }

    private void customSetBackground(int colorResource) {
        if (getContext() != null) {
            try {
                Class<?> clazz = HeadersSupportFragment.class;
                Method m = clazz.getDeclaredMethod("setBackgroundColor", Integer.TYPE);
                m.setAccessible(true);
                m.invoke(this, ContextCompat.getColor(getContext(), colorResource));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

}
