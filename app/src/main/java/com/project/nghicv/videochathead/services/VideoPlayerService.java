package com.project.nghicv.videochathead.services;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.project.nghicv.videochathead.R;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;

public class VideoPlayerService extends Service {

    private static final String DEFAULT_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private WindowManager mWindowManager;
    private View mVideoPlayerLayout;
    private RelativeLayout mLayoutHeader;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private SimpleExoPlayer mPlayer;
    private AlertDialog mDialog;

    private WindowManager.LayoutParams mParams;

    private PlaybackControlView.VisibilityListener mVisibilityListener =
            new PlaybackControlView.VisibilityListener() {
                @Override
                public void onVisibilityChange(int visibility) {
                    mLayoutHeader.setVisibility(visibility);
                }
            };

    @Override
    public IBinder onBind(Intent intent) {
        return new VideoPlayerServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        setupLayoutDialog();
        mParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.TOP | Gravity.LEFT;
        mParams.windowAnimations = android.R.style.Animation_Toast;
        mParams.x = 0;
        mParams.y = dpToPx(60);
        mLayoutHeader.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = mParams.x;
                        initialY = mParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        mParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        mParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mVideoPlayerLayout, mParams);
                        return true;
                }
                return false;
            }
        });
        initializePlayer();
        //addVideoToWindowManage();
        hideVideo();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoPlayerLayout != null) mWindowManager.removeView(mVideoPlayerLayout);
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    private void setUpLayout() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        mVideoPlayerLayout = inflater.inflate(R.layout.layout_video_player, null);

        mLayoutHeader = (RelativeLayout) mVideoPlayerLayout.findViewById(R.id.ll_header);
        mSimpleExoPlayerView =
                (SimpleExoPlayerView) mVideoPlayerLayout.findViewById(R.id.simple_exo_player_view);
        mSimpleExoPlayerView.requestFocus();
        mSimpleExoPlayerView.setUseController(true);
        mVideoPlayerLayout.findViewById(R.id.btn_close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVideoPlayerLayout.animate()
                                .alpha(0)
                                .setDuration(500)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        hideVideo();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                    }
                });
        mSimpleExoPlayerView.setControllerVisibilityListener(mVisibilityListener);
    }

    private void initializePlayer() {
        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the mPlayer
        mPlayer = ExoPlayerFactory.newSimpleInstance(getBaseContext(), trackSelector, loadControl);

        mSimpleExoPlayerView.setPlayer(mPlayer);

        mPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG, "Listener-onLoadingChanged...");
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged...");
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG, "Listener-onTimelineChanged...");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups,
                    TrackSelectionArray trackSelections) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                mPlayer.stop();
                mPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG, "Listener-onPositionDiscontinuity...");
            }
        });
    }

    public void setupDatasource(String url) {
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayerexample"),
                        defaultBandwidthMeter);
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        Uri uri = Uri.parse(url);
        MediaSource videoSource =
                new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
      /*  MediaSource videoSource = new HlsMediaSource(uri, dataSourceFactory, 1, null,
                null);*/
        // Prepare the mPlayer with the source.
        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        mPlayer.prepare(loopingSource);
        mPlayer.setPlayWhenReady(true);
        setupLayoutDialog();
    }

    private void addVideoToWindowManage() {
        mWindowManager.addView(mVideoPlayerLayout, mParams);
    }

    public void showVideo() {
        mVideoPlayerLayout.setVisibility(View.VISIBLE);
        mSimpleExoPlayerView.requestFocus();
        mSimpleExoPlayerView.setUseController(true);
    }

    public void hideVideo() {
        mVideoPlayerLayout.setVisibility(View.GONE);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void setupLayoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        mVideoPlayerLayout = inflater.inflate(R.layout.layout_video_player, null);

        mLayoutHeader = (RelativeLayout) mVideoPlayerLayout.findViewById(R.id.ll_header);
        mSimpleExoPlayerView =
                (SimpleExoPlayerView) mVideoPlayerLayout.findViewById(R.id.simple_exo_player_view);
        mSimpleExoPlayerView.requestFocus();
        mSimpleExoPlayerView.setUseController(true);
        mVideoPlayerLayout.findViewById(R.id.btn_close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVideoPlayerLayout.animate()
                                .alpha(0)
                                .setDuration(500)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        stopSelf();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                    }
                });
        mSimpleExoPlayerView.setControllerVisibilityListener(mVisibilityListener);
        builder.setView(mVideoPlayerLayout);
        mDialog = builder.create();
        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().setAttributes(layoutParams);
        /*mDialog.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mDialog.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        //mDialog.show();
    }

    public class VideoPlayerServiceBinder extends Binder {
        public VideoPlayerService getService() {
            return VideoPlayerService.this;
        }
    }
}
