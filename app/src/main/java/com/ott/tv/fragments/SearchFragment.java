package com.ott.tv.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SpeechRecognitionCallback;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.model.Movie;
import com.ott.tv.model.SearchContent;
import com.ott.tv.model.SearchModel;
import com.ott.tv.model.TvModel;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.model.phando.MapList;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.network.api.SearchApi;
import com.ott.tv.ui.Utils;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.PlayerActivity;
import com.ott.tv.ui.presenter.SearchCardPresenter;
import com.ott.tv.ui.presenter.TvSearchPresenter;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;

//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class SearchFragment extends androidx.leanback.app.SearchFragment implements androidx.leanback.app.SearchFragment.SearchResultProvider {

    private static final String TAG = "SearchFragment";

    private static final int REQUEST_SPEECH = 0x00000010;
    private static final long SEARCH_DELAY_MS = 1000L;
    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private int page_number = 1;
    private String mQuery;
    private List<SearchContent> mItems = new ArrayList<>();
    private List<SearchModel> searchList = new ArrayList<>();
    private String tvHeader = "";
    private String tvSeriesHeader = "";
    private String movieHeader = "";


    private final Runnable mDelayedLoad = () -> {
        //loadRows();
        Log.e(TAG, "code: ");
        getQueryData();

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        setSearchResultProvider(this);

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());

        if (!Utils.hasPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
            // SpeechRecognitionCallback is not required and if not provided recognition will be handled
            // using internal speech recognizer, in which case you must have RECORD_AUDIO permission
            setSpeechRecognitionCallback(() -> {
                Log.e(TAG, "recognizeSpeech");
            //    getQueryData();
                try {
                    startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
                } catch (ActivityNotFoundException e) {
                    Log.e(TAG, "Cannot find activity for speech recognizer", e);
                }
            });
        }
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o,
                                      RowPresenter.ViewHolder viewHolder2, Row row) {
                Log.e(TAG, "click");
                SearchContent searchContent = (SearchContent) o;
                switch (searchContent.getType()) {
                    case "tv": {
                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        intent.putExtra("id", searchContent.getId());
                        intent.putExtra("videoType", searchContent.getStreamFrom());
                        intent.putExtra("streamUrl", searchContent.getStreamUrl());
                        startActivity(intent);
                        break;
                    }
                    case "tvseries": {
                        Intent intent = new Intent(getActivity(), VideoDetailsFragment.class);
                        intent.putExtra("id", searchContent.getId());
                        intent.putExtra("type", "tvseries");
                        intent.putExtra("thumbImage", searchContent.getThumbnailUrl());
                        startActivity(intent);
                        break;
                    }
                    case "movie": {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                  /*      intent.putExtra("id", searchContent.getId());
                        intent.putExtra("type", "movie");
                        intent.putExtra("thumbImage", searchContent.getThumbnailUrl());
                        startActivity(intent);*/
                        intent.putExtra("id", searchContent.getId());
                        intent.putExtra("type", searchContent.getType());
                        intent.putExtra("thumbImage", searchContent.getThumbnailUrl());
                        intent.putExtra("poster_url", searchContent.getPosterUrl());
                        intent.putExtra("title", searchContent.getTitle());
                        intent.putExtra("description", searchContent.getDescription());
                        intent.putExtra("release", searchContent.getRelease());
                        intent.putExtra("video_quality", searchContent.getVideoQuality());
                        intent.putExtra("duration", searchContent.getRuntime());
                        intent.putExtra("ispaid", "1");
                        getContext().startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

                        break;
                    }
                }
            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult requestCode=" + requestCode +
                " resultCode=" + resultCode +
                " data=" + data);

        switch (requestCode) {
            case REQUEST_SPEECH:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        setSearchQuery(data, true);
                        break;
                    case RecognizerIntent.RESULT_CLIENT_ERROR:
                        Log.e(TAG, Integer.toString(requestCode));
                }
        }
    }

    public boolean hasResults() {
        return mRowsAdapter.size() > 0;
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        Log.e(TAG, "getResultsAdapter");
        // mRowsAdapter (Search result) has prepared in loadRows method
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        Log.e(TAG, String.format("Search Query Text Change %s", newQuery));
        //loadQueryWithDelay(newQuery, SEARCH_DELAY_MS);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e(TAG, String.format("Search Query Text Submit %s", query));
        // No need to delay(wait) loadQuery, since the query typing has completed.
        loadQueryWithDelay(query, 0);
        return true;
    }

    private void loadQueryWithDelay(String query, long delay) {
        mHandler.removeCallbacks(mDelayedLoad);
        if (!TextUtils.isEmpty(query) && !query.equals("")) {
            mQuery = query;
            mHandler.postDelayed(mDelayedLoad, delay);
            Log.e(TAG,  "Handler started");
        }
    }
      private void getQueryData() {
        final String query = mQuery;

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        Dashboard searchApi = retrofit.create(Dashboard.class);
        Constants.IS_FROM_HOME = false;
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(getContext());
        Log.e(TAG, "getQueryData: "+""+query);

        Call<List<ShowWatchlist>> call = searchApi.searchVideo(Config.API_KEY, "r", "","", "50");
        call.enqueue(new Callback<List<ShowWatchlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShowWatchlist>> call, Response<List<ShowWatchlist>> response) {
                Log.e(TAG,  "response: " + response.code());
                mItems = new ArrayList<>();
                List<ShowWatchlist> movieResult = new ArrayList<>();
                List<Movie> tvSeriesResult = new ArrayList<>();
                List<TvModel> tvResult = new ArrayList<>();
                if (response.code() == 200) {


                    if (response.body().size() != 0) {
                        movieHeader = "Movies";
                        movieResult.clear();
                        movieResult = response.body();
                        for (ShowWatchlist video : movieResult) {
                            String id = video.getId().toString();
                            String title = video.getTitle();
                            String description = video.getDetail();
                            String type = "movie";
                            String streamUrl = "";
                            String streamFrom = "";
                            String thumbnailUrl = video.getThumbnail();
                            SearchContent searchContent = new SearchContent(id, title, description, type, streamUrl, streamFrom, thumbnailUrl);
                            mItems.add(searchContent);
                        }
                    }
                    loadRows(movieResult, tvSeriesResult, tvResult);
                }

            }

            @Override
            public void onFailure(Call<List<ShowWatchlist>> call, Throwable t) {
                Log.e(TAG, "response : " + t.getLocalizedMessage());
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private void loadRows(final List<ShowWatchlist> movieResult, final List<Movie> tvSeriesResult, final List<TvModel> tvResult) {
        // offload processing from the UI thread
        new AsyncTask<String, Void, List<ListRow>>() {
            private final String query = mQuery;

            @Override
            protected void onPreExecute() {
                mRowsAdapter.clear();
            }

            @Override
            protected List<ListRow> doInBackground(String... params) {
                final List<SearchContent> result = new ArrayList<>();

                for (SearchContent video : mItems) {
                    // Main logic of search is here.
                    // Just check that "query" is contained in Title or Description or not. (NOTE: excluded studio information here)
                    if (video.getTitle().toLowerCase(Locale.ENGLISH).contains(query.toLowerCase(Locale.ENGLISH))
                            || video.getDescription().toLowerCase(Locale.ENGLISH).contains(query.toLowerCase(Locale.ENGLISH))) {
                        result.add(video);
                    }
                }

                List<ListRow> listRows = new ArrayList<>();

                ArrayObjectAdapter movieAdapter = new ArrayObjectAdapter(new SearchCardPresenter());
                ArrayObjectAdapter tvSeriesAdapter = new ArrayObjectAdapter(new SearchCardPresenter());
                ArrayObjectAdapter tvAdapter = new ArrayObjectAdapter(new TvSearchPresenter());

                for (SearchContent video : mItems) {
                    if (video.getType().equalsIgnoreCase("movie")) {
                        movieAdapter.add(video);
                    } else if (video.getType().equalsIgnoreCase("tvseries")) {
                        tvSeriesAdapter.add(video);
                    } else if (video.getType().equalsIgnoreCase("tv")) {
                        tvAdapter.add(video);
                    }
                }

                listRows.add(new ListRow(new HeaderItem(movieHeader), movieAdapter));
                listRows.add(new ListRow(new HeaderItem(tvSeriesHeader), tvSeriesAdapter));
                listRows.add(new ListRow(new HeaderItem(tvHeader), tvAdapter));

                return listRows;
            }

            @Override
            protected void onPostExecute(List<ListRow> listRow) {
                for (ListRow listRow1 : listRow) {
                    mRowsAdapter.add(listRow1);
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private void loadChannelRows(List<Movie> movieResult, List<Movie> tvSeriesResult, List<TvModel> tvResult) {

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        SearchCardPresenter searchCardPresenter = new SearchCardPresenter();
        HeaderItem header;


        if (movieResult.size() != 0) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(searchCardPresenter);

            for (Movie movie : movieResult) {
                listRowAdapter.add(movie);
            }

            rowsAdapter.add(new ListRow(new HeaderItem(0, "Movies"), listRowAdapter));

        }

        if (tvSeriesResult.size() != 0) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(searchCardPresenter);

            for (Movie movie : tvSeriesResult) {
                listRowAdapter.add(movie);
            }
            rowsAdapter.add(new ListRow(new HeaderItem(0, "Tv Series"), listRowAdapter));
        }

        if (tvResult.size() != 0) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(searchCardPresenter);

            for (TvModel tvModel : tvResult) {
                listRowAdapter.add(tvModel);
            }
            rowsAdapter.add(new ListRow(new HeaderItem(0, "Live TV"), listRowAdapter));
        }

        mRowsAdapter.add(rowsAdapter);
    }
}
