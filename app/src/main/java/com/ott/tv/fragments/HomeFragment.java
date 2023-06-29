package com.ott.tv.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;

import com.ott.tv.Constants;
import com.ott.tv.NetworkInst;
import com.ott.tv.R;
import com.ott.tv.database.homeContent.HomeContentViewModel;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.Channel;
import com.ott.tv.model.VideoContent;
import com.ott.tv.model.home_content.HomeContent;
import com.ott.tv.model.phando.LatestMovieList;
import com.ott.tv.model.home_content.LatestTvseries;

import com.ott.tv.network.api.Dashboard;
import com.ott.tv.ui.activity.DetailsActivityPhando;
import com.ott.tv.ui.activity.DetailsActivityTvSeries;
import com.ott.tv.ui.activity.ItemCountryActivity;
import com.ott.tv.ui.activity.LoginChooserActivity;
import com.ott.tv.ui.activity.NewMainActivity;
import com.ott.tv.ui.presenter.CardPresenterBanner;
import com.ott.tv.ui.presenter.CardPresenterNewLanscape;
import com.ott.tv.ui.presenter.SliderCardPresenter;
import com.ott.tv.ui.presenter.TvPresenter;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.ui.BackgroundHelper;
import com.ott.tv.ui.activity.ErrorActivity;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends RowsSupportFragment {

    private static final String TAG = "HomeFragment";
    private BackgroundHelper bgHelper;
    private ArrayObjectAdapter rowsAdapter;
    private CardPresenterNewLanscape cardPresenter;
    private CardPresenterBanner cardPresenterBanner;
    private SliderCardPresenter sliderCardPresenter;
    private HomeContent homeContent = null;
    private List<BrowseData> movieListContent = null;
    private HomeContentViewModel homeContentViewModel;
    private ImageView imageView;
    FrameLayout headerView;
    private String typeCategory;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            title = getActivity().getIntent().getStringExtra("title");
        }
        if (getActivity() != null) {
            bgHelper = new BackgroundHelper(getActivity());

            assert getArguments() != null;

            typeCategory = getArguments().getString("type_id");

           /* if(typeCategory.equalsIgnoreCase("catgeory")){
                typeCategory=getArguments().getString("type_id");
            }*/
            NewMainActivity activity = (NewMainActivity) getActivity();
          //  activity.showLogo();
            setOnItemViewClickedListener(getDefaultItemViewClickedListener());
            setOnItemViewSelectedListener(getDefaultItemSelectedListener());
            if (new NetworkInst(activity).isNetworkAvailable()) {
                //    getHomeContentDataFromServer();
                String id = "";
                int limit = 10;
                int offset = 0;
                fetchMovieData(id, typeCategory, limit, offset);

            } else {
                Intent intent = new Intent(activity, ErrorActivity.class);
                startActivity(intent);
                activity.finish();
            }
        }
    }

    private void fetchMovieData(String id, String type, int limit, int offset) {
        if (getActivity() != null) {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            Dashboard api = retrofit.create(Dashboard.class);
            Constants.IS_FROM_HOME = false;
            String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(requireContext());

            Call<List<BrowseData>> call = api.getBrowseDataList(accessToken, type, "", "", "", 10, offset);
            call.enqueue(new Callback<List<BrowseData>>() {
                @Override
                public void onResponse(@NonNull Call<List<BrowseData>> call, @NonNull Response<List<BrowseData>> response) {
                    if (response.code() == 200) {
                        movieListContent = response.body();

                        if(movieListContent.size()<=0)
                        {
                            if (getContext() != null) {
                                final NoDataFragmant noDataFragmant = new NoDataFragmant();
                                final FragmentManager fm = getFragmentManager();
                                fm.beginTransaction().add(R.id.browserSection, noDataFragmant).commit();
                                return;

                                //   Toast.makeText(getContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                            }
                        }
                        //  homeContent.setHomeContentId(1);
                        //   homeContent.getSlider();
                        //  loadSliderRows(homeContent.getSlider().getSlideArrayList());

                        loadRows();
                    /*if (movieList.size() <= 0) {

                    }

                    for (BrowseData movie : movieList) {

                    }
                    //   movies.addAll(movieList);*/

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
                public void onFailure(@NonNull Call<List<BrowseData>> call, @NonNull Throwable t) {
                    //   CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                    if (getContext() != null) {
                        final NoDataFragmant noDataFragmant = new NoDataFragmant();
                        final FragmentManager fm = getFragmentManager();
                        fm.beginTransaction().add(R.id.browserSection, noDataFragmant).commit();
                        return;
                      //  Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {

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
                Toast.makeText(getContext(),"You've been logged out because we have detected another login from your ID on a different device. You are not allowed to login on more than one device at a time.",Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        }
    }
/*
    private void getHomeContentDataFromServer() {
        if (getActivity() != null) {
            String userId = new DatabaseHelper(requireContext()).getUserData().getUserId();

            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            HomeApi api = retrofit.create(HomeApi.class);
            Call<HomeContent> call = api.getHomeContent(Config.API_KEY, userId);

            call.enqueue(new Callback<HomeContent>() {
                @Override
                public void onResponse(@NotNull Call<HomeContent> call, @NotNull retrofit2.Response<HomeContent> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            homeContent = response.body();
                            homeContent.setHomeContentId(1);
                            //   homeConftent.getSlider();
                            //  loadSliderRows(homeContent.getSlider().getSlideArrayList());
                            loadRows(homeContent.getFeaturesGenreAndMovie(), homeContent.getSlider().getSlideArrayList());

                        } else if (response.errorBody() != null) {
                            CMHelper.setSnackBar(requireView(), response.errorBody().toString(), 2);
                        } else {
                            CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                        }
                    } else {
                        CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HomeContent> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    CMHelper.setSnackBar(requireView(), t.getMessage(), 2);
                }
            });
        }
    }
*/

    private void loadRowsTV(List<LatestTvseries> homeContents) {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenterNewLanscape();
        cardPresenterBanner = new CardPresenterBanner();
        sliderCardPresenter = new SliderCardPresenter();
        TvPresenter tvPresenter = new TvPresenter();
        int i;
        for (i = 0; i < homeContents.size(); i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;
            if (i == 0) {
                // load slider
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, "");
            } else if (i == 1) {
                //load tv layout
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getTitle());
            } else {
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getTitle());
            }
            //for (int j = 0; j < NUM_COLS; j++) {
            for (int j = 0; j < homeContents.size(); j++) {
                LatestTvseries videoContent = homeContents.get(i);
                videoContent.setType("tv");
                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        setAdapter(rowsAdapter);
        setCustomPadding();
    }

    private void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenterNewLanscape();
        cardPresenterBanner = new CardPresenterBanner();
        // SliderCardPresenter sliderCardPresenter = new SliderCardPresenter();
        // TvPresenter tvPresenter = new TvPresenter();
   /*     for (int i = 0; i < 1; i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;

            listRowAdapter = new ArrayObjectAdapter(cardPresenterBanner);
            *//*listRowAdapter = new ArrayObjectAdapter(cardPresenter);*//*
            header = new HeaderItem(i, "");
            for (int j = 0; j < slideArrayList.size(); j++) {
                Video videoContent = slideArrayList.get(j);
                videoContent.setType(slideArrayList.get(j).getActionType());
                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));

        }*/


//todo: Here we need to create vertical thumnail for according to orientation of ui
        for (int i = 0; i < movieListContent.size(); i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;
       /*     if (movieListContent.get(i).getDisplayType().equalsIgnoreCase("TOP_BANNER")) {
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, "");


            } else*/ {

         /*   else if (i == 5) {
                //load tv layout
                listRowAdapter = new ArrayObjectAdapter(cardPresenterBanner);
                header = new HeaderItem(i, homeContents.get(i).getName());
            }*/

                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, movieListContent.get(i).getTitle());
                Log.i(TAG, "onItemSelected: "+"clickevent haha"+movieListContent.get(i).getTitle());

            }
            //for (int j = 0; j < NUM_COLS; j++) {
            for (int j = 0; j < movieListContent.get(i).getList().size(); j++) {
                LatestMovieList videoContent = movieListContent.get(i).getList().get(j);
                videoContent.setViewallTitle(movieListContent.get(i).getTitle());

              /*  if (movieListContent.get(i).getViewType().equalsIgnoreCase("tv")) {
                    videoContent.setType("tv");
                } else if (movieListContent.get(i).getViewType().equalsIgnoreCase("movie")) {
                    videoContent.setType("movie");
                } else if (movieListContent.get(i).getViewType().equalsIgnoreCase("tvseries")) {
                    videoContent.setType("tvseries");
                } else if (movieListContent.get(i).getViewType().equalsIgnoreCase("slider")) {
                    if (videoContent.getIsTvseries().equals("1")) {
                        videoContent.setType("tvseries");
                    } else if (videoContent.getIsTvseries().equals("0")) {
                        videoContent.setType("movie");
                    }
                } */
             /*
                if (movieListContent.get(i).getImage_orientation() == 0) {
                    // kranti --> here u need to set type
                } else {

                    //           videoContent.setType("movie");
                }*/

                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        setAdapter(rowsAdapter);
       // setCustomPadding();
    }

    private void setCustomPadding() {
        if (getView() != null) {
            getView().setPadding(20, 40, 10, -500);
        }
    }
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return (viewHolder, o, viewHolder2, row) -> {
            if (getActivity() != null && getContext() != null) {
                LatestMovieList videoContent = (LatestMovieList) o;

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
                    intent.putExtra("title", videoContent.getViewallTitle());

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
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return (itemViewHolder, item, rowViewHolder, row) -> {
            if (getActivity() != null) {
                if (item instanceof VideoContent) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((VideoContent) item).getPosterUrl());
                } else if (item instanceof Channel) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((Channel) item).getPosterUrl());

                }

            }
        };
    }

}
