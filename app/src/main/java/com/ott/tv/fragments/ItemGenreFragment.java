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
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.NetworkInst;
import com.ott.tv.model.Movie;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.MovieApi;
import com.ott.tv.ui.BackgroundHelper;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;
import com.ott.tv.ui.activity.ErrorActivity;
import com.ott.tv.ui.activity.ItemGenreActivity;
import com.ott.tv.ui.activity.VideoDetailsActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.ui.presenter.HorizontalCardPresenter;
import com.ott.tv.ui.presenter.VerticalCardPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ItemGenreFragment extends VerticalGridSupportFragment {

    private static final String TAG = ItemGenreFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 5;
    private List<Movie> movies = new ArrayList<>();
    private ArrayObjectAdapter mAdapter;
    private BackgroundHelper bgHelper;
    public static final String MOVIE = "movie";
    private int pageCount = 1;
    private boolean dataAvailable = true;


    private Context mContext;
    private String title;
    private String id = "";
    private ItemGenreActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        title = getActivity().getIntent().getStringExtra("title");
        id = getActivity().getIntent().getStringExtra("id");
        activity = (ItemGenreActivity) getActivity();

        setTitle(title);
        bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        //  setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment();

    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        // mAdapter = new ArrayObjectAdapter(new CardPresenter());
        mAdapter = new ArrayObjectAdapter(new HorizontalCardPresenter(MOVIE));
        setAdapter(mAdapter);
        fetchMovieData(id, pageCount);
    }

    private void fetchMovieData(String id, int pageCount) {

        if (!new NetworkInst(mContext).isNetworkAvailable()) {
            Intent intent = new Intent(mContext, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
            return;
        }

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        MovieApi api = retrofit.create(MovieApi.class);
        Call<List<Movie>> call = api.getMovieByGenre(Config.API_KEY, id, pageCount);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
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
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {
            Movie movie = (Movie) o;
            if(movie.getIsTvseries().equalsIgnoreCase("1")||movie.getIsTvseries().equalsIgnoreCase("tvseries")){
             movie.setIsTvseries("tvseries");
                Intent intent = new Intent(getActivity(), DetailsActivityTvSeries.class);
                intent.putExtra("video_id", movie.getVideosId());
                intent.putExtra("type", movie.getIsTvseries());
                intent.putExtra("thumbImage", movie.getThumbnailUrl());
                intent.putExtra("poster_url", movie.getPosterUrl());
                intent.putExtra("title", movie.getTitle());
                Log.e("@@title", movie.getTitle()+"Dd");
                intent.putExtra("description", movie.getDescription());
                intent.putExtra("release", movie.getRelease());
                intent.putExtra("video_quality", movie.getVideoQuality());
                intent.putExtra("duration", movie.getRuntime());
                intent.putExtra("ispaid", movie.getIsPaid());
                startActivity(intent);
            }else{
                movie.setIsTvseries("movie");
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("video_id", movie.getVideosId());
                intent.putExtra("type", movie.getIsTvseries());
                intent.putExtra("thumbImage", movie.getThumbnailUrl());
                intent.putExtra("poster_url", movie.getPosterUrl());
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("description", movie.getDescription());
                intent.putExtra("release", movie.getRelease());
                intent.putExtra("video_quality", movie.getVideoQuality());
                intent.putExtra("duration", movie.getRuntime());
                intent.putExtra("ispaid", movie.getIsPaid());
                startActivity(intent);
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
                        fetchMovieData(id, pageCount);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        movies = new ArrayList<>();
        pageCount = 1;
        dataAvailable = true;

    }

}
