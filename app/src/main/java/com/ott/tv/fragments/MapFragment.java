package com.ott.tv.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ott.tv.R;
import com.ott.tv.adapter.AdapterClickListener;
import com.ott.tv.adapter.MapListAdapter;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.ui.activity.StateListActivity;
import com.ott.tv.utils.PreferenceUtils;
import com.richpath.RichPathView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements AdapterClickListener {
    LinearLayout llSelectedState;
    RichPathView richPathView;
    RecyclerView rvList;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llSelectedState = view.findViewById(R.id.llSelectedState);
        richPathView = view.findViewById(R.id.richPathView);
        rvList = view.findViewById(R.id.rvList);

        llSelectedState.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), StateListActivity.class);
            requireActivity().startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceUtils.getInstance().setStateNamePref(requireContext(), "");
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < richPathView.findAllRichPaths().length; i++) {
            richPathView.findRichPathByIndex(i).setFillAlpha(getResources().getColor(R.color.black_color));
        }
        if (!PreferenceUtils.getInstance().getStateNamePref(requireContext()).isEmpty()) {
            richPathView.findRichPathByName(
                    PreferenceUtils.getInstance().getStateNamePref(requireContext())
            ).setFillColor(getResources().getColor(R.color.white));
            hitStateDateAPI(PreferenceUtils.getInstance().getStateNamePref(requireContext()));
        }
    }

    void hitStateDateAPI(String name) {
        mMoviesList.clear();

//        get data from api
//        then add data to array list

        setAdapter();
    }

    ArrayList<LatestMovieList> mMoviesList = new ArrayList<>();

    MapListAdapter mAdapter;

    void setAdapter() {
        GridLayoutManager manager = new GridLayoutManager(requireContext(), 2);
        rvList.setLayoutManager(manager);
        mAdapter = new MapListAdapter(mMoviesList, requireContext(), this);
        rvList.setAdapter(mAdapter);
    }


    @Override
    public void onItemClick(@NonNull LatestMovieList data) {
//        open details activity
    }
}
