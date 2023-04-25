package com.ott.tv.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.TitleViewAdapter;
import androidx.leanback.widget.VerticalGridPresenter;

import com.ott.tv.Config;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.Genre;
import com.ott.tv.model.Movie;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.GenreApi;
import com.ott.tv.network.api.MovieApi;
import com.ott.tv.ui.activity.DetailsActivity;
import com.ott.tv.ui.activity.DetailsActivityPhando;
import com.ott.tv.ui.activity.ErrorActivity;
import com.ott.tv.ui.activity.ItemCountryActivity;
import com.ott.tv.ui.activity.ItemGenreActivity;
import com.ott.tv.ui.presenter.CardPresenter;
import com.ott.tv.ui.presenter.HorizontalCardGenrePresenter;
import com.ott.tv.ui.presenter.HorizontalCardPresenter;
import com.ott.tv.utils.PreferenceUtils;

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
    private String gener_id = "";
    private String typeCategory;
    private List<ShowWatchlist> genres = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardPresenter = new CardPresenter();
        mContext = getContext();
        datatype = getArguments().getString("type");
        gener_id = getArguments().getString("gener_id");

        Log.i(TAG, "onCreate: " + datatype + id + title);
        showTitle(false);
        //  setTitle("Genre");
        assert getArguments() != null;
        typeCategory = getArguments().getString("type");
/*
                     getArguments().getString("gener_id");
*/
        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment(datatype);
    }

    private void setupFragment(String datatype) {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        //  mAdapter = new ArrayObjectAdapter(cardPresenter);
        mAdapter = new ArrayObjectAdapter(new HorizontalCardPresenter(datatype));
        setAdapter(mAdapter);

        fetchMovieData(pageCount);

    }


    private void fetchMovieData(int pageCount) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        GenreApi api = retrofit.create(GenreApi.class);
        datatype = "movie";
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(getContext());
        Call<List<ShowWatchlist>> call = api.getGenresViewall(accessToken, gener_id, "0,100", "");

        call.enqueue(new Callback<List<ShowWatchlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShowWatchlist>> call, @NonNull Response<List<ShowWatchlist>> response) {
                if (response.code() == 200) {
                    List<ShowWatchlist> movieList = response.body();
                    if (movieList.size() <= 0) {
                        dataAvailable = false;
                        if (getContext() != null) {
                            final NoDataFragmant noDataFragmant = new NoDataFragmant();
                            final FragmentManager fm = getFragmentManager();
                            fm.beginTransaction().add(R.id.browserSection, noDataFragmant).commit();

                            //   Toast.makeText(getContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    }

                    for (ShowWatchlist movie : movieList) {
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + genres.size());
                    genres.addAll(movieList);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShowWatchlist>> call, @NonNull Throwable t) {
                Log.e("Genre Item", "code: " + t.getLocalizedMessage());
            }
        });


    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {


            if (getActivity() != null && getContext() != null) {
                ShowWatchlist videoContent = (ShowWatchlist) o;

                String status = new DatabaseHelper(getContext()).getActiveStatusData().getStatus();

                if (videoContent.getType().equals("M") && videoContent.getIs_live().toString().equalsIgnoreCase("0")) {
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
                        intent.putExtra("is_live", videoContent.getIs_live().toString());
                    if (videoContent.getRating() != null)
                        intent.putExtra("rating", videoContent.getRating().toString());
                    if (videoContent.getTrailers() != null && videoContent.getTrailers().size() > 0 && videoContent.getTrailers().get(0) != null && videoContent.getTrailers().get(0).getMedia_url() != null) {
                        intent.putExtra("trailer", videoContent.getTrailers().get(0).getMedia_url());
                    }

//kranti
                    if (videoContent.getGenres() != null) {
                        if (videoContent.getGenres().size() > 0) {
                            String genres;
                            genres = videoContent.getGenres().get(0);
                            for (int i = 1; i < videoContent.getGenres().size(); i++) {
                                genres = genres.concat("," + videoContent.getGenres().get(i));
                            }
                            intent.putExtra("genres", genres);
                        }
                    }
                    getContext().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

                }
                if (videoContent.getType().equals("T")) {
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
                        intent.putExtra("is_live", videoContent.getIs_live().toString());
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
                    getContext().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

                }
                if (videoContent.getType().equals("M") && videoContent.getIs_live().toString().equalsIgnoreCase("1")) {
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
                        intent.putExtra("is_live", videoContent.getIs_live().toString());
                    if (videoContent.getRating() != null)
                        intent.putExtra("rating", videoContent.getRating().toString());
                    if (videoContent.getTrailers() != null && videoContent.getTrailers().size() > 0 && videoContent.getTrailers().get(0) != null && videoContent.getTrailers().get(0).getMedia_url() != null) {
                        intent.putExtra("trailer", videoContent.getTrailers().get(0).getMedia_url());
                    }

//kranti
                    if (videoContent.getGenres() != null) {
                        String genres;
                        genres = videoContent.getGenres().get(0);
                        for (int i = 1; i < videoContent.getGenres().size(); i++) {
                            genres = genres.concat("," + videoContent.getGenres().get(i));
                        }
                        intent.putExtra("genres", genres);
                    }
                    getContext().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

                }
                if (videoContent.getType().equalsIgnoreCase("VM")) {
                    Intent intent = new Intent(getActivity(), ItemCountryActivity.class);
                    intent.putExtra("id", videoContent.getId().toString());

                    intent.putExtra("title", videoContent.getTitle());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                }
                if (videoContent.getType().equalsIgnoreCase("OTT")) {
                    //   Intent intent = new Intent(context, ItemCountryActivity.class);

                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(
                            videoContent.getAndroid_link().substring(videoContent.getAndroid_link().lastIndexOf("=") + 1)
                    );

                    if (intent == null) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(videoContent.getAndroid_link()));
                    }

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);


                }


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
                    fetchMovieData(pageCount);
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
