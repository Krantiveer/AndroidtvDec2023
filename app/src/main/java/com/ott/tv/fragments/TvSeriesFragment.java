package com.ott.tv.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.model.Movie;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.TvSeriesApi;
import com.ott.tv.ui.BackgroundHelper;
import com.ott.tv.ui.activity.ErrorActivity;

import com.ott.tv.ui.presenter.VerticalCardPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvSeriesFragment extends VerticalGridSupportFragment {

    public static final String TV_SERIES = "tvSeries";
    private static final String TAG = TvSeriesFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 8;
    private int pageCount = 1;
    private boolean dataAvailable = true;


    private BackgroundHelper bgHelper;

    private List<Movie> movies = new ArrayList<>();
    private ArrayObjectAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);


        setTitle(getResources().getString(R.string.tv_series));

        bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
      //  setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        // setup
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(TV_SERIES));
        setAdapter(mAdapter);

        fetchTvSereisData(pageCount);


        //setupFragment();
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o,
                                      RowPresenter.ViewHolder viewHolder2, Row row) {

                Movie movie = (Movie) o;

                Intent intent = new Intent(getActivity(), VideoDetailsFragment.class);
                intent.putExtra("id", movie.getVideosId());
                intent.putExtra("type", "tvseries");
                intent.putExtra("thumbImage", movie.getThumbnailUrl());
                //poster transition
                ImageView imageView = ((ImageCardView) viewHolder.view).getMainImageView();
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        imageView, VideoDetailsFragment.TRANSITION_NAME).toBundle();

                startActivity(intent, bundle);

            }
        };
    }


    // selected listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, final Object item,
                                       RowPresenter.ViewHolder rowViewHolder, Row row) {

                // pagination
                if (dataAvailable) {
                    int itemPos = mAdapter.indexOf(item);
                    if (itemPos == movies.size() - 1) {
                        pageCount++;
                        fetchTvSereisData(pageCount);
                    }
                }

                //Log.d("iamge url: ------------------------------", itemPos+" : "+ movies.size());
                // change the background color when the item will select
                if (item instanceof Movie) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((Movie) item).getThumbnailUrl());

                }

            }
        };
    }

    public void fetchTvSereisData(int pageCount) {



        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();

          Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        Log.d("kranti",""+getActivity());
        TvSeriesApi api = retrofit.create(TvSeriesApi.class);
        Call<List<Movie>> call = api.getTvSeries(Config.API_KEY, pageCount);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.code() == 200) {
                    List<Movie> movieList = response.body();
                    if (movieList.size() == 0) {
                        dataAvailable = false;
                        if (pageCount != 2) {
                            
                        }
                    }

                    for (Movie movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                    movies.addAll(movieList);
                    //setAdapter(mAdapter);

                    // hide the spinner
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();

                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                t.printStackTrace();
                // hide the spinner
                fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        movies = new ArrayList<>();
        pageCount = 1;
        dataAvailable = true;

    }

}
