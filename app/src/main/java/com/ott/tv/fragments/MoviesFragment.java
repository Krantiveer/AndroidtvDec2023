package com.ott.tv.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;

import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.Movie;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.network.api.MovieApi;
import com.ott.tv.ui.activity.DetailsActivity;

import com.ott.tv.ui.activity.LeanbackActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.ui.presenter.HorizontalCardPresenter;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Query;

import com.ott.tv.R;

public class MoviesFragment extends VerticalGridSupportFragment {

    private static final String TAG = ItemCountryFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 4;
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

        if (datatype.equalsIgnoreCase("Pay And Watch")) {
            setTitle("Pay and Watch");
          //  setBadgeDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ul_pay_and_watch));
        } else {
            setTitle("Movies");
           // setBadgeDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ul_movies));
        }
        //bgHelper = new BackgroundHelper(getActivity());
        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment(datatype);
    }

    private void setupFragment(String datatype) {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
       //   mAdapter = new ArrayObjectAdapter(cardPresenter);
        mAdapter = new ArrayObjectAdapter(new HorizontalCardPresenter(datatype));
        setAdapter(mAdapter);
        String type ="movies";
        int limit=10;
        int offset=0;
        fetchMovieData(id, type,limit,offset);
    }

    private void fetchMovieData(String id, String type,int limit,int offset) {

       /* @Query("type") String type,
        @Query("genre_id") String genre_id,
        @Query("filter") String filter,
        @Query("filter_type") String filter_type,
        @Query("limit") int limit,
        @Query("offset") int offset*/
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        Dashboard api = retrofit.create(Dashboard.class);

        Constants.IS_FROM_HOME=false;
        Call<List<BrowseData>> call = api.getBrowseDataList(Config.API_KEY,type,"" ,"","",10,offset);
        call.enqueue(new Callback<List<BrowseData>>() {
            @Override
            public void onResponse(@NonNull Call<List<BrowseData>> call, @NonNull Response<List<BrowseData>> response) {
                if (response.code() == 200) {
                    List<BrowseData> movieList = response.body();
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        //Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }

                    for (BrowseData movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                 //   movies.addAll(movieList);

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<BrowseData>> call, @NonNull Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });
    }
/*
    private void fetchMovieData(String id, int pageCount) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        MovieApi api = retrofit.create(MovieApi.class);
        if (datatype.equalsIgnoreCase("movie")) {
            datatype = "movie";
        }
        Constants.IS_FROM_HOME=false;
        Call<List<Movie>> call = api.getMovies(Config.API_KEY, pageCount, datatype);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                if (response.code() == 200) {
                    List<Movie> movieList = response.body();
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        //Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }

                    for (Movie movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                    movies.addAll(movieList);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });
    }
*/

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {

            Movie movie = (Movie) o;
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("video_id", movie.getVideosId());
            intent.putExtra("type", "movie");
            intent.putExtra("thumbImage", movie.getThumbnailUrl());
            intent.putExtra("poster_url", movie.getPosterUrl());
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("release", movie.getRelease());
            intent.putExtra("video_quality", movie.getVideoQuality());
            intent.putExtra("duration", movie.getRuntime());
            intent.putExtra("ispaid", movie.getIsPaid());
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
                //    fetchMovieData(id, pageCount);
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
