/*
package com.ott.tv;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ott.tv.model.Event;
import com.ott.tv.music_service.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Retrofit;

public class ContentDetailsTopHomePage extends AppCompatActivity implements TrailerAndMoreDetailAdapter.ItemOnClickListener ,EpisodeListDisplayTabAdapter.ItemOnClickListenerEpisodeTabs{

    private static final String TAG = ContentDetailsActivityNeww.class.getSimpleName();

    private static final int DETAIL_THUMB_WIDTH_COVERPOSTER = 360;
    private static final int DETAIL_THUMB_HEIGHT_COVERPOSTER = 202;

    public static final String PARAM_CARD_DATA = "CardData";
    public static final String PARAM_CAROUSEL_INFO_DATA = "CarouselInfoData";
    public static final String SHARED_ELEMENT_NAME = "shared_element";

    public final static String PARAM_CARD_ID = "selected_card_id";
    public final static String PARAM_PARTNER_ID = "partner_content_id";
    public final static String PARAM_AD_ENBLED = "is_ad_enabled";
    public final static String PARAM_AD_PROVIDER = "ad_provider";
    public static final String PARAM_CARD_DATA_TYPE = "card_data_type";
    public final static String PARAM_AUTO_PLAY = "auto_play";
    */
/*public static CustomPaymentDilogue dialog;*//*

    public  static String price_contet;
    public static boolean paymentdailogue=false;
    public static boolean purchase_content=false;
    private String contentId;
    private int count ;
    private int tabsShowDisplayEnd ;
    private int totalNumbertabs ;
    private String display_oredre;
    List<String> displayTabs =new ArrayList<>();
    private int lastestEpisode;
    private int lastestEpisodeFrom;

    public RowsFragment mRowsFragment;
    public EpisodeListFragment episodeListFragment;
    public RowsFragment mRelatedRowsFragment;
    TextView mContentTitle;
    TextView mContentLanguage;
    TextView mContentDescription;
    TextView mTextViewCastHeading;
    TextView mTextViewDirectorHeading;
    TextView mContentCast;
    TextView mDirectorName;
    TextView mReadMore;
    private View mDescriptionViewContainer;
    private ArrayObjectAdapter mRowsAdapter,mRowsAdapterForRecommended;
    private CardData mSelectedContent;
    private CardData mEpisodesList = null;
    public static CardData hardCodeCardData;
    private CarouselInfoData mCarouselInfoData;
    private Drawable mImageDrawable;
    private View mContentCastContainer;
    private List<CardData> mDummyCarouselData = new ArrayList<>();
    private List<CardData> mSeasonList;
    private List<DetailsDescrptionsList> detailsDescrptionsLists = new ArrayList<>();
    private List<String> mSeasonNames;
    private ArrayList<CardData> similarContentList = new ArrayList<>();
    private Map<String, List<CardData>> sRelatedEpisodesCache = new HashMap<>();
    private ProgressBar mLastWatchedProgressBar;
    private Intent playerPendingItent;
    public TextView mContentGenre;
    public TextView mContentReleaseYear;
    public TextView mContentDuration;
    public TextView contentType;
    public ImageView hdImage, ccImage;
    public boolean isLoadMoreAvailable = false;
    public int position = 0;
    public int mStartIndex = 1;
    public boolean isLoadMoreRequest = false;
    public VerticalGridView mControlsGridView;
    public VerticalGridView mRelatedContentGridView;
    private String mSource;
    private String mSourceDetails;
    private View mDot1;
    private View mDot2;
    private View mDot3;
    private boolean alreadyLoggedIn;
    private boolean isRetryAlreadyDone = false;
    public static boolean isDeeplinkingURL = false;
    private ImageView relativeLayoutRoot;
    public Context mContext;
    public boolean watchlisted = false;
    ListRow watchlistListRow;
    private LinearLayout mFragmentContainer;
    private ContentDetailTopFragment mContentDetailTopFragment;
    private boolean shouldReverseTheBackgroundOfFragmentContainer = false;
    private String artistName, language;
    private ArtistConfig data;
    List<Artistcarousel> artistcarousels;
    TrailerAndMoreDetailAdapter trailerAndMoreDetailAdapter;
    EpisodeListDisplayTabAdapter episodeListDisplayTab;
    private RecyclerView tandmDetailsRecyclerView;
    private RecyclerView tandmDetailsRecyclerViewList;
    private FrameLayout episodeFrameLayout;
    private FrameLayout loadingProgressLayout;
    private ProgressBar progressBar;
    private ImageView manoramamaxFullLogo;
    private LinearLayout episodeFrameLayout_first_half;
    private List<String> totalCarouselNamesList = new CopyOnWriteArrayList<>();
    private Handler mHandler = new Handler();
    int initui = 0;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            setUpFragmentViews();
        }
    };
    private boolean isDirectedToPlayerScreen = false;
    private BroadcastReceiver localBroadCastReceiver;
    private List<String> directorList = new ArrayList<>();
    String directors = "";
    List<String> listOfMonths = new ArrayList<>();
    private int mEpisodesTabSelectedPosition=0;

    */
/**
     * Called when the activity is first created.
     *//*

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate called.");
        if(displayTabs !=null)
            displayTabs.clear();
        tabsShowDisplayEnd = 0;;
        loadingProgressLayout = findViewById(R.id.loadingProgress);
        manoramamaxFullLogo = findViewById(R.id.mmtv_full_logo);
        progressBar = findViewById(R.id.progress_bar);
        loadingProgressLayout.setVisibility(View.INVISIBLE);
        super.onCreate(savedInstanceState);
        PrefUtils.getInstance().setBackPressedFromPlayBackScreen(false);
        mSelectedContent = (CardData) getIntent().getSerializableExtra(PARAM_CARD_DATA);
        if(getIntent() != null && getIntent().hasExtra(CardPresenter.MOVIE_TAG)) {
            CardData movie = (CardData) getIntent().getSerializableExtra(CardPresenter.MOVIE_TAG);
            if(movie != null){
                mSelectedContent = movie;
            }
        }
        if (getIntent() != null && getIntent().hasExtra(Const.PERSON)) {
            artistName = getIntent().getStringExtra(Const.PERSON);
        }
        fetchpriceContent();
        itemFavoritesOrNot();
//      fetchpriceContent1();
        registerForElapsedTimeBroadCastMessages();
        PrefUtils.getInstance().setEpisode_show_display_id(mSelectedContent);
        loadElapsedTime();
    }

*/
/*public void pushDetailsToCleverTap() {
        if (mSelectedContent != null) {
        Map<String, String> params = new HashMap<>();
        params.put(Analytics.PROPERTY_CONTENT_NAME, mSelectedContent.getTitle());
        params.put(Analytics.PROPERTY_CONTENT_GENRE, mSelectedContent.getGenre());
        params.put(Analytics.PROPERTY_SERIES_NAME, Utils.fetchSeriesName(mSelectedContent) != null ? Utils.fetchSeriesName(mSelectedContent) : "na");
        params.put(Analytics.PROPERTY_CONTENT_TYPE, mSelectedContent.generalInfo.type);
        params.put(Analytics.PROPERTY_CONTENT_LANGUAGE, mSelectedContent.getLanguage());
        params.put(Analytics.PROPERTY_CONTENT_ID, String.valueOf(mSelectedContent._id));
        params.put(Analytics.CLEVERTAP_CONTENT_TAB, convertToLowerCase(PrefUtils.getInstance().getCurrentTab()));
        if (mSelectedContent.getSource() != null)
        params.put(PROPERTIES_SOURCE, mSelectedContent.getSource());
        else
        params.put(PROPERTIES_SOURCE, PrefUtils.getInstance().getSourceDetails());

        params.put(PROPERTIES_SOURCE_DETAILS, PrefUtils.getInstance().getCleverTapSourceDetails() != null ?
        convertToLowerCase(PrefUtils.getInstance().getCleverTapSourceDetails()) : mSelectedContent.getSourceDetails());
        params.put(Analytics.CLEVERTAP_CONTENT_MODEL, mSelectedContent.getFreeType() == true ? "Free" : "Paid");
        int userid = PrefUtils.getInstance().getPrefUserId();
        params.put(Analytics.USER_ID, userid == 0 ? Analytics.NOT_AVAILABLE : String.valueOf(userid));
        Analytics.mixpanelIdentify();
        Analytics.eventCalling(Analytics.EventPriority.MEDIUM, Analytics.CLEVERTAP_EVENT_BROWSED_CONTENT_DETAILS, params);
        }
        }*//*



*/
/*
private void fetchpriceContent() {
        ContentDetails.Params params = new ContentDetails.Params(mSelectedContent._id, "mdpi", "coverposter", 10, APIConstants.HTTP_NO_CACHE, "elapsedTime,playerConfig,relatedMedia,videos,generalInfo,packages,user/currentdata");
        ContentDetails contentDetails = new ContentDetails(params, new APICallback<CardResponseData>() {
@Override
public void onResponse(APIResponse<CardResponseData> response) {
//                Log.d(TAG, "Error body : size()" + response.body().results.size());
//                Log.d(TAG, "Error body : size() package" + response.body().results.get(0).packages);
        if (response != null && response.body() != null && response.body().results != null && response.body().results.size() > 0 &&
        response.body().results.get(0) != null && response.body().results.get(0).packages.size() > 0 &&
        response.body().results.get(0).packages != null && response.body().results.get(0).packages.get(0) != null &&
        response.body().results.get(0).packages.get(0).priceDetails.size() > 0 &&
        response.body().results.get(0).packages.get(0).priceDetails.get(0) != null && (response.body().results.get(0).generalInfo.isSellable) && !(response.body().results.get(0).generalInfo.contentRights.contains("avod"))) {
        Log.d(TAG, "Error body :" + response.body().results.get(0).packages.get(0).priceDetails.get(0).price);
        price_contet = String.valueOf(response.body().results.get(0).packages.get(0).priceDetails.get(0).price);
        paymentdailogue = true;
        if (response.body().results != null && response.body().results.size() > 0 && response.body().results.get(0) != null && response.body().results.get(0).currentUserData != null && response.body().results.get(0).currentUserData.purchase != null && response.body().results.get(0).currentUserData.purchase.size() > 0) {
        purchase_content = true;
        }
        }

        if (response != null && response.body() != null && response.body().results.size() > 0) {
        CardData tvseriesCardData = response.body().results.get(0);
        mEpisodesList = null;
        if (tvseriesCardData != null && tvseriesCardData.generalInfo != null && tvseriesCardData.generalInfo.type.equalsIgnoreCase("tvseries")) {
        fetchEpisodeData();
        }
        Log.d("TAG", "response.body().results ContentDea" + response.body().results);
                    *//*

*/
/*if (mSelectedContent != null) {
                        mSelectedContent.elapsedTime = response.body().results.get(0).elapsedTime;
                        Log.e(TAG, "LoadElapsedTime :: MOVIE mSelectedContent.elapsedTime 1333 :" + mSelectedContent.elapsedTime);
                        //setUpActionsPresentor(getContentActionList(mSelectedContent), mRowsAdapter);
                    }*//*
*/
/*

        }
        }

@Override
public void onFailure(Throwable t, int errorCode) {

        }
        });
        APIService.getInstance().execute(contentDetails);

        }
*//*


*/
/*
private void fetchEpisodeData() {
        myplexAPI api = myplexAPI.getInstance();
        myplexAPI.myplexAPIInterface myplexAPIService = api.myplexAPIService;
        String clientKey = PrefUtils.getInstance().getPrefClientkey();

        Call<CardResponseData> call = myplexAPIService.requestRelatedVODListWithOrderMode(clientKey,
        mSelectedContent._id, "contents,videos,images,generalInfo,images,relatedCast,publishingHouse,contents,relatedMedia,reviews/user,globalServiceId", 1, 10,
        "1");
        call.enqueue(new Callback<CardResponseData>() {

@Override
public void onResponse(Response<CardResponseData> response, Retrofit retrofit) {
        if (response == null)
        return;
        if (response.body() != null && response.code() == 200) {
        List<CardData> results = response.body().results;
        if(results.get(0).generalInfo.showDisplayTabs != null) {
        if (results.get(0).generalInfo.showDisplayTabs.getShowDisplayType() != null) {
        count = Integer.parseInt(results.get(0).generalInfo.showDisplayTabs.getShowDisplayfrequency());
        tabsShowDisplayEnd = Integer.parseInt(results.get(0).generalInfo.showDisplayTabs.getShowDisplayEnd());
        display_oredre = results.get(0).generalInfo.showDisplayTabs.getShowDisplayOrder();
        if(display_oredre != null)
        PrefUtils.getInstance().setDisplay_order(display_oredre);
        totalNumbertabs = tabsShowDisplayEnd / count ;
        }
        }
        getLatestEpisode(results.get(0),totalNumbertabs ,count);
//                    showDetailedList(results);
        Log.d("tag", "results seasons" + results.size());
        Log.d("tag", "results seasons" + results.get(0).title);
//                    safelyNotifyItemChanged(mRelatedRowsFragment, position, results, mRowsAdapterForRecommended);
        }
        }

@Override
public void onFailure(Throwable t) {
        }
        });

        }
*//*


*/
/*
public void getLatestEpisode(CardData content , int postion ,int count) {
        myplexAPI api = myplexAPI.getInstance();
        myplexAPI.myplexAPIInterface myplexAPIService = api.myplexAPIService;
        String clientKey = PrefUtils.getInstance().getPrefClientkey();

        Call<CardResponseData> call = myplexAPIService.requestRelatedVODListWithOrderMode(clientKey,
        content._id, "contents,videos,images,generalInfo,images,relatedCast,publishingHouse,contents,relatedMedia,reviews/user,globalServiceId", postion, count,
        "1");
        call.enqueue(new Callback<CardResponseData>() {

@Override
public void onResponse(Response<CardResponseData> response, Retrofit retrofit) {
        if (response == null)
        return;
        if (response.body() != null && response.code() == 200) {
        List<CardData> results = response.body().results;
        if (results != null && results.size() > 0) {
        Collections.reverse(results);
        mEpisodesList = results.get(0);
        }
        //                   showDetailedList(results);
        Log.d("tag", "results seasons" + results.size());
//                    safelyNotifyItemChanged(mRelatedRowsFragment, position, results, mRowsAdapterForRecommended);
        }
        }

@Override
public void onFailure(Throwable t) {
        }
        });

        }
*//*


*/
/*private void fetchSeasonsList() {
        myplexAPI api = myplexAPI.getInstance();
        myplexAPI.myplexAPIInterface myplexAPIService = api.myplexAPIService;
        String clientKey = PrefUtils.getInstance().getPrefClientkey();
        Call<CardResponseData> call = myplexAPIService.requestRelatedVODListWithOrderMode(clientKey,
        mSelectedContent._id, "contents,videos,images,generalInfo,images,relatedCast,publishingHouse,contents,relatedMedia,reviews/user,globalServiceId", 1, 10,
        "1");
        call.enqueue(new Callback<CardResponseData>() {

@Override
public void onResponse(Response<CardResponseData> response, Retrofit retrofit) {
        if (response == null)
        return;
        if (response.body() != null && response.code() == 200) {
        List<CardData> results = response.body().results;
        if(results.get(0).generalInfo.showDisplayTabs != null) {
        if (results.get(0).generalInfo.showDisplayTabs.getShowDisplayType() != null) {
        count = Integer.parseInt(results.get(0).generalInfo.showDisplayTabs.getShowDisplayfrequency());
        tabsShowDisplayEnd = Integer.parseInt(results.get(0).generalInfo.showDisplayTabs.getShowDisplayEnd());
        display_oredre = results.get(0).generalInfo.showDisplayTabs.getShowDisplayOrder();
        if(display_oredre != null)
        PrefUtils.getInstance().setDisplay_order(display_oredre);
        totalNumbertabs = tabsShowDisplayEnd / count ;
        }
        }
        displayTabs .clear();
        displayTabs.add("Latest Episodes");
        lastestEpisode = tabsShowDisplayEnd - count;
        lastestEpisodeFrom = tabsShowDisplayEnd - count;

        Log.d("tag","results1 no of tabs test"+totalNumbertabs);
        Log.d("tag","results1 no of tabs count"+count);
        Log.d("tag","results1 no of tabs tabsShowDisplayEnd"+tabsShowDisplayEnd);
        Log.d("tag","results1  display_oredre"+display_oredre);
        for (int i = 1 ; i <= totalNumbertabs -1 ;i++){
        lastestEpisode = lastestEpisode - count;
        displayTabs.add("Ep " + (lastestEpisode + 1) + "-" + "" + (lastestEpisodeFrom));
        Log.d("tag", "results1 displayTabs" + "Ep" + lastestEpisodeFrom + 1 + "-" + "" + (lastestEpisode));
        lastestEpisodeFrom = lastestEpisodeFrom - count;


        }
        contentId = results.get(0)._id;
        episodeFrameLayout_first_half.setVisibility(VISIBLE);
        episodeFrameLayout.setVisibility(VISIBLE);
        if(tandmDetailsRecyclerViewList !=null){
        tandmDetailsRecyclerViewList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        tandmDetailsRecyclerViewList.setLayoutManager(mLayoutManager);
        new Handler().postDelayed(new Runnable() {
@Override
public void run() {
        tandmDetailsRecyclerViewList.setAdapter(episodeListDisplayTab);


        }
        }, 100);
//                        tandmDetailsRecyclerViewList.requestFocus();
        }
        getEpisodeListTabs(results.get(0) ,displayTabs.size());

        Log.d("tag","results seasons"+results.size());

        }
        }

@Override
public void onFailure(Throwable t) {
        }
        });

        }*//*


*/
/*public void getEpisodeListTabs(CardData content ,int index){
        myplexAPI api = myplexAPI.getInstance();
        myplexAPI.myplexAPIInterface myplexAPIService = api.myplexAPIService;
        String clientKey = PrefUtils.getInstance().getPrefClientkey();
        episodeListDisplayTab = new EpisodeListDisplayTabAdapter(displayTabs,getApplicationContext(),mSelectedContent, this);
        Call<CardResponseData> call = myplexAPIService.requestRelatedVODListWithOrderMode(clientKey,
        contentId, "contents,videos,images,generalInfo,images,relatedCast,publishingHouse,contents,relatedMedia,reviews/user,globalServiceId", index, count,
        "1");
        call.enqueue(new Callback<CardResponseData>() {

@Override
public void onResponse(Response<CardResponseData> response, Retrofit retrofit) {
        if (response == null)
        return;
        if (response.body() != null && response.code() == 200) {
        List<CardData> results = response.body().results;
        showDetailedList(results);
        Log.d("tag","results seasons"+results.size());
//                    safelyNotifyItemChanged(mRelatedRowsFragment, position, results, mRowsAdapterForRecommended);
        }
        }

@Override
public void onFailure(Throwable t) {
        }
        });

        }*//*


    public void showDetailedList(List<CardData> list) {
        tandmDetailsRecyclerView = findViewById(R.id.tandmDetailsRecyclerView);
        Collections.reverse(list);
        trailerAndMoreDetailAdapter = new TrailerAndMoreDetailAdapter(list, getApplicationContext(),mSelectedContent,this);
        if(tandmDetailsRecyclerView !=null) {
            tandmDetailsRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            tandmDetailsRecyclerView.setLayoutManager(mLayoutManager);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tandmDetailsRecyclerView.setAdapter(trailerAndMoreDetailAdapter);
                }
            }, 400);
        }

    }

    private void registerForElapsedTimeBroadCastMessages() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(APIConstants.ELAPSED_TIME_BROADCAST);

        localBroadCastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction() != null) {
                    if (intent.getAction().equalsIgnoreCase(APIConstants.ELAPSED_TIME_BROADCAST)) {
                        mSelectedContent.elapsedTime = intent.getIntExtra(APIConstants.PARAM_ELAPSED_TIME, 0);
                        Log.d("CheckBroadcast", "broadcast received");
                        Log.d(TAG, "PrefUtils.getInstance().getSourceDetails()" + PrefUtils.getInstance().getSourceDetails());
                        if (PrefUtils.getInstance().getSourceDetails() != null && PrefUtils.getInstance().getSourceDetails().equals(APIConstants.SIMILAR_CONTENTS)) {
                            Log.d(TAG, "PrefUtils.getInstance().getSourceDetails()" + PrefUtils.getInstance().getSourceDetails());
                        } else {
                            initUI();
                            mHandler.postDelayed(mRunnable, 100);
                        }
                    }
                }
            }
        };
        AndroidTVApplication.getLocalBroadcastManager().registerReceiver(localBroadCastReceiver, filter);
    }


    private void setUpFragmentViews() {

        if (mRelatedRowsFragment == null || mRelatedRowsFragment.getVerticalGridView() == null) {
            mHandler.postDelayed(mRunnable, 100);
            return;
        }
        if (mRelatedContentGridView == null) {
            mRelatedContentGridView = mRelatedRowsFragment.getVerticalGridView();
        }
        if (mRelatedRowsFragment !=null && mRelatedRowsFragment.getView() != null) {
            if (!mRelatedRowsFragment.getView().hasFocus()) {
                Log.e("Image ", " Scroll to Bottom");
                //scrollToBottom();
                bannerScrollToBottom(mRelatedRowsFragment.getVerticalGridView());
            }
        }

        if (mControlsGridView == null) {
            mControlsGridView = mRowsFragment.getVerticalGridView();
            // mControlsGridView.setItemSpacing(Utils.convertDpToPixel(ContentDetailsActivityNeww.this,24));
            if (mControlsGridView != null) {
                mControlsGridView.setItemAlignmentOffsetPercent(0);
                mControlsGridView.setHorizontalSpacing(12);
                mControlsGridView.setHasOverlappingRendering(true);
                mControlsGridView.setPruneChild(true);
            }
        }
        if (mControlsGridView != null) {
            mControlsGridView.requestFocus();
            mRelatedRowsFragment.getVerticalGridView().clearFocus();
        }
    }

*/
/*public void continueInitialization() {
        manoramamaxFullLogo.setVisibility(VISIBLE);
        this.mContext = ContentDetailsActivityNeww.this;
        mDescriptionViewContainer = findViewById(R.id.content_details_overview_container);
        tandmDetailsRecyclerView = findViewById(R.id.tandmDetailsRecyclerView);
        relativeLayoutRoot = findViewById(R.id.bg_imageview);
        mLastWatchedProgressBar = findViewById(R.id.continue_watching_progress);
        initViews(mDescriptionViewContainer);
        mCarouselInfoData = (CarouselInfoData) getIntent().getSerializableExtra(PARAM_CAROUSEL_INFO_DATA);
        mImageDrawable = getResources().getDrawable(R.drawable.portrait_placeholder);
        mSource = getIntent().getStringExtra(AnalyticsConstants.PROPERTY_SOURCE);
        mSourceDetails = getIntent().getStringExtra(AnalyticsConstants.PROPERTY_SOURCE_DETAILS);
        SDKLogger.debugLog("ContentDetailsActivity" + mSource + "   " + mSourceDetails);

        mFragmentContainer = findViewById(R.id.fragmentContainer);
        mContentDetailTopFragment = (ContentDetailTopFragment) getFragmentManager().findFragmentById(R.id.topFragment);
        if (mContentDetailTopFragment.getView() != null) {
        mContentDetailTopFragment.getView().setVisibility(View.INVISIBLE);
        }

        if (!this.isFinishing()) {
        prepareBackgroundManager();
        }
        if (getIntent() == null) {
        itemWatchlistedOrNot();
        //initUI();
        return;
        }
        if (!onHandleExternalIntent(this.getIntent()))
        itemWatchlistedOrNot();
        //initUI();

        }*//*


    private void initUI() {
        List<Integer> listOfSettings = new ArrayList<>();
        Log.e("ANRCheck", "init ui: " + initui++);
        if (isDirectedToPlayerScreen) {
            onBackPressed();
            return;
        }
        updateDescription(mSelectedContent);
        mRowsFragment = new RowsFragment();
        mRowsFragment.setExpand(false);
        mRowsFragment.enableRowScaling(false);

        try {
            FragmentManager fragmentManager = getFragmentManager();
//            removeFragment(mCurrentFragment);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_details_fragment, mRowsFragment);
//            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (Throwable e) {
            e.printStackTrace();
//            Crashlytics.logException(e);
        }
//        pushFragment(mRowsFragment, R.id.content_details_fragment);

        setupAdapter();

        if (mSelectedContent == null) {
            return;
        }
        if (mSelectedContent.isMovie() || mSelectedContent.isMusicVideo()|| mSelectedContent.isNews() || mSelectedContent.isTVEpisode() || (mSelectedContent.isVideo() || mSelectedContent.isLiveOrProgram())){
            if (mRowsAdapter != null) {
              */
