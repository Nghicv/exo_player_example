package com.project.nghicv.videochathead;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.multidex.MultiDex;
import com.crashlytics.android.Crashlytics;
import com.project.nghicv.videochathead.common.Logger;
import com.project.nghicv.videochathead.services.VideoPlayerService;
import io.fabric.sdk.android.Fabric;

public class VideoPlayerApp extends Application {

    private static VideoPlayerApp mInstance;
    private VideoPlayerService mVideoPlayerService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.debug(this, "On connect to video service");
            VideoPlayerService.VideoPlayerServiceBinder binder =
                    (VideoPlayerService.VideoPlayerServiceBinder) service;
            mVideoPlayerService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.debug(this, "On disconnect to service");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        bindVideoService();
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static VideoPlayerApp getInstance() {
        return mInstance;
    }

    public void bindVideoService() {
        bindService(new Intent(this, VideoPlayerService.class), mServiceConnection, Context
                .BIND_AUTO_CREATE);
    }

    public VideoPlayerService getVideoPlayerService() {
        return mVideoPlayerService;
    }

    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
