/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ott.tv.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;


import androidx.core.app.ActivityOptionsCompat;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.CursorObjectAdapter;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;


import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ott.tv.database.VideoContract;
import com.ott.tv.model.Playlist;
import com.ott.tv.model.VideoCursorMapper;
import com.ott.tv.model.VideoNew;

import com.ott.tv.player.VideoPlayerGlue;

import com.ott.tv.ui.presenter.CardPresenter;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Plays selected video, loads playlist and related videos, and delegates playback to {@link
 * VideoPlayerGlue}.
 */
public class PlaybackFragmentNew extends VideoSupportFragment {

    private static final int UPDATE_DELAY = 16;

    private VideoPlayerGlue mPlayerGlue;
    private LeanbackPlayerAdapter mPlayerAdapter;
    private SimpleExoPlayer mPlayer;
    private TrackSelector mTrackSelector;
    private PlaylistActionListener mPlaylistActionListener;
    private VideoNew mVideo;
    private Playlist mPlaylist;
    private CursorObjectAdapter mVideoCursorAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
         //   mVideo = getActivity().getIntent().getParcelableExtra(VideoDetailsActivity.VIDEO);
            mPlaylist = new Playlist();
            if ((Util.SDK_INT <= 23 || mPlayer == null)) {
               // initializePlayer();
            }
        }
    }

    /**
     * Pauses the player.
     */
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onPause() {
        super.onPause();
        if (mPlayerGlue != null && mPlayerGlue.isPlaying()) {
            mPlayerGlue.pause();
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

  /*  private void initializePlayer() {
        if (getActivity() != null) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), mTrackSelector);
            mPlayerAdapter = new LeanbackPlayerAdapter(getActivity(), mPlayer, UPDATE_DELAY);
            mPlaylistActionListener = new PlaylistActionListener(mPlaylist);
            mPlayerGlue = new VideoPlayerGlue(getActivity(), mPlayerAdapter, mPlaylistActionListener);
            mPlayerGlue.setHost(new VideoSupportFragmentGlueHost(this));
            mPlayerGlue.playWhenPrepared();

            play(mVideo);

          *//*  ArrayObjectAdapter mRowsAdapter = initializeRelatedVideosRow();
            setAdapter(mRowsAdapter);*//*
        }
    }
*/
    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            mTrackSelector = null;
            mPlayerGlue = null;
            mPlayerAdapter = null;
            mPlaylistActionListener = null;
        }
    }

    private void play(VideoNew video) {
        if (video != null) {
            if (video.title != null) {
                mPlayerGlue.setTitle(video.title);
            } else {
                mPlayerGlue.setTitle("title");
            }
            mPlayerGlue.setSeekEnabled(true);
            /*mPlayerGlue.setArt(R.drawable.logo);*/
            if (video.description != null) {
                mPlayerGlue.setSubtitle(video.description);
            } else {
                mPlayerGlue.setSubtitle("description");

            }
            if (video.getCategory() != null && video.getCategory().contains("youtube") && video.videoUrl != null) {
                extractYoutubeUrl(Uri.parse(video.videoUrl), 18);
            } else {
                //prepareMediaForPlaying(Uri.parse(video.videoUrl));
            }
            mPlayerGlue.play();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void extractYoutubeUrl(Uri url, final int tag) {
        if (getContext() != null) {
            new YouTubeExtractor(getContext()) {
                @Override
                public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                    if (ytFiles != null) {
                        int itag = tag;
                        String dashUrl = ytFiles.get(itag).getUrl();
                        try {/*
                            MediaSource source = mediaSource(Uri.parse(dashUrl), getContext());
                            mPlayer.prepare(source, true, false);
                            //player.setPlayWhenReady(false);
                            *//*exoPlayerView.setPlayer(mPlayer);*//*
                            mPlayer.setPlayWhenReady(true);*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.extract(String.valueOf(url), true, true);

        }
    }

  /*  private MediaSource mediaSource(Uri uri, Context context) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }

    private void prepareMediaForPlaying(Uri mediaSourceUri) {
        if (getActivity() != null) {
            String userAgent = Util.getUserAgent(getActivity(), "VideoPlayerGlue");
            MediaSource mediaSource =
                    new ExtractorMediaSource(
                            mediaSourceUri,
                            new DefaultDataSourceFactory(getActivity(), userAgent),
                            new DefaultExtractorsFactory(),
                            null,
                            null);

            mPlayer.prepare(mediaSource);
        }
    }*/

    private ArrayObjectAdapter initializeRelatedVideosRow() {
        /*
         * To add a new row to the mPlayerAdapter and not lose the controls row that is provided by the
         * glue, we need to compose a new row with the controls row and our related videos row.
         *
         * We start by creating a new {@link ClassPresenterSelector}. Then add the controls row from
         * the media player glue, then add the related videos row.
         */
        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(
                mPlayerGlue.getControlsRow().getClass(), mPlayerGlue.getPlaybackRowPresenter());
        presenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(presenterSelector);

        rowsAdapter.add(mPlayerGlue.getControlsRow());

     /*   HeaderItem header = new HeaderItem(getString(R.string.related_movies));
        ListRow row = new ListRow(header, mVideoCursorAdapter);
        rowsAdapter.add(row);*/



        return rowsAdapter;
    }

    private CursorObjectAdapter setupRelatedVideosCursor() {
        CursorObjectAdapter videoCursorAdapter = new CursorObjectAdapter(new CardPresenter());
        videoCursorAdapter.setMapper(new VideoCursorMapper());

        Bundle args = new Bundle();
        args.putString(VideoContract.VideoEntry.COLUMN_CATEGORY, mVideo.category);
        return videoCursorAdapter;
    }

    public void skipToNext() {
        mPlayerGlue.next();
    }

    public void skipToPrevious() {
        mPlayerGlue.previous();
    }

    public void rewind() {
        mPlayerGlue.rewind();
    }

    public void fastForward() {
        mPlayerGlue.fastForward();
    }

    /**
     * Opens the video details page when a related video has been clicked.
     */

    /**
     * Loads a playlist with videos from a cursor and also updates the related videos cursor.
     */

    class PlaylistActionListener implements VideoPlayerGlue.OnActionClickedListener {

        private final Playlist mPlaylist;

        PlaylistActionListener(Playlist playlist) {
            this.mPlaylist = playlist;
        }

        @Override
        public void onPrevious() {
            play(mPlaylist.previous());
        }

        @Override
        public void onNext() {
            play(mPlaylist.next());
        }
    }
}