/*  ArrayList<CardData> layoutToShow = new ArrayList<>();
                layoutToShow.add(mSelectedContent);*//*

                Log.e("Image initUI", String.valueOf(mSelectedContent.elapsedTime));
//                mSelectedContent.elapsedTime = 10;
                if (mSelectedContent.elapsedTime > 0) {
                    if (mRowsAdapter != null && mRowsAdapter.size() > 0) {
                        mRowsAdapter.clear();
                    }
                    listOfSettings.clear();
                    for (int i = 0; i < 4; i++) {
                        listOfSettings.add(i);
                    }
                } else {
                    listOfSettings.clear();
                    for (int i = 0; i < 3; i++) {
                        listOfSettings.add(i);
                    }
                }
            }
        } else if (mSelectedContent.isTVEpisode() || mSelectedContent.isTVSeries()) {
            listOfSettings.clear();
            for (int i = 0; i < 5; i++) {
                listOfSettings.add(i);
            }
        }
       */
/* else if((mSelectedContent.isMusicVideo()|| mSelectedContent.isNews() || mSelectedContent.isTVEpisode() ||(mSelectedContent.isVideo()) && (mSelectedContent.elapsedTime > 0))){
            listOfSettings.clear();
            for (int i = 0; i < 4; i++) {
                listOfSettings.add(i);
            }
        }
        else if((mSelectedContent.isMusicVideo()|| mSelectedContent.isNews() || mSelectedContent.isTVEpisode() ||(mSelectedContent.isVideo()) && (mSelectedContent.elapsedTime <= 0))){
            listOfSettings.clear();
            for (int i = 0; i < 4; i++) {
                listOfSettings.add(i);
            }
        }*//*

        DetailsActionPresenter detailsActionPresenterNewlayout = new DetailsActionPresenter(detailsDescrptionsLists, mSelectedContent);
        ArrayObjectAdapter detailsActionPresenterNewlayout1 = new ArrayObjectAdapter(detailsActionPresenterNewlayout);
        for (Integer c : listOfSettings) {
            detailsActionPresenterNewlayout1.add(c);
        }
        if (detailsActionPresenterNewlayout1.size() > 0) {
            HeaderItem header = new HeaderItem(0, "");
            mRowsAdapter.add(mRowsAdapter.size(), new ListRow(header, detailsActionPresenterNewlayout1));
            mRowsAdapter.notifyArrayItemRangeChanged(1, mRowsAdapter.size());
        }




       */
/* else if(mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo() || (mSelectedContent.isVideo() && Utils.checkTypeEpisode(mSelectedContent)) ){
            if(mRowsAdapter != null) {
                ArrayList<CardData> layoutToShow = new ArrayList<>();
                layoutToShow.add(mSelectedContent);
                DetailsActionPresenter cardPresenter = new DetailsActionPresenter(APIConstants.PLAY_LAYOUT, mSelectedContent);
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                listRowAdapter.add(layoutToShow);
                ListRow playListRow = new ListRow(listRowAdapter);

                DetailsActionPresenter cardPresenterWatchlist = new DetailsActionPresenter(APIConstants.WATCHLIST_LAYOUT, watchlisted);
                ArrayObjectAdapter listRowAdapterWatchlist = new ArrayObjectAdapter(cardPresenterWatchlist);
                listRowAdapterWatchlist.add(layoutToShow);
                watchlistListRow = new ListRow(listRowAdapterWatchlist);

                DetailsActionPresenter cardPresenterResume = new DetailsActionPresenter(APIConstants.RESUME_LAYOUT);
                ArrayObjectAdapter listRowAdapterResume = new ArrayObjectAdapter(cardPresenterResume);
                listRowAdapterResume.add(layoutToShow);
                ListRow resumeListRow = new ListRow(listRowAdapterResume);

                DetailsActionPresenter cardPresenterBegin = new DetailsActionPresenter(APIConstants.PLAY_BEGIN_LAYOUT);
                ArrayObjectAdapter listRowAdapterBegin = new ArrayObjectAdapter(cardPresenterBegin);
                listRowAdapterBegin.add(layoutToShow);
                ListRow playBeginListRow = new ListRow(listRowAdapterBegin);

                *//*
*/
/*DetailsActionPresenter cardPresenterMoreEpisodes = new DetailsActionPresenter(APIConstants.MORE_EPISODES_LAYOUT, mSelectedContent);
                ArrayObjectAdapter listRowAdapterMoreEpisodes = new ArrayObjectAdapter(cardPresenterMoreEpisodes);
                listRowAdapterMoreEpisodes.add(layoutToShow);
                ListRow moreEpisodesListRow = new ListRow(listRowAdapterMoreEpisodes);*//*
*/
/*

                if(mSelectedContent.elapsedTime > 0 && mSelectedContent.isTVShow()) {
                    if (mRowsAdapter != null && mRowsAdapter.size() > 0) {
                        mRowsAdapter.clear();
                    }
                    mRowsAdapter.add( 0,resumeListRow);
                    mRowsAdapter.add( 1,playBeginListRow);
                        if(Utils.checkTypeMonthly(mSelectedContent) || Utils.checkTypeEpisode(mSelectedContent)) {
                            //mRowsAdapter.add(2, moreEpisodesListRow);
                            mRowsAdapter.add(2, watchlistListRow);
                        } else {
                            mRowsAdapter.add( 2,watchlistListRow);
                        }
                    } else {
                        mRowsAdapter.add(0, playListRow);
                        if (Utils.checkTypeMonthly(mSelectedContent) || Utils.checkTypeEpisode(mSelectedContent)) {
                                //mRowsAdapter.add(1, moreEpisodesListRow);
                                mRowsAdapter.add(1, watchlistListRow);
                            } else {
                                mRowsAdapter.add(1, watchlistListRow);
                            }
                        }
                mRowsAdapter.notifyItemRangeChanged(0, mRowsAdapter.size());
                *//*
*/
/*if(mRowsFragment != null && mRowsFragment.getView() != null) {
                    mRowsFragment.getView().requestFocus();
                }*//*
*/
/*
             }
        }*//*

        if (!PrefUtils.getInstance().getBackPressedFromPlayBackScreen()) {
            fetchSimilarContent();
        }
        */
/*if(mRowsFragment != null && mRowsFragment.getView() != null) {
            mRowsFragment.getView().requestFocus();
        }*//*

        eventVideoViewedDetails(mSource, mSourceDetails);
    }

    public void eventVideoViewedDetails(String source, String sourceDetails) {
        Map<String, Object> params = new HashMap<>();
        if (source != null) {
            params.put(PROPERTY_SOURCE, source);
        }
        if (sourceDetails != null) {
            params.put(PROPERTY_SOURCE_DETAILS, sourceDetails);
        }
        Event event = new Event(null, EVENT_VIDEO_DETAILS_VIEWED, null, null, params, HIGH);
        //MyplexAnalytics.getInstance(this).sendEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = false;
        // When using DPad, show all the OSD so that focus can move freely
        // from/to ActionBar to/from PlayerController
        switch (keyCode) {

            case KeyEvent.KEYCODE_DPAD_UP:
              */
/*  Log.e("KEy","UP Button Pressed");
                View view1 = this.getCurrentFocus();
                if(view1 instanceof FrameLayout){
                    mControlContainer.setVisibility(VISIBLE);
                }
                mControlContainer.requestFocus(FOCUS_DOWN);*//*

                */
/*Log.d(TAG,"getFoucsed_right postion_episodes_tab"+PrefUtils.getInstance().getFoucsed_postion_episodes_tab()
                        +" tab focus :" +tandmDetailsRecyclerViewList .hasFocus());*//*

                if(episodeFrameLayout !=null && episodeFrameLayout.getVisibility() ==  VISIBLE ) {
                    if (tandmDetailsRecyclerViewList != null && tandmDetailsRecyclerViewList.hasFocus()) {
                        Log.d(TAG, "getFoucsed_postion_episodes_tab" + PrefUtils.getInstance().getFoucsed_postion_episodes_tab());
                        if (PrefUtils.getInstance().getFoucsed_postion_episodes_tab() == 0) {
                            tandmDetailsRecyclerViewList.requestFocus();
                            return true;
                        } else return false;
                    }  else {
                        if (tandmDetailsRecyclerView != null && tandmDetailsRecyclerView.hasFocus()) {
                            Log.d(TAG, "getFoucsed_postion_episodes_tab postion" + PrefUtils.getInstance().getFoucsed_postion_episodes_details_tab());
                            if (PrefUtils.getInstance().getFoucsed_postion_episodes_details_tab() == 0) {
//                                tandmDetailsRecyclerViewList.requestFocus();
                                tandmDetailsRecyclerViewList.postDelayed(new Runnable() {
                                    @Override
                                    public void run() { Log.d(TAG,"getFoucsed_postion UP episodes_tab selected position:"+mEpisodesTabSelectedPosition +" child position :"+tandmDetailsRecyclerViewList.getLayoutManager().findViewByPosition(mEpisodesTabSelectedPosition));
                                        if(tandmDetailsRecyclerViewList!=null &&  tandmDetailsRecyclerViewList.getLayoutManager().findViewByPosition(mEpisodesTabSelectedPosition)!=null) {
                                            tandmDetailsRecyclerViewList.getLayoutManager().findViewByPosition(mEpisodesTabSelectedPosition).setFocusable(true);
                                            tandmDetailsRecyclerViewList.getLayoutManager().findViewByPosition(mEpisodesTabSelectedPosition).requestFocus();
                                        }
                                    }
                                },100);
                                return true;
                            } else return false;
                        }
                    }
                }

                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
              */
/*  Log.e("KEy","Down Button Pressed");
                View view = this.getCurrentFocus();
                if(view instanceof LinearLayout){
                    //mControlContainer.setVisibility(GONE);
                }*//*


                break;
            case KeyEvent.KEYCODE_BACK:
                if(episodeFrameLayout !=null && episodeFrameLayout.getVisibility() ==  VISIBLE ) {
                    episodeFrameLayout.setVisibility(GONE);
                    if(episodeFrameLayout_first_half !=null && episodeFrameLayout_first_half.getVisibility() == VISIBLE)
                        episodeFrameLayout_first_half.setVisibility(GONE);
                    return true;
                }
                else {
                    onBackPressed();
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return false;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(episodeFrameLayout !=null && episodeFrameLayout.getVisibility() ==  VISIBLE ) {
                   */
/* Log.d(TAG,"getFoucsed_ left postion_episodes_tab"+PrefUtils.getInstance().getFoucsed_postion_episodes_tab()
                    +" tab focus :" +tandmDetailsRecyclerViewList .hasFocus());*//*

                    if(tandmDetailsRecyclerViewList !=null && tandmDetailsRecyclerViewList .hasFocus()){
                        if(PrefUtils.getInstance().getFoucsed_postion_episodes_tab() == 0){
                            return true;
                        }
                        else return false;
                    }
                    else {
                        if(tandmDetailsRecyclerView !=null && tandmDetailsRecyclerView.hasFocus()){
                            return true;
                        }
                    }
                    episodeFrameLayout.requestFocus();
                    return true;
                }
                else
                    return false;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_UP_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
                return false;
        }


        return handled ? true : super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume will call");
        mHandler.postDelayed(mRunnable, 100);
    }

    private void setupAdapter() {
        Log.v(TAG, "setupAdapter called.");
        CustomListRowPresenter customListRowPresenter = new CustomListRowPresenter(0, false);
        customListRowPresenter.setSelectEffectEnabled(false);
        customListRowPresenter.setShadowEnabled(false);
        customListRowPresenter.enableChildRoundedCorners(true);
        episodeFrameLayout =findViewById(R.id.secondHalf);
        episodeFrameLayout_first_half =findViewById(R.id.layout_episodes_frame);
        tandmDetailsRecyclerViewList = findViewById(R.id.episode_detail_list);
        mRowsAdapter = new ArrayObjectAdapter(customListRowPresenter);
        mRowsFragment.setAdapter(mRowsAdapter);
        Log.e("ANRCheck", "setupAdapter");
        mRowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                SDKLogger.debugLog("ContentDetailsActivityNeww" + item + " " + "Clicked");
                DetailsActionPresenter.ViewHolder viewHolder1 = (DetailsActionPresenter.ViewHolder) itemViewHolder;
                viewHolder1.watchlisttext.getText().toString();
                SDKLogger.debugLog("ContentDetailsActivityNeww", "mSelectedContent" + mSelectedContent + " " + "Clicked +");
                int selectedPosition = mRowsFragment.getSelectedPosition();
                Log.d(TAG, "mSelectedContent" + selectedPosition + " gettext:" + viewHolder1.watchlisttext.getText().toString());
                if (PrefUtils.getInstance().getDoesTvSupportDolby() && mSelectedContent.getShowDolby() != null
                        && mSelectedContent.getShowDolby().equalsIgnoreCase("true")) {
                    dolbyEnabled = true;
                    PrefUtils.getInstance().setContentSupportDolby(true);
                } else if (mSelectedContent.generalInfo.type.equalsIgnoreCase(Const.LIVE) || mSelectedContent.generalInfo.type.equalsIgnoreCase(Const.PROGRAM)) {
                    PrefUtils.getInstance().setContentSupportDolby(false);
                } else
                    PrefUtils.getInstance().setContentSupportDolby(false);
                Log.d(TAG, "viewHolder1.watchlisttext.getText().toString()" + viewHolder1.watchlisttext.getText().toString());
                Log.d(TAG, "viewHolder1.APIConstants.MMTV_WATCH_NOW" + APIConstants.MMTV_WATCH_NOW);

                if (viewHolder1.watchlisttext.getText().toString().equalsIgnoreCase(APIConstants.MMTV_ADDED_LIKE) || viewHolder1.watchlisttext.getText().toString().equalsIgnoreCase(APIConstants.MMTV_ADDED_LIKED)) {
*/
/*
        FavouriteContentSelection sendFavouriteContentSelection = new FavouriteContentSelection(mSelectedContent._id, new APICallback<BaseResponseData>() {
@Override
public void onResponse(APIResponse<BaseResponseData> response) {
        dismissProgressBar();
        if (response.isSuccess()) {
        if (response.body().code == 200) {
        mSelectedContent.isFavourite = false;
        String isFavourite = "removed";
        fireFavouriteEventToAnalytics(mSelectedContent, isFavourite);
        if (mSelectedContent.isMusicVideo()) {
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isMovie() || mSelectedContent.isTVEpisode()) {
        if (mSelectedContent.elapsedTime > 0 && checkRelatedMultiMediaAvailable())
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        else if (mSelectedContent.elapsedTime > 0)
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        else
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isTVShow() && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        } else if (mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo()) {
        if ((Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime == 0) {
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        } else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        } else
        mRowsAdapter.notifyItemRangeChanged(1, 1);
        }

        } else if (response.body().code == 201) {
        mSelectedContent.isFavourite = true;
        String isFavourite = "added";
        fireFavouriteEventToAnalytics(mSelectedContent, isFavourite);
        if (mSelectedContent.isMusicVideo()) {
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isMovie() || mSelectedContent.isTVEpisode()) {
        if (mSelectedContent.elapsedTime > 0 && checkRelatedMultiMediaAvailable())
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        else if (mSelectedContent.elapsedTime > 0)
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        else
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isTVShow() && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        } else if (mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo()) {
        if ((Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime == 0) {
        mRowsAdapter.notifyItemRangeChanged(1, 1);
        } else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        } else
        mRowsAdapter.notifyItemRangeChanged(1, 1);
        }
        } else if (response.body().code == 401 || (response.body().code == 402)) {
//                        Toast.makeText(ContentDetailsActivityNeww.this, response.message(), Toast.LENGTH_SHORT).show();
        AlertDialogUtil.showToastNotification(response.body().message);
        }
        }
        Log.d("tag","mSelectedContent.isFavourite test"+(mSelectedContent.isFavourite));
        if (mSelectedContent.isFavourite) {
        viewHolder1.watchlisttext.setText(R.string.mmtv_unlike);
        viewHolder1.watchListImage.setImageResource(R.drawable.mmtv_like_icon_checked_selected);
        } else {
        viewHolder1.watchlisttext.setText(R.string.mmtv_like);
        viewHolder1.watchListImage.setImageResource(R.drawable.mmtv_like_icon_default);
        }
        }

@Override
public void onFailure(Throwable t, int errorCode) {
        dismissProgressBar();
        }
        });
*//*

                    APIService.getInstance().execute(sendFavouriteContentSelection);
//                    addToWatchlist(mSelectedContent);
                }*/
/*if(viewHolder1.watchlisttext.getText().toString().equalsIgnoreCase(APIConstants.MMTV_ADD_TO_PLAYLIST) || viewHolder1.watchlisttext.getText().toString().equalsIgnoreCase(APIConstants.MMTV_ADDED_TO_PLAYLIST)) {
        Log.d(TAG, "viewHolder1.watchlisttext.getText().toString()" + viewHolder1.watchlisttext.getText().toString());
        WatchListContentSelection sendFavouriteContentSelection = new WatchListContentSelection(mSelectedContent._id, new APICallback<BaseResponseData>() {
@Override
public void onResponse(APIResponse<BaseResponseData> response) {
        dismissProgressBar();
        if (response.isSuccess()) {
        if (response.body().code == 200) {
        if(response.body().isWatchList)
        mSelectedContent.isWatchList = true;
        else
        mSelectedContent.isWatchList = false;
        String isFavourite = "removed";
        fireFavouriteEventToAnalytics(mSelectedContent, isFavourite);
        if (mSelectedContent.isMusicVideo()) {
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isMovie() || mSelectedContent.isTVEpisode()) {
        if (mSelectedContent.elapsedTime > 0 && checkRelatedMultiMediaAvailable())
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        else if (mSelectedContent.elapsedTime > 0)
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        else
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isTVShow() && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        } else if (mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo()) {
        if ((Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime == 0) {
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        } else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        } else
        mRowsAdapter.notifyItemRangeChanged(1, 1);
        }

        } else if (response.body().code == 201) {
        mSelectedContent.isWatchList = true;
        String isFavourite = "added";
        fireFavouriteEventToAnalytics(mSelectedContent, isFavourite);
        if (mSelectedContent.isMusicVideo()) {
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isMovie() || mSelectedContent.isTVEpisode()) {
        if (mSelectedContent.elapsedTime > 0 && checkRelatedMultiMediaAvailable())
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        else if (mSelectedContent.elapsedTime > 0)
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        else
        mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
        } else if (mSelectedContent.isTVShow() && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(3, 1);
        } else if (mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo()) {
        if ((Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime == 0) {
        mRowsAdapter.notifyItemRangeChanged(1, 1);
        } else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime > 0) {
        mRowsAdapter.notifyItemRangeChanged(2, 1);
        } else
        mRowsAdapter.notifyItemRangeChanged(1, 1);
        }
        } else if (response.body().code == 401 || (response.body().code == 402)) {
//                        Toast.makeText(ContentDetailsActivityNeww.this, response.message(), Toast.LENGTH_SHORT).show();
        AlertDialogUtil.showToastNotification(response.body().message);
        }
        }
        Log.d("tag", "mSelectedContent.isFavourite test" + (mSelectedContent.isWatchList));
        if (mSelectedContent.isWatchList) {
        viewHolder1.watchlisttext.setText(R.string.mmtv_added_to_playlist);
        viewHolder1.watchListImage.setImageResource(R.drawable.mmtv_add_to_playlist_added);
        } else {
        viewHolder1.watchlisttext.setText(R.string.mmtv_add_to_playlist);
        viewHolder1.watchListImage.setImageResource(R.drawable.mmtv_add_to_playlist_default);
        }
        }

@Override
public void onFailure(Throwable t, int errorCode) {
        dismissProgressBar();
        }
        });
        APIService.getInstance().execute(sendFavouriteContentSelection);
//                    addToWatchlist(mSelectedContent);

        }*//*

                if (viewHolder1.watchlisttext.getText().toString().equals(APIConstants.MMTV_WATCH_NOW) || viewHolder1.watchlisttext.getText().toString().equalsIgnoreCase(APIConstants.MMTV_WATCH_LATEST_EPISODE)) {
                    Log.d(TAG, "viewHolder1.watchlisttext.getText().toString()" + viewHolder1.watchlisttext.getText().toString());
                    onItemClick(mSelectedContent, true);
                }
                if (viewHolder1.watchlisttext.getText().toString().equals(APIConstants.MMTV_RESUME)) {
                    Log.d(TAG, "viewHolder1.watchlisttext.getText().toString()" + viewHolder1.watchlisttext.getText().toString());
                    onItemClick(mSelectedContent, true);
                }
                if (viewHolder1.watchlisttext.getText().toString().equals(APIConstants.MMTV_PLAY_FROM_BEGINNING) || viewHolder1.watchlisttext.getText().toString().equals(APIConstants.MMTV_START_FROM_BEGINNING)) {
                    Log.d(TAG, "viewHolder1.watchlisttext.getText().toString()" + viewHolder1.watchlisttext.getText().toString());
                    onItemClick(mSelectedContent, false);
                }
                if (viewHolder1.watchlisttext.getText().toString().equals(APIConstants.MMTV_SEASONS_EPISODES)) {
                    Log.d(TAG, "viewHolder1.watchlisttext.getText().toString()" + viewHolder1.watchlisttext.getText().toString());
                    fetchSeasonsList();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(tandmDetailsRecyclerViewList != null) {
                                tandmDetailsRecyclerViewList.requestFocus();
                            }
                        }
                    }, 400);

                }



             */
