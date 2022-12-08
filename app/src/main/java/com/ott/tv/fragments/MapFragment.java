package com.ott.tv.fragments;


import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.adapter.AdapterClickListener;
import com.ott.tv.adapter.MapListAdapter;
import com.ott.tv.countrycodepicker.CountryCodeActivity;
import com.ott.tv.countrycodepicker.StateCodeActivity;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.model.phando.MapList;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.ui.activity.LoginChooserActivity;
import com.ott.tv.ui.activity.StateListActivity;
import com.ott.tv.utils.PreferenceUtils;
import com.richpath.RichPathView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements AdapterClickListener {
    LinearLayout llSelectedState;
    RichPathView richPathView;
    TextView txtSelectedState;
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
        txtSelectedState = view.findViewById(R.id.txtSelectedState);
        rvList = view.findViewById(R.id.rvList);

        txtSelectedState.setText(PreferenceUtils.getInstance().getUvtv_state_namePref(getContext()));

        llSelectedState.setOnClickListener(v -> {
    /*        Intent i=new Intent(getContext(), StateCodeActivity.class);
            getContext().startActivity(i);
            fetchStateApi(PreferenceUtils.getInstance().getUvtv_state_namePref(getContext()));
*/
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
            Objects.requireNonNull(richPathView.findRichPathByIndex(i)).setFillAlpha(getResources().getColor(R.color.black_color));
        }
        if (!PreferenceUtils.getInstance().getStateNamePref(requireContext()).isEmpty()) {
            Objects.requireNonNull(richPathView.findRichPathByName(
                    PreferenceUtils.getInstance().getStateNamePref(requireContext())
            )).setFillColor(getResources().getColor(R.color.white));
            hitStateDateAPI(PreferenceUtils.getInstance().getStateNamePref(requireContext()));
        }
    }

    void hitStateDateAPI(String name) {
        mMoviesList.clear();

//        get data from
      //  fetchStateApi(name);


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

    private void fetchStateApi( String state_name) {
        if (getActivity() != null) {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            Dashboard api = retrofit.create(Dashboard.class);
            Constants.IS_FROM_HOME = false;
            String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(requireContext());

            Call<List<MapList>> call = api.getMapDataList(accessToken, state_name);
            call.enqueue(new Callback<List<MapList>>() {
                @Override
                public void onResponse(@NonNull Call<List<MapList>> call, @NonNull Response<List<MapList>> response) {
                    if (response.code() == 200) {


                    } else if (response.code() == 401) {

                        signOut();

                    } else if (response.errorBody() != null) {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "sorry! Something went wrong. Please try again after some time" + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                        //  CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                    } else {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(@NonNull Call<List<MapList>> call, @NonNull Throwable t) {
                    //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                    if (getContext() != null) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("MapFragment --", "onFailure: "+t);

                    }
                }

            });
        }
    }


    private void signOut() {
        if (getContext() != null && getActivity() != null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        /*    String userId = databaseHelper.getUserData().getUserId();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
            }*/
            if (PreferenceUtils.getInstance().getAccessTokenPref(getContext()) != "") {
                SharedPreferences.Editor editor = getContext().getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false);
                editor.apply();
                databaseHelper.deleteUserData();
                PreferenceUtils.clearSubscriptionSavedData(getContext());
                PreferenceUtils.getInstance().setAccessTokenNPref(getContext(), "");
                startActivity(new Intent(getContext(), LoginChooserActivity.class));
                getActivity().finish();
            }
        }
    }


}
