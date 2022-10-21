package com.ott.tv.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.R;
import com.ott.tv.model.CountryModel;
import com.ott.tv.model.Genre;
import com.ott.tv.model.Movie;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.CountryApi;
import com.ott.tv.network.api.GenreApi;
import com.ott.tv.ui.activity.ItemCountryActivity;
import com.ott.tv.ui.activity.ItemGenreActivity;
import com.ott.tv.ui.activity.LeanbackActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.ui.presenter.HorizontalCardCountryPresenter;
import com.ott.tv.ui.presenter.HorizontalCardGenrePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountryMovieFragment extends VerticalGridSupportFragment {

    private static final String TAG = ItemCountryFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 5;
    private List<Movie> movies = new ArrayList<>();
    private ArrayObjectAdapter mAdapter;
    //private BackgroundHelper bgHelper;
    public static final String MOVIE = "movie";
    private int pageCount = 1;
    private boolean dataAvailable = true;
    private CardPresenter cardPresenter;

    private Context mContext;
    private String title;
    private String id = "";
    private String datatype = "";
    private LeanbackActivity activity;
    private List<CountryModel> genres = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardPresenter = new CardPresenter();
        mContext = getContext();
        if (getActivity() != null) {
            id = getActivity().getIntent().getStringExtra("id");
            activity = (LeanbackActivity) getActivity();
            title = getActivity().getIntent().getStringExtra("title");
            datatype = getArguments().getString("type");
        }
        Log.i(TAG, "onCreate: " + datatype + id + title);
/*
        showTitle(false);
*/
        setTitle(getResources().getString(R.string.country));

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment(datatype);
    }

    private void setupFragment(String datatype) {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        //  mAdapter = new ArrayObjectAdapter(cardPresenter);
        mAdapter = new ArrayObjectAdapter(new HorizontalCardCountryPresenter("movies"));
        setAdapter(mAdapter);
        fetchMovieData( pageCount);

    }


    private void fetchMovieData( int pageCount) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        CountryApi api = retrofit.create(CountryApi.class);
        datatype = "movie";
        
        
        Call<List<CountryModel>> call = api.getAllCountry(Config.API_KEY);

        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CountryModel>> call, @NonNull Response<List<CountryModel>> response) {
                if (response.code() == 200) {
                    List<CountryModel> movieList = response.body();
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        //Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }

                    for (CountryModel movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + genres.size());
                    genres.addAll(movieList);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });


    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {
            CountryModel countryModel = (CountryModel) o;
            Intent intent = new Intent(getActivity(), ItemCountryActivity.class);
            intent.putExtra("id", countryModel.getCountryId());
            intent.putExtra("title", countryModel.getName());
            startActivity(intent);

        };
    }


    // selected listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return (itemViewHolder, item, rowViewHolder, row) -> {

            // pagination
            if (dataAvailable) {
                int itemPos = mAdapter.indexOf(item);
                if (itemPos == movies.size() - 1) {
                    pageCount++;
                    fetchMovieData( pageCount);
                }
            }

            // change the background color when the item will select
            if (item instanceof Movie) {
                /*bgHelper = new BackgroundHelper(getActivity());
                bgHelper.prepareBackgroundManager();
                bgHelper.startBackgroundTimer(((Movie) item).getThumbnailUrl());*/

            }

        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        movies = new ArrayList<>();
        pageCount = 1;
        dataAvailable = true;

    }

}