/*   if(mSelectedContent.isMovie()|| mSelectedContent.isTVEpisode()) {
                    addToWatchlist(mSelectedContent);
                    if (mSelectedContent.elapsedTime > 0) {
                        switch (selectedPosition) {
                            case 0:
                                if (item instanceof CardData) {
                                    onItemClick(item, true);

                                } else {
                                    onItemClick(((ArrayList<CardData>) item).get(0), true);
                                }
                                break;
                            case 1:
                                if (item instanceof CardData) {
                                    mSelectedContent.elapsedTime = 0;
                                    onItemClick(item, false);
                                } else {
                                    mSelectedContent.elapsedTime = 0;
                                    onItemClick(((ArrayList<CardData>) item).get(0), false);
                                }
                                break;
                            case 2:
                                if(checkRelatedMultiMediaAvailable()) {
                                    openTrailersAndMoreActivity(itemViewHolder);
                                } else
                                    addToWatchlist(((ArrayList<CardData>) item).get(0));
                                break;
                            case 3:
                                if(checkRelatedMultiMediaAvailable()) {
                                    addToWatchlist(((ArrayList<CardData>) item).get(0));
                                } else {
                                    if (item instanceof CardData) {
                                        onClickOfRecommendation((CardData) item, itemViewHolder.view);
                                    } else {
                                        onClickOfRecommendation(((ArrayList<CardData>) item).get(0), itemViewHolder.view);
                                    }
                                }
                                break;
                        }
                    } else {
                        switch (selectedPosition) {
                            case 0:
                                if (item instanceof CardData) {
                                    onItemClick(item, true);
                                } else {
                                    onItemClick(((ArrayList<CardData>) item).get(0), false);
                                }
                                break;
                            case 1:
                                if(checkRelatedMultiMediaAvailable()) {
                                    openTrailersAndMoreActivity(itemViewHolder);
                                } else
                                    addToWatchlist(((ArrayList<CardData>) item).get(0));
                                break;
                            case 2:
                                if(checkRelatedMultiMediaAvailable()) {
                                    addToWatchlist(((ArrayList<CardData>) item).get(0));
                                } else {
                                    if (item instanceof CardData) {
                                        onClickOfRecommendation((CardData) item, itemViewHolder.view);
                                    } else {
                                        onClickOfRecommendation(((ArrayList<CardData>) item).get(0), itemViewHolder.view);
                                    }
                                }
                                break;
                        }
                    }
                }*//*

             */
/*   if(mSelectedContent.isVideoAlbum()|| mSelectedContent.isTVShow()){
                    switch (selectedPosition) {
                        case 0:
                            if (item instanceof CardData) {
                                if(mRowsAdapterForRecommended != null) {
                                    item = mRowsAdapterForRecommended.get(0);
                                    onItemClick(item, true);
                                }
                            } else {
                                if(mRowsAdapterForRecommended != null) {
                                    ListRow listRow = (ListRow) mRowsAdapterForRecommended.get(0);
                                    item = listRow.getAdapter().get(0);
                                    if(mSelectedContent.elapsedTime>0)
                                        ((CardData) item).elapsedTime = mSelectedContent.elapsedTime;
                                    onItemClick(item, true);
                                }
                            }
                            break;
                        case 1:
                            if(mSelectedContent.isTVShow() && mSelectedContent.elapsedTime>0 ) {
                                if(Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) {
                                    onItemClick(item, true);
                                } else {
                                    onItemClick(item, true);
                                }
                            } else {
                                *//*
*/
/*if (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) {
                                    openMoreEpisodesLayout(itemViewHolder);
                                } else*//*
*/
/*
                                    addToWatchlist(((ArrayList<CardData>) item).get(0));
                            }
                            break;
                        case 2:
                            if(mSelectedContent.isTVShow() && mSelectedContent.elapsedTime == 0 && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent))) {
                                //openMoreEpisodesLayout(itemViewHolder);
                                addToWatchlist(((ArrayList<CardData>) item).get(0));
                            } *//*
*/
/*else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime>0 && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent))) {
                                openMoreEpisodesLayout(itemViewHolder);
                            }*//*
*/
/* else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime>0) {
                                addToWatchlist(((ArrayList<CardData>) item).get(0));
                            } *//*
*/
/*else {
                                openMoreEpisodesLayout(itemViewHolder);
                            }*//*
*/
/*
                            break;
                        case 3:
                            if(mSelectedContent.isTVShow())
                                addToWatchlist(((ArrayList<CardData>) item).get(0));
                    }

                }
                if(mSelectedContent.isMusicVideo()){
                    switch (selectedPosition) {
                        case 0:
                            if (item instanceof CardData) {
                                onItemClick(item, false);
                            } else {
                                onItemClick(((ArrayList<CardData>) item).get(0), false);
                            }
                            break;
                        case 1:
                            addToWatchlist(((ArrayList<CardData>) item).get(0));
                            break;
                    }

                }*//*

            }
        });
    }

    public void openTrailersAndMoreActivity(Presenter.ViewHolder viewHolder) {
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                viewHolder.view,
                ContentDetailsActivityNeww.SHARED_ELEMENT_NAME).toBundle();
//                TODO Launch Details Activity with the card data
        try {
            Intent moreEpisodeIntent = TrailersAndMoreActivity.createIntent(this, mSelectedContent, null, mSource, mSourceDetails);
            this.startActivityForResult(moreEpisodeIntent, MainActivity.INTENT_REQUEST_TYPE_LOGIN, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openMoreEpisodesLayout(Presenter.ViewHolder viewHolder) {
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                viewHolder.view,
                ContentDetailsActivityNeww.SHARED_ELEMENT_NAME).toBundle();
//                TODO Launch Details Activity with the card data
        try {
            Intent moreEpisodeIntent = EpisodeDetailActivity.createIntent(this, mSelectedContent, null, null, null);
            this.startActivityForResult(moreEpisodeIntent, MainActivity.INTENT_REQUEST_TYPE_LOGIN, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onItemClick(Object item, boolean startFromResume) {
        PrefUtils.getInstance().setSourceDetails("");
        if (item instanceof CardData || item instanceof String) {
            if (similarContentList == null
                    || similarContentList.isEmpty()) {
                try {
                    if (paymentdailogue == true && PrefUtils.isUserLoggedIn() && !purchase_content) {
                        dialog = new CustomPaymentDilogue(mContext);
                        dialog.show();
                        return;
//                        paymentdailogue=false;
                    }
                    */
/*ListRow listRow = (ListRow) row;
                    if (listRow == null || listRow.getAdapter().size() == 0) {
                        return;
                    }
                    ArrayList<CardData> cardDataList = new ArrayList<>();
                    for (int i = 0; i < listRow.getAdapter().size(); i++) {
                        cardDataList.add((CardData) listRow.getAdapter().get(i));
                    }*//*

                    Utils.fromDetials = true;
                    launchPlayerActivity((CardData) item, mCarouselInfoData, similarContentList, startFromResume);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (paymentdailogue == true && PrefUtils.isUserLoggedIn() && !purchase_content) {
                dialog = new CustomPaymentDilogue(mContext);
                dialog.show();
                return;
            }
            Utils.fromDetials = true;
            launchPlayerActivity((CardData) item, mCarouselInfoData, similarContentList, startFromResume);
        } else if (item instanceof Action) {
            handleActionClick((Action) item);
        } else if (item instanceof CardData) {
            Utils.fromDetials = true;
            launchPlayerActivity((CardData) item, mCarouselInfoData, similarContentList, startFromResume);
        } else if (mSelectedContent.isTVShow()) {
            if (mRowsAdapterForRecommended != null) {
                ListRow listRow = (ListRow) mRowsAdapterForRecommended.get(0);
                item = listRow.getAdapter().get(0);
                Utils.fromDetials = true;
                ((CardData) item).elapsedTime = 0;
                launchPlayerActivity((CardData) item, mCarouselInfoData, similarContentList, startFromResume);
            }
        }
        //fetchSubtitles(Long.parseLong(mSelectedContent._id));
    }

    public void itemSelected(Object item, Row row) {
       */
/* if (row instanceof ListRow) {
            ListRow listRow = (ListRow) row;
            if (listRow != null
                    && listRow.getHeaderItem() == null) {
                SDKLogger.debugLog("selected " + item + " position- " + position + " is a action container");
                return;
            }
            ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) listRow.getAdapter();
            position = mRowsAdapter.indexOf(row);
            int selectedItemPositionOffset = rowAdapter.size() / 2;
            int offsetItemPosition = (rowAdapter.size() - selectedItemPositionOffset) - 1;
            SDKLogger.debugLog("selected- " + item + " " +
                    " position- " + position + " " +
                    " last item- " + rowAdapter.get(offsetItemPosition) + "" +
                    " size- " + rowAdapter.size());
            int currentItemPosition = rowAdapter.indexOf(item);
            if (mSelectedContent != null && currentItemPosition >= offsetItemPosition) {
                if (isLoadMoreAvailable && !isLoadMoreRequest) {
                    mStartIndex = mStartIndex + 1;
                    isLoadMoreRequest = true;
                    addLoadingCardView(mRowsFragment, mRowsAdapter, position);
                    fetchEpisodes();
                }
            }
        }*//*

    }

    private void removeItem(final int position, final ArrayObjectAdapter rowsAdapter) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!mRowsFragment.getVerticalGridView().isComputingLayout()) {
                    mRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
                    rowsAdapter.remove(rowsAdapter.get(position));
                    rowsAdapter.notifyArrayItemRangeChanged(rowsAdapter.size(), 1);
                } else {
                    removeItem(position, rowsAdapter);
                }
            }
        });
    }

    private void removeTheItem(final String name, final ArrayObjectAdapter rowsAdapter) {
        if (mRelatedRowsFragment.getVerticalGridView() != null) {
            mRelatedRowsFragment.getVerticalGridView().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!mRelatedRowsFragment.getVerticalGridView().isComputingLayout()) {
                            mRelatedRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
                            int pos = listOfMonths.indexOf(name);
                            rowsAdapter.remove(rowsAdapter.get(pos));
                            listOfMonths.remove(pos);
                            rowsAdapter.notifyArrayItemRangeChanged(0, rowsAdapter.size());
                        } else {
                            removeTheItem(name, rowsAdapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void removeSimilarItem(final String title, final int position, final ArrayObjectAdapter mRowsAdapterForRecommended) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!mRelatedRowsFragment.getVerticalGridView().isComputingLayout()) {
                        mRelatedRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
                        mRowsAdapterForRecommended.remove(mRowsAdapterForRecommended.get((getCarouselPositionFromList(title))));
                        mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
                    } else {
                        removeSimilarItem(title, position, mRowsAdapterForRecommended);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    private void removeSimilarArtistItem(final String title, final int position, final ArrayObjectAdapter mRowsAdapterForRecommended) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!mRelatedRowsFragment.getVerticalGridView().isComputingLayout()) {
                        mRelatedRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
                        mRowsAdapterForRecommended.remove(mRowsAdapterForRecommended.get((getPositionFromList(title))));
                        mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
                    } else {
                        removeSimilarArtistItem(title, position, mRowsAdapterForRecommended);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    private void removeItemWithOutPost(final int position, final ArrayObjectAdapter rowsAdapter) {
        if (!mRowsFragment.getVerticalGridView().isComputingLayout()) {
            mRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
            rowsAdapter.remove(rowsAdapter.get(position));
            rowsAdapter.notifyArrayItemRangeChanged(rowsAdapter.size(), 1);
        } else {
            removeItemWithOutPost(position, rowsAdapter);
        }
    }

    private void addLoadingCardView(final RowsFragment mRowsFragment, final ArrayObjectAdapter rowsAdapter, final int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!mRowsFragment.getVerticalGridView().isComputingLayout()) {
                    ListRow row = (ListRow) rowsAdapter.get(position);
                    ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) row.getAdapter();
                    CardData cardData = new CardData();
                    cardData.generalInfo = new CardDataGeneralInfo();
                    cardData.generalInfo.type = CardData.TYPE_LOADING;
                    rowAdapter.add(rowAdapter.size(), cardData);
                } else {
                    addLoadingCardView(mRowsFragment, rowsAdapter, position);
                }
            }
        });
    }

    private void addLoadingCardViewWithOutPost(RowsFragment mRowsFragment, final ArrayObjectAdapter rowsAdapter, final int position) {
       */
/* new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {*//*

        if (!mRowsFragment.getVerticalGridView().isComputingLayout()) {
            ListRow row = (ListRow) rowsAdapter.get(position);
            ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) row.getAdapter();
            CardData cardData = new CardData();
            cardData.generalInfo = new CardDataGeneralInfo();
            cardData.generalInfo.type = CardData.TYPE_LOADING;
            rowAdapter.add(rowAdapter.size(), cardData);
        } else {
            addLoadingCardViewWithOutPost(mRowsFragment, rowsAdapter, position);
        }
            */
/*}
        });*//*

    }

    private void handleActionClick(Action item) {
        if (item == null) return;
        ListRow row;
        CardData cardData;
        List<CardData> cardDataList;
        switch ((int) item.getId()) {
            case Action.ACTION_ID_WATCH_NOW:
                launchPlayerActivity(mSelectedContent, mCarouselInfoData, similarContentList, false);
                break;
            case Action.ACTION_ID_RESUME_FROM_LAST_POS:
                launchPlayerActivity(mSelectedContent, mCarouselInfoData, similarContentList, true);
                break;
            case Action.ACTION_ID_WATCH_FIRST_EPISODE:
                try {
                    if (mRowsAdapter == null || mRowsAdapter.size() == 0) {
                        return;
                    }
                    row = (ListRow) mRowsAdapter.get(1);
                    if (row == null || row.getAdapter().size() == 0) {
                        return;
                    }
                    cardData = (CardData) row.getAdapter().get(0);
                    if (cardData == null) {
                        return;
                    }
                    cardDataList = new ArrayList<>();
                    for (int i = 0; i < row.getAdapter().size(); i++) {
                        cardDataList.add((CardData) row.getAdapter().get(i));
                    }
                    launchPlayerActivity(cardData, mCarouselInfoData, cardDataList, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Action.ACTION_ID_WATCH_LATEST_EPISODE:
                try {
                    if (mRowsAdapter == null || mRowsAdapter.size() == 0) {
                        return;
                    }
                    row = (ListRow) mRowsAdapter.get(mRowsAdapter.size() - 1);
                    if (row == null || row.getAdapter().size() == 0) {
                        return;
                    }
                    cardData = (CardData) row.getAdapter().get(0);
                    if (cardData == null) {
                        return;
                    }
                    cardDataList = new ArrayList<>();
                    for (int i = 0; i < row.getAdapter().size(); i++) {
                        cardDataList.add((CardData) row.getAdapter().get(i));
                    }
                    launchPlayerActivity(cardData, mCarouselInfoData, cardDataList, false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    */
/*private void setUpActionsPresentor(List<Action> contentActionList, ArrayObjectAdapter mRowsAdapter) {
        ButtonPresenter buttonPresenter = new ButtonPresenter();
        buttonPresenter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemSelected(View view, boolean selected) {
                if (selected) {
                    if (mSelectedContent.isTVSeason() || mSelectedContent.isTVSeries() || mSelectedContent.isTVEpisode()) {
                        updateDescription(mSelectedContent);
                    }
                }
            }

            @Override
            public void onViewClicked(View view) {
//                if (view.getTag() instanceof Action) {
//                    Action item = (Action) view.getTag();
//                    handleActionClick( item);
//                }
            }
        });
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(buttonPresenter);
       *//*
*/
/* for (com.myplex.vodafone.tv.model.Action action : contentActionList) {
            listRowAdapter.add(LeanbackHelpers.translateActionToLeanBackAction(action));
        }*//*
*/
/*
        listRowAdapter.addAll(0, contentActionList);

        // Only add the header and row for recommendations if there are any recommended content.
        if (mRowsAdapter.size() > 0 && listRowAdapter.size() > 0) {
            mRowsAdapter.replace(0, new ListRow(listRowAdapter));
            mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size());
        } else {
            mRowsAdapter.add(0, new ListRow(listRowAdapter));
            mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size());
        }

    }*//*


    */
/**
     * Get content action list.
     *
     * @param content Content.
     * @return List of action for provided content.
     *//*

    public List<Action> getContentActionList(CardData content) {
        if (content == null) {
            return null;
        }
        List<Action> contentActionList = new ArrayList<>();
        StringBuilder actionLabel = new StringBuilder();
        String seasonText = "S";
        String episodeText = "E";

        if (content.isTVShow() || content.isMusicVideo() || content.isTVSeries()) {
            if (content.isTVSeries() || content.isTVShow()) {
                actionLabel.append(getString(R.string.start_watching_episode));
                actionLabel.append("\n");
                actionLabel.append(" " + seasonText);
                actionLabel.append(" 01" + "- ");
                actionLabel.append(episodeText + " 01");
            }
            contentActionList.add(new Action()
                    .setId(Action.ACTION_ID_WATCH_FIRST_EPISODE)
                    .setLabel1(actionLabel.toString()));
            return contentActionList;
        }
        SDKLogger.debugLog("elapsedTime- " + content.elapsedTime);

        if (content.isTVEpisode() || content.isMovie()) {
            if (content.elapsedTime > 6) {
                contentActionList.add(new Action()
                        .setId(Action.ACTION_ID_RESUME_FROM_LAST_POS)
                        .setLabel1(getResources().getString(
                                R.string.resume_1) + " " + getResources().getString(
                                R.string.resume_2)));

            }

        }
        if (mSelectedContent.isLiveOrProgram()) {
            contentActionList.add(new Action()
                    .setId(Action.ACTION_ID_WATCH_NOW)
                    .setLabel1(getResources().getString(
                            R.string.watch_now_1) + " " + getResources().getString(
                            R.string.watch_now_2)));
        } else {
            if (!content.isVideo() && (content.elapsedTime > 6)) {
                contentActionList.add(new Action()
                        .setId(Action.ACTION_ID_WATCH_NOW)
                        .setLabel1(getResources().getString(R.string.watch_from_beginning_1)
                                + " " + getResources().getString(R.string.watch_from_beginning_2)));
            } else {
                contentActionList.add(new Action()
                        .setId(Action.ACTION_ID_WATCH_NOW)
                        .setLabel1(getResources().getString(R.string.watch_now_1)
                                + " " + getResources().getString(R.string.watch_now_2)));
            }
        }

        return contentActionList;
    }

    public ArrayList<CardData> removeDuplicates(List<CardData> list) {
        Set<CardData> set = new TreeSet<>(new Comparator<CardData>() {
            @Override
            public int compare(CardData o1, CardData o2) {
                if (o1._id.equalsIgnoreCase(o2._id)) {
                    return 0;
                }
                return 1;
            }
        });
        for (int i = 0; i < list.size(); i++) {
            CardData data = list.get(i);
            if (mSelectedContent != null
                    && mSelectedContent._id.equalsIgnoreCase(data._id)) {
                list.remove(i);
                break;
            }
        }

        set.addAll(list);
        return new ArrayList(set);
    }


    private void fetchRelatedVideos() {
        if (mSelectedContent == null
                || TextUtils.isEmpty(mSelectedContent.globalServiceId)) {
            Log.d(TAG, "fetchRelatedVideos: globalServiceId is NA");
            return;
        }
        new CacheManager().getRelatedVODListTypeExclusion(mSelectedContent.globalServiceId, 1, true, APIConstants.TYPE_TVSEASON,
                APIConstants.PAGE_INDEX_COUNT,
                new CacheManager.CacheManagerCallback() {
                    @Override
                    public void OnCacheResults(List<CardData> dataList) {
                        if (dataList == null || dataList.isEmpty()) {
                            return;
                        }
                        Log.d(TAG, "fetchFilterDataWithLanguageAndGenre: onResponse: message - ");
                        List<CardData> dataList1 = new ArrayList<>(dataList);
                        similarContentList = removeDuplicates(dataList1);
                        if (similarContentList == null
                                || similarContentList.isEmpty()) {
                            return;
                        }
                        String title = "";
                        if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VODCHANNEL)) {
                            title = getResources().getString(R.string.similar_episodes_title);
                        } else if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VIDEO_ALBUM)) {
                            title = getResources().getString(R.string.similar_music_title);
                        } else if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_MUSIC_VIDEO)) {
                            title = getResources().getString(R.string.similar_music_title);
                        } else {
                            title = getResources().getString(R.string.similar_videos_title);
                        }
                        setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, title);

                    }

                    @Override
                    public void OnOnlineResults(List<CardData> dataList) {
                        if (dataList == null || dataList.isEmpty()) {
                            return;
                        }

                        List<CardData> dataList1 = new ArrayList<>(dataList);
                        similarContentList = removeDuplicates(dataList1);
                        if (similarContentList == null
                                || similarContentList.isEmpty()) {
                            return;
                        }
                        String title = "";
                        if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VODCHANNEL)) {
                            title = getResources().getString(R.string.similar_episodes_title);
                        } else if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VIDEO_ALBUM)) {
                            title = getResources().getString(R.string.similar_music_title);
                        } else if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_MUSIC_VIDEO)) {
                            title = getResources().getString(R.string.similar_music_title);
                        } else {
                            title = getResources().getString(R.string.similar_videos_title);
                        }
                        setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, title);

                    }

                    @Override
                    public void OnOnlineError(Throwable error, int errorCode) {
                        Log.d(TAG, "fetchCarouselData: OnOnlineError: t- ");
                    }
                });
    }

    public boolean isLive(CardData data) {
        if (data != null && data.generalInfo != null
                && APIConstants.TYPE_LIVE.equalsIgnoreCase(data.generalInfo.type)) {
            return true;
        }
        return false;
    }

    private List<CardData> removeSameProgram(List<CardData> dataList) {
        if (mSelectedContent == null
                || dataList == null
                || dataList.isEmpty()) {
            return dataList;
        }
        String globalServiceId = mSelectedContent.globalServiceId;
        if (isLive(mSelectedContent)) {
            globalServiceId = mSelectedContent._id;
        }
        if (globalServiceId == null) {
            return dataList;
        }
        List<CardData> cardDataList = new ArrayList<>();
        cardDataList.addAll(dataList);
        for (int i = 0; i < cardDataList.size(); i++) {
            if (cardDataList.get(i).globalServiceId != null && cardDataList.get(i).globalServiceId.equalsIgnoreCase(globalServiceId)) {
                cardDataList.remove(i);
                return cardDataList;
            }
        }

        return cardDataList;
    }

    */
