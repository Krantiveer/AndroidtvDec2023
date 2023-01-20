package com.ott.tv.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.Movie;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.ui.activity.DetailsActivityPhando;
import com.ott.tv.ui.activity.LeanbackActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.ui.presenter.HorizontalCardPresenter;
import com.ott.tv.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowWatchlistFragment extends VerticalGridSupportFragment {

    private static final String TAG = ItemCountryFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 4;
    private List<ShowWatchlist> movies = new ArrayList<>();
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
    public void onResume() {
        super.onResume();
        // setupFragment("");
        // fetchMovieData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardPresenter = new CardPresenter();
        mContext = getContext();
        if (getActivity() != null) {
            id = getActivity().getIntent().getStringExtra("id");
            title = getActivity().getIntent().getStringExtra("title");
            datatype = getArguments().getString("type");
        }
        Log.i(TAG, "onCreate: " + datatype + id + title);

        if (datatype.equalsIgnoreCase("Watchlist")) {
            // setTitle("Watchlist");
        } else {
            //      setTitle("Watchlist");
        }
        //  showTitle(false);
        //bgHelper = new BackgroundHelper(getActivity());
        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());
        Constants.IS_FROM_HOME = false;
        setupFragment(datatype);
    }

    private void setupFragment(String datatype) {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        //  setCustomPadding();

        //  mAdapter = new ArrayObjectAdapter(cardPresenter);
        mAdapter = new ArrayObjectAdapter(new HorizontalCardPresenter(datatype));
        setAdapter(mAdapter);
        String type = "movies";
        int limit = 10;
        int offset = 0;
        //fetchMovieData(id, type, limit, offset);
        fetchMovieData();
    }
    private void setCustomPadding() {
        if (getView() != null) {
            getView().setPadding(920, 0, 10, -500);
        }
    }

    private void fetchMovieData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        Dashboard api = retrofit.create(Dashboard.class);
        Constants.IS_FROM_HOME = false;
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(mContext);
        Call<List<ShowWatchlist>> call = api.getShowWishListAPI(accessToken);
        call.enqueue(new Callback<List<ShowWatchlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShowWatchlist>> call, @NonNull Response<List<ShowWatchlist>> response) {
                if (response.code() == 200) {

                    List<ShowWatchlist> movieList = response.body();
                    assert movieList != null;
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        if(getContext()!=null){
                        Toast.makeText(getContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }}
                    mAdapter.clear();
                    for (ShowWatchlist movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                    //   movies.addAll(movieList);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShowWatchlist>> call, @NonNull Throwable t) {
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
/*

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
*/


            ShowWatchlist videoContent = (ShowWatchlist) o;

            String status = new DatabaseHelper(getContext()).getActiveStatusData().getStatus();
            if (videoContent.getType().equals("M")) {
                Intent intent = new Intent(getActivity(), DetailsActivityPhando.class);
                if (videoContent.getType() != null)
                    intent.putExtra("type", videoContent.getType());
                if (videoContent.getThumbnail() != null)
                    intent.putExtra("thumbImage", videoContent.getThumbnail());
                if (videoContent.getId() != null)
                    intent.putExtra("video_id", videoContent.getId().toString());
                if (videoContent.getTitle() != null)
                    intent.putExtra("title", videoContent.getTitle());
                if (videoContent.getDetail() != null)
                    intent.putExtra("description", videoContent.getDetail());
                if (videoContent.getRelease_date() != null)
                    intent.putExtra("release", videoContent.getRelease_date());
                if (videoContent.getDuration_str() != null)
                    intent.putExtra("duration", videoContent.getDuration_str());
                if (videoContent.getMaturity_rating() != null)
                    intent.putExtra("maturity_rating", videoContent.getMaturity_rating());
                if (videoContent.getIs_free() != null)
                    intent.putExtra("ispaid", videoContent.getIs_free().toString());
                if (videoContent.getLanguage_str() != null)
                    intent.putExtra("language_str", videoContent.getLanguage_str());
                if (videoContent.getIs_live() != null)
                    intent.putExtra("is_live", videoContent.getIs_live());
                if (videoContent.getRating() != null)
                    intent.putExtra("rating", videoContent.getRating().toString());
                if (videoContent.getTrailers() != null && videoContent.getTrailers().size() > 0 && videoContent.getTrailers().get(0) != null && videoContent.getTrailers().get(0).getMedia_url() != null) {
                    intent.putExtra("trailer", videoContent.getTrailers().get(0).getMedia_url());
                }


                if (videoContent.getGenres() != null) {
                    String genres;
                    genres = videoContent.getGenres().get(0);
                    for (int i = 1; i < videoContent.getGenres().size(); i++) {
                        genres = genres.concat("," + videoContent.getGenres().get(i));
                    }
                    intent.putExtra("genres", genres);
                }
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }

            //startActivity(intent);
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
       // fetchMovieData();

    }





}
