//package com.ott.tv.fragments;
//
//import static android.view.View.GONE;
//import static android.view.View.VISIBLE;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//
//
//
//public class ContentDetailTopFragment extends Fragment {
//
//
//    private TextView mContentTitle;
//    private TextView mContentLanguage;
//    private TextView mContentDescription;
//    private TextView mTextViewCastHeading;
//    private TextView mContentCast;
//    private TextView mReadMore;
//    private TextView mContentReleaseYear;
//    private TextView mContentDuration;
//    private ImageView hdImage, ccImage;
//    private TextView mContentGenre;
//    private View mContentCastContainer;
//    private View mDot1;
//    private View mDot2;
//    private View mDot3;
//    private ImageView mContentImage;
//    private FrameLayout frameLayout;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.content_details_top_fragment, null);
//        initializeViews(view);
//        return view;
//    }
//
//    private void initializeViews(View view) {
//        mContentTitle = (TextView) view.findViewById(R.id.content_detail_title);
//        mContentLanguage = (TextView) view.findViewById(R.id.content_language);
//        mContentReleaseYear = (TextView) view.findViewById(R.id.content_releaseyear);
//        mContentDuration = (TextView) view.findViewById(R.id.content_duration);
//        hdImage = (ImageView) view.findViewById(R.id.hd_image);
//        ccImage = (ImageView) view.findViewById(R.id.cc_image);
//        mContentGenre = (TextView) view.findViewById(R.id.content_genre);
//        mContentDescription = (TextView) view.findViewById(R.id.content_detail_description);
//        mContentCastContainer = view.findViewById(R.id.cast_layout);
//        mTextViewCastHeading = (TextView) view.findViewById(R.id.carddetailbriefdescription_cast_heading);
//        mContentCast = (TextView) view.findViewById(R.id.carddetailbriefdescription_cast);
//        mReadMore = (TextView) view.findViewById(R.id.content_read_more);
//        mContentImage = (ImageView) view.findViewById(R.id.content_image);
//        mDot1 = view.findViewById(R.id.view1);
//        mDot2 = view.findViewById(R.id.view2);
//        mDot3 = view.findViewById(R.id.view3);
//        frameLayout = view.findViewById(R.id.content_details_fragment);
//    }
//
//
//    public void updateCardData(CardData mCardData){
//        onBindViewHolder(mCardData);
//        updateBackGroundImage(mCardData);
//    }
//
//    private void updateBackGroundImage(CardData cardData) {
//        if (!isAdded())
//            return;
//        ViewGroup.LayoutParams layoutParams = mContentImage.getLayoutParams();
////        layoutParams.width = (int) (getActivity().getWindowManager().getDefaultDisplay().getWidth()/1.8);
//        layoutParams.height = (int)(getActivity().getWindowManager().getDefaultDisplay().getHeight()/1.5);
//        mContentImage.setLayoutParams(layoutParams);
//        frameLayout.setVisibility(VISIBLE);
//        mContentImage.setVisibility(VISIBLE);
//        Log.d("tag","cardData.getImageLink(APIConstants.COVERPOSTER)"+cardData.getImageLink(APIConstants.COVERPOSTER ,true));
//        UiUtil.loadImageWithCrossFadeTransition(getActivity(), mContentImage,cardData.getImageLink(APIConstants.COVERPOSTER ,true));
//    }
//
//    public void onBindViewHolder(final CardData mSelectedContent) {
//        if (mContentLanguage != null
//                && mContentGenre != null
//                && mContentDuration != null
//                && mReadMore != null) {
//            mContentLanguage.setVisibility(View.GONE);
//            mContentGenre.setVisibility(View.GONE);
//            mContentDuration.setVisibility(View.GONE);
//            mReadMore.setVisibility(View.GONE);
//            mDot1.setVisibility(GONE);
//            mDot2.setVisibility(GONE);
//            mDot3.setVisibility(GONE);
//            hdImage.setVisibility(GONE);
//            ccImage.setVisibility(GONE);
//        }else {
//            return;
//        }
//        if (mSelectedContent != null) {
//            if (mSelectedContent.getTitle() != null && !TextUtils.isEmpty(mSelectedContent.getTitle()))
//                mContentTitle.setText(mSelectedContent.getTitle());
//
//
//mReadMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                    if (!TextUtils.isEmpty(mSelectedContent.getDescription())) {
//                        mReadMore.setVisibility(View.GONE);
//                    }
//                    dialog.setMessage(mSelectedContent.getDescription());
//                    dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            return;
//                        }
//
//                    });
//                    final AlertDialog alert = dialog.create();
//                    alert.show();
//                    TextView messageView = (TextView) alert.findViewById(android.R.id.message);
//                    messageView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//                    Button button = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//                    ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
//                    margins.setMargins(0, 4, 0, 8);
//                    button.setBackground(getResources().getDrawable(R.color.red_highlight_color));
//                    button.setTextColor(getResources().getColor(R.color.white));
//                    Field f = null;
//                    try {
//                        f = AlertDialog.class.getDeclaredField("mAlert");
//                        f.setAccessible(true);//Very important, this allows the setting to work.
//                        Object alertCotroller = f.get(alert);
//                        f = alertCotroller.getClass().getDeclaredField("mScrollView");
//                        f.setAccessible(true);
//                        ScrollView mScrollView = (ScrollView) f.get(alertCotroller);
//                        mScrollView.getLayoutParams().height = 480;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//
//            mContentDescription.setVisibility(View.GONE);
//            if (!TextUtils.isEmpty(mSelectedContent.getDescription())) {
//                mContentDescription.setText(mSelectedContent.getDescription());
//                mContentDescription.setVisibility(View.VISIBLE);
//mContentDescription.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mReadMore == null) {
//                            return;
//                        }
//                        mReadMore.setVisibility(GONE);
//                        Layout l = mContentDescription.getLayout();
//                        if (l != null) {
//                            int lines = l.getLineCount();
//                            if (lines > 0) {
//                                if (l.getEllipsisCount(lines - 1) > 0) {
//                                    mReadMore.setVisibility(GONE);
//                                }
//                            }
//                        }
//
//                    }
//                });
//
//            }
//
//            if (mSelectedContent.isTVSeries()) {
//                if (!TextUtils.isEmpty(mSelectedContent.getReleaseYear())) {
//                    mContentReleaseYear.setVisibility(VISIBLE);
//                    mContentReleaseYear.setText(mSelectedContent.getReleaseYear());
//                }
//                if (!TextUtils.isEmpty(mSelectedContent.getGenre())) {
//                    mContentLanguage.setVisibility(VISIBLE);
//                    mContentLanguage.setText(mSelectedContent.getGenre());
//                }
//                if (!TextUtils.isEmpty(mSelectedContent.getGenre()) && !TextUtils.isEmpty(mSelectedContent.getReleaseYear())) {
//                    mDot1.setVisibility(VISIBLE);
//                }
//            } else {
//                if (!TextUtils.isEmpty(mSelectedContent.getLanguage())) {
//                    mContentLanguage.setVisibility(View.VISIBLE);
//                    mContentLanguage.setText(mSelectedContent.getLanguage());
//                }
//                if (!TextUtils.isEmpty(mSelectedContent.getReleaseYear())) {
//                    mContentReleaseYear.setVisibility(View.VISIBLE);
//                    mContentReleaseYear.setText(mSelectedContent.getReleaseYear());
//                    mDot1.setVisibility(View.VISIBLE);
//                }
//                if (!TextUtils.isEmpty(mSelectedContent.getGenre())) {
//                    mContentGenre.setVisibility(View.VISIBLE);
//                    mContentGenre.setText(mSelectedContent.getGenre());
//                    mDot2.setVisibility(View.VISIBLE);
//                }
//                if (!TextUtils.isEmpty(mSelectedContent.convertTimeToMinutes())) {
//                    mContentDuration.setVisibility(View.VISIBLE);
//                    mContentDuration.setText(mSelectedContent.convertTimeToMinutes());
//                    mDot3.setVisibility(View.VISIBLE);
//                }
//            }
//            String castMembers = mSelectedContent.getCastMembers();
//            if (TextUtils.isEmpty(castMembers) || mSelectedContent.isTVEpisode()) {
//                mContentCastContainer.setVisibility(View.GONE);
//            } else {
//
//                mContentCastContainer.setVisibility(View.VISIBLE);
//                mTextViewCastHeading.setVisibility(VISIBLE);
//                mContentCast.setText(castMembers);
//            }
//        }
//    }
//}