/*private void fetchEpgData() {

        final Date date = SDKUtils.getCurrentDate(0);
        final String time = SDKUtils.getCurrentEpgT4ablePosition();
        final StringBuilder languages = new StringBuilder();
        if (mSelectedContent != null
                && mSelectedContent.content != null
                && mSelectedContent.content.language != null
                && mSelectedContent.content.language.size() > 0) {
            for (String language : mSelectedContent.content.language) {
                languages.append(languages.length() == 0 ? language : "," + language);
            }
        }
        final StringBuilder genres = new StringBuilder();
        if (mSelectedContent != null
                && mSelectedContent.content != null
                && mSelectedContent.content.genre != null
                && mSelectedContent.content.genre.size() > 0) {
            for (CardDataGenre genre : mSelectedContent.content.genre) {
                genres.append(genres.length() == 0 ? genre.name : "," + genre.name);
            }
        }
        final String oldLanguage = EPG.langFilterValues;
        final String oldGenre = EPG.genreFilterValues;
        EPG.langFilterValues = languages.toString();
        EPG.genreFilterValues = genres.toString();
        SDKLogger.debugLog("TVGuide: fetchEpgData: time- " + time + "" +
                " oldGenre- " + oldGenre + "" +
                " languages- " + languages + "" +
                " oldLanguage- " + oldLanguage + mSelectedContent.generalInfo);

        boolean isDVROnly = (PrefUtils.getInstance().getPrefEnablePastEpg() && 0 - PrefUtils.getInstance().getPrefNoOfPastEpgDays() < 0) ? true : false;

        EPG.getInstance(SDKUtils.getCurrentDate(0)).findPrograms(11, SDKUtils.getServerDateFormat(time, date), time, date, 1, true, SDKUtils.getMCCAndMNCValues(getApplicationContext()), isDVROnly, new EPG.CacheManagerCallback() {

            @Override
            public void OnlineResults(List<CardData> dataList, int pageIndex) {
                //System.out.println("phani size "+dataList.size());
                EPG.langFilterValues = oldLanguage;
                EPG.genreFilterValues = oldGenre;
                List list = removeSameProgram(dataList);
                if (list == null
                        || list.isEmpty()
                        || list.size() < 2) {
                  *//*
*/
/*  if (mListener != null) {
                        mListener.onSimilarMoviesDataLoaded(APIConstants.FAILED);
                    }*//*
*/
/*
                    return;
                }
                SDKLogger.debugLog("cardDataList" + removeDuplicates(list));

                // mListener.onSimilarMoviesDataLoaded(APIConstants.SUCCESS);
                //  AdapterSmallHorizontalCarousel mAdapterCarouselInfo = new AdapterSmallHorizontalCarousel(mContext, list);
                //  mRecyclerViewCarouselInfo.setAdapter(mAdapterCarouselInfo);
                setupRelatedContentRow(list, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, ContentDetailsActivityNeww.this.getString(R.string.currently_playing_Channels));

            }

            @Override
            public void OnlineError(Throwable error, int errorCode) {
             *//*
*/
/*   if (mListener != null) {
                    mListener.onSimilarMoviesDataLoaded(APIConstants.FAILED);
                }*//*
*/
/*
            }
        }, true);


    }*//*


    private myplexAPI api = myplexAPI.getInstance();

    private void fetchSimilarContent() {

        if (!TextUtils.isEmpty(artistName)) {
            parseActorConfig(artistName);
            return;
        }
        String contentId = mSelectedContent._id;
        if (mSelectedContent.isMusicVideo() || mSelectedContent.isVideoAlbum()) {
            fetchRelatedVideos();
            return;
        }
        if (mSelectedContent.isTVShow()) {
            if (mSelectedContent.generalInfo.showDisplayTabs != null) {
                if (mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayType() != null &&
                        mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayType().equalsIgnoreCase("monthly")) {
                    prepareDates();
                } else {
                    fetchEpisodes();
                }
            } else
                fetchEpisodes();
            return;
        }

      */
/*  if (mSelectedContent.isTVSeries()) {
            fetchSeasons();
            return;
        }*//*


        if (mSelectedContent.isLiveOrProgram()) {
            //fetchEpgData();
            return;

        }

        api.myplexAPIService.fetchSimilarMovies(contentId).enqueue(new Callback<SimilarMoviesData>() {
            @Override
            public void onResponse(Response<SimilarMoviesData> response, Retrofit retrofit) {
                if (response == null
                        || response.body() == null) {
//                            if (mListener != null) {
//                                mListener.onSimilarMoviesDataLoaded(APIConstants.FAILED);
//                            }
                    return;
                }

                Log.d(TAG, "fetchFilterDataWithLanguageAndGenre: onResponse: message- " + response.body().message);

                List<CardData> dataList = response.body().results;
                List<CardData> dataList1 = new ArrayList<>(dataList);
                similarContentList = removeDuplicates(dataList1);
                if (similarContentList == null
                        || similarContentList.isEmpty()
                        || similarContentList.size() < 3) {
                    return;
                }
                setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_BIG_ITEM, ContentDetailsActivityNeww.this.getString(R.string.Recommended));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "fetchFilterDataWithLanguageAndGenre: onResponse: t- " + t);
            }


        });

    }

    private void parseActorConfig(String person) {
        if (PrefUtils.getInstance().getArtistSearchLanguage() != null) {
            language = PrefUtils.getInstance().getArtistSearchLanguage();
        } else {
            language = PrefUtils.getInstance().getUserLanguage();
        }
        data = new Gson().fromJson(PrefUtils.getInstance().getArtistConfig(), ArtistConfig.class);
        if (data != null) {
            setUpRelatedArtistRows();
        }
    }


    public void prepareDates() {
        if (Utils.checkTypeMonthly(mSelectedContent)) {
            hardCodeCardData = mSelectedContent;
            if (!TextUtils.isEmpty(mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayText())) {
                mSelectedContent.generalInfo.showDisplayTabs.setShowDisplayText(mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayText().replace("MMM-YY", "MMMM yyyy"));
            }
            listOfMonths = CalenderUtils.getDatesBetween(CalenderUtils.getDateOnFormat(mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayStart()),
                    CalenderUtils.getDateOnFormat(mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayEnd()), mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayText());
            if (mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayOrder().equalsIgnoreCase("desc"))
                Collections.reverse(listOfMonths);
            setupRelatedContentTVShowRows(listOfMonths, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM);
        } else if (Utils.checkTypeEpisode(mSelectedContent)) {
            listOfMonths = Utils.getEpisodeList(mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayStart(),
                    mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayEnd(),
                    mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayfrequency());

        }
    }

    public void getMonthlyEpisodeList(String _id, int startIndex, int count) {

        RequestMonthlyEpisodeVODList.Params relatedVODListParams = new RequestMonthlyEpisodeVODList.Params(_id, startIndex, count);
        RequestMonthlyEpisodeVODList mRequestMonthlyVODList = new RequestMonthlyEpisodeVODList(relatedVODListParams, new APICallback<CardResponseData>() {
            String mAPIErrorMessage = null;

            @Override
            public void onResponse(APIResponse<CardResponseData> response) {

                if (response == null || response.body() == null) {
                    onFailure(new Throwable(APIConstants.ERROR_RESPONSE_OR_RESPONSE_BODY_NULL), APIRequest.ERR_UN_KNOWN);
                    return;
                }
                mAPIErrorMessage = response.body().message;
                if (response.body().results == null) {
                    onFailure(new Throwable(APIConstants.ERROR_EPMTY_RESULTS), APIRequest.ERR_UN_KNOWN);
                    return;
                }
                List<CardData> cardDataFromResult = null;
                if (!response.body().results.isEmpty()) {
                    cardDataFromResult = new ArrayList<>();
                    for (CardData cardData : response.body().results) {
                        cardDataFromResult.add(cardData);
                    }
                }
                if (mSelectedContent.isLoadMoreRequest) {
                    safelyNotifyItemChanged(mRelatedRowsFragment, position, cardDataFromResult, mRowsAdapterForRecommended);
                    return;
                }
                if (cardDataFromResult == null) {
                    //removeSimilarItem(rowAdapter.size() - 1, rowAdapter);
                    return;
                }
                */
/*if (cardDataFromResult == null || cardDataFromResult.isEmpty()) {
                    return;
                }*//*


                safelyNotifyItemChanged(mRelatedRowsFragment, position, cardDataFromResult, mRowsAdapterForRecommended);
            }

            @Override
            public void onFailure(Throwable t, int errorCode) {
                String errorMessage = null;
                String reason = t != null && (t.getMessage() != null) ? t.getMessage() : errorMessage;
                if (!TextUtils.isEmpty(mAPIErrorMessage)) {
                    reason = mAPIErrorMessage;
                }
            }
        });
        APIService.getInstance().execute(mRequestMonthlyVODList);

    }

    */
/*public void getEpisodeMonthlyList(final int position, String _id, String startDate, String endDate, final ArrayObjectAdapter listRowAdapter) {

        RequestEpisodeVODList.Params relatedVODListParams = new RequestEpisodeVODList.Params(_id,startDate,endDate);
        RequestEpisodeVODList mRequestEpisodeVODList = new RequestEpisodeVODList(relatedVODListParams, new APICallback<CardResponseData>() {
            String mAPIErrorMessage = null;

            @Override
            public void onResponse(APIResponse<CardResponseData> response) {
                *//*
*/
/*if(adapterTvEpisodes != null)
                    mEDetailRecyclerView.setAdapter(null);*//*
*/
/*
                if (response == null || response.body() == null) {
                    onFailure(new Throwable(APIConstants.ERROR_RESPONSE_OR_RESPONSE_BODY_NULL), APIRequest.ERR_UN_KNOWN);
                    return;
                }
                mAPIErrorMessage = response.body().message;
                if (response.body().results == null) {
                    onFailure(new Throwable(APIConstants.ERROR_EPMTY_RESULTS), APIRequest.ERR_UN_KNOWN);
                    return;
                }
                List<CardData> cardDataFromResult = null;
                if (!response.body().results.isEmpty()) {
                    cardDataFromResult = new ArrayList<>();
                    cardDataFromResult.clear();
                    for (CardData cardData : response.body().results) {
                        cardDataFromResult.add(cardData);
                    }
                    sRelatedEpisodesCache.put(listOfMonths.get(position), cardDataFromResult);
                }
                if (mSelectedContent.isLoadMoreRequest) {
                    safelyNotifyItemChanged(mRelatedRowsFragment, position, cardDataFromResult, mRowsAdapterForRecommended);
                    return;
                }
                *//*
*/
/*if (cardDataFromResult == null) {
                    removeSimilarItem(position, listRowAdapter);
                    //listOfMonths.remove(position);
                    return;
                }*//*
*/
/*
     *//*
*/
/*if (cardDataFromResult == null || cardDataFromResult.isEmpty()) {
                    return;
                }*//*
*/
/*

                safelyNotifyItemChanged(mRelatedRowsFragment, position, cardDataFromResult, mRowsAdapterForRecommended);
        }

            @Override
            public void onFailure(Throwable t, int errorCode) {
                String errorMessage = null;
                String reason = t != null && (t.getMessage() != null) ? t.getMessage() : errorMessage;
                if (!TextUtils.isEmpty(mAPIErrorMessage)) {
                    reason = mAPIErrorMessage;
                }
            }
        });
        APIService.getInstance().execute(mRequestEpisodeVODList);

    }*//*


    public void getEpisodeMonthlyList(final int position, String _id, String startDate, String endDate,
                                      boolean isCacheRequest, final ArrayObjectAdapter listRowAdapter, List<String> listOfMonths) {
        String monthName = null;
        if (listOfMonths.size() > 0) {
            monthName = listOfMonths.get(position);
        }
        if (monthName != null) {
            makeEpisodeMonthlyCall(monthName, _id, startDate, endDate, isCacheRequest, listRowAdapter, listOfMonths);
        }
    }

    public void makeEpisodeMonthlyCall(final String name, String _id, String startDate, String endDate,
                                       boolean isCacheRequest, final ArrayObjectAdapter listRowAdapter, List<String> listOfMonths) {
        new CacheManager().getEpisodeMonthlyList(name, _id, 1, startDate,
                endDate, isCacheRequest, listRowAdapter, listOfMonths,
                new CacheManager.CacheManagerCallback() {
                    @Override
                    public void OnCacheResults(List<CardData> dataList) {
                        checkSafelyNotifyItemChanged(mRelatedRowsFragment, name, dataList, mRowsAdapterForRecommended, listOfMonths);
                    }

                    @Override
                    public void OnOnlineResults(List<CardData> dataList) {
                        checkSafelyNotifyItemChanged(mRelatedRowsFragment, name, dataList, mRowsAdapterForRecommended, listOfMonths);
                    }

                    @Override
                    public void OnOnlineError(Throwable error, int errorCode) {
                        SDKLogger.debugLog("OnOnlineError  " + error);
                        if (error != null) {
                            error.printStackTrace();
                            SDKLogger.debugLog("OnOnlineError  message- " + error.getMessage());
                        }
                        isLoadMoreRequest = false;
                    }
                });
    }


    private void fetchElapsedTime() {
        ContentDetails.Params params = new ContentDetails.Params(mSelectedContent._id, null, null, 10, APIConstants.HTTP_NO_CACHE, APIConstants.PARAM_ELAPSED_TIME);
        ContentDetails contentDetails = new ContentDetails(params, new APICallback<CardResponseData>() {
            @Override
            public void onResponse(APIResponse<CardResponseData> response) {
                if (response != null && response.body() != null && response.body().results.size() > 0) {
                    if (mSelectedContent != null) {
                        mSelectedContent.elapsedTime = response.body().results.get(0).elapsedTime;
                        //setUpActionsPresentor(getContentActionList(mSelectedContent), mRowsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorCode) {

            }
        });
        APIService.getInstance().execute(contentDetails);

    }


    private void fetchEpisodes() {
        String contentId = mSelectedContent._id;
        SDKLogger.debugLog("ContentID" + " " + contentId);
        if (mSelectedContent.globalServiceId != null) {
            contentId = mSelectedContent.globalServiceId;
        }
        if (contentId == null) {
            return;
        }
        SDKLogger.debugLog("mStartIndex- " + mStartIndex);

        new CacheManager().getRelatedVODList(contentId, mStartIndex, true,
                new CacheManager.CacheManagerCallback() {
                    @Override
                    public void OnCacheResults(List<CardData> dataList) {
                        // isLoadMoreAvailable = true;
                        List<CardData> dataList1 = new ArrayList<>(dataList);
                        String title = "";
                        if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VODCHANNEL)) {
                            title = getResources().getString(R.string.similar_episodes_title);
                        } else if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VIDEO_ALBUM)) {
                            title = getResources().getString(R.string.similar_music_title);
                        } else {
                            title = getResources().getString(R.string.similar_videos_title);
                        }
                        Log.d("TAG", "currentItemPosition dataList1 cache" + dataList1.size());
                        similarContentList = removeDuplicates(dataList1);
                        Log.d("TAG", "currentItemPosition cache" + similarContentList.size());
                        if (isLoadMoreRequest) {
                            if (dataList.size() >= APIConstants.PAGE_INDEX_COUNT) {
                                isLoadMoreAvailable = true;
                            } else {
                                SDKLogger.debugLog("no more load more ");
                                isLoadMoreAvailable = false;
                            }

                            setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, title);
                            isLoadMoreRequest = false;
                            return;
                        }
                        if (similarContentList == null
                                || similarContentList.isEmpty()) {
                            return;
                        }
                        SDKLogger.debugLog("dataListSize-  " + dataList.size());
                        isLoadMoreAvailable = dataList.size() >= APIConstants.PAGE_INDEX_COUNT;
                        setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, title);
                    }

                    @Override
                    public void OnOnlineResults(List<CardData> dataList) {

                        String title = "";
                        if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VODCHANNEL)) {
                            title = getResources().getString(R.string.similar_episodes_title);
                        } else if (mSelectedContent != null
                                && mSelectedContent.generalInfo != null
                                && mSelectedContent.generalInfo.type != null
                                && mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VIDEO_ALBUM)) {
                            title = getResources().getString(R.string.similar_music_title);
                        } else {
                            title = getResources().getString(R.string.similar_videos_title);
                        }
                        List<CardData> dataList1 = new ArrayList<>(dataList);
                        similarContentList = removeDuplicates(dataList1);
                        if (isLoadMoreRequest) {
                            if (dataList.size() >= APIConstants.PAGE_INDEX_COUNT) {
                                isLoadMoreAvailable = true;
                            } else {
                                SDKLogger.debugLog("no more load more ");
                                isLoadMoreAvailable = false;
                            }
                            setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, title);
                            isLoadMoreRequest = false;
                            return;
                        }
                        if (similarContentList == null
                                || similarContentList.isEmpty()) {
                            return;
                        }
                        isLoadMoreAvailable = dataList.size() >= APIConstants.PAGE_INDEX_COUNT;
                        setupRelatedContentRow(similarContentList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, title);
                    }

                    @Override
                    public void OnOnlineError(Throwable error, int errorCode) {
                        SDKLogger.debugLog("OnOnlineError  " + error);
                        if (error != null) {
                            error.printStackTrace();
                            SDKLogger.debugLog("OnOnlineError  message- " + error.getMessage());
                        }
                        isLoadMoreRequest = false;
                    }
                });
    }

    private void fetchEpisodes(final int seasonPos) {
        final CardData seasonData = mSeasonList.get(seasonPos);
        new CacheManager().getRelatedVODList(seasonData._id, seasonData.startIndex, true,
                new CacheManager.CacheManagerCallback() {
                    @Override
                    public void OnCacheResults(List<CardData> dataList) {
                        if (seasonData != null
                                && dataList.size() >= APIConstants.PAGE_INDEX_COUNT) {
                            seasonData.isLoadMoreAvailable = true;
                        } else {
                            seasonData.isLoadMoreAvailable = false;
                        }
                       */
/* if (seasonData.isLoadMoreRequest) {
                            SDKLogger.debugLog("EpisodeResultData-   " + "seasonData.startIndex-    " + seasonData.startIndex + "dataListsize-   " + dataList.size());
                            safelyNotifyItemChanged(mRowsFragment, seasonPos, dataList, mRowsAdapter);
                            return;
                        }
                        if (dataList == null || dataList.isEmpty()) {
                            return;
                        }
                        safelyNotifyItemChanged(mRowsFragment, seasonPos, dataList, mRowsAdapter);
                        SDKLogger.debugLog("EpisodeResultData-   " + "seasonData.startIndex-    " + seasonData.startIndex + "dataListsize-   " + dataList.size());
*//*

                    }

                    @Override
                    public void OnOnlineResults(List<CardData> dataList) {
                        if (seasonData != null
                                && dataList.size() >= APIConstants.PAGE_INDEX_COUNT) {
                            seasonData.isLoadMoreAvailable = true;
                        } else {
                            seasonData.isLoadMoreAvailable = false;
                        }
                       */
/* if (seasonData.isLoadMoreRequest) {
                            safelyNotifyItemChanged(mRowsFragment, seasonPos, dataList, mRowsAdapter);
                            return;
                        }
                        if (dataList == null || dataList.isEmpty()) {
                            return;
                        }
                        safelyNotifyItemChanged(mRowsFragment, seasonPos, dataList, mRowsAdapter);*//*

                    }

                    @Override
                    public void OnOnlineError(Throwable error, int errorCode) {

                    }
                });
    }

    private void checkSafelyNotifyItemChanged(final RowsFragment mRelatedRowsFragment, final String name, final List<CardData> listCardData, final ArrayObjectAdapter mRowsAdapterForRecommended, List<String> mListOfMonths) {
        try {
            mRelatedRowsFragment.getVerticalGridView().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (!mRelatedRowsFragment.getVerticalGridView().isComputingLayout()) {
                        mRelatedRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
                        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
                        cardPresenter.setItemViewType(CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, APIConstants.TYPE_TVEPISODE);
                        cardPresenter.setOnSelectionListener(new CardPresenterVodafone.OnSelectionListener() {
                            @Override
                            public void onItemSelected(View view, boolean selected) {
                                if (selected && view.getTag() != null && view.getTag() instanceof CardData) {
                                    CardData cardData = (CardData) view.getTag();
                                    updateDescription(cardData);
                                }
                            }
                        });
                        if (listOfMonths.indexOf(name) >= mListOfMonths.size()) {
                            return;
                        }
                        if (listCardData == null || listCardData.size() == 0 || listCardData.isEmpty()) {
                            removeTheItem(name, mRowsAdapterForRecommended);
                            //listOfMonths.remove(position);
                            Log.d("datalist", "empty");
                            return;
                        }
                        int value = mListOfMonths.indexOf(name);
                        if (listCardData == null || listCardData.isEmpty()) {
                            SDKLogger.debugLog("layout type- " + "Empty");
                            return;
                        }
                        HeaderItem header = new HeaderItem(0, Const.EPISODES + mListOfMonths.get(value));
                        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                        listRowAdapter.addAll(0, listCardData);
                        ListRow listRow = new ListRow(header, listRowAdapter);
                        if (mRowsAdapterForRecommended.size() > 1)
                            mRowsAdapterForRecommended.replace(mListOfMonths.indexOf(name), listRow);
                        else
                            mRowsAdapterForRecommended.add(mListOfMonths.indexOf(name), listRow);
                    } else {
                        checkSafelyNotifyItemChanged(mRelatedRowsFragment, name, listCardData, mRowsAdapterForRecommended, mListOfMonths);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void safelyNotifyItemChanged(final RowsFragment mRelatedRowsFragment, final int position, final List<CardData> listCardData, final ArrayObjectAdapter mRowsAdapterForRecommended) {
        try {
            SDKLogger.debugLog("DeletionCarouselInfo: notify change " + position);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //final CardData seasonData = mSeasonList.get(position);
                    if (mRelatedRowsFragment != null && mRelatedRowsFragment.getVerticalGridView() != null && !mRelatedRowsFragment.getVerticalGridView().isComputingLayout()) {
                        mRelatedRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
                        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
                        cardPresenter.setOnSelectionListener(new CardPresenterVodafone.OnSelectionListener() {
                            @Override
                            public void onItemSelected(View view, boolean selected) {
                                if (selected && view.getTag() != null && view.getTag() instanceof CardData) {
                                    CardData cardData = (CardData) view.getTag();
                                    updateDescription(cardData);
                                }
                            }
                        });
                        String seasonName = null;
                        if (totalCarouselNamesList != null && totalCarouselNamesList.size() > 0) {
                            try {
                                seasonName = totalCarouselNamesList.get(position);
                                if (listCardData == null || listCardData.size() <= 0) {
                                    removeSimilarArtistItem(seasonName, position, mRowsAdapterForRecommended);
                                    return;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                return;
                            }
                        } else {
                            try {
                                seasonName = listOfMonths.get(position);
                                if (listCardData == null) {
                                    removeSimilarItem(seasonName, position, mRowsAdapterForRecommended);
                                    return;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                return;
                            }
                        }
                        */
