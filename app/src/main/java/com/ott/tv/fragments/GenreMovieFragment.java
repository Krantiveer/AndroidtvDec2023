package com.ott.tv.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.model.Genre;
import com.ott.tv.model.Movie;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.GenreApi;
import com.ott.tv.network.api.MovieApi;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.ErrorActivity;
import com.ott.tv.ui.activity.ItemGenreActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.ui.presenter.HorizontalCardGenrePresenter;
import com.ott.tv.ui.presenter.HorizontalCardPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenreMovieFragment extends VerticalGridSupportFragment {

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

    private List<Genre> genres = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardPresenter = new CardPresenter();
        mContext = getContext();

        Log.i(TAG, "onCreate: " + datatype + id + title);
/*
        showTitle(false);
*/
        setTitle("Genre");

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment(datatype);
    }

    private void setupFragment(String datatype) {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        //  mAdapter = new ArrayObjectAdapter(cardPresenter);
        mAdapter = new ArrayObjectAdapter(new HorizontalCardGenrePresenter("movies"));
        setAdapter(mAdapter);
        fetchMovieData( pageCount);

    }


    private void fetchMovieData( int pageCount) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        GenreApi api = retrofit.create(GenreApi.class);
        datatype = "movie";
        Call<List<Genre>> call = api.getGenres(Config.API_KEY, pageCount);

        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(@NonNull Call<List<Genre>> call, @NonNull Response<List<Genre>> response) {
                if (response.code() == 200) {
                    List<Genre> movieList = response.body();
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        //Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }

                    for (Genre movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + genres.size());
                    genres.addAll(movieList);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Genre>> call, @NonNull Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });


    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {
            Genre genre = (Genre) o;
            Intent intent = new Intent(getActivity(), ItemGenreActivity.class);
            intent.putExtra("id", genre.getGenreId());
            intent.putExtra("title", genre.getName());
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
