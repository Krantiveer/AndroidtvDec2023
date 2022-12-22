package com.ott.tv.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.model.Movie;
import com.ott.tv.model.VideoContent;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.FavouriteApi;
import com.ott.tv.ui.BackgroundHelper;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;
import com.ott.tv.ui.activity.ErrorActivity;
import com.ott.tv.ui.activity.LeanbackActivity;
import com.ott.tv.ui.presenter.HorizontalCardPresenter;
import com.ott.tv.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FavouriteFragment extends VerticalGridSupportFragment {

    public static final String FAVORITE = "favorite";

    private static final String TAG = FavouriteFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 4;

    private BackgroundHelper bgHelper;

    private LinkedHashMap<String, List<VideoContent>> mVideoLists = null;
    private ArrayObjectAdapter mAdapter;

    private LeanbackActivity activity;
    private List<Movie> movies = new ArrayList<>();
    private boolean dataAvailable;
    private int pageCount = 1;
    public String watchListType = "";
    private String title;
    private String id = "";
    private String datatype = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        activity = (LeanbackActivity) getActivity();

        if (getActivity() != null) {
            id = getActivity().getIntent().getStringExtra("id");
            activity = (LeanbackActivity) getActivity();
            title = getActivity().getIntent().getStringExtra("title");
            datatype = getArguments().getString("type");
        }
        Log.i(TAG, "onCreate:data " + datatype + id + title);
        if (datatype.equalsIgnoreCase("Watch Later")) {
            setTitle("Watch Later");
            //setBadgeDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ul_watchlater));
        } else {
            setTitle("Favourite");
            //setBadgeDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ul_fav));
        }
        // activity.showLogo();;
        activity.showLogo();
        //  setTitle(getResources().getString(R.string.favorite));

        bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
      //  setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment();
    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        mAdapter = new ArrayObjectAdapter(new HorizontalCardPresenter(FAVORITE));
        /*mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(FAVORITE));*/
        setAdapter(mAdapter);

        if (datatype.equalsIgnoreCase("Watch Later")) {

            fetch_WacthLaterData();
            } else {
            fetch_FavouriteData();
            }

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
        return (itemViewHolder, item, rowViewHolder, row) -> {
            // pagination
            if (dataAvailable) {
                int itemPos = mAdapter.indexOf(item);
                if (itemPos == movies.size() - 1) {
                    pageCount++;
                    if (datatype.equalsIgnoreCase("Watch Later")) {
                        fetch_FavouriteData();
                    } else {
                        fetch_WacthLaterData();
                    }
                }
            }

            if (item instanceof Movie) {
                bgHelper = new BackgroundHelper(getActivity());
                bgHelper.prepareBackgroundManager();
                bgHelper.startBackgroundTimer(((Movie) item).getThumbnailUrl());

            }

        };
    }


    public void fetch_FavouriteData() {
//Todo : when network/ net not there it goinging to erroractivity and finish activity.
//Todo 1 : rewrite code

        if (!new NetworkInst(activity).isNetworkAvailable()) {
            Intent intent = new Intent(activity, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
            return;
        }

        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        assert fm != null;
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();
        String userId = PreferenceUtils.getUserId(getContext());
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        watchListType = Constants.WishListType.fav;
        Call<List<Movie>> call = api.getFavoriteList(Config.API_KEY, userId, pageCount, watchListType);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull retrofit2.Response<List<Movie>> response) {
                List<Movie> movieList = response.body();
                if (movieList.size() <= 0) {
                    dataAvailable = false;
//Todo 3 : write code for toast check also it crashing
                    //     Toast.makeText(getContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                t.printStackTrace();
                // hide the spinner
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
            }
        });

    }
    public void fetch_WacthLaterData() {
//Todo : when network/ net not there it goinging to erroractivity and finish activity.
//Todo 1 : rewrite code

        if (!new NetworkInst(activity).isNetworkAvailable()) {
            Intent intent = new Intent(activity, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
            return;
        }

        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        assert fm != null;
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();
        String userId = PreferenceUtils.getUserId(getContext());
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        watchListType = Constants.WishListType.watch_later;
        Call<List<Movie>> call = api.getFavoriteList(Config.API_KEY, userId, pageCount, watchListType);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull retrofit2.Response<List<Movie>> response) {
                List<Movie> movieList = response.body();
                if (movieList.size() <= 0) {
                    dataAvailable = false;
//Todo 3 : write code for toast check also it crashing
                    //     Toast.makeText(getContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                t.printStackTrace();
                // hide the spinner
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
            }
        });

    }
}