/*if (seasonData.isLoadMoreRequest) {
                            int pos = position;
                            if (seasonData.isLoadMoreRequest) {
                                pos = position + 1;
                                SDKLogger.debugLog("seasonPosition-" + "     " + position);
                            }
                            SDKLogger.debugLog("adapter position- " + pos);
                            ListRow listRow = (ListRow) mRowsAdapter.get(pos);
                            ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) listRow.getAdapter();
                            removeItem(rowAdapter.size() - 1, rowAdapter);
                            rowAdapter.addAll(rowAdapter.size(), listCardData);
                            seasonData.isLoadMoreRequest = false;
                            return;
                        }*//*

                        cardPresenter.setItemViewType(CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM, APIConstants.TYPE_TVEPISODE);
                        HeaderItem header = new HeaderItem(0, Const.EPISODES + seasonName);
                        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                        listRowAdapter.addAll(0, listCardData);
                        ListRow listRow = new ListRow(header, listRowAdapter);
                        if (mRowsAdapterForRecommended.size() > 1)
                            mRowsAdapterForRecommended.replace(position, listRow);
                        else
                            mRowsAdapterForRecommended.add(position, listRow);
                    } else {
                        safelyNotifyItemChanged(mRelatedRowsFragment, position, listCardData, mRowsAdapterForRecommended);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPositionFromList(String carouselName) {
        for (int i = 0; i < totalCarouselNamesList.size(); i++) {
            if (totalCarouselNamesList.get(i).equalsIgnoreCase(carouselName)) {
                totalCarouselNamesList.remove(i);
                return i;
            }
        }
        return -1;
    }

    private int getCarouselPositionFromList(String carouselName) {
        for (int i = 0; i < listOfMonths.size(); i++) {
            if (listOfMonths.get(i).equalsIgnoreCase(carouselName)) {
                listOfMonths.remove(i);
                return i;
            }
        }
        return -1;
    }

    private void safelyNotifyItemChangedWithOutPost(RowsFragment mRowsFragment, final int position, final List<CardData> listCardData, final ArrayObjectAdapter rowsAdapter) {

        final CardData seasonData = mSeasonList.get(position);
        if (!mRowsFragment.getVerticalGridView().isComputingLayout()) {
            mRowsFragment.getVerticalGridView().getRecycledViewPool().clear();
            CardPresenter cardPresenter = new CardPresenter();
            cardPresenter.setOnSelectionListener(new CardPresenter.OnSelectionListener() {
                @Override
                public void onItemSelected(View view, boolean selected) {

                    if (selected && view.getTag() != null && view.getTag() instanceof CardData) {
                        CardData cardData = (CardData) view.getTag();
                        updateDescription(cardData);
                    }
                }
            });
            String seasonName = mSeasonNames.get(position);

            if (listCardData == null) {
                return;
            }
            if (seasonData.isLoadMoreRequest) {
                int pos = position;
                if (seasonData.isLoadMoreRequest) {
                    pos = position + 1;
                    SDKLogger.debugLog("seasonPosition-" + "     " + position);
                }
                SDKLogger.debugLog("adapter position- " + pos);
                ListRow listRow = (ListRow) mRowsAdapter.get(pos);
                ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) listRow.getAdapter();
                removeItemWithOutPost(rowAdapter.size() - 1, rowAdapter);
                rowAdapter.addAll(rowAdapter.size(), listCardData);
                return;
            }
            cardPresenter.setItemViewType(CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM);
            HeaderItem header = new HeaderItem(0, seasonName);
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            listRowAdapter.addAll(0, listCardData);
            ListRow listRow = new ListRow(header, listRowAdapter);
            if (rowsAdapter.size() > 1)
                rowsAdapter.replace(position + 1, listRow);
            else
                rowsAdapter.add(position, listRow);
        } else {
            safelyNotifyItemChangedWithOutPost(mRowsFragment, position, listCardData, rowsAdapter);
        }

    }

    private boolean isActionButtonsPresenterAdded(ArrayObjectAdapter rowsAdapter) {
        if (rowsAdapter == null
                || rowsAdapter.size() == 0
                || rowsAdapter.get(0) == null) return false;

        ListRow listRow = ((ListRow) rowsAdapter.get(0));
        ObjectAdapter adapter = listRow.getAdapter();
        if (adapter.size() <= 0) {
            return false;
        }
        Presenter presenter = adapter.getPresenter(adapter.get(0));
        //if (presenter instanceof ButtonPresenter) return true;
        return false;
    }

    private void fetchSeasons() {
        String contentId = mSelectedContent._id;
        if (mSelectedContent.globalServiceId != null) {
            contentId = mSelectedContent.globalServiceId;
        }
        if (contentId == null) {
            return;
        }

        CacheManager mCacheManager = new CacheManager();
        mCacheManager.getRelatedVODListTypeExclusion(contentId, 1, true, APIConstants.TYPE_TVSEASON,
                15,
                new CacheManager.CacheManagerCallback() {
                    @Override
                    public void OnCacheResults(List<CardData> dataList) {
                        if (dataList == null || dataList.isEmpty()) {
                            showNoDataMessage();
                            return;
                        }
                        mSeasonList = dataList;
                        prepareSeasons();
                    }

                    @Override
                    public void OnOnlineResults(List<CardData> dataList) {
                        if (dataList == null || dataList.isEmpty()) {
                            showNoDataMessage();
                            return;
                        }
                        mSeasonList = dataList;
                        prepareSeasons();
                    }

                    @Override
                    public void OnOnlineError(Throwable error, int errorCode) {
                        Log.d(TAG, "fetchCarouselData: OnOnlineError: t- ");
                        if (errorCode == APIRequest.ERR_NO_NETWORK) {
//                            AlertDialogUtil.showToastNotification(getString(R.string.network_error));
                            return;
                        }
                        showNoDataMessage();
                    }
                });
    }

    private void prepareSeasons() {
        setupRelatedContentRows(mSeasonList, CarouselInfoData.ITEM_TYPE_HORIZONTAL_LIST_SMALL_ITEM);
    }

    private void showNoDataMessage() {

    }

    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(this);
        mBackgroundManager.attach(getWindow());
        mDefaultBackground = ContextCompat.getDrawable(this, R.drawable.banner_placeholder);
        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void updateBackground(String uri) {

        Log.v(TAG, "updateBackground called");
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .centerCrop()
                .placeholder(mDefaultBackground)
                .error(mDefaultBackground)
                .into(new SimpleTarget<Bitmap>(mMetrics.widthPixels, mMetrics.heightPixels) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mBackgroundManager.setBitmap(resource);

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.banner_placeholder);
                        mBackgroundManager.setBitmap(icon);
                    }
                });


    }

    private void setupSimilarContentRow(List<CardData> cardDataList, int viewType, String title) {
        mRelatedRowsFragment = new RowsFragment();
        mRelatedRowsFragment.setExpand(true);
        CustomListRowPresenter movieCustomListRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL, false);
        movieCustomListRowPresenter.setShadowEnabled(false);
        movieCustomListRowPresenter.enableChildRoundedCorners(true);
        BannerRowHeaderPresenter headerPresenter = new BannerRowHeaderPresenter();
        movieCustomListRowPresenter.setHeaderPresenter(headerPresenter);
        mRowsAdapterForRecommended = new ArrayObjectAdapter(movieCustomListRowPresenter);
        mRelatedRowsFragment.setAdapter(mRowsAdapterForRecommended);

        mRelatedRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
                CardData movieData = (CardData) o;
                if (movieData != null) {
                    onRelatedMediaSelected(movieData);
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRelatedRowsFragment != null && mRelatedRowsFragment.getVerticalGridView() != null) {
                    mRelatedRowsFragment.getVerticalGridView().getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                        @Override
                        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                            if (oldFocus != null && newFocus != null) {
                                Log.e("ContentBrowse", "Old Focus: " + oldFocus.toString() + " " + "New Focus: " + newFocus.toString());
                                if (newFocus.getTag() != null) {
                                    if (!shouldReverseTheBackgroundOfFragmentContainer) {
                                        showTopFragmentUI();
                                        scrollToTop();
                                    }
                                } else {
                                    if (shouldReverseTheBackgroundOfFragmentContainer) {
                                        hideTopFragmentUI();
                                        //scrollToBottom();
                                        bannerScrollToBottom(mRelatedRowsFragment.getVerticalGridView());
                                        */
/*if(mRowsFragment != null && mRowsFragment.getView() != null) {
                                            mRowsFragment.getView().requestFocus();
                                        }*//*

                                    }

                                }
                            }
                        }
                    });
                }
            }
        }, 400);
        mRelatedRowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row) {
                if (item instanceof CardData) {
                    onClickOfRecommendation((CardData) item, viewHolder.view);

                } else {
                    onClickOfRecommendation(((ArrayList<CardData>) item).get(0), viewHolder.view);
                }
            }
        });
        if (isLoadMoreRequest) {
            ListRow listRow = (ListRow) mRowsAdapterForRecommended.get(position);
            SDKLogger.debugLog("EpisodePosition-" + "   " + position);
            ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) listRow.getAdapter();
            SDKLogger.debugLog("rowAdapterList-   " + "size      " + rowAdapter.size());
            removeItemWithOutPost(rowAdapter.size() - 1, rowAdapter);
            SDKLogger.debugLog("rowAdapterList-   " + "size      " + rowAdapter.size());
            if (cardDataList == null || cardDataList.isEmpty()) {
                return;
            }
            rowAdapter.addAll(rowAdapter.size(), cardDataList);
            return;
        }
        if (cardDataList == null || cardDataList.isEmpty()) {
            return;
        }
        //setUpActionsPresentor(getContentActionList(mSelectedContent), mRowsAdapter);
        mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
        cardPresenter.setItemViewType(viewType);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for (CardData c : cardDataList) {
            listRowAdapter.add(c);
        }

        // Only add the header and row for recommendations if there are any recommended content.
        if (listRowAdapter.size() > 0) {
            HeaderItem header = new HeaderItem(0, title);
            mRowsAdapterForRecommended.add(mRowsAdapterForRecommended.size(), new ListRow(header, listRowAdapter));
            mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
        }
    }

    private void setupRelatedContentRow(List<CardData> cardDataList, int viewType, String title) {
        mRelatedRowsFragment = new RowsFragment();
        mRelatedRowsFragment.setExpand(true);
//        pushFragment(mRelatedRowsFragment, R.id.related_content_details_fragment);
        try {
            FragmentManager fragmentManager = getFragmentManager();
//            removeFragment(mCurrentFragment);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.related_content_details_fragment, mRelatedRowsFragment);
//            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (Throwable e) {
            e.printStackTrace();
//            Crashlytics.logException(e);
        }
        CustomListRowPresenter movieCustomListRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL, false);
        movieCustomListRowPresenter.setShadowEnabled(false);
        movieCustomListRowPresenter.enableChildRoundedCorners(true);
        BannerRowHeaderPresenter headerPresenter = new BannerRowHeaderPresenter();
        movieCustomListRowPresenter.setRecycledPoolSize(movieCustomListRowPresenter, 0);
        movieCustomListRowPresenter.setHeaderPresenter(headerPresenter);
        mRowsAdapterForRecommended = new ArrayObjectAdapter(movieCustomListRowPresenter);
        mRowsAdapterForRecommended.setHasStableIds(true);
        mRelatedRowsFragment.setAdapter(mRowsAdapterForRecommended);
        Log.e("ANRCheck", "setupRelatedContentRow");

        mRelatedRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
                CardData movieData = (CardData) o;
                if (movieData != null) {
                    onRelatedMediaSelected(movieData);
                }
                Log.e("ANRCheck", "setOnItemViewSelectedListener");
                if (row instanceof ListRow) {
                    ListRow listRow = (ListRow) row;
                    ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) listRow.getAdapter();
                    Log.d(TAG, "currentItemPosition position" + position);
                    Log.d(TAG, "currentItemPosition mRowsAdapterForRecommended" + mRowsAdapterForRecommended.size());
                    int currentItemPosition = rowAdapter.indexOf(o);
                    int selectedItemPositionOffset = rowAdapter.size() / 2;
                    int offsetItemPosition = (rowAdapter.size() - selectedItemPositionOffset) - 1;
                    Log.d(TAG, "onItemSelected selected");
                    Log.d(TAG, "currentItemPosition" + currentItemPosition);
                    Log.d(TAG, "currentItemPosition position" + position);
                    Log.d(TAG, "currentItemPosition offsetItemPosition" + offsetItemPosition);
                    if (currentItemPosition >= offsetItemPosition) {
                        mStartIndex++;
                        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
                        if (mSelectedContent.globalServiceId != null) {
                            new CacheManager().getRelatedVODList(mSelectedContent.globalServiceId, mStartIndex, true,
                                    new CacheManager.CacheManagerCallback() {
                                        @Override
                                        public void OnCacheResults(List<CardData> dataList) {
                                            List<CardData> dataList1 = new ArrayList<>(dataList);
                                            for (CardData c : dataList1) {
                                                rowAdapter.add(c);
                                            }
                                        }

                                        @Override
                                        public void OnOnlineResults(List<CardData> dataList) {
                                            List<CardData> dataList1 = new ArrayList<>(dataList);
                                            similarContentList = removeDuplicates(dataList1);

                                            for (CardData c : dataList1) {
                                                rowAdapter.add(c);
                                            }
                                        }

                                        @Override
                                        public void OnOnlineError(Throwable error, int errorCode) {
                                            Log.d(TAG, " currentItem PosistiongetRelatedVODList");
                                        }
                                    });
                        }
                    }
                }
            }
        });

        if (mRelatedRowsFragment != null && mRelatedRowsFragment.getVerticalGridView() != null) {
            mRelatedRowsFragment.getVerticalGridView().getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                @Override
                public void onDraw() {
                    //scrollToBottom();
                    bannerScrollToBottom(mRelatedRowsFragment.getVerticalGridView());
                }
            });
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRelatedRowsFragment != null && mRelatedRowsFragment.getVerticalGridView() != null) {
                    mRelatedRowsFragment.getVerticalGridView().getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                        @Override
                        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                            if (oldFocus != null && newFocus != null) {
                                Log.e("ContentBrowse", "Old Focus: " + oldFocus.toString() + " " + "New Focus: " + newFocus.toString());
                                if (newFocus.getTag() != null) {
                                    if (!shouldReverseTheBackgroundOfFragmentContainer) {
                                        showTopFragmentUI();
                                        scrollToTop();
                                    }
                                } else {
                                    if (shouldReverseTheBackgroundOfFragmentContainer) {
                                        hideTopFragmentUI();
                                        //scrollToBottom();
                                        bannerScrollToBottom(mRelatedRowsFragment.getVerticalGridView());
                                        */
/*if(mRowsFragment != null && mRowsFragment.getView() != null) {
                                            mRowsFragment.getView().requestFocus();
                                        }*//*

                                    }

                                }
                            }
                        }
                    });
                }
            }
        }, 400);
        mRelatedRowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row) {
                if (item instanceof CardData) {
                    onClickOfRecommendation((CardData) item, viewHolder.view);

                } else {
                    onClickOfRecommendation(((ArrayList<CardData>) item).get(0), viewHolder.view);
                }
            }
        });
        if (isLoadMoreRequest) {
            ListRow listRow = (ListRow) mRowsAdapterForRecommended.get(position);
            SDKLogger.debugLog("EpisodePosition-" + "   " + position);
            ArrayObjectAdapter rowAdapter = (ArrayObjectAdapter) listRow.getAdapter();
            SDKLogger.debugLog("rowAdapterList-   " + "size      " + rowAdapter.size());
            removeItemWithOutPost(rowAdapter.size() - 1, rowAdapter);
            SDKLogger.debugLog("rowAdapterList-   " + "size      " + rowAdapter.size());
            if (cardDataList == null || cardDataList.isEmpty()) {
                return;
            }
            rowAdapter.addAll(rowAdapter.size(), cardDataList);
            return;
        }
        if (cardDataList == null || cardDataList.isEmpty()) {
            return;
        }
        //setUpActionsPresentor(getContentActionList(mSelectedContent), mRowsAdapter);
        mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
        if (mSelectedContent.generalInfo.type.equals(APIConstants.TYPE_MOVIE) ||
                mSelectedContent.generalInfo.type.equals(Const.VIDEO_ALBUM)) {
            cardPresenter.setItemViewType(viewType);
        } else if ((mSelectedContent.publishingHouse != null && mSelectedContent.publishingHouse.publishingHouseId == Const.COMEDYCLIPS_PUBLISHINGHOUSEID) ||
                (mSelectedContent.publishingHouse != null && mSelectedContent.publishingHouse.publishingHouseId == Const.TRAILER_PUBLISHINGHOUSEID)) {
            cardPresenter.setItemViewType(viewType);
        } else if (mSelectedContent.generalInfo.type.equals(Const.VODCHANNEL)) {
            cardPresenter.setItemViewType(viewType, APIConstants.TYPE_TVEPISODE);
        }

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for (CardData c : cardDataList) {
            listRowAdapter.add(c);
        }

        // Only add the header and row for recommendations if there are any recommended content.
        if (listRowAdapter.size() > 0) {
            HeaderItem header = new HeaderItem(0, title);
            mRowsAdapterForRecommended.add(mRowsAdapterForRecommended.size(), new ListRow(header, listRowAdapter));
            mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
        }
    }

    private void onRelatedMediaSelected(CardData mCardData) {
        if (mContentDetailTopFragment != null) {
            mContentDetailTopFragment.updateCardData(mCardData);
        }
    }

    */
/*
        Will be called if the newFocus element has non-null tag
        Shows the TOP Fragment UI
     *//*

    private void showTopFragmentUI() {

        if (mContentDetailTopFragment != null && mContentDetailTopFragment.getView() != null) {
            fadeInView(mContentDetailTopFragment.getView());
            mContentDetailTopFragment.getView().setVisibility(VISIBLE);
        }
        if (mFragmentContainer != null) {
            shouldReverseTheBackgroundOfFragmentContainer = true;
            TransitionDrawable transitionDrawable = (TransitionDrawable) mFragmentContainer.getBackground();
            transitionDrawable.startTransition(500);
        }
    }

    */
/*
        Will be called if the newFocus element has null tag
        hide the TOP Fragment UI
     *//*

    private void hideTopFragmentUI() {
        if (mContentDetailTopFragment != null && mContentDetailTopFragment.getView() != null) {
            fadeOutView(mContentDetailTopFragment.getView());
            mContentDetailTopFragment.getView().setVisibility(View.INVISIBLE);
        }
        if (mFragmentContainer != null) {
            shouldReverseTheBackgroundOfFragmentContainer = false;
            TransitionDrawable transitionDrawable = (TransitionDrawable) mFragmentContainer.getBackground();
            transitionDrawable.reverseTransition(500);
        }
    }

    AnimatorSet animatorSet = new AnimatorSet();

    private AnimatorSet getAnimatorAgent() {
        return animatorSet;
    }

    public void scrollToTop() {

       */
/* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        TransitionManager.beginDelayedTransition(mTransitionGroup,autoTransition);
        mRelatedRowsFragment.setAlignment(height/16);*//*


        VerticalGridView verticalGridView = mRelatedRowsFragment.getVerticalGridView();
        if (verticalGridView == null)
            return;

        int distance = verticalGridView.getTop() + verticalGridView.getHeight();

        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(verticalGridView, "alpha", 0, 1),
                ObjectAnimator.ofFloat(verticalGridView, "translationY", -distance, 0)
        );
        UiUtil.translateY(verticalGridView, 0f, 500);

    }

    public void bannerScrollToBottom(View view) {
        if (view == null)
            return;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int viewHeight = (int) (displayMetrics.heightPixels / (3.8));

        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", viewHeight);
        animation.setDuration(300);
        animation.start();
    }

    public void scrollToBottom() {
    */
/*    DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        TransitionManager.beginDelayedTransition(mTransitionGroup,autoTransition);
        mRelatedRowsFragment.setAlignment(height/3);*//*

        VerticalGridView verticalGridView = mRelatedRowsFragment.getVerticalGridView();
        if (verticalGridView == null)
            return;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int viewHeight = displayMetrics.heightPixels / 5;

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0f, Animation.RELATIVE_TO_PARENT, verticalGridView.getX(),
                Animation.RELATIVE_TO_PARENT,
                0f,
                Animation.RELATIVE_TO_PARENT, (float) viewHeight);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                verticalGridView.setY(viewHeight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        verticalGridView.setAnimation(translateAnimation);
        verticalGridView.animate();
    }

    */
