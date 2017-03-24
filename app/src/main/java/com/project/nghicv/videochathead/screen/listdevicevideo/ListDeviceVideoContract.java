package com.project.nghicv.videochathead.screen.listdevicevideo;

import com.project.nghicv.videochathead.base.BasePresenter;
import com.project.nghicv.videochathead.base.BaseView;
import com.project.nghicv.videochathead.model.Video;
import java.util.List;

public interface ListDeviceVideoContract {
    interface Presenter extends BasePresenter {
        void loadVideos();
        void playVideo(Video video);
    }

    interface  View extends BaseView<ListDeviceVideoContract.Presenter> {
        void showVideos(List<Video> videos);
        void showEmptyView();
        void showVideoPlay(Video video);
    }
}