/*public void scrollToTop(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        TransitionManager.beginDelayedTransition((ViewGroup) mRelatedRowsFragment.getView());
        mRelatedRowsFragment.setAlignment(height/16);
    }

    public void scrollToBottom() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        TransitionManager.beginDelayedTransition((ViewGroup) mRelatedRowsFragment.getView());
        mRelatedRowsFragment.setAlignment(height/3);
    }*//*


    private void fadeOutView(final View titleView) {
        Animation slideOut = AnimationUtils.loadAnimation(titleView.getContext(), R.anim.slide_out);
        slideOut.setInterpolator(new DecelerateInterpolator());
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                titleView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        titleView.startAnimation(slideOut);

    }

    private void fadeInView(final View titleView) {
        Animation slideIn = AnimationUtils.loadAnimation(titleView.getContext(), R.anim.slide_in);
        slideIn.setInterpolator(new AccelerateInterpolator());
        titleView.setAnimation(slideIn);
        titleView.animate();
        titleView.setVisibility(VISIBLE);
    }

    private List<String> prepareSeasonNames(List<CardData> dataList) {
        List<String> seasons = new ArrayList<>();
        String seasonText = "Season ";
        for (Iterator<CardData> iterator = dataList.iterator(); iterator.hasNext(); ) {
            CardData seasonData = iterator.next();
            if (seasonData.content != null
                    && seasonData.content.serialNo != null) {
                seasons.add(seasonText + seasonData.content.serialNo);
            }
        }
        return seasons;
    }

    private List<CardData> getDummyCardData() {
        if (!mDummyCarouselData.isEmpty()) {
            return mDummyCarouselData;
        }
        for (int i = 0; i < 10; i++) {
            CardData cardData = new CardData();
            cardData.generalInfo = new CardDataGeneralInfo();
            cardData.generalInfo.title = "No Information Available";
            mDummyCarouselData.add(cardData);
        }
        return mDummyCarouselData;
    }

    private void setupRelatedContentRows(List<CardData> cardDataList, int viewType) {

        if (cardDataList == null || cardDataList.isEmpty()) {
            return;
        }
        mRelatedRowsFragment = new RowsFragment();
        mRelatedRowsFragment.setExpand(true);
        CustomListRowPresenter movieCustomListRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL, false);
        movieCustomListRowPresenter.setShadowEnabled(false);
        movieCustomListRowPresenter.enableChildRoundedCorners(true);
        BannerRowHeaderPresenter headerPresenter = new BannerRowHeaderPresenter();
        movieCustomListRowPresenter.setHeaderPresenter(headerPresenter);
        mRowsAdapterForRecommended = new ArrayObjectAdapter(movieCustomListRowPresenter);
        mRelatedRowsFragment.setAdapter(mRowsAdapterForRecommended);
        Log.e("ANRCheck", "setupRelatedContentRows");
        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
        cardPresenter.setItemViewType(viewType);
        updateActionButtonsForTVSeries(mSeasonList, mRowsAdapter);
        mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size());
        mSeasonNames = prepareSeasonNames(cardDataList);
        for (int i = 0; i < mSeasonNames.size(); i++) {
            String seasontitle = mSeasonNames.get(i);
            HeaderItem header = new HeaderItem(0, seasontitle);
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            mSeasonList.get(i).startIndex = 1;
            fetchEpisodes(i);
            listRowAdapter.addAll(0, getDummyCardData());
            mRowsAdapter.add(mRowsAdapter.size(), new ListRow(header, listRowAdapter));
        }
        mSelectedContent.isLoadMoreRequest = false;
        mSelectedContent.isLoadMoreAvailable = false;
        mSelectedContent.startIndex = 1;
        mRowsAdapter.notifyArrayItemRangeChanged(0, mRowsAdapter.size());

    }

    private void setUpRelatedArtistRows() {
        if (data != null && data.artistcarousel != null) {
            artistcarousels = data.artistcarousel;
            mRelatedRowsFragment = new RowsFragment();
            mRelatedRowsFragment.setExpand(true);
            pushFragment(mRelatedRowsFragment, R.id.related_content_details_fragment);
            CustomListRowPresenter movieCustomListRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL, false);
            movieCustomListRowPresenter.setShadowEnabled(false);
            movieCustomListRowPresenter.enableChildRoundedCorners(true);
            BannerRowHeaderPresenter headerPresenter = new BannerRowHeaderPresenter();
            movieCustomListRowPresenter.setHeaderPresenter(headerPresenter);
            mRowsAdapterForRecommended = new ArrayObjectAdapter(movieCustomListRowPresenter);
            mRelatedRowsFragment.setAdapter(mRowsAdapterForRecommended);
            Log.e("ANRCheck", "setUpRelatedArtistRows");
            CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
            for (int i = 0; i < artistcarousels.size(); i++) {
                totalCarouselNamesList.add(artistcarousels.get(i).displayName);
                HeaderItem header = new HeaderItem(0, artistcarousels.get(i).displayName);
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                makeArtistAPiCall(artistcarousels.get(i).key, artistcarousels.get(i).displayName, 1, mRowsAdapterForRecommended,
                        artistName, artistcarousels.get(i).orderBy, artistcarousels.get(i).orderMode, artistcarousels.get(i).pageCount, artistcarousels.get(i).publishingHouse, null, i);
                listRowAdapter.addAll(0, getDummyCardData());
                mRowsAdapterForRecommended.add(mRowsAdapterForRecommended.size(), new ListRow(header, listRowAdapter));
            }
            mRelatedRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
                @Override
                public void onItemSelected(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
                    CardData movieData = (CardData) o;
                    if (movieData != null) {
                        onRelatedMediaSelected(movieData);
                    }
                    Log.e("ANRCheck", "setUpRelatedArtistRows setOnItemViewSelectedListener");
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRelatedRowsFragment != null && mRelatedRowsFragment.getVerticalGridView() != null) {
                        mRelatedRowsFragment.getVerticalGridView().getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                            @Override
                            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                                if (oldFocus != null && newFocus != null) {
                                    Log.e("ContentBrowse", "Old Focus: " + oldFocus.toString() + " " + "New Focus: " + newFocus.toString());
                                    if (newFocus.getTag() != null) {
                                        if (!shouldReverseTheBackgroundOfFragmentContainer) {
                                            showTopFragmentUI();
                                            scrollToTop();
                                        }
                                    } else {
                                        if (shouldReverseTheBackgroundOfFragmentContainer) {
                                            hideTopFragmentUI();
                                            //scrollToBottom();
                                            bannerScrollToBottom(mRelatedRowsFragment.getVerticalGridView());
                                            */
/*if(mRowsFragment != null && mRowsFragment.getView() != null) {
                                                mRowsFragment.getView().requestFocus();
                                            }*//*

                                        }

                                    }
                                }
                            }
                        });
                    }
                }
            }, 400);
            mRelatedRowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
                @Override
                public void onItemClicked(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row) {
                    if (item instanceof CardData) {
                        onClickOfRecommendation((CardData) item, viewHolder.view);

                    } else {
                        onClickOfRecommendation(((ArrayList<CardData>) item).get(0), viewHolder.view);
                    }
                }
            });
        }
    }

    public void makeArtistAPiCall(final String key, final String displayName, final int startIndex,
                                  final ArrayObjectAdapter arrayObjectAdapter, final String person,
                                  final String orderBy, final String orderMode, final int count, final String publishingHouse, final CustomListRow listRow, int position) {
        myplexAPI api = myplexAPI.getInstance();
        myplexAPI.myplexAPIInterface myplexAPIService = api.myplexAPIService;
        String clientKey = PrefUtils.getInstance().getPrefClientkey();

        Call<CardResponseData> call = myplexAPIService.retrieveContentList(clientKey,
                key, "", "", "contents,images,generalInfo,publishingHouse,packages",
                startIndex, count, "", person, orderBy, orderMode, language, publishingHouse);
        call.enqueue(new Callback<CardResponseData>() {

            @Override
            public void onResponse(Response<CardResponseData> response, Retrofit retrofit) {
                if (response == null)
                    return;
                if (response.body() != null && response.code() == 200) {
                    List<CardData> results = response.body().results;
                    safelyNotifyItemChanged(mRelatedRowsFragment, position, results, mRowsAdapterForRecommended);
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void setupRelatedContentTVShowRows(List<String> listOfMonths, int viewType) {

        if (listOfMonths == null || listOfMonths.isEmpty()) {
            return;
        }
        mRelatedRowsFragment = new RowsFragment();
        mRelatedRowsFragment.setExpand(true);
        pushFragment(mRelatedRowsFragment, R.id.related_content_details_fragment);
        CustomListRowPresenter movieCustomListRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL, false);
        movieCustomListRowPresenter.setShadowEnabled(false);
        movieCustomListRowPresenter.enableChildRoundedCorners(true);
        BannerRowHeaderPresenter headerPresenter = new BannerRowHeaderPresenter();
        movieCustomListRowPresenter.setRecycledPoolSize(movieCustomListRowPresenter, 0);
        movieCustomListRowPresenter.setHeaderPresenter(headerPresenter);
        mRowsAdapterForRecommended = new ArrayObjectAdapter(movieCustomListRowPresenter);
        mRelatedRowsFragment.setAdapter(mRowsAdapterForRecommended);
        Log.e("ANRCheck", "setupRelatedContentTVShowRows");
        CardPresenterVodafone cardPresenter = new CardPresenterVodafone();
        //cardPresenter.setItemViewType(viewType);
        //mRowsAdapterForRecommended.notifyArrayItemRangeChanged(0, mRowsAdapterForRecommended.size());
        for (int i = 0; i < listOfMonths.size(); i++) {
            String seasontitle = listOfMonths.get(i);
            HeaderItem header = new HeaderItem(0, Const.EPISODES + seasontitle);
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            if (Utils.checkTypeMonthly(mSelectedContent)) {

                String startDate = CalenderUtils.getStartDateFromString(listOfMonths.get(i), mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayText());
                String endDate = CalenderUtils.getEndDateFromString(listOfMonths.get(i), mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayText());
                getEpisodeMonthlyList(i, mSelectedContent.globalServiceId, startDate, endDate, true, listRowAdapter, listOfMonths);
            } else {
                getMonthlyEpisodeList(mSelectedContent.globalServiceId, i, Integer.parseInt(mSelectedContent.generalInfo.showDisplayTabs.getShowDisplayfrequency()));
            }
            //listRowAdapter.addAll(0, getDummyCardData());
            mRowsAdapterForRecommended.add(mRowsAdapterForRecommended.size(), new ListRow(header, listRowAdapter));
        }

        mRelatedRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
                CardData movieData = (CardData) o;
                if (movieData != null) {
                    onRelatedMediaSelected(movieData);
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRelatedRowsFragment != null && mRelatedRowsFragment.getVerticalGridView() != null) {
                    mRelatedRowsFragment.getVerticalGridView().getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                        @Override
                        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                            if (oldFocus != null && newFocus != null) {
                                Log.e("ContentBrowse", "Old Focus: " + oldFocus.toString() + " " + "New Focus: " + newFocus.toString());
                                if (newFocus.getTag() != null) {
                                    if (!shouldReverseTheBackgroundOfFragmentContainer) {
                                        showTopFragmentUI();
                                        scrollToTop();
                                    }
                                } else {
                                    if (shouldReverseTheBackgroundOfFragmentContainer) {
                                        hideTopFragmentUI();
                                        //scrollToBottom();
                                        bannerScrollToBottom(mRelatedRowsFragment.getVerticalGridView());
                                        */
/*if(mRowsFragment != null && mRowsFragment.getView() != null) {
                                            mRowsFragment.getView().requestFocus();
                                        }*//*

                                    }

                                }
                            }
                        }
                    });
                }
            }
        }, 400);
        mRelatedRowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row) {
                if (item instanceof CardData) {
                    onClickOfRecommendation((CardData) item, viewHolder.view);

                } else {
                    onClickOfRecommendation(((ArrayList<CardData>) item).get(0), viewHolder.view);
                }
            }
        });
    }

    */
/**
     * Get content action list.
     *
     * @return List of action for provided content.
     *//*

    public Action getContentAction(String lb, int id) {
        return new com.myplex.model.Action()
                .setId(id)
                .setLabel1(lb);
    }

    private void updateActionButtonsForTVSeries(List<CardData> mSeasonList, ArrayObjectAdapter rowsAdapter) {
        if (mSeasonList == null || mSeasonList.isEmpty()) {
            return;
        }
        List<Action> contentActionList = new ArrayList<>();
        StringBuilder actionLabel = new StringBuilder();
        String seasonText = "S";
        String episodeText = "E";
        if (mSeasonList.size() == 1) {
            actionLabel.append(getString(R.string.start_watching_episode));
            actionLabel.append("\n");
            actionLabel.append(" " + seasonText);
            CardData seasonData = mSeasonList.get(mSeasonList.size() - 1);
            String seasonNo = seasonData.getSeasonNo();
            if (!TextUtils.isEmpty(seasonNo)) {
                actionLabel.append(seasonNo + "- ");
            }
            actionLabel.append(episodeText + " 01");
            contentActionList.add(getContentAction(actionLabel.toString(), Action.ACTION_ID_WATCH_FIRST_EPISODE));
        } else if (mSeasonList.size() > 1) {
            actionLabel = new StringBuilder();
            actionLabel.append(getString(R.string.start_watching_episode));
            actionLabel.append("\n");
            actionLabel.append(" " + seasonText);
            CardData seasonData = mSeasonList.get(mSeasonList.size() - 1);
            String seasonNo = seasonData.getSeasonNo();
            if (!TextUtils.isEmpty(seasonNo)) {
                actionLabel.append(seasonNo + "- ");
            }
            actionLabel.append(episodeText + " 01");
            contentActionList.add(getContentAction(actionLabel.toString(), Action.ACTION_ID_WATCH_LATEST_EPISODE));

           */
/* actionLabel = new StringBuilder();
            actionLabel.append(getString(R.string.start_watching_episode));
            actionLabel.append("\n");
            actionLabel.append(" " + seasonText);
            seasonData = mSeasonList.get(0);
            seasonNo = seasonData.getSeasonNo();
            if (!TextUtils.isEmpty(seasonNo)) {
                actionLabel.append(seasonNo + "- ");
            }
            actionLabel.append(episodeText + " 01");
            contentActionList.add(getContentAction(actionLabel.toString(), Action.ACTION_ID_WATCH_FIRST_EPISODE));*//*

        }
        //setUpActionsPresentor(contentActionList, rowsAdapter);
    }

    private void updateDescription(CardData cardData) {
        onBindViewHolder(cardData);
        bindImageDrawable(cardData);
        manoramamaxFullLogo.setVisibility(VISIBLE);
    }

    private void initViews(View view) {
        mContentTitle = (TextView) view.findViewById(R.id.content_detail_title);
        mContentLanguage = (TextView) view.findViewById(R.id.content_language);
        mContentReleaseYear = (TextView) view.findViewById(R.id.content_releaseyear);
        mContentDuration = (TextView) view.findViewById(R.id.content_duration);
        hdImage = (ImageView) view.findViewById(R.id.hd_image);
        ccImage = (ImageView) view.findViewById(R.id.cc_image);
        mContentGenre = (TextView) view.findViewById(R.id.content_genre);
        mContentDescription = (TextView) view.findViewById(R.id.content_detail_description);
        mContentCastContainer = view.findViewById(R.id.cast_layout);
        mTextViewCastHeading = (TextView) view.findViewById(R.id.carddetailbriefdescription_cast_heading);
        mTextViewDirectorHeading = (TextView) view.findViewById(R.id.carddetailbriefdescription_director_heading);
        mContentCast = (TextView) view.findViewById(R.id.carddetailbriefdescription_cast);
        mDirectorName = (TextView) view.findViewById(R.id.directorName);
        mReadMore = (TextView) view.findViewById(R.id.content_read_more);
        mDot1 = findViewById(R.id.view1);
        mDot2 = findViewById(R.id.view2);
        mDot3 = findViewById(R.id.view3);
        contentType = view.findViewById(R.id.content_type);

    }

    private void setupDetailsOverviewRow(CardData cardData) {

        Log.d(TAG, "doInBackground");
//        row.setActionsAdapter(new ArrayObjectAdapter(new TenFootActionPresenterSelector()));
//        String imagesLink = mSelectedContent.getImageLink(APIConstants.IMAGE_TYPE_COVERPOSTER);
        int width = UiUtil.convertPixelToDp(getApplicationContext(),
                DETAIL_THUMB_WIDTH_COVERPOSTER);
        int height = UiUtil.convertPixelToDp(getApplicationContext(),
                DETAIL_THUMB_HEIGHT_COVERPOSTER);
        */
/*if (imagesItem == null || TextUtils.isEmpty(imagesItem.link)) return;
        if (imagesItem.type.equalsIgnoreCase(APIConstants.IMAGE_TYPE_PORTRAIT_COVERPOSTER)) {
            width = UiUtil.convertPixelToDp(getApplicationContext(),
                    DETAIL_THUMB_WIDTH_PORTRAIT_COVERPOSTER);
            height = UiUtil.convertPixelToDp(getApplicationContext(),
                    DETAIL_THUMB_HEIGHT_PORTRAIT_COVERPOSTER);
        }*//*


        SDKLogger.debugLog("imageLink- details   " + cardData.getImageLink(APIConstants.IMAGE_TYPE_COVERPOSTER));
        if (cardData == null) {
            return;
        }
        Glide.with(this)
                .asBitmap()
                .load(cardData.getImageLink(APIConstants.IMAGE_TYPE_COVERPOSTER,true))
                .placeholder(R.drawable.portrait_placeholder)
                .error(R.drawable.portrait_placeholder)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "content_details_activity_layout overview card image url " +
                                "ready: " + resource);
                        setImageBitmap(ContentDetailsActivityNeww.this, resource);
                    }
                });
    }

    */
/**
     * Sets a Bitmap as the image of this details overview.  Must be called on UI thread
     * after row is bound to view.
     *
     * @param context The context to retrieve display metrics from.
     * @param bm      The bitmap to set.
     *//*

    public final void setImageBitmap(Context context, Bitmap bm) {
        mImageDrawable = new BitmapDrawable(context.getResources(), bm);

        relativeLayoutRoot.setBackground(mImageDrawable);
    }

    void bindImageDrawable(CardData mSelectedContent) {

        if (relativeLayoutRoot != null) {
            relativeLayoutRoot.setBackground(getImageDrawable());
        }
        setupDetailsOverviewRow(mSelectedContent);
    }

    private Drawable getImageDrawable() {
        return mImageDrawable;
    }

    public void onBindViewHolder(final CardData mSelectedContent) {
        if (mContentLanguage != null
                && mContentGenre != null
                && mContentDuration != null
                && mReadMore != null) {
            mContentLanguage.setVisibility(GONE);
            mContentGenre.setVisibility(GONE);
            mContentDuration.setVisibility(GONE);
            mReadMore.setVisibility(GONE);
            mDot1.setVisibility(GONE);
            mDot2.setVisibility(GONE);
            mDot3.setVisibility(GONE);
            hdImage.setVisibility(GONE);
            ccImage.setVisibility(GONE);
        } else {
            return;
        }
        if (mSelectedContent != null) {
            if (mSelectedContent.getTitle() != null && !TextUtils.isEmpty(mSelectedContent.getTitle())) {
                mContentTitle.setText(mSelectedContent.getTitle());
                manoramamaxFullLogo.setVisibility(VISIBLE);
            }


            if (mSelectedContent != null && mSelectedContent.content != null && !TextUtils.isEmpty(mSelectedContent.content.categoryType)) {
                contentType.setText(mSelectedContent.content.categoryType);
                contentType.setVisibility(VISIBLE);
            }else {contentType.setVisibility(GONE);}

            mReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ContentDetailsActivityNeww.this);
                    if (!TextUtils.isEmpty(mSelectedContent.getDescription())) {
                        mReadMore.setVisibility(VISIBLE);
                    }
                    dialog.setMessage(mSelectedContent.getDescription());
                    dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }

                    });
                    final AlertDialog alert = dialog.create();
                    alert.show();
                    TextView messageView = (TextView) alert.findViewById(android.R.id.message);
                    messageView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    Button button = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
                    margins.setMargins(0, 4, 0, 8);
                    button.setBackground(getResources().getDrawable(R.color.red_highlight_color));
                    button.setTextColor(getResources().getColor(R.color.white));
                    Field f = null;
                    try {
                        f = AlertDialog.class.getDeclaredField("mAlert");
                        f.setAccessible(true);//Very important, this allows the setting to work.
                        Object alertCotroller = f.get(alert);
                        f = alertCotroller.getClass().getDeclaredField("mScrollView");
                        f.setAccessible(true);
                        ScrollView mScrollView = (ScrollView) f.get(alertCotroller);
                        mScrollView.getLayoutParams().height = 480;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mContentDescription.setVisibility(GONE);
            if (!TextUtils.isEmpty(mSelectedContent.getDescription())) {
                mContentDescription.setText(mSelectedContent.getDescription());
                mContentDescription.setVisibility(VISIBLE);
                */
/*mContentDescription.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mReadMore == null) {
                            return;
                        }
                        mReadMore.setVisibility(View.GONE);
                        Layout l = mContentDescription.getLayout();
                        if (l != null) {
                            int lines = l.getLineCount();
                            if (lines > 0) {
                                if (l.getEllipsisCount(lines - 1) > 0) {
                                    mReadMore.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }
                });*//*

            }
            if(mSelectedContent!=null &&mSelectedContent.generalInfo!=null && mSelectedContent.generalInfo.type!=null
                    &&mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_NEWS)) {
                mContentTitle.setText("");
                mContentTitle.setVisibility(GONE);
//                mContentDescription.setText("");
//                mContentDescription.setVisibility(GONE);
            }else {
                mContentTitle.setVisibility(VISIBLE);
//                mContentDescription.setVisibility(VISIBLE);
            }
            if (mSelectedContent.isTVSeries()) {
                if (!TextUtils.isEmpty(mSelectedContent.getReleaseYear())) {
                    mContentReleaseYear.setVisibility(VISIBLE);
                    mContentReleaseYear.setText(mSelectedContent.getReleaseYear());
                }
                if (!TextUtils.isEmpty(mSelectedContent.getGenre())) {
                    mContentLanguage.setVisibility(VISIBLE);
                    mContentLanguage.setText(mSelectedContent.getGenre());
                }
                if (!TextUtils.isEmpty(mSelectedContent.getGenre()) && !TextUtils.isEmpty(mSelectedContent.getReleaseYear())) {
                    mDot1.setVisibility(VISIBLE);
                }
            } else {
                if (!TextUtils.isEmpty(mSelectedContent.getLanguage())) {
                    mContentLanguage.setVisibility(VISIBLE);
                    mContentLanguage.setText(mSelectedContent.getLanguage());
                }
                if (!TextUtils.isEmpty(mSelectedContent.getReleaseYear())) {
                    mContentReleaseYear.setVisibility(VISIBLE);
                    mContentReleaseYear.setText(mSelectedContent.getReleaseYear());
                    mDot1.setVisibility(VISIBLE);
                }
                if (!TextUtils.isEmpty(mSelectedContent.getGenre())) {
                    mContentGenre.setVisibility(VISIBLE);
                    mContentGenre.setText(mSelectedContent.getGenre());
                    mDot2.setVisibility(VISIBLE);
                }
               */
/* if(!TextUtils.isEmpty(mSelectedContent.content.contentRating)){
                    contentRating.setVisibility(VISIBLE);
                    contentRating.setText(mSelectedContent.content.contentRating+"+");
                }*//*


                if (!mSelectedContent.generalInfo.type.equalsIgnoreCase(APIConstants.TYPE_VODCHANNEL)
                        && !mSelectedContent.isVideoAlbum()
                        && !TextUtils.isEmpty(mSelectedContent.convertTimeToMinutes())) {
                    mContentDuration.setVisibility(VISIBLE);
                    mContentDuration.setText(mSelectedContent.convertTimeToMinutes());
                    mDot3.setVisibility(VISIBLE);
                }

                if (mSelectedContent.content != null && mSelectedContent.content.videoQuality != null && mSelectedContent.content.videoQuality.equalsIgnoreCase("HD")) {
                    hdImage.setVisibility(VISIBLE);
                }
                if (mSelectedContent.subtitles != null && mSelectedContent.subtitles.values != null
                        && mSelectedContent.subtitles.values.size() > 0) {
                    ccImage.setVisibility(VISIBLE);
                }

            }
            String castMembers = mSelectedContent.getCastMembers();
            if (TextUtils.isEmpty(castMembers) || mSelectedContent.isTVEpisode()) {
                mContentCastContainer.setVisibility(GONE);
            } else {
                mContentCastContainer.setVisibility(VISIBLE);
                mTextViewCastHeading.setVisibility(VISIBLE);
                mContentCast.setText(castMembers);
            }
            if (directorList.size() == 0)
                setDirectorname();
            mLastWatchedProgressBar.setVisibility(GONE);
            if (mSelectedContent.isVideo() || mSelectedContent.isVideo() || mSelectedContent.isTVEpisode()) {
                if (mSelectedContent.elapsedTime > 0) {
                    try {
                        int position = mSelectedContent.elapsedTime;
                        int duration = mSelectedContent.calculateElapsedTimeInSeconds();
                        int percent = 0;
                        if (duration > 0) {
                            // use long to avoid overflow
                            percent = (int) (100L * position / duration);
                        }
                        SDKLogger.debugLog("updatePlayerStatus duration percent- " + percent);
                        mLastWatchedProgressBar.setVisibility(VISIBLE);
                        mLastWatchedProgressBar.setProgress(mSelectedContent.elapsedTime);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setDirectorname() {
        if (mSelectedContent != null && mSelectedContent.relatedCast != null &&
                mSelectedContent.relatedCast.values != null) {
            for (int i = 0; i < mSelectedContent.relatedCast.values.size(); i++) {
                if (mSelectedContent.relatedCast.values.get(i).types.size() > 0) {
                    for (int j = 0; j < mSelectedContent.relatedCast.values.get(i).types.size(); j++) {
                        if (mSelectedContent.relatedCast.values.get(i).types.get(j).equalsIgnoreCase("Director")) {
                            directorList.add(mSelectedContent.relatedCast.values.get(i).name);
                        }
                    }
                } else {
                    if (mSelectedContent.relatedCast.values.get(i).types.get(0).equalsIgnoreCase("Director")) {
                        directorList.add(mSelectedContent.relatedCast.values.get(i).name);
                    }
                }
            }
            if (directorList.size() > 1) {
                for (String s : directorList) {
                    directors += s + ", ";
                }
                if (!TextUtils.isEmpty(directors)) {
                    mTextViewDirectorHeading.setVisibility(VISIBLE);
                    mDirectorName.setText(directors);
                } else {
                    mDirectorName.setVisibility(GONE);
                }
            } else {
                if (directorList.size() == 1) {
                    mTextViewDirectorHeading.setVisibility(VISIBLE);
                    mDirectorName.setText(directorList.get(0));
                } else {
                    mDirectorName.setVisibility(GONE);
                }
            }
        }
    }

    public static Intent createIntent(Context activity, CardData content, CarouselInfoData carouselInfoData, String source, String sourceDetails) {
        Intent intent = new Intent(activity, ContentDetailsActivityNeww.class);
        intent.putExtra(PARAM_CARD_DATA, content);
        intent.putExtra(AnalyticsConstants.PROPERTY_SOURCE, source);
        intent.putExtra(AnalyticsConstants.PROPERTY_SOURCE_DETAILS, sourceDetails);
        SDKLogger.debugLog("create intent from: " + activity.getClass().getSimpleName());
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        price_contet = null;
        paymentdailogue = false;
        purchase_content = false;
        PrefUtils.getInstance().setEpisode_show_display_id(null);
        finish();
    }

    public void launchPlayerActivity(CardData item, CarouselInfoData mCarouselInfoData, List<CardData> cardDataList, boolean playFromLastWatched) {
        */
/*if(sRelatedEpisodesCache.size() > 0) {
            playerPendingItent = VideoPlaybackActivity.createIntent(this, item, mCarouselInfoData, sRelatedEpisodesCache, playFromLastWatched, mSource, mSourceDetails);
        } else*//*

        if (item != null && item.generalInfo != null && item.generalInfo.type.equalsIgnoreCase("tvseries") && mEpisodesList != null) {
            item = mEpisodesList;
        }
        if(!PrefUtils.getInstance().isUSER_LOGGED_IN()){
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        playerPendingItent = VideoPlaybackActivity.createIntent(this, item, mCarouselInfoData, cardDataList, playFromLastWatched, mSource, mSourceDetails);
        alreadyLoggedIn = false;
        */
/*if (!PrefUtils.isUserLoggedIn()) {
            alreadyLoggedIn = true;
            launchLoginActivity();
            return;
        }*//*

        startActivityForResult(playerPendingItent, INTENT_REQUEST_TYPE_LOGIN);
    }


    private void launchLoginActivity() {
        //startActivityForResult(LoginActivity.createIntent(this, true, mSource, mSourceDetails), INTENT_REQUEST_TYPE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SDKLogger.debugLog("requestCode- " + requestCode + " resultCode- " + resultCode);
        Log.e("ANRCheck", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_TYPE_PLAYER) {
            if (resultCode == MainActivity.INTENT_RESPONSE_TYPE_SUCCESS) {
                // showDetailsFragment();
                if (playerPendingItent != null && alreadyLoggedIn) {
                    setResult(MainActivity.INTENT_RESPONSE_TYPE_SUCCESS);
                    startActivityForResult(playerPendingItent, INTENT_REQUEST_TYPE_LOGIN);
                }
            } else if (resultCode == MainActivity.INTENT_RESPONSE_TYPE_FAILED_REFRESH_CONTENT) {
                setResult(MainActivity.INTENT_RESPONSE_TYPE_FAILED_REFRESH_CONTENT);
            } else if (resultCode == ELAPSED_TIME_TAG_OK) {
                if (data != null && data.hasExtra(ELAPSED_TIME_TAG)) {
                    mSelectedContent.elapsedTime = (int) data.getIntExtra(ELAPSED_TIME_TAG, mSelectedContent.elapsedTime);
                }
                initUI();
                setResult(MainActivity.INTENT_RESPONSE_TYPE_FAILED_REFRESH_CONTENT);
            }
        }
    }

    private boolean onHandleExternalIntent(final Intent intent) {
        if (intent == null || getIntent().getExtras() == null) {
            Log.d(TAG, "intent is null");
            return false;
        }
        for (String key : getIntent().getExtras().keySet()) {
            SDKLogger.debugLog("CleverTap: MainActivity: key- " + key + " value- " + getIntent().getExtras().get(key));
        }
        SDKLogger.debugLog("CleverTap: MainActivity: getExtras- " + getIntent().getExtras());
        if (intent.hasExtra(APIConstants
                .NOTIFICATION_PARAM_NOTIFICATION_ID)) {
            try {
                String snotificationId = intent.getStringExtra(APIConstants
                        .NOTIFICATION_PARAM_NOTIFICATION_ID);
                Log.d(TAG, "snotificationId " + snotificationId);
                if (snotificationId != null) {
                    long notificationId = Long.parseLong(snotificationId);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel((int) notificationId);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        boolean intentHandled = false;

        String notificationTitle = null;
        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_TITLE)) {
            //launch card details
            Log.d(TAG, "notification title " + intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_TITLE));
            notificationTitle = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_TITLE);
            // Analytics.gaNotificationEvent(Analytics.EVENT_ACTION_OPENS_AUTO_REMINDER, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_TITLE));
        } else if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_MESSAGE)) {
            Log.d(TAG, "notification message " + intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_MESSAGE));
            notificationTitle = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_MESSAGE);
            // Analytics.mixpanelNotificationOpened(intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_MESSAGE), intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
            //  Analytics.gaNotificationEvent(Analytics.EVENT_ACTION_OPENS, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_MESSAGE));
            //AppsFlyerTracker.eventNotificationOpened(new HashMap<String, Object>());
        }
        Log.d(TAG, "notification notificationTitle- " + notificationTitle);

        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_PARTNER_ID) && intent.hasExtra(APIConstants.NOTIFICATION_PARAM_PARTNER_NAME)) {
            // handleIfPartnerContent(intent, null);
            intentHandled = true;
        }
        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_CONTENT_ID)) {
            final String _id = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_CONTENT_ID);
            final String _aid = intent.getExtras().getString(APIConstants.NOTIFICATION_PARAM_AID);
            final String videoUrl = intent.getExtras().getString(APIConstants.NOTIFICATION_PARAM_VIDEO_URL);
            final String yuid = intent.getExtras().getString(APIConstants.NOTIFICATION_PARAM_YUID);
            CacheManager cacheManager = new CacheManager();
            Log.d(TAG, "notification _id " + _id);
            if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_CONTENT_TYPE)) {
                String contentType = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_CONTENT_TYPE);
                Log.d(TAG, "notification contentType " + contentType);
                if (contentType.equalsIgnoreCase(APIConstants.TYPE_PROGRAM)) {
                    cacheManager.setNotifiationTitle(intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_TITLE));
                    cacheManager.setNotifiationNid(intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
                    final String finalNotificationTitle = notificationTitle;
                    cacheManager.getProgramDetail(_id, true, new CacheManager.CacheManagerCallback() {
                        @Override
                        public void OnCacheResults(List<CardData> dataList) {
                            Log.d(TAG, "OnCacheResults() ");
                            if (dataList == null
                                    || dataList.isEmpty()) {
                                return;
                            }
                            final CardData programData = dataList.get(0);
                            if (null == programData) {
                                return;
                            }
                            if (null == programData.globalServiceId) {
                                return;
                            }

                            CacheManager.setSelectedCardData(programData);
                            final Bundle args = new Bundle();
                            args.putString(ContentDetailsActivityNeww.PARAM_CARD_ID, programData.globalServiceId);
                            args.putString(ContentDetailsActivityNeww.PARAM_CARD_DATA_TYPE, APIConstants.TYPE_PROGRAM);

                            if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_NID)) {
                                args.putString(APIConstants.NOTIFICATION_PARAM_NID, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
                            }
                            if (null != programData.startDate
                                    && null != programData.endDate) {
                                Date startDate = Utils.getDate(programData.startDate);
                                Date endDate = Utils.getDate(programData.endDate);
                                Date currentDate = new Date();
                                if ((currentDate.after(startDate)
                                        && currentDate.before(endDate))
                                        || currentDate.after(endDate)) {
                                    args.putBoolean(ContentDetailsActivityNeww
                                            .PARAM_AUTO_PLAY, true);
                                }
                            }

                            args.putString(APIConstants.NOTIFICATION_PARAM_TITLE, finalNotificationTitle);
                            if (!TextUtils.isEmpty(finalNotificationTitle)) {
                                //launch card details
                                //Analytics.gaNotificationEvent(Analytics.EVENT_ACTION_OPENS_AUTO_REMINDER, finalNotificationTitle);
                            }

                            if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_ACTION)) {
                                //launch card details
                                String action = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_ACTION);
                                Log.d(TAG, "action:- " + action);
                                if (action != null
                                        && action.equals(APIConstants.NOTIFICATION_PARAM_AUTOPLAY)) {
                                    args.putBoolean(ContentDetailsActivityNeww
                                            .PARAM_AUTO_PLAY, true);
                                }
                            }
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE, AnalyticsConstants.VALUE_SOURCE_NOTIFICATION);
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE_DETAILS, "reminder");


                           */
/* if (mDraggablePanel != null) {
                                mDraggablePanel.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDetailsFragment(args, programData);
                                    }
                                });
                            }*//*

                            mSelectedContent = programData;
                            initUI();
                        }

                        @Override
                        public void OnOnlineResults(List<CardData> dataList) {
                            isDeeplinkingURL = true;
                            Log.d(TAG, "OnOnlineResults() ");
                            if (dataList == null
                                    || dataList.isEmpty()) {
                                Log.d(TAG, "OnOnlineResults dataList- " + dataList);
                                return;
                            }
                            final CardData programData = dataList.get(0);
                            if (programData == null
                                    || programData.globalServiceId == null) {
                                Log.d(TAG, "OnOnlineResults dataList- " + dataList);
                                return;
                            }

                            CacheManager.setSelectedCardData(programData);
                            final Bundle args = new Bundle();
                            args.putString(ContentDetailsActivityNeww.PARAM_CARD_ID, programData.globalServiceId);
                            args.putString(ContentDetailsActivityNeww.PARAM_CARD_DATA_TYPE, APIConstants.TYPE_PROGRAM);
                            if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_NID)) {
                                args.putString(APIConstants.NOTIFICATION_PARAM_NID, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
                            }
                            if (null != programData.startDate
                                    && null != programData.endDate) {
                                Date startDate = Utils.getDate(programData.startDate);
                                Date endDate = Utils.getDate(programData.endDate);
                                Date currentDate = new Date();
                                if ((currentDate.after(startDate)
                                        && currentDate.before(endDate))
                                        || currentDate.after(endDate)) {
                                    args.putBoolean(ContentDetailsActivityNeww
                                            .PARAM_AUTO_PLAY, true);
                                }
                            }
                            args.putString(APIConstants.NOTIFICATION_PARAM_TITLE, finalNotificationTitle);
                            if (!TextUtils.isEmpty(finalNotificationTitle)) {
                                //launch card details
                                // Analytics.gaNotificationEvent(Analytics.EVENT_ACTION_OPENS_AUTO_REMINDER, finalNotificationTitle);
                            }
                            if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_ACTION)) {
                                //launch card details
                                String action = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_ACTION);
                                Log.d(TAG, "action:- " + action);
                                if (action != null
                                        && action.equals(APIConstants.NOTIFICATION_PARAM_AUTOPLAY)) {
                                    args.putBoolean(ContentDetailsActivityNeww
                                            .PARAM_AUTO_PLAY, true);
                                }

                            }
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE, AnalyticsConstants.VALUE_SOURCE_NOTIFICATION);
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE_DETAILS, "reminder");

                          */
/*  if (mDraggablePanel != null) {
                                mDraggablePanel.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDetailsFragment(args, programData);
                                    }
                                });
                            }
*//*

                            mSelectedContent = programData;
                            initUI();
                        }

                        @Override
                        public void OnOnlineError(Throwable error, int errorCode) {
                            Log.d(TAG, "onOnlineError " + error);
                            if (error != null) {
                                String errorMessage = error.getMessage();
                                Log.e(TAG, "showErrorMessage: errorMessage: " + errorMessage);
                                if (errorMessage != null && errorMessage.contains(APIConstants.MESSAGE_ERROR_CONN_RESET) && !isRetryAlreadyDone) {
                                    //Retry for data connection
                                    Log.e(TAG, "showErrorMessage: retrying again for reconnection");
                                    isRetryAlreadyDone = true;
                                    onHandleExternalIntent(intent);
                                }
                                if (Fabric.isInitialized()) {
                                    Crashlytics.logException(error);
                                }
                            }

                        }
                    });
                    intentHandled = true;
                }
            } else {
                cacheManager.setNotifiationTitle(intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_MESSAGE));
                cacheManager.setNotifiationNid(intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
                final String finalNotificationTitle1 = notificationTitle;
                cacheManager.getCardDetails(_id, false, new CacheManager.CacheManagerCallback() {
                    @Override
                    public void OnCacheResults(List<CardData> dataList) {
                        Log.d(TAG, "OnCacheResults ");
                        if (dataList == null
                                || dataList.isEmpty()) {
                            return;
                        }
                        final CardData cardData = dataList.get(0);
                        if (null == cardData) {
                            return;
                        }
                       */
/* if (handleIfPartnerContent(intent, cardData)) {
                            return;
                        }*//*

                        if (!TextUtils.isEmpty(_aid)) {
                            Log.d(TAG, "_aid- " + _aid);
                            cardData._aid = _aid;
                        }
                        if (!TextUtils.isEmpty(videoUrl)) {
                            Log.d(TAG, "videoUrl- " + videoUrl);
                            setVideoUrlCardData(cardData, videoUrl, APIConstants.TYPE_NEWS);
                        }
                        if (!TextUtils.isEmpty(yuid)) {
                            Log.d(TAG, "yuid- " + yuid);
                            setVideoUrlCardData(cardData, yuid, APIConstants.TYPE_YOUTUBE);
                        }

                        CacheManager.setSelectedCardData(cardData);
//                        TODO show RelatedVOdListFragment
                        final Bundle args = new Bundle();
                        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_NID)) {
                            args.putString(APIConstants.NOTIFICATION_PARAM_NID, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
                        }
                        if (null != cardData.startDate
                                && null != cardData.endDate) {
                            Date startDate = Utils.getDate(cardData.startDate);
                            Date endDate = Utils.getDate(cardData.endDate);
                            Date currentDate = new Date();
                            if ((currentDate.after(startDate)
                                    && currentDate.before(endDate))
                                    || currentDate.after(endDate)) {
                                args.putBoolean(ContentDetailsActivityNeww
                                        .PARAM_AUTO_PLAY, true);
                            }
                        }


                        if (!TextUtils.isEmpty(finalNotificationTitle1)) {
                            //launch cardData details
                            args.putString(APIConstants.NOTIFICATION_PARAM_TITLE, finalNotificationTitle1);
                        }
                        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_ACTION)) {
                            //launch cardData details
                            String action = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_ACTION);
                            Log.d(TAG, "action:- " + action);
                            if (action != null
                                    && action.equals(APIConstants.NOTIFICATION_PARAM_AUTOPLAY)) {
                                args.putBoolean(ContentDetailsActivityNeww
                                        .PARAM_AUTO_PLAY, true);
                            }
                        }
                        if (cardData != null
                                && cardData.generalInfo != null) {
                            String contentId = cardData._id;
                            if (APIConstants.TYPE_PROGRAM.equalsIgnoreCase(cardData.generalInfo.type)
                                    && cardData.globalServiceId != null) {
                                contentId = cardData.globalServiceId;
                            }
                            args.putString(ContentDetailsActivityNeww.PARAM_CARD_ID, contentId);
                           */
/* if (APIConstants.TYPE_VODCHANNEL.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_VODCATEGORY.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_VODYOUTUBECHANNEL.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_TVSEASON.equalsIgnoreCase(cardData.generalInfo.type)) {*//*

                            //Launching ActivityRelatedVODList for vodcategory,vodchannel content type's
                              */
/*  args.putSerializable(FragmentRelatedVODList.PARAM_SELECTED_VOD_DATA, cardData);
                                pushFragment(FragmentRelatedVODList.newInstance(args));*//*

                            if (APIConstants.TYPE_VOD.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_TVEPISODE.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_LIVE.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_PROGRAM.equalsIgnoreCase(cardData.generalInfo.type)) {
                                launchPlayerActivity(cardData, null, null);
                                return;
                            }

                            if (intent.hasExtra(APIConstants.MESSAGE_TYPE)) {
                                if (APIConstants.NOTIFICATION_PARAM_MESSAGE_TYPE_INAPP.equalsIgnoreCase(intent.getStringExtra(APIConstants.MESSAGE_TYPE))) {
                                    args.putBoolean(ContentDetailsActivityNeww
                                            .PARAM_AUTO_PLAY, true);
                                }
                            }

                            // args.putInt(ContentDetailsActivityNeww.PARAM_PARTNER_TYPE, Util.getPartnerTypeContent(cardData));
                            String partnerId = cardData == null || cardData.generalInfo == null || cardData.generalInfo.partnerId == null ? null : cardData.generalInfo.partnerId;
                            args.putString(ContentDetailsActivityNeww.PARAM_PARTNER_ID, partnerId);
                            String adProvider = null;
                            boolean adEnabled = false;
                            if (cardData != null
                                    && cardData.content != null) {
                                if (!TextUtils.isEmpty(cardData.content.adProvider)) {
                                    adProvider = cardData.content.adProvider;
                                }
                                adEnabled = cardData.content.adEnabled;
                            }
                            args.putString(ContentDetailsActivityNeww.PARAM_AD_PROVIDER, adProvider);
                            args.putBoolean(ContentDetailsActivityNeww.PARAM_AD_ENBLED, adEnabled);
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE, AnalyticsConstants.VALUE_SOURCE_NOTIFICATION);
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE_DETAILS, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));

                          */
/*  if (mDraggablePanel != null) {
                                mDraggablePanel.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDetailsFragment(args, cardData);
                                    }
                                });
                            }*//*

                            mSelectedContent = cardData;
                            initUI();

                        }
                    }

                    @Override
                    public void OnOnlineResults(List<CardData> dataList) {
                        isDeeplinkingURL = true;
                        Log.d(TAG, "OnOnlineResults ");
                        if (dataList == null
                                || dataList.isEmpty()) {
                            return;
                        }
                        final CardData cardData = dataList.get(0);
                        if (null == cardData) {
                            return;
                        }
                      */
/*  if (handleIfPartnerContent(intent, cardData)) {
                            return;
                        }*//*

                        if (!TextUtils.isEmpty(_aid)) {
                            Log.d(TAG, "_aid- " + _aid);
                            cardData._aid = _aid;
                        }
                        if (!TextUtils.isEmpty(videoUrl)) {
                            Log.d(TAG, "videoUrl- " + videoUrl);
                            setVideoUrlCardData(cardData, videoUrl, APIConstants.TYPE_NEWS);
                        }
                        if (!TextUtils.isEmpty(yuid)) {
                            Log.d(TAG, "yuid- " + yuid);
                            setVideoUrlCardData(cardData, yuid, APIConstants.TYPE_YOUTUBE);
                        }

                        CacheManager.setSelectedCardData(cardData);
//                        TODO show RelatedVOdListFragment
                        final Bundle args = new Bundle();
                        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_NID)) {
                            args.putString(APIConstants.NOTIFICATION_PARAM_NID, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
                        }
                        if (null != cardData.startDate
                                && null != cardData.endDate) {
                            Date startDate = Utils.getDate(cardData.startDate);
                            Date endDate = Utils.getDate(cardData.endDate);
                            Date currentDate = new Date();
                            if ((currentDate.after(startDate)
                                    && currentDate.before(endDate))
                                    || currentDate.after(endDate)) {
                                args.putBoolean(ContentDetailsActivityNeww
                                        .PARAM_AUTO_PLAY, true);
                            }
                        }

                        if (intent.hasExtra(APIConstants.MESSAGE_TYPE)) {
                            if (APIConstants.NOTIFICATION_PARAM_MESSAGE_TYPE_INAPP.equalsIgnoreCase(intent.getStringExtra(APIConstants.MESSAGE_TYPE))) {
                                args.putBoolean(ContentDetailsActivityNeww
                                        .PARAM_AUTO_PLAY, true);
                            }
                        }

                        if (!TextUtils.isEmpty(finalNotificationTitle1)) {
                            //launch cardData details
                            args.putString(APIConstants.NOTIFICATION_PARAM_TITLE, finalNotificationTitle1);
                        }
                        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_ACTION)) {
                            //launch cardData details
                            String action = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_ACTION);
                            Log.d(TAG, "action:- " + action);
                            if (action != null
                                    && action.equals(APIConstants.NOTIFICATION_PARAM_AUTOPLAY)) {
                                args.putBoolean(ContentDetailsActivityNeww
                                        .PARAM_AUTO_PLAY, true);
                            }
                        }

                        if (cardData != null
                                && cardData.generalInfo != null) {
                            String contentId = cardData._id;
                            if (APIConstants.TYPE_PROGRAM.equalsIgnoreCase(cardData.generalInfo.type)
                                    && cardData.globalServiceId != null) {
                                contentId = cardData.globalServiceId;
                            }
                            args.putString(ContentDetailsActivityNeww.PARAM_CARD_ID, contentId);
                            */
/*if (APIConstants.TYPE_VODCHANNEL.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_VODCATEGORY.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_VODYOUTUBECHANNEL.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_TVSEASON.equalsIgnoreCase(cardData.generalInfo.type)) {
                                //Launching ActivityRelatedVODList for vodcategory,vodchannel content type's
                           *//*
*/
/*     args.putSerializable(FragmentRelatedVODList.PARAM_SELECTED_VOD_DATA, cardData);
                                pushFragment(FragmentRelatedVODList.newInstance(args));*//*

                            if (APIConstants.TYPE_VOD.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_TVEPISODE.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_LIVE.equalsIgnoreCase(cardData.generalInfo.type)
                                    || APIConstants.TYPE_PROGRAM.equalsIgnoreCase(cardData.generalInfo.type)) {
                                launchPlayerActivity(cardData, null, null);
                                return;
                            }
                            //  args.putInt(ContentDetailsActivityNeww.PARAM_PARTNER_TYPE, Utils.getPartnerTypeContent(cardData));
                            String partnerId = cardData == null || cardData.generalInfo == null || cardData.generalInfo.partnerId == null ? null : cardData.generalInfo.partnerId;
                            args.putString(ContentDetailsActivityNeww.PARAM_PARTNER_ID, partnerId);
                            String adProvider = null;
                            boolean adEnabled = false;
                            if (cardData != null
                                    && cardData.content != null) {
                                if (!TextUtils.isEmpty(cardData.content.adProvider)) {
                                    adProvider = cardData.content.adProvider;
                                }
                                adEnabled = cardData.content.adEnabled;
                            }
                            args.putString(ContentDetailsActivityNeww.PARAM_AD_PROVIDER, adProvider);
                            args.putBoolean(ContentDetailsActivityNeww.PARAM_AD_ENBLED, adEnabled);
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE, AnalyticsConstants.VALUE_SOURCE_NOTIFICATION);
                            args.putString(AnalyticsConstants.PROPERTY_SOURCE_DETAILS, intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_NID));
*/
/*
                            if (mDraggablePanel != null) {
                                mDraggablePanel.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDetailsFragment(args, cardData);
                                    }
                                });
                            }*//*

                            mSelectedContent = cardData;
                            initUI();
                        }
                    }

                    @Override
                    public void OnOnlineError(Throwable error, int errorCode) {
                        Log.d(TAG, "onOnlineError " + error);
                        if (error != null) {
                            String errorMessage = error.getMessage();
                            if (errorMessage != null && errorMessage.contains(APIConstants.MESSAGE_ERROR_CONN_RESET) && !isRetryAlreadyDone) {
                                //Retry for data connection
                                isRetryAlreadyDone = true;
                                onHandleExternalIntent(intent);
                            }
                            Log.d(TAG, " Fabric.isInitialized()- " + Fabric.isInitialized());
                            if (Fabric.isInitialized()) {
                                Crashlytics.logException(error);
                            }
                        }

                    }
                });
                intentHandled = true;

            }
            return intentHandled;
        } else if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_CHANNEL)) {
            System.out.println("phani channel ");
            //  Intent programIntent = new Intent(this, ProgramGuideChannelActivity.class);
            String id = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_CONTENT_ID);
            //programIntent.putExtra(APIConstants.NOTIFICATION_PARAM_CONTENT_ID, id);
            // programIntent.putExtra(ProgramGuideChannelActivity.DATE_POS, PrefUtils.getInstance().getPrefEnablePastEpg() ? PrefUtils.getInstance().getPrefNoOfPastEpgDays() : ApplicationController.DATE_POSITION);
            // programIntent.putExtra(ProgramGuideChannelActivity.PARAM_FROM, false);
            //  Log.d(TAG, "channelId, _id:- " + id + "datePos- " + ApplicationController.DATE_POSITION);
            //  startActivity(programIntent);
            intentHandled = true;

        }
        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_VURL)) {
            String vurl = intent.getExtras().getString(APIConstants.NOTIFICATION_PARAM_VURL);
            Intent videoLaunchIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(vurl));
            videoLaunchIntent.setDataAndType(Uri.parse(vurl), "video/mp4");
            final PackageManager pm = getApplicationContext().getPackageManager();
            int i = 0;
            for (ResolveInfo ri : pm.queryIntentActivities(videoLaunchIntent,
                    PackageManager.MATCH_DEFAULT_ONLY)) {
                if ((i == 0 || i == 1) && ri.activityInfo.enabled) {
                    videoLaunchIntent.setClassName(ri.activityInfo.packageName,
                            ri.activityInfo.name);
                }
                i++;
            }
            Log.d(TAG, "vurl- " + vurl);
            startActivity(videoLaunchIntent);
            intentHandled = true;
            return intentHandled;
        }
        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_YUID)) {
            String yuid = intent.getExtras().getString(APIConstants.NOTIFICATION_PARAM_YUID);
            Log.d(TAG, "yuid- " + yuid);
            //Util.launchYouyubePlayer((Activity) mContext, yuid);
            intentHandled = true;
        }
        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_PAGE)) {
            String page = intent.getExtras().getString(APIConstants.NOTIFICATION_PARAM_PAGE);
            Log.d(TAG, "page- " + page);
            intentHandled = true;
          */
/*  if (mTabPageIndicator == null || homePagerAdapterDynamicMenu == null) {
                return intentHandled;
            }
            redirectToPage(page);*//*

        }
        if (intent.hasExtra(APIConstants.NOTIFICATION_PARAM_URL)) {
            String url = intent.getStringExtra(APIConstants.NOTIFICATION_PARAM_URL);
            Log.d(TAG, "NOTIFICATION_PARAM_URL, url:- " + url);
            //SDKUtils.launchBrowserIntent(this, url);
            intentHandled = true;

        }
        return intentHandled;

    }

    private void setVideoUrlCardData(CardData cardData, String videoUrl, String type) {
        CardDataVideos videos = new CardDataVideos();
        CardDataVideosItem videosItem = new CardDataVideosItem();
        if (null != type) {
            if (type.equalsIgnoreCase(APIConstants.TYPE_YOUTUBE)) {
                videosItem.type = APIConstants.TYPE_YOUTUBE;
                if (null != cardData.generalInfo) {
                    cardData.generalInfo.videoAvailable = true;
                }
            } else {
                videosItem.type = APIConstants.TYPE_NEWS;
            }
        }
        videosItem.link = videoUrl;
        videos.values = new ArrayList<>();
        videos.values.add(videosItem);
        cardData.videos = videos;

    }


    public void launchPlayerActivity(CardData item, String source, String sourceDetails) {
        playerPendingItent = VideoPlaybackActivity.createIntent(getApplicationContext(), item, null, null, true, source, sourceDetails);
        if (!PrefUtils.isUserLoggedIn()) {
            launchLoginActivity();
            return;
        }
        Utils.fromDetials = true;
        startActivityForResult(playerPendingItent, INTENT_REQUEST_TYPE_LOGIN);
    }

    private int elapsed_time = 0;

    private void loadElapsedTime() {
        showProgressBar();
        if(mEpisodesList == null)
            mEpisodesList = mSelectedContent;
        String  id = mEpisodesList._id;
        myplexAPI api = myplexAPI.getInstance();
        String clientKey = PrefUtils.getInstance().getPrefClientkey();
        myplexAPI.myplexAPIInterface myplexAPIService = api.myplexAPIService;
        Log.d("TAG","clientKey FOR checking"+clientKey);
        Call<CardVideoResponseContainer> call =
                myplexAPIService.fetchElapsedTime((clientKey == null) ? "" : clientKey, String.valueOf(id));
        call.enqueue(new Callback<CardVideoResponseContainer>() {


            @Override
            public void onResponse(Response<CardVideoResponseContainer> response, Retrofit retrofit) {
     */
/*           if (progressDialog != null) {
                    progressDialog.dismiss();
                }
     *//*

                if (response == null || response.body() == null) {
                    dismissProgressBar();
                    continueInitialization();
                    return;
                }
                CardVideoResponseContainer body = response.body();
                List<CardVideoResponseResult> results = body.results;

                // CardDataVideosItem  results = response.body().results;

                if (results != null && results.size() > 0) {
                    CardVideoResponseResult cardVideoResponseResult = results.get(0);
                    if(cardVideoResponseResult.videos !=null && cardVideoResponseResult.videos.values !=null && cardVideoResponseResult.videos.values.size()>0 && cardVideoResponseResult.videos.values.get(0)!=null)
                        elapsed_time = cardVideoResponseResult.videos.values.get(0).elapsedTime;
                    if (mSelectedContent != null && cardVideoResponseResult.relatedCast != null && cardVideoResponseResult.relatedCast.values != null
                            && cardVideoResponseResult.relatedCast.values.size() != 0) {
                        mSelectedContent.relatedCast = cardVideoResponseResult.relatedCast;
                    }
                    Log.e(TAG, "LoadElapsedTime :: elapsed_time :" + elapsed_time);

                    if (mSelectedContent != null) {
                        Log.e(TAG, "LoadElapsedTime :: MOVIE elapsed_time :" + elapsed_time);
                        mSelectedContent.elapsedTime = elapsed_time;

                    }
                }
                dismissProgressBar();
                continueInitialization();
                return;

            }

            @Override
            public void onFailure(Throwable t) {
                dismissProgressBar();
                continueInitialization();
                return;
            }
        });
    }

    public void addToWatchlist(CardData item) {
        favouriteAPICall(item);
    }

    private void favouriteAPICall(final CardData content) {
        showProgressBar();
        FavouriteContentSelection sendFavouriteContentSelection = new FavouriteContentSelection(content._id, new APICallback<BaseResponseData>() {
            @Override
            public void onResponse(APIResponse<BaseResponseData> response) {
                dismissProgressBar();
                if (response.isSuccess()) {
                    if (response.body().code == 200) {
                        mSelectedContent.isFavourite = false;
                        String isFavourite = "removed";
                        fireFavouriteEventToAnalytics(content, isFavourite);
                        if (mSelectedContent.isMusicVideo()) {
                            mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
                        } else if (mSelectedContent.isMovie() || mSelectedContent.isTVEpisode()) {
                            if (mSelectedContent.elapsedTime > 0 && checkRelatedMultiMediaAvailable())
                                mRowsAdapter.notifyItemRangeChanged(3, 1);
                            else if (mSelectedContent.elapsedTime > 0)
                                mRowsAdapter.notifyItemRangeChanged(2, 1);
                            else
                                mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
                        } else if (mSelectedContent.isTVShow() && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime > 0) {
                            mRowsAdapter.notifyItemRangeChanged(3, 1);
                        } else if (mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo()) {
                            if ((Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime == 0) {
                                mRowsAdapter.notifyItemRangeChanged(2, 1);
                            } else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime > 0) {
                                mRowsAdapter.notifyItemRangeChanged(2, 1);
                            } else
                                mRowsAdapter.notifyItemRangeChanged(1, 1);
                        }

                    } else if (response.body().code == 201) {
                        mSelectedContent.isFavourite = true;
                        String isFavourite = "added";
                        fireFavouriteEventToAnalytics(content, isFavourite);
                        if (mSelectedContent.isMusicVideo()) {
                            mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
                        } else if (mSelectedContent.isMovie() || mSelectedContent.isTVEpisode()) {
                            if (mSelectedContent.elapsedTime > 0 && checkRelatedMultiMediaAvailable())
                                mRowsAdapter.notifyItemRangeChanged(3, 1);
                            else if (mSelectedContent.elapsedTime > 0)
                                mRowsAdapter.notifyItemRangeChanged(2, 1);
                            else
                                mRowsAdapter.notifyArrayItemRangeChanged(1, 1);
                        } else if (mSelectedContent.isTVShow() && (Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime > 0) {
                            mRowsAdapter.notifyItemRangeChanged(3, 1);
                        } else if (mSelectedContent.isTVShow() || mSelectedContent.isVideoAlbum() || mSelectedContent.isMusicVideo()) {
                            if ((Utils.checkTypeEpisode(mSelectedContent) || Utils.checkTypeMonthly(mSelectedContent)) && mSelectedContent.elapsedTime == 0) {
                                mRowsAdapter.notifyItemRangeChanged(1, 1);
                            } else if (mSelectedContent.isTVShow() && mSelectedContent.elapsedTime > 0) {
                                mRowsAdapter.notifyItemRangeChanged(2, 1);
                            } else
                                mRowsAdapter.notifyItemRangeChanged(1, 1);
                        }
                    } else if (response.body().code == 401 || (response.body().code == 402)) {
//                        Toast.makeText(ContentDetailsActivityNeww.this, response.message(), Toast.LENGTH_SHORT).show();
                        AlertDialogUtil.showToastNotification(response.body().message);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorCode) {
                dismissProgressBar();
            }
        });
        APIService.getInstance().execute(sendFavouriteContentSelection);
    }

    public void fireFavouriteEventToAnalytics(CardData carddata, String isFavourite) {
//        LogUtils.debug(TAG, "fireFavouriteEventToAnalytics favorite :" + isFavourite);
        Map<String, String> param = new HashMap<>();

        if (carddata.generalInfo != null) {
            param.put(Analytics.PROPERTY_CONTENT_NAME, convertToLowerCase(carddata.generalInfo.title));
            param.put(Analytics.CONTENT_TYPE, carddata.generalInfo.type);

        } else {
            param.put(Analytics.PROPERTY_CONTENT_NAME, NULL_VALUE);
        }
        param.put(Analytics.PROPERTY_CONTENT_ID, carddata._id);
        param.put(Analytics.PROPERTY_CONTENT_LANGUAGE, NULL_VALUE);
        if (carddata.content != null && carddata.content.language != null) {
            StringBuilder language = new StringBuilder();
            for (String a : carddata.content.language) {
                if (language.length() == 0) {
                    language.append(a);
                } else {
                    language.append(",").append(a);
                }
            }
            if (language.length() == 0) {
                param.put(Analytics.PROPERTY_CONTENT_LANGUAGE, NULL_VALUE);
            } else {
                param.put(Analytics.PROPERTY_CONTENT_LANGUAGE, convertToLowerCase(String.valueOf(language)));
            }
        }

        String subgenre = NULL_VALUE;
        if (carddata != null && carddata.content != null
                && carddata.content.genre != null
                && carddata.content.genre.size() > 0) {
            subgenre = carddata.content.genre.get(0).name;
        }
        param.put(Analytics.PROPERTY_CONTENT_GENRE, Analytics.sortCommaSeparatedString(Analytics.convertToLowerCase(subgenre)));
        */
/*param.put(PROPERTIES_SOURCE, carddata.source);
        param.put(PROPERTIES_SOURCE_DETAILS, carddata.sourceDetails);*//*

        if (carddata.getSource() != null)
            param.put(PROPERTIES_SOURCE, carddata.getSource());
        else
            param.put(PROPERTIES_SOURCE, PrefUtils.getInstance().getSourceDetails());

        param.put(PROPERTIES_SOURCE_DETAILS, PrefUtils.getInstance().getCleverTapSourceDetails() != null ?
                convertToLowerCase(PrefUtils.getInstance().getCleverTapSourceDetails()) : carddata.getSourceDetails());
        if (carddata.globalServiceId != null) {
            param.put(Analytics.PROPERTY_SERIES_NAME, Utils.getSplitGlobalServiceId(carddata.globalServiceId));
        }
       */
/* else {
            param.put(Analytics.PROPERTY_SERIES_NAME, NULL_VALUE);
        }*//*

// if (carddata != null && carddata.generalInfo != null && carddata.generalInfo.contentRights != null && carddata.generalInfo.contentRights.size() > 0 && carddata.generalInfo.contentRights.get(0) != null && carddata.generalInfo.contentRights.get(0).equalsIgnoreCase(APIConstants.TYPE_FREE)) {
// param.put(Analytics.PROPERTY_CONTENT_MODEL, Analytics.FREE);
// } else {
// param.put(Analytics.PROPERTY_CONTENT_MODEL, Analytics.PAID);
// }
        param.put(Analytics.PROPERTY_ACTION, "" + isFavourite);
        Analytics.fireAddTOWatchListEvents(param);
    }

    public boolean checkRelatedMultiMediaAvailable() {
        return mSelectedContent.relatedMultimedia != null && mSelectedContent.relatedMultimedia.values != null &&
                mSelectedContent.relatedMultimedia.values.size() > 0;
    }

    public void itemWatchlistedOrNot() {
        IsSelectedAsaWatchList getIsItSelectedAsFavouriteContent = new IsSelectedAsaWatchList(mSelectedContent._id,
                new APICallback<GetFavouriteContentData>() {
                    @Override
                    public void onResponse(APIResponse<GetFavouriteContentData> response) {
                        if (response.isSuccess()) {
                            if (response.body().getCode() == 200) {
                                try {
                                    Log.d(TAG,"response.body().isWatchlist()"+response.body().watchlist);
                                    if (response.body().watchlist) {
                                        mSelectedContent.isWatchList = true;
                                        initUI();
                                        return;
                                    } else {
                                        mSelectedContent.isWatchList = false;
                                        initUI();
                                        return;
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    initUI();
                                    return;
                                }
                            } else if (response.body().getCode() == 401) {
                                mSelectedContent.isWatchList = false;
                                initUI();
                                return;
                            }
                        }
                        initUI();
                        return;
                    }

                    @Override
                    public void onFailure(Throwable t, int errorCode) {
                        initUI();
                        return;
                    }
                });
        APIService.getInstance().execute(getIsItSelectedAsFavouriteContent);
    }

    public void itemFavoritesOrNot() {
        IsItSelectedAsFavouriteContent getIsItSelectedAsFavouriteContent = new IsItSelectedAsFavouriteContent(mSelectedContent._id,
                new APICallback<GetFavouriteContentData>() {
                    @Override
                    public void onResponse(APIResponse<GetFavouriteContentData> response) {
                        if (response.isSuccess()) {
                            if (response.body().getCode() == 200) {
                                try {
                                    if (response.body().getFavorite()) {
                                        mSelectedContent.isFavourite = true;
//                                        initUI();
                                        return;
                                    } else {
                                        mSelectedContent.isFavourite = false;
//                                        initUI();
                                        return;
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
//                                    initUI();
                                    return;
                                }
                            } else if (response.body().getCode() == 401) {
                                mSelectedContent.isFavourite = false;
//                                initUI();
                                return;
                            }
                        }
//                        initUI();
                        return;
                    }

                    @Override
                    public void onFailure(Throwable t, int errorCode) {
//                        initUI();
                        return;
                    }
                });
        APIService.getInstance().execute(getIsItSelectedAsFavouriteContent);
    }
    */
/*public void onClickOfRecommendation(CardData cardData,View sharedElement){
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedElement,
                ContentDetailsActivityNeww.SHARED_ELEMENT_NAME).toBundle();
//                TODO Launch Details Activity with the card data
        try {
            Intent detailsIntent = ContentDetailsActivityNeww.createIntent(this, cardData, null, AnalyticsConstants.Source.CAROUSEL,"recommendation");
            Utils.fromDetials = true;
            startActivityForResult(detailsIntent, MainActivity.INTENT_REQUEST_TYPE_LOGIN, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*//*

    public void onClickOfRecommendation(CardData cardData, View sharedElement) {
        price_contet = null;
        paymentdailogue = false;
        purchase_content = false;
        if (PrefUtils.getInstance().getDoesTvSupportDolby() && cardData.getShowDolby() != null
                && cardData.getShowDolby().equalsIgnoreCase("true")) {
            dolbyEnabled = true;
            PrefUtils.getInstance().setContentSupportDolby(true);
        } else if (cardData.generalInfo.type.equalsIgnoreCase(Const.LIVE) || cardData.generalInfo.type.equalsIgnoreCase(Const.PROGRAM)) {
            PrefUtils.getInstance().setContentSupportDolby(false);
        } else
            PrefUtils.getInstance().setContentSupportDolby(false);
        PrefUtils.getInstance().setBackPressedFromPlayBackScreen(false);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedElement,
                ContentDetailsActivityNeww.SHARED_ELEMENT_NAME).toBundle();
// TODO Launch Details Activity with the card data
        if (cardData.isVideo() || cardData.isMusicVideo()) {
            launchPlayerActivity(cardData, null, null, false);
        } else {
            try {
                Intent detailsIntent = ContentDetailsActivityNeww.createIntent(this, cardData, null, AnalyticsConstants.Source.CAROUSEL, "recommendation");
                Utils.fromDetials = true;
                startActivityForResult(detailsIntent, MainActivity.INTENT_REQUEST_TYPE_LOGIN, bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadCastReceiver != null) {
            AndroidTVApplication.getLocalBroadcastManager().unregisterReceiver(localBroadCastReceiver);
        }
        displayTabs = null;
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onItemClick(CardData episodelist) {
        launchPlayerActivity(episodelist, null, null, false);
    }

    @Override
    public void onItemClickShowdisplayTabs(String episodelist , int index,int selectedPosition) {
        mEpisodesTabSelectedPosition=selectedPosition;
        getEpisodeListTabs(mSelectedContent , index);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(tandmDetailsRecyclerView !=null){
                    tandmDetailsRecyclerView.requestFocus();
                }
            }
        }, 1200);

    }


    private void showProgressBar() {
        if(loadingProgressLayout != null){
            loadingProgressLayout.setVisibility(VISIBLE);
            manoramamaxFullLogo.setVisibility(View.INVISIBLE);
            if(android.os.Build.VERSION.SDK_INT >= 11){
                // will update the "progress" propriety of seekbar until it reaches progress
                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress" , 70);
                animation.setDuration(1000); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
            }
        }
    }

    private void dismissProgressBar() {
        if(loadingProgressLayout != null){
            loadingProgressLayout.setVisibility(GONE);
            manoramamaxFullLogo.setVisibility(VISIBLE);
        }
    }
}

*/
